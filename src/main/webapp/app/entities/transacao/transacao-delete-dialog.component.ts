import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransacao } from 'app/shared/model/transacao.model';
import { TransacaoService } from './transacao.service';

@Component({
  templateUrl: './transacao-delete-dialog.component.html',
})
export class TransacaoDeleteDialogComponent {
  transacao?: ITransacao;

  constructor(protected transacaoService: TransacaoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transacaoListModification');
      this.activeModal.close();
    });
  }
}
