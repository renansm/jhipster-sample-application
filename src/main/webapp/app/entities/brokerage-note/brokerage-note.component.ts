import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBrokerageNote } from 'app/shared/model/brokerage-note.model';
import { BrokerageNoteService } from './brokerage-note.service';
import { BrokerageNoteDeleteDialogComponent } from './brokerage-note-delete-dialog.component';

@Component({
  selector: 'jhi-brokerage-note',
  templateUrl: './brokerage-note.component.html',
})
export class BrokerageNoteComponent implements OnInit, OnDestroy {
  brokerageNotes?: IBrokerageNote[];
  eventSubscriber?: Subscription;

  constructor(
    protected brokerageNoteService: BrokerageNoteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.brokerageNoteService.query().subscribe((res: HttpResponse<IBrokerageNote[]>) => (this.brokerageNotes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBrokerageNotes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBrokerageNote): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBrokerageNotes(): void {
    this.eventSubscriber = this.eventManager.subscribe('brokerageNoteListModification', () => this.loadAll());
  }

  delete(brokerageNote: IBrokerageNote): void {
    const modalRef = this.modalService.open(BrokerageNoteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.brokerageNote = brokerageNote;
  }
}
