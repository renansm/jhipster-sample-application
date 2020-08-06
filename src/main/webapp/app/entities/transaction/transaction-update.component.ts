import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { IPaper } from 'app/shared/model/paper.model';
import { PaperService } from 'app/entities/paper/paper.service';
import { IBrokerageNote } from 'app/shared/model/brokerage-note.model';
import { BrokerageNoteService } from 'app/entities/brokerage-note/brokerage-note.service';

type SelectableEntity = IPaper | IBrokerageNote;

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html',
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;
  papers: IPaper[] = [];
  brokeragenotes: IBrokerageNote[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    quantity: [],
    value: [],
    date: [],
    type: [],
    paper: [],
    brokerageNote: [],
  });

  constructor(
    protected transactionService: TransactionService,
    protected paperService: PaperService,
    protected brokerageNoteService: BrokerageNoteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);

      this.paperService.query().subscribe((res: HttpResponse<IPaper[]>) => (this.papers = res.body || []));

      this.brokerageNoteService.query().subscribe((res: HttpResponse<IBrokerageNote[]>) => (this.brokeragenotes = res.body || []));
    });
  }

  updateForm(transaction: ITransaction): void {
    this.editForm.patchValue({
      id: transaction.id,
      quantity: transaction.quantity,
      value: transaction.value,
      date: transaction.date,
      type: transaction.type,
      paper: transaction.paper,
      brokerageNote: transaction.brokerageNote,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      value: this.editForm.get(['value'])!.value,
      date: this.editForm.get(['date'])!.value,
      type: this.editForm.get(['type'])!.value,
      paper: this.editForm.get(['paper'])!.value,
      brokerageNote: this.editForm.get(['brokerageNote'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
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
