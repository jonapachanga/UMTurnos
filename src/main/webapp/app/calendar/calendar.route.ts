import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { CalendarComponent } from './';

export const CALENDAR_ROUTE: Route = {
    path: 'calendar',
    component: CalendarComponent,
    data: {
        authorities: [],
        pageTitle: 'calendar.title'
    },
    canActivate: [UserRouteAccessService]
};
