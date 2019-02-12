import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { ITurn } from 'app/shared/model/turn.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { TurnService } from 'app/entities/turn';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared';

@Injectable()
export class EventsService {
    dateCalendar: any[] = [];
    turns: HttpResponse<ITurn[]>;

    constructor(private turnService: TurnService) {}

    public getEvents(): Observable<any> {
        this.loadAll();
        return Observable.of(this.dateCalendar);
    }

    private loadAll() {
        this.turnService.query().subscribe(
            (res: HttpResponse<ITurn[]>) => {
                this.convertTurnsForCalendar(res);
                console.log(this.dateCalendar);
            },
            (res: HttpErrorResponse) => console.log(res.message)
        );
    }

    private convertTurnsForCalendar(data: HttpResponse<ITurn[]>) {
        data.body.forEach((turn: ITurn, index) => {
            const date = {
                id: index,
                title: turn.patient.fullName,
                start: moment(turn.dateAndHour).format(DATE_TIME_FORMAT)
            };
            this.dateCalendar.push(date);
        });
    }
}
