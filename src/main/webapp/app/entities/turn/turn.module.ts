import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UmturnosSharedModule } from 'app/shared';
import { UmturnosAdminModule } from 'app/admin/admin.module';
import {
    TurnComponent,
    TurnDetailComponent,
    TurnUpdateComponent,
    TurnDeletePopupComponent,
    TurnDeleteDialogComponent,
    turnRoute,
    turnPopupRoute
} from './';

const ENTITY_STATES = [...turnRoute, ...turnPopupRoute];

@NgModule({
    imports: [UmturnosSharedModule, UmturnosAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TurnComponent, TurnDetailComponent, TurnUpdateComponent, TurnDeleteDialogComponent, TurnDeletePopupComponent],
    entryComponents: [TurnComponent, TurnUpdateComponent, TurnDeleteDialogComponent, TurnDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UmturnosTurnModule {}
