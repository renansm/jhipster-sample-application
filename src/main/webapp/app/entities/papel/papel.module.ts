import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PapelComponent } from './papel.component';
import { PapelDetailComponent } from './papel-detail.component';
import { PapelUpdateComponent } from './papel-update.component';
import { PapelDeleteDialogComponent } from './papel-delete-dialog.component';
import { papelRoute } from './papel.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(papelRoute)],
  declarations: [PapelComponent, PapelDetailComponent, PapelUpdateComponent, PapelDeleteDialogComponent],
  entryComponents: [PapelDeleteDialogComponent],
})
export class JhipsterSampleApplicationPapelModule {}
