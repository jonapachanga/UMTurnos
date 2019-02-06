import { Inject, Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { DateCalendar, IDateCalendar } from 'app/um-calendar/data-calendar';
import { ITurn } from 'app/shared/model/turn.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { TurnService } from 'app/entities/turn';

@Injectable()
export class EventsService {
    dateCalendar: any[] = [];
    turns: ITurn[];

    constructor(private turnService: TurnService) {}

    public getEvents(): Observable<any> {
        this.getTurns();
        const dateObj = new Date();
        const yearMonth = dateObj.getUTCFullYear() + '-' + (dateObj.getUTCMonth() + 1);
        setInterval(() => {
            for (let i = 0; i < this.turns.length; i++) {
                const date = {
                    id: i,
                    title: this.turns[i].patient.fullName,
                    start: this.turns[i].dateAndHour
                };
                this.dateCalendar.push(date);
            }
        }, 1000);
        // const data: any = [
        //     {
        //         title: 'All Day Event',
        //         start: yearMonth + '-01'
        //     },
        //     {
        //         title: 'Long Event',
        //         start: yearMonth + '-07',
        //         end: yearMonth + '-10'
        //     },
        //     {
        //         id: 999,
        //         title: 'Repeating Event',
        //         start: yearMonth + '-09T16:00:00'
        //     },
        //     {
        //         id: 999,
        //         title: 'Repeating Event',
        //         start: yearMonth + '-16T16:00:00'
        //     },
        //     {
        //         id: 999,
        //         title: 'Conference',
        //         start: yearMonth + '-11',
        //         end: yearMonth + '-13'
        //     },
        //     {
        //         title: 'Meeting',
        //         start: yearMonth + '-12T10:30:00',
        //         end: yearMonth + '-12T12:30:00'
        //     },
        //     {
        //         title: 'Lunch',
        //         start: yearMonth + '-12T12:00:00'
        //     },
        //     {
        //         title: 'Meeting',
        //         start: yearMonth + '-12T14:30:00'
        //     },
        //     {
        //         title: 'Happy Hour',
        //         start: yearMonth + '-12T17:30:00'
        //     },
        //     {
        //         title: 'Dinner',
        //         start: yearMonth + '-12T20:00:00'
        //     },
        //     {
        //         title: 'Birthday Party',
        //         start: yearMonth + '-13T07:00:00'
        //     },
        //     {
        //         title: 'Click for Google',
        //         url: 'http://google.com/',
        //         start: yearMonth + '-28',
        //         end: yearMonth + '-29'
        //     }
        // ];
        return Observable.of(this.dateCalendar);
    }

    private getTurns() {
        this.turnService.findAllTurns().subscribe(
            (res: HttpResponse<ITurn[]>) => {
                this.turns = res.body;
            },
            (res: HttpErrorResponse) => console.log(res.message)
        );
    }
}
