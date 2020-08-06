import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICorretora } from 'app/shared/model/corretora.model';

@Component({
  selector: 'jhi-corretora-detail',
  templateUrl: './corretora-detail.component.html',
})
export class CorretoraDetailComponent implements OnInit {
  corretora: ICorretora | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ corretora }) => (this.corretora = corretora));
  }

  previousState(): void {
    window.history.back();
  }
}
