import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITurnType } from 'app/shared/model/turn-type.model';

@Component({
    selector: 'jhi-turn-type-detail',
    templateUrl: './turn-type-detail.component.html'
})
export class TurnTypeDetailComponent implements OnInit {
    turnType: ITurnType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ turnType }) => {
            this.turnType = turnType;
        });
    }

    previousState() {
        window.history.back();
    }
}
