import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBroker, Broker } from 'app/shared/model/broker.model';
import { BrokerService } from './broker.service';
import { BrokerComponent } from './broker.component';
import { BrokerDetailComponent } from './broker-detail.component';
import { BrokerUpdateComponent } from './broker-update.component';

@Injectable({ providedIn: 'root' })
export class BrokerResolve implements Resolve<IBroker> {
  constructor(private service: BrokerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBroker> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((broker: HttpResponse<Broker>) => {
          if (broker.body) {
            return of(broker.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Broker());
  }
}

export const brokerRoute: Routes = [
  {
    path: '',
    component: BrokerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Brokers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BrokerDetailComponent,
    resolve: {
      broker: BrokerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Brokers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BrokerUpdateComponent,
    resolve: {
      broker: BrokerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Brokers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BrokerUpdateComponent,
    resolve: {
      broker: BrokerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Brokers',
    },
    canActivate: [UserRouteAccessService],
  },
];
