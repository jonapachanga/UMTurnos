import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITurnType } from 'app/shared/model/turn-type.model';
import { TurnTypeService } from './turn-type.service';

@Component({
    selector: 'jhi-turn-type-update',
    templateUrl: './turn-type-update.component.html'
})
export class TurnTypeUpdateComponent implements OnInit {
    private _turnType: ITurnType;
    isSaving: boolean;

    constructor(private turnTypeService: TurnTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ turnType }) => {
            this.turnType = turnType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.turnType.id !== undefined) {
            this.subscribeToSaveResponse(this.turnTypeService.update(this.turnType));
        } else {
            this.subscribeToSaveResponse(this.turnTypeService.create(this.turnType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITurnType>>) {
        result.subscribe((res: HttpResponse<ITurnType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get turnType() {
        return this._turnType;
    }

    set turnType(turnType: ITurnType) {
        this._turnType = turnType;
    }
}
