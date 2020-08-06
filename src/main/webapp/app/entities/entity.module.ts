import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'paper',
        loadChildren: () => import('./paper/paper.module').then(m => m.JhipsterSampleApplicationPaperModule),
      },
      {
        path: 'position',
        loadChildren: () => import('./position/position.module').then(m => m.JhipsterSampleApplicationPositionModule),
      },
      {
        path: 'broker',
        loadChildren: () => import('./broker/broker.module').then(m => m.JhipsterSampleApplicationBrokerModule),
      },
      {
        path: 'brokerage-note',
        loadChildren: () => import('./brokerage-note/brokerage-note.module').then(m => m.JhipsterSampleApplicationBrokerageNoteModule),
      },
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.JhipsterSampleApplicationTransactionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSampleApplicationEntityModule {}
