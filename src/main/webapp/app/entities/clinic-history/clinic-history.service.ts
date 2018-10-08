import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClinicHistory } from 'app/shared/model/clinic-history.model';

type EntityResponseType = HttpResponse<IClinicHistory>;
type EntityArrayResponseType = HttpResponse<IClinicHistory[]>;

@Injectable({ providedIn: 'root' })
export class ClinicHistoryService {
    private resourceUrl = SERVER_API_URL + 'api/clinic-histories';

    constructor(private http: HttpClient) {}

    create(clinicHistory: IClinicHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(clinicHistory);
        return this.http
            .post<IClinicHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(clinicHistory: IClinicHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(clinicHistory);
        return this.http
            .put<IClinicHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IClinicHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IClinicHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(clinicHistory: IClinicHistory): IClinicHistory {
        const copy: IClinicHistory = Object.assign({}, clinicHistory, {
            dateAndHour:
                clinicHistory.dateAndHour != null && clinicHistory.dateAndHour.isValid() ? clinicHistory.dateAndHour.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateAndHour = res.body.dateAndHour != null ? moment(res.body.dateAndHour) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((clinicHistory: IClinicHistory) => {
            clinicHistory.dateAndHour = clinicHistory.dateAndHour != null ? moment(clinicHistory.dateAndHour) : null;
        });
        return res;
    }
}
