import { Component, OnInit, ViewChild } from '@angular/core';

import { CalendarComponent } from 'ng-fullcalendar';
import { Options } from 'fullcalendar';
import { EventsService } from './events.service';

@Component({
    selector: 'jhi-um-calendar',
    templateUrl: './um-calendar.component.html',
    styles: []
})
export class UmCalendarComponent implements OnInit {
    calendarOptions: Options;
    displayEvent: any;
    dateObj: Date;
    @ViewChild(CalendarComponent) ucCalendar: CalendarComponent;

    constructor(private eventsService: EventsService) {
        this.dateObj = new Date();
    }

    ngOnInit() {
        this.eventsService.getEvents().subscribe(data => {
            this.calendarOptions = {
                editable: true,
                eventLimit: false,
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay,listMonth'
                },
                events: data
            };
        });
    }

    clickButton(model: any) {
        this.displayEvent = model;
    }

    eventClick(model: any) {
        model = {
            event: {
                id: model.event.id,
                start: model.event.start,
                end: model.event.end,
                title: model.event.title,
                allDay: model.event.allDay
                // other params
            },
            duration: {}
        };
        this.displayEvent = model;
    }

    updateEvent(model: any) {
        model = {
            event: {
                id: model.event.id,
                start: model.event.start,
                end: model.event.end,
                title: model.event.title
                // other params
            },
            duration: {
                _data: model.duration._data
            }
        };
        this.displayEvent = model;
    }
}
