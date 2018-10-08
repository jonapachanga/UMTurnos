import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UmturnosSharedModule } from 'app/shared';
import {
    ClinicHistoryComponent,
    ClinicHistoryDetailComponent,
    ClinicHistoryUpdateComponent,
    ClinicHistoryDeletePopupComponent,
    ClinicHistoryDeleteDialogComponent,
    clinicHistoryRoute,
    clinicHistoryPopupRoute
} from './';

const ENTITY_STATES = [...clinicHistoryRoute, ...clinicHistoryPopupRoute];

@NgModule({
    imports: [UmturnosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClinicHistoryComponent,
        ClinicHistoryDetailComponent,
        ClinicHistoryUpdateComponent,
        ClinicHistoryDeleteDialogComponent,
        ClinicHistoryDeletePopupComponent
    ],
    entryComponents: [
        ClinicHistoryComponent,
        ClinicHistoryUpdateComponent,
        ClinicHistoryDeleteDialogComponent,
        ClinicHistoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UmturnosClinicHistoryModule {}
