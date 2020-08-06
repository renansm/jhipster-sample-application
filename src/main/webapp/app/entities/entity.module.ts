import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'papel',
        loadChildren: () => import('./papel/papel.module').then(m => m.JhipsterSampleApplicationPapelModule),
      },
      {
        path: 'posicao',
        loadChildren: () => import('./posicao/posicao.module').then(m => m.JhipsterSampleApplicationPosicaoModule),
      },
      {
        path: 'corretora',
        loadChildren: () => import('./corretora/corretora.module').then(m => m.JhipsterSampleApplicationCorretoraModule),
      },
      {
        path: 'nota-corretagem',
        loadChildren: () => import('./nota-corretagem/nota-corretagem.module').then(m => m.JhipsterSampleApplicationNotaCorretagemModule),
      },
      {
        path: 'transacao',
        loadChildren: () => import('./transacao/transacao.module').then(m => m.JhipsterSampleApplicationTransacaoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSampleApplicationEntityModule {}
