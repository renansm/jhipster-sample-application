import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBroker, Broker } from 'app/shared/model/broker.model';
import { BrokerService } from './broker.service';

@Component({
  selector: 'jhi-broker-update',
  templateUrl: './broker-update.component.html',
})
export class BrokerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected brokerService: BrokerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ broker }) => {
      this.updateForm(broker);
    });
  }

  updateForm(broker: IBroker): void {
    this.editForm.patchValue({
      id: broker.id,
      name: broker.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const broker = this.createFromForm();
    if (broker.id !== undefined) {
      this.subscribeToSaveResponse(this.brokerService.update(broker));
    } else {
      this.subscribeToSaveResponse(this.brokerService.create(broker));
    }
  }

  private createFromForm(): IBroker {
    return {
      ...new Broker(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBroker>>): void {
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
