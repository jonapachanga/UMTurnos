import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UmturnosSharedModule } from 'app/shared';
import {
    PatientComponent,
    PatientDetailComponent,
    PatientUpdateComponent,
    PatientDeletePopupComponent,
    PatientDeleteDialogComponent,
    patientRoute,
    patientPopupRoute
} from './';

const ENTITY_STATES = [...patientRoute, ...patientPopupRoute];

@NgModule({
    imports: [UmturnosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PatientComponent,
        PatientDetailComponent,
        PatientUpdateComponent,
        PatientDeleteDialogComponent,
        PatientDeletePopupComponent
    ],
    entryComponents: [PatientComponent, PatientUpdateComponent, PatientDeleteDialogComponent, PatientDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UmturnosPatientModule {}
