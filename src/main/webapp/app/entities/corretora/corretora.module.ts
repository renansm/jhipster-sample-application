import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CorretoraComponent } from './corretora.component';
import { CorretoraDetailComponent } from './corretora-detail.component';
import { CorretoraUpdateComponent } from './corretora-update.component';
import { CorretoraDeleteDialogComponent } from './corretora-delete-dialog.component';
import { corretoraRoute } from './corretora.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(corretoraRoute)],
  declarations: [CorretoraComponent, CorretoraDetailComponent, CorretoraUpdateComponent, CorretoraDeleteDialogComponent],
  entryComponents: [CorretoraDeleteDialogComponent],
})
export class JhipsterSampleApplicationCorretoraModule {}
