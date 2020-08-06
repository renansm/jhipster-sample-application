import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPapel, Papel } from 'app/shared/model/papel.model';
import { PapelService } from './papel.service';

@Component({
  selector: 'jhi-papel-update',
  templateUrl: './papel-update.component.html',
})
export class PapelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [],
    nome: [],
    precoMercado: [],
  });

  constructor(protected papelService: PapelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ papel }) => {
      this.updateForm(papel);
    });
  }

  updateForm(papel: IPapel): void {
    this.editForm.patchValue({
      id: papel.id,
      codigo: papel.codigo,
      nome: papel.nome,
      precoMercado: papel.precoMercado,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const papel = this.createFromForm();
    if (papel.id !== undefined) {
      this.subscribeToSaveResponse(this.papelService.update(papel));
    } else {
      this.subscribeToSaveResponse(this.papelService.create(papel));
    }
  }

  private createFromForm(): IPapel {
    return {
      ...new Papel(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      precoMercado: this.editForm.get(['precoMercado'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPapel>>): void {
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
}
