import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EditorModule } from '@tinymce/tinymce-angular';
import { FullCalendarModule } from 'ng-fullcalendar';

import { UmturnosSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
    imports: [UmturnosSharedModule, RouterModule.forChild([HOME_ROUTE]), EditorModule, FullCalendarModule],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UmturnosHomeModule {}
