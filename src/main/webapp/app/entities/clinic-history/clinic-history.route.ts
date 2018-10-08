import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClinicHistory } from 'app/shared/model/clinic-history.model';
import { ClinicHistoryService } from './clinic-history.service';
import { ClinicHistoryComponent } from './clinic-history.component';
import { ClinicHistoryDetailComponent } from './clinic-history-detail.component';
import { ClinicHistoryUpdateComponent } from './clinic-history-update.component';
import { ClinicHistoryDeletePopupComponent } from './clinic-history-delete-dialog.component';
import { IClinicHistory } from 'app/shared/model/clinic-history.model';

@Injectable({ providedIn: 'root' })
export class ClinicHistoryResolve implements Resolve<IClinicHistory> {
    constructor(private service: ClinicHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((clinicHistory: HttpResponse<ClinicHistory>) => clinicHistory.body));
        }
        return of(new ClinicHistory());
    }
}

export const clinicHistoryRoute: Routes = [
    {
        path: 'clinic-history',
        component: ClinicHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'umturnosApp.clinicHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clinic-history/:id/view',
        component: ClinicHistoryDetailComponent,
        resolve: {
            clinicHistory: ClinicHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.clinicHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clinic-history/new',
        component: ClinicHistoryUpdateComponent,
        resolve: {
            clinicHistory: ClinicHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.clinicHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clinic-history/:id/edit',
        component: ClinicHistoryUpdateComponent,
        resolve: {
            clinicHistory: ClinicHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.clinicHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clinicHistoryPopupRoute: Routes = [
    {
        path: 'clinic-history/:id/delete',
        component: ClinicHistoryDeletePopupComponent,
        resolve: {
            clinicHistory: ClinicHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.clinicHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
