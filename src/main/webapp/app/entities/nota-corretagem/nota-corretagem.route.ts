import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INotaCorretagem, NotaCorretagem } from 'app/shared/model/nota-corretagem.model';
import { NotaCorretagemService } from './nota-corretagem.service';
import { NotaCorretagemComponent } from './nota-corretagem.component';
import { NotaCorretagemDetailComponent } from './nota-corretagem-detail.component';
import { NotaCorretagemUpdateComponent } from './nota-corretagem-update.component';

@Injectable({ providedIn: 'root' })
export class NotaCorretagemResolve implements Resolve<INotaCorretagem> {
  constructor(private service: NotaCorretagemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotaCorretagem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((notaCorretagem: HttpResponse<NotaCorretagem>) => {
          if (notaCorretagem.body) {
            return of(notaCorretagem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NotaCorretagem());
  }
}

export const notaCorretagemRoute: Routes = [
  {
    path: '',
    component: NotaCorretagemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NotaCorretagems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NotaCorretagemDetailComponent,
    resolve: {
      notaCorretagem: NotaCorretagemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NotaCorretagems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotaCorretagemUpdateComponent,
    resolve: {
      notaCorretagem: NotaCorretagemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NotaCorretagems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotaCorretagemUpdateComponent,
    resolve: {
      notaCorretagem: NotaCorretagemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NotaCorretagems',
    },
    canActivate: [UserRouteAccessService],
  },
];
