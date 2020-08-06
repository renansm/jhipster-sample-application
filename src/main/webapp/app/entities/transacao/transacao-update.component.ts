import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransacao, Transacao } from 'app/shared/model/transacao.model';
import { TransacaoService } from './transacao.service';
import { IPapel } from 'app/shared/model/papel.model';
import { PapelService } from 'app/entities/papel/papel.service';
import { INotaCorretagem } from 'app/shared/model/nota-corretagem.model';
import { NotaCorretagemService } from 'app/entities/nota-corretagem/nota-corretagem.service';

type SelectableEntity = IPapel | INotaCorretagem;

@Component({
  selector: 'jhi-transacao-update',
  templateUrl: './transacao-update.component.html',
})
export class TransacaoUpdateComponent implements OnInit {
  isSaving = false;
  papels: IPapel[] = [];
  notacorretagems: INotaCorretagem[] = [];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    quantidade: [],
    valor: [],
    data: [],
    tipo: [],
    papel: [],
    notaCorretagem: [],
  });

  constructor(
    protected transacaoService: TransacaoService,
    protected papelService: PapelService,
    protected notaCorretagemService: NotaCorretagemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transacao }) => {
      this.updateForm(transacao);

      this.papelService.query().subscribe((res: HttpResponse<IPapel[]>) => (this.papels = res.body || []));

      this.notaCorretagemService.query().subscribe((res: HttpResponse<INotaCorretagem[]>) => (this.notacorretagems = res.body || []));
    });
  }

  updateForm(transacao: ITransacao): void {
    this.editForm.patchValue({
      id: transacao.id,
      quantidade: transacao.quantidade,
      valor: transacao.valor,
      data: transacao.data,
      tipo: transacao.tipo,
      papel: transacao.papel,
      notaCorretagem: transacao.notaCorretagem,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transacao = this.createFromForm();
    if (transacao.id !== undefined) {
      this.subscribeToSaveResponse(this.transacaoService.update(transacao));
    } else {
      this.subscribeToSaveResponse(this.transacaoService.create(transacao));
    }
  }

  private createFromForm(): ITransacao {
    return {
      ...new Transacao(),
      id: this.editForm.get(['id'])!.value,
      quantidade: this.editForm.get(['quantidade'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      data: this.editForm.get(['data'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      papel: this.editForm.get(['papel'])!.value,
      notaCorretagem: this.editForm.get(['notaCorretagem'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransacao>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
