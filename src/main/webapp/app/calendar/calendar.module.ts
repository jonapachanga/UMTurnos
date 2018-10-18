import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UmturnosSharedModule } from '../shared';

import { CALENDAR_ROUTE, CalendarComponent } from './';

@NgModule({
    imports: [
      UmturnosSharedModule,
      RouterModule.forRoot([ CALENDAR_ROUTE ], { useHash: true })
    ],
    declarations: [
      CalendarComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UmturnosAppCalendarModule {}
