import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICorretora, Corretora } from 'app/shared/model/corretora.model';
import { CorretoraService } from './corretora.service';
import { CorretoraComponent } from './corretora.component';
import { CorretoraDetailComponent } from './corretora-detail.component';
import { CorretoraUpdateComponent } from './corretora-update.component';

@Injectable({ providedIn: 'root' })
export class CorretoraResolve implements Resolve<ICorretora> {
  constructor(private service: CorretoraService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICorretora> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((corretora: HttpResponse<Corretora>) => {
          if (corretora.body) {
            return of(corretora.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Corretora());
  }
}

export const corretoraRoute: Routes = [
  {
    path: '',
    component: CorretoraComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Corretoras',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CorretoraDetailComponent,
    resolve: {
      corretora: CorretoraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Corretoras',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CorretoraUpdateComponent,
    resolve: {
      corretora: CorretoraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Corretoras',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CorretoraUpdateComponent,
    resolve: {
      corretora: CorretoraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Corretoras',
    },
    canActivate: [UserRouteAccessService],
  },
];
