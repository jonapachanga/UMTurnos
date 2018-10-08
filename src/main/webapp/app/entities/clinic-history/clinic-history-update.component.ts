import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IClinicHistory } from 'app/shared/model/clinic-history.model';
import { ClinicHistoryService } from './clinic-history.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';

@Component({
    selector: 'jhi-clinic-history-update',
    templateUrl: './clinic-history-update.component.html'
})
export class ClinicHistoryUpdateComponent implements OnInit {
    private _clinicHistory: IClinicHistory;
    isSaving: boolean;

    patients: IPatient[];
    dateAndHour: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private clinicHistoryService: ClinicHistoryService,
        private patientService: PatientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clinicHistory }) => {
            this.clinicHistory = clinicHistory;
        });
        this.patientService.query().subscribe(
            (res: HttpResponse<IPatient[]>) => {
                this.patients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.clinicHistory.dateAndHour = moment(this.dateAndHour, DATE_TIME_FORMAT);
        if (this.clinicHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.clinicHistoryService.update(this.clinicHistory));
        } else {
            this.subscribeToSaveResponse(this.clinicHistoryService.create(this.clinicHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClinicHistory>>) {
        result.subscribe((res: HttpResponse<IClinicHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPatientById(index: number, item: IPatient) {
        return item.id;
    }
    get clinicHistory() {
        return this._clinicHistory;
    }

    set clinicHistory(clinicHistory: IClinicHistory) {
        this._clinicHistory = clinicHistory;
        this.dateAndHour = moment(clinicHistory.dateAndHour).format(DATE_TIME_FORMAT);
    }
}
