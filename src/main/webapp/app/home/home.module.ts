import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EditorModule } from '@tinymce/tinymce-angular';
import { EventsService } from '../um-calendar/events.service';
import { FullCalendarModule } from 'ng-fullcalendar';

import { UmCalendarComponent } from '../um-calendar/um-calendar.component';

import { UmturnosSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
    imports: [UmturnosSharedModule, RouterModule.forChild([HOME_ROUTE]), EditorModule, FullCalendarModule],
    declarations: [HomeComponent, UmCalendarComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [EventsService]
})
export class UmturnosHomeModule {}
