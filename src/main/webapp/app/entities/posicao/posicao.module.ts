import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PosicaoComponent } from './posicao.component';
import { PosicaoDetailComponent } from './posicao-detail.component';
import { PosicaoUpdateComponent } from './posicao-update.component';
import { PosicaoDeleteDialogComponent } from './posicao-delete-dialog.component';
import { posicaoRoute } from './posicao.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(posicaoRoute)],
  declarations: [PosicaoComponent, PosicaoDetailComponent, PosicaoUpdateComponent, PosicaoDeleteDialogComponent],
  entryComponents: [PosicaoDeleteDialogComponent],
})
export class JhipsterSampleApplicationPosicaoModule {}
