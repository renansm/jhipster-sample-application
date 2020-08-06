import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPapel } from 'app/shared/model/papel.model';
import { PapelService } from './papel.service';

@Component({
  templateUrl: './papel-delete-dialog.component.html',
})
export class PapelDeleteDialogComponent {
  papel?: IPapel;

  constructor(protected papelService: PapelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.papelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('papelListModification');
      this.activeModal.close();
    });
  }
}
