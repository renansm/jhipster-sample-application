import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPosicao } from 'app/shared/model/posicao.model';

@Component({
  selector: 'jhi-posicao-detail',
  templateUrl: './posicao-detail.component.html',
})
export class PosicaoDetailComponent implements OnInit {
  posicao: IPosicao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ posicao }) => (this.posicao = posicao));
  }

  previousState(): void {
    window.history.back();
  }
}
