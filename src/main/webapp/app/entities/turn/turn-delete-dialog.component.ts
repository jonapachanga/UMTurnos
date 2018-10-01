import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITurn } from 'app/shared/model/turn.model';
import { TurnService } from './turn.service';

@Component({
    selector: 'jhi-turn-delete-dialog',
    templateUrl: './turn-delete-dialog.component.html'
})
export class TurnDeleteDialogComponent {
    turn: ITurn;

    constructor(private turnService: TurnService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.turnService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'turnListModification',
                content: 'Deleted an turn'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-turn-delete-popup',
    template: ''
})
export class TurnDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ turn }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TurnDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.turn = turn;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
