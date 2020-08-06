import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPapel } from 'app/shared/model/papel.model';
import { PapelService } from './papel.service';
import { PapelDeleteDialogComponent } from './papel-delete-dialog.component';

@Component({
  selector: 'jhi-papel',
  templateUrl: './papel.component.html',
})
export class PapelComponent implements OnInit, OnDestroy {
  papels?: IPapel[];
  eventSubscriber?: Subscription;

  constructor(protected papelService: PapelService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.papelService.query().subscribe((res: HttpResponse<IPapel[]>) => (this.papels = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPapels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPapel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPapels(): void {
    this.eventSubscriber = this.eventManager.subscribe('papelListModification', () => this.loadAll());
  }

  delete(papel: IPapel): void {
    const modalRef = this.modalService.open(PapelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.papel = papel;
  }
}
