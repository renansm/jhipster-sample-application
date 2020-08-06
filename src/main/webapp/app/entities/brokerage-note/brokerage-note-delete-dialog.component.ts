import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBrokerageNote } from 'app/shared/model/brokerage-note.model';
import { BrokerageNoteService } from './brokerage-note.service';

@Component({
  templateUrl: './brokerage-note-delete-dialog.component.html',
})
export class BrokerageNoteDeleteDialogComponent {
  brokerageNote?: IBrokerageNote;

  constructor(
    protected brokerageNoteService: BrokerageNoteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.brokerageNoteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('brokerageNoteListModification');
      this.activeModal.close();
    });
  }
}
