import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBroker } from 'app/shared/model/broker.model';

@Component({
  selector: 'jhi-broker-detail',
  templateUrl: './broker-detail.component.html',
})
export class BrokerDetailComponent implements OnInit {
  broker: IBroker | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ broker }) => (this.broker = broker));
  }

  previousState(): void {
    window.history.back();
  }
}
