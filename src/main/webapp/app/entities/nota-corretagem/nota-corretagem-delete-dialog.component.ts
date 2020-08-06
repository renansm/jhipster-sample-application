import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotaCorretagem } from 'app/shared/model/nota-corretagem.model';
import { NotaCorretagemService } from './nota-corretagem.service';

@Component({
  templateUrl: './nota-corretagem-delete-dialog.component.html',
})
export class NotaCorretagemDeleteDialogComponent {
  notaCorretagem?: INotaCorretagem;

  constructor(
    protected notaCorretagemService: NotaCorretagemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notaCorretagemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('notaCorretagemListModification');
      this.activeModal.close();
    });
  }
}
