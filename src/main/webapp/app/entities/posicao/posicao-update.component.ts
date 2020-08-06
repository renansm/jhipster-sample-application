import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPosicao, Posicao } from 'app/shared/model/posicao.model';
import { PosicaoService } from './posicao.service';
import { IPapel } from 'app/shared/model/papel.model';
import { PapelService } from 'app/entities/papel/papel.service';

@Component({
  selector: 'jhi-posicao-update',
  templateUrl: './posicao-update.component.html',
})
export class PosicaoUpdateComponent implements OnInit {
  isSaving = false;
  papels: IPapel[] = [];

  editForm = this.fb.group({
    id: [],
    quantidade: [],
    papel: [],
  });

  constructor(
    protected posicaoService: PosicaoService,
    protected papelService: PapelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ posicao }) => {
      this.updateForm(posicao);

      this.papelService.query().subscribe((res: HttpResponse<IPapel[]>) => (this.papels = res.body || []));
    });
  }

  updateForm(posicao: IPosicao): void {
    this.editForm.patchValue({
      id: posicao.id,
      quantidade: posicao.quantidade,
      papel: posicao.papel,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const posicao = this.createFromForm();
    if (posicao.id !== undefined) {
      this.subscribeToSaveResponse(this.posicaoService.update(posicao));
    } else {
      this.subscribeToSaveResponse(this.posicaoService.create(posicao));
    }
  }

  private createFromForm(): IPosicao {
    return {
      ...new Posicao(),
      id: this.editForm.get(['id'])!.value,
      quantidade: this.editForm.get(['quantidade'])!.value,
      papel: this.editForm.get(['papel'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPosicao>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPapel): any {
    return item.id;
  }
}
