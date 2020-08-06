import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { BrokerComponent } from './broker.component';
import { BrokerDetailComponent } from './broker-detail.component';
import { BrokerUpdateComponent } from './broker-update.component';
import { BrokerDeleteDialogComponent } from './broker-delete-dialog.component';
import { brokerRoute } from './broker.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(brokerRoute)],
  declarations: [BrokerComponent, BrokerDetailComponent, BrokerUpdateComponent, BrokerDeleteDialogComponent],
  entryComponents: [BrokerDeleteDialogComponent],
})
export class JhipsterSampleApplicationBrokerModule {}
