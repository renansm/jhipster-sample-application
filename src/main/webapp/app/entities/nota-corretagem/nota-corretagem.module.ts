import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { NotaCorretagemComponent } from './nota-corretagem.component';
import { NotaCorretagemDetailComponent } from './nota-corretagem-detail.component';
import { NotaCorretagemUpdateComponent } from './nota-corretagem-update.component';
import { NotaCorretagemDeleteDialogComponent } from './nota-corretagem-delete-dialog.component';
import { notaCorretagemRoute } from './nota-corretagem.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(notaCorretagemRoute)],
  declarations: [
    NotaCorretagemComponent,
    NotaCorretagemDetailComponent,
    NotaCorretagemUpdateComponent,
    NotaCorretagemDeleteDialogComponent,
  ],
  entryComponents: [NotaCorretagemDeleteDialogComponent],
})
export class JhipsterSampleApplicationNotaCorretagemModule {}
