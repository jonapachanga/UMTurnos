import { Injectable, OnDestroy } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { ITurn } from 'app/shared/model/turn.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { TurnService } from 'app/entities/turn';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared';
import { Subscription } from 'rxjs';

@Injectable()
export class EventsService implements OnDestroy {
    dateCalendar: any[];
    turns: Subscription;

    constructor(private turnService: TurnService) {
        this.dateCalendar = [];
    }

    public getEvents(): Observable<any> {
        this.turns = this.turnService.query().subscribe(
            (res: HttpResponse<ITurn[]>) => {
                this.convertTurnsForCalendar(res);
            },
            (res: HttpErrorResponse) => console.log(res.message)
        );

        return Observable.of(this.dateCalendar);
    }

    private convertTurnsForCalendar(data: HttpResponse<ITurn[]>) {
        data.body.forEach((turn: ITurn) => {
            const date = {
                id: turn.id,
                title: turn.patient.fullName,
                start: moment(turn.dateAndHour).format(DATE_TIME_FORMAT)
            };

            this.dateCalendar.push(date);
        });
    }

    ngOnDestroy(): void {
        this.turns.unsubscribe();
        this.dateCalendar = null;
        console.log('Destroy Service');
    }
}
