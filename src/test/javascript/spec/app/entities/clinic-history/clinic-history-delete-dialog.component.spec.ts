/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UmturnosTestModule } from '../../../test.module';
import { ClinicHistoryDeleteDialogComponent } from 'app/entities/clinic-history/clinic-history-delete-dialog.component';
import { ClinicHistoryService } from 'app/entities/clinic-history/clinic-history.service';

describe('Component Tests', () => {
    describe('ClinicHistory Management Delete Component', () => {
        let comp: ClinicHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<ClinicHistoryDeleteDialogComponent>;
        let service: ClinicHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UmturnosTestModule],
                declarations: [ClinicHistoryDeleteDialogComponent]
            })
                .overrideTemplate(ClinicHistoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClinicHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClinicHistoryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
