import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { UmturnosTurnModule } from './turn/turn.module';
import { UmturnosPatientModule } from './patient/patient.module';
import { UmturnosClinicModule } from './clinic/clinic.module';
import { UmturnosTurnTypeModule } from './turn-type/turn-type.module';
import { UmturnosClinicHistoryModule } from './clinic-history/clinic-history.module';
import { NgbDatepickerPopup } from './datepicker/datepicker-popup.component';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        NgModule,
        UmturnosTurnModule,
        UmturnosPatientModule,
        UmturnosClinicModule,
        UmturnosTurnTypeModule,
        UmturnosClinicHistoryModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [NgbDatepickerPopup],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UmturnosEntityModule {}
