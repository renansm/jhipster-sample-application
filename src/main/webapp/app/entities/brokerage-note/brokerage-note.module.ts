import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { BrokerageNoteComponent } from './brokerage-note.component';
import { BrokerageNoteDetailComponent } from './brokerage-note-detail.component';
import { BrokerageNoteUpdateComponent } from './brokerage-note-update.component';
import { BrokerageNoteDeleteDialogComponent } from './brokerage-note-delete-dialog.component';
import { brokerageNoteRoute } from './brokerage-note.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(brokerageNoteRoute)],
  declarations: [BrokerageNoteComponent, BrokerageNoteDetailComponent, BrokerageNoteUpdateComponent, BrokerageNoteDeleteDialogComponent],
  entryComponents: [BrokerageNoteDeleteDialogComponent],
})
export class JhipsterSampleApplicationBrokerageNoteModule {}
