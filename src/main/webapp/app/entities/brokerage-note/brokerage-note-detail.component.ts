import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBrokerageNote } from 'app/shared/model/brokerage-note.model';

@Component({
  selector: 'jhi-brokerage-note-detail',
  templateUrl: './brokerage-note-detail.component.html',
})
export class BrokerageNoteDetailComponent implements OnInit {
  brokerageNote: IBrokerageNote | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ brokerageNote }) => (this.brokerageNote = brokerageNote));
  }

  previousState(): void {
    window.history.back();
  }
}
