import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransacao } from 'app/shared/model/transacao.model';

@Component({
  selector: 'jhi-transacao-detail',
  templateUrl: './transacao-detail.component.html',
})
export class TransacaoDetailComponent implements OnInit {
  transacao: ITransacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transacao }) => (this.transacao = transacao));
  }

  previousState(): void {
    window.history.back();
  }
}
