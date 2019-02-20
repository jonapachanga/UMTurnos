import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';

import { CalendarComponent } from 'ng-fullcalendar';
import { Options } from 'fullcalendar';
import { EventsService } from './events.service';
import { ITurn } from 'app/shared/model/turn.model';
import { TurnService } from 'app/entities/turn';
import { Subscription } from 'rxjs';
import * as moment from 'moment';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-um-calendar',
    templateUrl: './um-calendar.component.html',
    styleUrls: ['./um-calendar.component.css'],
    providers: [TurnService]
})
export class UmCalendarComponent implements OnInit, OnDestroy {
    calendarOptions: Options;
    displayEvent: any;
    turn: ITurn;
    data: Subscription;
    @ViewChild(CalendarComponent) ucCalendar: CalendarComponent;

    constructor(private eventsService: EventsService, private turnService: TurnService, private router: Router) {}

    ngOnInit() {
        this.data = this.eventsService.getEvents().subscribe(data => {
            setTimeout(() => {
                this.calendarOptions = {
                    editable: true,
                    eventLimit: false,
                    monthNames: [
                        'Enero',
                        'Febrero',
                        'Marzo',
                        'Abril',
                        'Mayo',
                        'Junio',
                        'Julio',
                        'Agosto',
                        'Septiembre',
                        'Octubre',
                        'Noviembre',
                        'Diciembre'
                    ],
                    buttonText: {
                        today: 'Hoy',
                        month: 'Mes',
                        week: 'Semana',
                        day: 'Dia',
                        list: 'Lista'
                    },
                    customButtons: {
                        myCustomButton: {
                            text: 'SOLICITAR TURNO',
                            click: () => {
                                this.router.navigate(['/turn/new']);
                            }
                        }
                    },
                    monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
                    dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
                    dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'],
                    header: {
                        left: 'prev,next today myCustomButton',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay,listMonth'
                    },
                    events: data
                };
            }, 700);
        });
    }

    ngOnDestroy(): void {
        this.calendarOptions = null;
        if (this.data) {
            console.log('OnDestroy');
            this.data.unsubscribe();
        }
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
            },
            duration: {}
        };
        this.turnService.find(model.event.id).subscribe(res => (this.turn = res.body));
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
        this.turnService.find(model.event.id).subscribe(res => {
            this.turn = res.body;
            this.turn.dateAndHour = moment(new Date(model.event.start));
            this.turnService.update(this.turn).subscribe();
        });
        this.displayEvent = model;
    }
}
