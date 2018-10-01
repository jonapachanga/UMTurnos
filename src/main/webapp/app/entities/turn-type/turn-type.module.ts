import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UmturnosSharedModule } from 'app/shared';
import {
    TurnTypeComponent,
    TurnTypeDetailComponent,
    TurnTypeUpdateComponent,
    TurnTypeDeletePopupComponent,
    TurnTypeDeleteDialogComponent,
    turnTypeRoute,
    turnTypePopupRoute
} from './';

const ENTITY_STATES = [...turnTypeRoute, ...turnTypePopupRoute];

@NgModule({
    imports: [UmturnosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TurnTypeComponent,
        TurnTypeDetailComponent,
        TurnTypeUpdateComponent,
        TurnTypeDeleteDialogComponent,
        TurnTypeDeletePopupComponent
    ],
    entryComponents: [TurnTypeComponent, TurnTypeUpdateComponent, TurnTypeDeleteDialogComponent, TurnTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UmturnosTurnTypeModule {}
