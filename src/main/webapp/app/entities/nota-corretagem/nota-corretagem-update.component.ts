import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INotaCorretagem, NotaCorretagem } from 'app/shared/model/nota-corretagem.model';
import { NotaCorretagemService } from './nota-corretagem.service';
import { ICorretora } from 'app/shared/model/corretora.model';
import { CorretoraService } from 'app/entities/corretora/corretora.service';

@Component({
  selector: 'jhi-nota-corretagem-update',
  templateUrl: './nota-corretagem-update.component.html',
})
export class NotaCorretagemUpdateComponent implements OnInit {
  isSaving = false;
  corretoras: ICorretora[] = [];

  editForm = this.fb.group({
    id: [],
    numero: [],
    emolumentos: [],
    liquidacao: [],
    outrasTaxas: [],
    valor: [],
    corretora: [],
  });

  constructor(
    protected notaCorretagemService: NotaCorretagemService,
    protected corretoraService: CorretoraService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notaCorretagem }) => {
      this.updateForm(notaCorretagem);

      this.corretoraService.query().subscribe((res: HttpResponse<ICorretora[]>) => (this.corretoras = res.body || []));
    });
  }

  updateForm(notaCorretagem: INotaCorretagem): void {
    this.editForm.patchValue({
      id: notaCorretagem.id,
      numero: notaCorretagem.numero,
      emolumentos: notaCorretagem.emolumentos,
      liquidacao: notaCorretagem.liquidacao,
      outrasTaxas: notaCorretagem.outrasTaxas,
      valor: notaCorretagem.valor,
      corretora: notaCorretagem.corretora,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notaCorretagem = this.createFromForm();
    if (notaCorretagem.id !== undefined) {
      this.subscribeToSaveResponse(this.notaCorretagemService.update(notaCorretagem));
    } else {
      this.subscribeToSaveResponse(this.notaCorretagemService.create(notaCorretagem));
    }
  }

  private createFromForm(): INotaCorretagem {
    return {
      ...new NotaCorretagem(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      emolumentos: this.editForm.get(['emolumentos'])!.value,
      liquidacao: this.editForm.get(['liquidacao'])!.value,
      outrasTaxas: this.editForm.get(['outrasTaxas'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      corretora: this.editForm.get(['corretora'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotaCorretagem>>): void {
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

  trackById(index: number, item: ICorretora): any {
    return item.id;
  }
}
