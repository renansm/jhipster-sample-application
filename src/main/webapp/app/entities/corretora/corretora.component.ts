import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICorretora } from 'app/shared/model/corretora.model';
import { CorretoraService } from './corretora.service';
import { CorretoraDeleteDialogComponent } from './corretora-delete-dialog.component';

@Component({
  selector: 'jhi-corretora',
  templateUrl: './corretora.component.html',
})
export class CorretoraComponent implements OnInit, OnDestroy {
  corretoras?: ICorretora[];
  eventSubscriber?: Subscription;

  constructor(protected corretoraService: CorretoraService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.corretoraService.query().subscribe((res: HttpResponse<ICorretora[]>) => (this.corretoras = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCorretoras();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICorretora): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCorretoras(): void {
    this.eventSubscriber = this.eventManager.subscribe('corretoraListModification', () => this.loadAll());
  }

  delete(corretora: ICorretora): void {
    const modalRef = this.modalService.open(CorretoraDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.corretora = corretora;
  }
}
