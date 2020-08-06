import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBroker } from 'app/shared/model/broker.model';
import { BrokerService } from './broker.service';

@Component({
  templateUrl: './broker-delete-dialog.component.html',
})
export class BrokerDeleteDialogComponent {
  broker?: IBroker;

  constructor(protected brokerService: BrokerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.brokerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('brokerListModification');
      this.activeModal.close();
    });
  }
}
