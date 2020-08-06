import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { TransacaoComponent } from './transacao.component';
import { TransacaoDetailComponent } from './transacao-detail.component';
import { TransacaoUpdateComponent } from './transacao-update.component';
import { TransacaoDeleteDialogComponent } from './transacao-delete-dialog.component';
import { transacaoRoute } from './transacao.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(transacaoRoute)],
  declarations: [TransacaoComponent, TransacaoDetailComponent, TransacaoUpdateComponent, TransacaoDeleteDialogComponent],
  entryComponents: [TransacaoDeleteDialogComponent],
})
export class JhipsterSampleApplicationTransacaoModule {}
