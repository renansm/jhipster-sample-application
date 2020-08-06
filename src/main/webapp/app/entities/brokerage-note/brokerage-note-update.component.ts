import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBrokerageNote, BrokerageNote } from 'app/shared/model/brokerage-note.model';
import { BrokerageNoteService } from './brokerage-note.service';
import { IBroker } from 'app/shared/model/broker.model';
import { BrokerService } from 'app/entities/broker/broker.service';

@Component({
  selector: 'jhi-brokerage-note-update',
  templateUrl: './brokerage-note-update.component.html',
})
export class BrokerageNoteUpdateComponent implements OnInit {
  isSaving = false;
  brokers: IBroker[] = [];

  editForm = this.fb.group({
    id: [],
    number: [],
    emoluments: [],
    liquidation: [],
    otherTaxes: [],
    value: [],
    broker: [],
  });

  constructor(
    protected brokerageNoteService: BrokerageNoteService,
    protected brokerService: BrokerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ brokerageNote }) => {
      this.updateForm(brokerageNote);

      this.brokerService.query().subscribe((res: HttpResponse<IBroker[]>) => (this.brokers = res.body || []));
    });
  }

  updateForm(brokerageNote: IBrokerageNote): void {
    this.editForm.patchValue({
      id: brokerageNote.id,
      number: brokerageNote.number,
      emoluments: brokerageNote.emoluments,
      liquidation: brokerageNote.liquidation,
      otherTaxes: brokerageNote.otherTaxes,
      value: brokerageNote.value,
      broker: brokerageNote.broker,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const brokerageNote = this.createFromForm();
    if (brokerageNote.id !== undefined) {
      this.subscribeToSaveResponse(this.brokerageNoteService.update(brokerageNote));
    } else {
      this.subscribeToSaveResponse(this.brokerageNoteService.create(brokerageNote));
    }
  }

  private createFromForm(): IBrokerageNote {
    return {
      ...new BrokerageNote(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      emoluments: this.editForm.get(['emoluments'])!.value,
      liquidation: this.editForm.get(['liquidation'])!.value,
      otherTaxes: this.editForm.get(['otherTaxes'])!.value,
      value: this.editForm.get(['value'])!.value,
      broker: this.editForm.get(['broker'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBrokerageNote>>): void {
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

  trackById(index: number, item: IBroker): any {
    return item.id;
  }
}
