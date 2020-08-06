import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaper } from 'app/shared/model/paper.model';

@Component({
  selector: 'jhi-paper-detail',
  templateUrl: './paper-detail.component.html',
})
export class PaperDetailComponent implements OnInit {
  paper: IPaper | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paper }) => (this.paper = paper));
  }

  previousState(): void {
    window.history.back();
  }
}
