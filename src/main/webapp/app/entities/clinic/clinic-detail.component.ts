import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClinic } from 'app/shared/model/clinic.model';

@Component({
    selector: 'jhi-clinic-detail',
    templateUrl: './clinic-detail.component.html'
})
export class ClinicDetailComponent implements OnInit {
    clinic: IClinic;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clinic }) => {
            this.clinic = clinic;
        });
    }

    previousState() {
        window.history.back();
    }
}
