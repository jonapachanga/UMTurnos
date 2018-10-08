import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClinicHistory } from 'app/shared/model/clinic-history.model';
import { ClinicHistoryService } from './clinic-history.service';

@Component({
    selector: 'jhi-clinic-history-delete-dialog',
    templateUrl: './clinic-history-delete-dialog.component.html'
})
export class ClinicHistoryDeleteDialogComponent {
    clinicHistory: IClinicHistory;

    constructor(
        private clinicHistoryService: ClinicHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clinicHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clinicHistoryListModification',
                content: 'Deleted an clinicHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clinic-history-delete-popup',
    template: ''
})
export class ClinicHistoryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clinicHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClinicHistoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.clinicHistory = clinicHistory;
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
