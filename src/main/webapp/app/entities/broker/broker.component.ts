import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBroker } from 'app/shared/model/broker.model';
import { BrokerService } from './broker.service';
import { BrokerDeleteDialogComponent } from './broker-delete-dialog.component';

@Component({
  selector: 'jhi-broker',
  templateUrl: './broker.component.html',
})
export class BrokerComponent implements OnInit, OnDestroy {
  brokers?: IBroker[];
  eventSubscriber?: Subscription;

  constructor(protected brokerService: BrokerService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.brokerService.query().subscribe((res: HttpResponse<IBroker[]>) => (this.brokers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBrokers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBroker): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBrokers(): void {
    this.eventSubscriber = this.eventManager.subscribe('brokerListModification', () => this.loadAll());
  }

  delete(broker: IBroker): void {
    const modalRef = this.modalService.open(BrokerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.broker = broker;
  }
}
