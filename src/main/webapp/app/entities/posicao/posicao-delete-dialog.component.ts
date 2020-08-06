import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPosicao } from 'app/shared/model/posicao.model';
import { PosicaoService } from './posicao.service';

@Component({
  templateUrl: './posicao-delete-dialog.component.html',
})
export class PosicaoDeleteDialogComponent {
  posicao?: IPosicao;

  constructor(protected posicaoService: PosicaoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.posicaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('posicaoListModification');
      this.activeModal.close();
    });
  }
}
