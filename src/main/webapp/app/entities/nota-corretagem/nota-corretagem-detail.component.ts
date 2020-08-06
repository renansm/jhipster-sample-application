import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotaCorretagem } from 'app/shared/model/nota-corretagem.model';

@Component({
  selector: 'jhi-nota-corretagem-detail',
  templateUrl: './nota-corretagem-detail.component.html',
})
export class NotaCorretagemDetailComponent implements OnInit {
  notaCorretagem: INotaCorretagem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notaCorretagem }) => (this.notaCorretagem = notaCorretagem));
  }

  previousState(): void {
    window.history.back();
  }
}
