import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatient } from 'app/shared/model/patient.model';

@Component({
    selector: 'jhi-patient-detail',
    templateUrl: './patient-detail.component.html'
})
export class PatientDetailComponent implements OnInit {
    patient: IPatient;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ patient }) => {
            this.patient = patient;
        });
    }

    previousState() {
        window.history.back();
    }
}
