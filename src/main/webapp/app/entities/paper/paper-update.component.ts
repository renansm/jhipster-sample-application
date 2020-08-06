import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaper, Paper } from 'app/shared/model/paper.model';
import { PaperService } from './paper.service';

@Component({
  selector: 'jhi-paper-update',
  templateUrl: './paper-update.component.html',
})
export class PaperUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    marketValue: [],
  });

  constructor(protected paperService: PaperService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paper }) => {
      this.updateForm(paper);
    });
  }

  updateForm(paper: IPaper): void {
    this.editForm.patchValue({
      id: paper.id,
      code: paper.code,
      name: paper.name,
      marketValue: paper.marketValue,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paper = this.createFromForm();
    if (paper.id !== undefined) {
      this.subscribeToSaveResponse(this.paperService.update(paper));
    } else {
      this.subscribeToSaveResponse(this.paperService.create(paper));
    }
  }

  private createFromForm(): IPaper {
    return {
      ...new Paper(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      marketValue: this.editForm.get(['marketValue'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaper>>): void {
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
