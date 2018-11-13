import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Clinic } from 'app/shared/model/clinic.model';
import { ClinicService } from './clinic.service';
import { ClinicComponent } from './clinic.component';
import { ClinicDetailComponent } from './clinic-detail.component';
import { ClinicUpdateComponent } from './clinic-update.component';
import { ClinicDeletePopupComponent } from './clinic-delete-dialog.component';
import { IClinic } from 'app/shared/model/clinic.model';

@Injectable({ providedIn: 'root' })
export class ClinicResolve implements Resolve<IClinic> {
    constructor(private service: ClinicService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((clinic: HttpResponse<Clinic>) => clinic.body));
        }
        return of(new Clinic());
    }
}

export const clinicRoute: Routes = [
    {
        path: 'clinic',
        component: ClinicComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY', 'ROLE_DOCTOR'],
            defaultSort: 'id,asc',
            pageTitle: 'umturnosApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clinic/:id/view',
        component: ClinicDetailComponent,
        resolve: {
            clinic: ClinicResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY', 'ROLE_DOCTOR'],
            pageTitle: 'umturnosApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clinic/new',
        component: ClinicUpdateComponent,
        resolve: {
            clinic: ClinicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clinic/:id/edit',
        component: ClinicUpdateComponent,
        resolve: {
            clinic: ClinicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clinicPopupRoute: Routes = [
    {
        path: 'clinic/:id/delete',
        component: ClinicDeletePopupComponent,
        resolve: {
            clinic: ClinicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'umturnosApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
