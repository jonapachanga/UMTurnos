import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITurnType } from 'app/shared/model/turn-type.model';
import { TurnTypeService } from './turn-type.service';

@Component({
    selector: 'jhi-turn-type-delete-dialog',
    templateUrl: './turn-type-delete-dialog.component.html'
})
export class TurnTypeDeleteDialogComponent {
    turnType: ITurnType;

    constructor(private turnTypeService: TurnTypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.turnTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'turnTypeListModification',
                content: 'Deleted an turnType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-turn-type-delete-popup',
    template: ''
})
export class TurnTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ turnType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TurnTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.turnType = turnType;
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
