import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITurn } from 'app/shared/model/turn.model';
import { TurnService } from './turn.service';
import { ITurnType } from 'app/shared/model/turn-type.model';
import { TurnTypeService } from 'app/entities/turn-type';
import { IClinic } from 'app/shared/model/clinic.model';
import { ClinicService } from 'app/entities/clinic';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';
import { IUser, UserService, Account, Principal } from 'app/core';

@Component({
    selector: 'jhi-turn-update',
    templateUrl: './turn-update.component.html'
})
export class TurnUpdateComponent implements OnInit {
    private _turn: ITurn;
    isSaving: boolean;

    turntypes: ITurnType[];

    clinics: IClinic[];

    patients: IPatient[];

    account: Account;
    users: IUser[];
    dateAndHour: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private turnService: TurnService,
        private turnTypeService: TurnTypeService,
        private clinicService: ClinicService,
        private patientService: PatientService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
            console.log('Cuenta: ', this.account);
        });
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ turn }) => {
            this.turn = turn;
        });
        this.turnTypeService.query().subscribe(
            (res: HttpResponse<ITurnType[]>) => {
                this.turntypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.clinicService.query().subscribe(
            (res: HttpResponse<IClinic[]>) => {
                this.clinics = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.patientService.query().subscribe(
            (res: HttpResponse<IPatient[]>) => {
                this.patients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.turn.dateAndHour = moment(this.dateAndHour, DATE_TIME_FORMAT);
        this.turn.user = this.account;
        if (this.turn.id !== undefined) {
            this.subscribeToSaveResponse(this.turnService.update(this.turn));
        } else {
            this.subscribeToSaveResponse(this.turnService.create(this.turn));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITurn>>) {
        result.subscribe((res: HttpResponse<ITurn>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTurnTypeById(index: number, item: ITurnType) {
        return item.id;
    }

    trackClinicById(index: number, item: IClinic) {
        return item.id;
    }

    trackPatientById(index: number, item: IPatient) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get turn() {
        return this._turn;
    }

    set turn(turn: ITurn) {
        this._turn = turn;
        this.dateAndHour = moment(turn.dateAndHour).format(DATE_TIME_FORMAT);
    }
}
