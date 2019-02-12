import { Component, OnInit, ViewChild } from '@angular/core';

import { CalendarComponent } from 'ng-fullcalendar';
import { Options } from 'fullcalendar';
import { EventsService } from './events.service';
import { ITurn } from 'app/shared/model/turn.model';

@Component({
    selector: 'jhi-um-calendar',
    templateUrl: './um-calendar.component.html',
    styles: []
})
export class UmCalendarComponent implements OnInit {
    calendarOptions: Options;
    displayEvent: any;
    turns: ITurn[];
    @ViewChild(CalendarComponent) ucCalendar: CalendarComponent;

    constructor(private eventsService: EventsService) {}

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
            console.log('Eventos', data);
        });
    }

    clickButton(model: any) {
        this.displayEvent = model;
    }

    eventClick(model: any) {
        console.log('Click Event');
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
