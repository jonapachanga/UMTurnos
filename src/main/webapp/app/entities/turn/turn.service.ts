import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITurn } from 'app/shared/model/turn.model';

type EntityResponseType = HttpResponse<ITurn>;
type EntityArrayResponseType = HttpResponse<ITurn[]>;

@Injectable({ providedIn: 'root' })
export class TurnService {
    private resourceUrl = SERVER_API_URL + 'api/turns';

    constructor(private http: HttpClient) {}

    create(turn: ITurn): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(turn);
        return this.http
            .post<ITurn>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(turn: ITurn): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(turn);
        return this.http
            .put<ITurn>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITurn>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITurn[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    findAllTurns(): Observable<EntityArrayResponseType> {
        return this.http.get<ITurn[]>(this.resourceUrl, { observe: 'response' });
    }

    private convertDateFromClient(turn: ITurn): ITurn {
        const copy: ITurn = Object.assign({}, turn, {
            dateAndHour: turn.dateAndHour != null && turn.dateAndHour.isValid() ? turn.dateAndHour.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateAndHour = res.body.dateAndHour != null ? moment(res.body.dateAndHour) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((turn: ITurn) => {
            turn.dateAndHour = turn.dateAndHour != null ? moment(turn.dateAndHour) : null;
        });
        return res;
    }
}
