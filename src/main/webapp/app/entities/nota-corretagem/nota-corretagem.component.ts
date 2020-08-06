import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INotaCorretagem } from 'app/shared/model/nota-corretagem.model';
import { NotaCorretagemService } from './nota-corretagem.service';
import { NotaCorretagemDeleteDialogComponent } from './nota-corretagem-delete-dialog.component';

@Component({
  selector: 'jhi-nota-corretagem',
  templateUrl: './nota-corretagem.component.html',
})
export class NotaCorretagemComponent implements OnInit, OnDestroy {
  notaCorretagems?: INotaCorretagem[];
  eventSubscriber?: Subscription;

  constructor(
    protected notaCorretagemService: NotaCorretagemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.notaCorretagemService.query().subscribe((res: HttpResponse<INotaCorretagem[]>) => (this.notaCorretagems = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNotaCorretagems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INotaCorretagem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNotaCorretagems(): void {
    this.eventSubscriber = this.eventManager.subscribe('notaCorretagemListModification', () => this.loadAll());
  }

  delete(notaCorretagem: INotaCorretagem): void {
    const modalRef = this.modalService.open(NotaCorretagemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.notaCorretagem = notaCorretagem;
  }
}
