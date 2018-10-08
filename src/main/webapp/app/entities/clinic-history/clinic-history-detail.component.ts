import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClinicHistory } from 'app/shared/model/clinic-history.model';

@Component({
    selector: 'jhi-clinic-history-detail',
    templateUrl: './clinic-history-detail.component.html'
})
export class ClinicHistoryDetailComponent implements OnInit {
    clinicHistory: IClinicHistory;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clinicHistory }) => {
            this.clinicHistory = clinicHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
