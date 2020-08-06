import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaper } from 'app/shared/model/paper.model';
import { PaperService } from './paper.service';
import { PaperDeleteDialogComponent } from './paper-delete-dialog.component';

@Component({
  selector: 'jhi-paper',
  templateUrl: './paper.component.html',
})
export class PaperComponent implements OnInit, OnDestroy {
  papers?: IPaper[];
  eventSubscriber?: Subscription;

  constructor(protected paperService: PaperService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.paperService.query().subscribe((res: HttpResponse<IPaper[]>) => (this.papers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPapers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPaper): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPapers(): void {
    this.eventSubscriber = this.eventManager.subscribe('paperListModification', () => this.loadAll());
  }

  delete(paper: IPaper): void {
    const modalRef = this.modalService.open(PaperDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paper = paper;
  }
}
