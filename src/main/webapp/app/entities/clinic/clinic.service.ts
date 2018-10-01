import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClinic } from 'app/shared/model/clinic.model';

type EntityResponseType = HttpResponse<IClinic>;
type EntityArrayResponseType = HttpResponse<IClinic[]>;

@Injectable({ providedIn: 'root' })
export class ClinicService {
    private resourceUrl = SERVER_API_URL + 'api/clinics';

    constructor(private http: HttpClient) {}

    create(clinic: IClinic): Observable<EntityResponseType> {
        return this.http.post<IClinic>(this.resourceUrl, clinic, { observe: 'response' });
    }

    update(clinic: IClinic): Observable<EntityResponseType> {
        return this.http.put<IClinic>(this.resourceUrl, clinic, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IClinic>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClinic[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
