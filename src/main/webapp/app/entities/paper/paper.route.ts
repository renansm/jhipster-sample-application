import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaper, Paper } from 'app/shared/model/paper.model';
import { PaperService } from './paper.service';
import { PaperComponent } from './paper.component';
import { PaperDetailComponent } from './paper-detail.component';
import { PaperUpdateComponent } from './paper-update.component';

@Injectable({ providedIn: 'root' })
export class PaperResolve implements Resolve<IPaper> {
  constructor(private service: PaperService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaper> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paper: HttpResponse<Paper>) => {
          if (paper.body) {
            return of(paper.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Paper());
  }
}

export const paperRoute: Routes = [
  {
    path: '',
    component: PaperComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Papers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaperDetailComponent,
    resolve: {
      paper: PaperResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Papers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaperUpdateComponent,
    resolve: {
      paper: PaperResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Papers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaperUpdateComponent,
    resolve: {
      paper: PaperResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Papers',
    },
    canActivate: [UserRouteAccessService],
  },
];
