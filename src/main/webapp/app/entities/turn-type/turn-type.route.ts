import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TurnType } from 'app/shared/model/turn-type.model';
import { TurnTypeService } from './turn-type.service';
import { TurnTypeComponent } from './turn-type.component';
import { TurnTypeDetailComponent } from './turn-type-detail.component';
import { TurnTypeUpdateComponent } from './turn-type-update.component';
import { TurnTypeDeletePopupComponent } from './turn-type-delete-dialog.component';
import { ITurnType } from 'app/shared/model/turn-type.model';

@Injectable({ providedIn: 'root' })
export class TurnTypeResolve implements Resolve<ITurnType> {
    constructor(private service: TurnTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((turnType: HttpResponse<TurnType>) => turnType.body));
        }
        return of(new TurnType());
    }
}

export const turnTypeRoute: Routes = [
    {
        path: 'turn-type',
        component: TurnTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'umturnosApp.turnType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'turn-type/:id/view',
        component: TurnTypeDetailComponent,
        resolve: {
            turnType: TurnTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.turnType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'turn-type/new',
        component: TurnTypeUpdateComponent,
        resolve: {
            turnType: TurnTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.turnType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'turn-type/:id/edit',
        component: TurnTypeUpdateComponent,
        resolve: {
            turnType: TurnTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.turnType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const turnTypePopupRoute: Routes = [
    {
        path: 'turn-type/:id/delete',
        component: TurnTypeDeletePopupComponent,
        resolve: {
            turnType: TurnTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.turnType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
