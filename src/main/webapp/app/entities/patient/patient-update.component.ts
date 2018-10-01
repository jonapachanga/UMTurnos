import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';

@Component({
    selector: 'jhi-patient-update',
    templateUrl: './patient-update.component.html'
})
export class PatientUpdateComponent implements OnInit {
    private _patient: IPatient;
    isSaving: boolean;

    constructor(private patientService: PatientService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ patient }) => {
            this.patient = patient;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.patient.id !== undefined) {
            this.subscribeToSaveResponse(this.patientService.update(this.patient));
        } else {
            this.subscribeToSaveResponse(this.patientService.create(this.patient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>) {
        result.subscribe((res: HttpResponse<IPatient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get patient() {
        return this._patient;
    }

    set patient(patient: IPatient) {
        this._patient = patient;
    }
}
