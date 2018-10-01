import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITurnType } from 'app/shared/model/turn-type.model';

type EntityResponseType = HttpResponse<ITurnType>;
type EntityArrayResponseType = HttpResponse<ITurnType[]>;

@Injectable({ providedIn: 'root' })
export class TurnTypeService {
    private resourceUrl = SERVER_API_URL + 'api/turn-types';

    constructor(private http: HttpClient) {}

    create(turnType: ITurnType): Observable<EntityResponseType> {
        return this.http.post<ITurnType>(this.resourceUrl, turnType, { observe: 'response' });
    }

    update(turnType: ITurnType): Observable<EntityResponseType> {
        return this.http.put<ITurnType>(this.resourceUrl, turnType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITurnType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITurnType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
