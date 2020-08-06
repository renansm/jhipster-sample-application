import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPosicao, Posicao } from 'app/shared/model/posicao.model';
import { PosicaoService } from './posicao.service';
import { PosicaoComponent } from './posicao.component';
import { PosicaoDetailComponent } from './posicao-detail.component';
import { PosicaoUpdateComponent } from './posicao-update.component';

@Injectable({ providedIn: 'root' })
export class PosicaoResolve implements Resolve<IPosicao> {
  constructor(private service: PosicaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPosicao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((posicao: HttpResponse<Posicao>) => {
          if (posicao.body) {
            return of(posicao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Posicao());
  }
}

export const posicaoRoute: Routes = [
  {
    path: '',
    component: PosicaoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Posicaos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PosicaoDetailComponent,
    resolve: {
      posicao: PosicaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Posicaos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PosicaoUpdateComponent,
    resolve: {
      posicao: PosicaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Posicaos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PosicaoUpdateComponent,
    resolve: {
      posicao: PosicaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Posicaos',
    },
    canActivate: [UserRouteAccessService],
  },
];
