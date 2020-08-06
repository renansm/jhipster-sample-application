import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPapel, Papel } from 'app/shared/model/papel.model';
import { PapelService } from './papel.service';
import { PapelComponent } from './papel.component';
import { PapelDetailComponent } from './papel-detail.component';
import { PapelUpdateComponent } from './papel-update.component';

@Injectable({ providedIn: 'root' })
export class PapelResolve implements Resolve<IPapel> {
  constructor(private service: PapelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPapel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((papel: HttpResponse<Papel>) => {
          if (papel.body) {
            return of(papel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Papel());
  }
}

export const papelRoute: Routes = [
  {
    path: '',
    component: PapelComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Papels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PapelDetailComponent,
    resolve: {
      papel: PapelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Papels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PapelUpdateComponent,
    resolve: {
      papel: PapelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Papels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PapelUpdateComponent,
    resolve: {
      papel: PapelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Papels',
    },
    canActivate: [UserRouteAccessService],
  },
];
