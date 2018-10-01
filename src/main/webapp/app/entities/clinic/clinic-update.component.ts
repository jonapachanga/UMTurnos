import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IClinic } from 'app/shared/model/clinic.model';
import { ClinicService } from './clinic.service';

@Component({
    selector: 'jhi-clinic-update',
    templateUrl: './clinic-update.component.html'
})
export class ClinicUpdateComponent implements OnInit {
    private _clinic: IClinic;
    isSaving: boolean;

    constructor(private clinicService: ClinicService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clinic }) => {
            this.clinic = clinic;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.clinic.id !== undefined) {
            this.subscribeToSaveResponse(this.clinicService.update(this.clinic));
        } else {
            this.subscribeToSaveResponse(this.clinicService.create(this.clinic));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClinic>>) {
        result.subscribe((res: HttpResponse<IClinic>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get clinic() {
        return this._clinic;
    }

    set clinic(clinic: IClinic) {
        this._clinic = clinic;
    }
}
