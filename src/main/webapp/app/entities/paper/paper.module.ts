import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PaperComponent } from './paper.component';
import { PaperDetailComponent } from './paper-detail.component';
import { PaperUpdateComponent } from './paper-update.component';
import { PaperDeleteDialogComponent } from './paper-delete-dialog.component';
import { paperRoute } from './paper.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(paperRoute)],
  declarations: [PaperComponent, PaperDetailComponent, PaperUpdateComponent, PaperDeleteDialogComponent],
  entryComponents: [PaperDeleteDialogComponent],
})
export class JhipsterSampleApplicationPaperModule {}
