import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPosicao } from 'app/shared/model/posicao.model';
import { PosicaoService } from './posicao.service';
import { PosicaoDeleteDialogComponent } from './posicao-delete-dialog.component';

@Component({
  selector: 'jhi-posicao',
  templateUrl: './posicao.component.html',
})
export class PosicaoComponent implements OnInit, OnDestroy {
  posicaos?: IPosicao[];
  eventSubscriber?: Subscription;

  constructor(protected posicaoService: PosicaoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.posicaoService.query().subscribe((res: HttpResponse<IPosicao[]>) => (this.posicaos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPosicaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPosicao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPosicaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('posicaoListModification', () => this.loadAll());
  }

  delete(posicao: IPosicao): void {
    const modalRef = this.modalService.open(PosicaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.posicao = posicao;
  }
}
