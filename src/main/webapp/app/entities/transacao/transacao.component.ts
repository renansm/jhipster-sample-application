import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransacao } from 'app/shared/model/transacao.model';
import { TransacaoService } from './transacao.service';
import { TransacaoDeleteDialogComponent } from './transacao-delete-dialog.component';

@Component({
  selector: 'jhi-transacao',
  templateUrl: './transacao.component.html',
})
export class TransacaoComponent implements OnInit, OnDestroy {
  transacaos?: ITransacao[];
  eventSubscriber?: Subscription;

  constructor(protected transacaoService: TransacaoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.transacaoService.query().subscribe((res: HttpResponse<ITransacao[]>) => (this.transacaos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransacaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransacao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransacaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('transacaoListModification', () => this.loadAll());
  }

  delete(transacao: ITransacao): void {
    const modalRef = this.modalService.open(TransacaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transacao = transacao;
  }
}
