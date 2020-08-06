import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaper } from 'app/shared/model/paper.model';
import { PaperService } from './paper.service';

@Component({
  templateUrl: './paper-delete-dialog.component.html',
})
export class PaperDeleteDialogComponent {
  paper?: IPaper;

  constructor(protected paperService: PaperService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paperService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paperListModification');
      this.activeModal.close();
    });
  }
}
