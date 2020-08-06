import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICorretora, Corretora } from 'app/shared/model/corretora.model';
import { CorretoraService } from './corretora.service';

@Component({
  selector: 'jhi-corretora-update',
  templateUrl: './corretora-update.component.html',
})
export class CorretoraUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [],
  });

  constructor(protected corretoraService: CorretoraService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ corretora }) => {
      this.updateForm(corretora);
    });
  }

  updateForm(corretora: ICorretora): void {
    this.editForm.patchValue({
      id: corretora.id,
      nome: corretora.nome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const corretora = this.createFromForm();
    if (corretora.id !== undefined) {
      this.subscribeToSaveResponse(this.corretoraService.update(corretora));
    } else {
      this.subscribeToSaveResponse(this.corretoraService.create(corretora));
    }
  }

  private createFromForm(): ICorretora {
    return {
      ...new Corretora(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICorretora>>): void {
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
