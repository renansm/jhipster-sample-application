import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBrokerageNote, BrokerageNote } from 'app/shared/model/brokerage-note.model';
import { BrokerageNoteService } from './brokerage-note.service';
import { BrokerageNoteComponent } from './brokerage-note.component';
import { BrokerageNoteDetailComponent } from './brokerage-note-detail.component';
import { BrokerageNoteUpdateComponent } from './brokerage-note-update.component';

@Injectable({ providedIn: 'root' })
export class BrokerageNoteResolve implements Resolve<IBrokerageNote> {
  constructor(private service: BrokerageNoteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBrokerageNote> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((brokerageNote: HttpResponse<BrokerageNote>) => {
          if (brokerageNote.body) {
            return of(brokerageNote.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BrokerageNote());
  }
}

export const brokerageNoteRoute: Routes = [
  {
    path: '',
    component: BrokerageNoteComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BrokerageNotes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BrokerageNoteDetailComponent,
    resolve: {
      brokerageNote: BrokerageNoteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BrokerageNotes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BrokerageNoteUpdateComponent,
    resolve: {
      brokerageNote: BrokerageNoteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BrokerageNotes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BrokerageNoteUpdateComponent,
    resolve: {
      brokerageNote: BrokerageNoteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BrokerageNotes',
    },
    canActivate: [UserRouteAccessService],
  },
];
