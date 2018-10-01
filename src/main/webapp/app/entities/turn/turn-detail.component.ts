import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITurn } from 'app/shared/model/turn.model';

@Component({
    selector: 'jhi-turn-detail',
    templateUrl: './turn-detail.component.html'
})
export class TurnDetailComponent implements OnInit {
    turn: ITurn;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ turn }) => {
            this.turn = turn;
        });
    }

    previousState() {
        window.history.back();
    }
}
