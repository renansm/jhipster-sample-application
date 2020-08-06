import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICorretora } from 'app/shared/model/corretora.model';
import { CorretoraService } from './corretora.service';

@Component({
  templateUrl: './corretora-delete-dialog.component.html',
})
export class CorretoraDeleteDialogComponent {
  corretora?: ICorretora;

  constructor(protected corretoraService: CorretoraService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.corretoraService.delete(id).subscribe(() => {
      this.eventManager.broadcast('corretoraListModification');
      this.activeModal.close();
    });
  }
}
