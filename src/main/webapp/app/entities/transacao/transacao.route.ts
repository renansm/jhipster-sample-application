import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransacao, Transacao } from 'app/shared/model/transacao.model';
import { TransacaoService } from './transacao.service';
import { TransacaoComponent } from './transacao.component';
import { TransacaoDetailComponent } from './transacao-detail.component';
import { TransacaoUpdateComponent } from './transacao-update.component';

@Injectable({ providedIn: 'root' })
export class TransacaoResolve implements Resolve<ITransacao> {
  constructor(private service: TransacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transacao: HttpResponse<Transacao>) => {
          if (transacao.body) {
            return of(transacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Transacao());
  }
}

export const transacaoRoute: Routes = [
  {
    path: '',
    component: TransacaoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Transacaos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransacaoDetailComponent,
    resolve: {
      transacao: TransacaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Transacaos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransacaoUpdateComponent,
    resolve: {
      transacao: TransacaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Transacaos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransacaoUpdateComponent,
    resolve: {
      transacao: TransacaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Transacaos',
    },
    canActivate: [UserRouteAccessService],
  },
];
