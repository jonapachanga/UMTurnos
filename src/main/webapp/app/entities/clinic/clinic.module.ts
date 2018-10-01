import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UmturnosSharedModule } from 'app/shared';
import {
    ClinicComponent,
    ClinicDetailComponent,
    ClinicUpdateComponent,
    ClinicDeletePopupComponent,
    ClinicDeleteDialogComponent,
    clinicRoute,
    clinicPopupRoute
} from './';

const ENTITY_STATES = [...clinicRoute, ...clinicPopupRoute];

@NgModule({
    imports: [UmturnosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ClinicComponent, ClinicDetailComponent, ClinicUpdateComponent, ClinicDeleteDialogComponent, ClinicDeletePopupComponent],
    entryComponents: [ClinicComponent, ClinicUpdateComponent, ClinicDeleteDialogComponent, ClinicDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UmturnosClinicModule {}
