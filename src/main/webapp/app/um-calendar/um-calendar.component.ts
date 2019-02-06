import { Component, OnInit, ViewChild } from '@angular/core';

import { CalendarComponent } from 'ng-fullcalendar';
import { Options } from 'fullcalendar';
import { EventsService } from './events.service';
import { TurnService } from 'app/entities/turn';
import { ITurn } from 'app/shared/model/turn.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';

import { DateCalendar } from './data-calendar';

@Component({
    selector: 'jhi-um-calendar',
    templateUrl: './um-calendar.component.html',
    styles: []
})
export class UmCalendarComponent implements OnInit {
    calendarOptions: Options;
    displayEvent: any;
    turns: ITurn[];
    dateCalendar: Array<DateCalendar>;
    @ViewChild(CalendarComponent) ucCalendar: CalendarComponent;

    constructor(private eventsService: EventsService, private turnService: TurnService, private jhiAlertService: JhiAlertService) {}

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

        // for (const turn of this.turns) {
        //     const dc = new DateCalendar();
        //     let date: string;
        //     date = turn.dateAndHour.year() + '-' + turn.dateAndHour.month() + '-' + turn.dateAndHour.day();
        //     dc.id = turn.id;
        //     dc.title = turn.patient.fullName;
        //     dc.start = date;
        //
        //     this.dateCalendar.push(dc);
        // }
        // this.dateCalendar.forEach(function (e) {
        //     console.log(e);
        // });
        // // console.log(this.dateCalendar);
        // console.log('OnInit..');
    }

    private getTurns(data: ITurn[]) {
        this.turns = data;
    }

    loadAll() {
        this.turnService
            .query()
            .subscribe((res: HttpResponse<ITurn[]>) => (this.turns = res.body), (res: HttpErrorResponse) => this.onError(res.message));

        console.log(this.turns);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
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
