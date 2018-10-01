/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UmturnosTestModule } from '../../../test.module';
import { PatientDeleteDialogComponent } from 'app/entities/patient/patient-delete-dialog.component';
import { PatientService } from 'app/entities/patient/patient.service';

describe('Component Tests', () => {
    describe('Patient Management Delete Component', () => {
        let comp: PatientDeleteDialogComponent;
        let fixture: ComponentFixture<PatientDeleteDialogComponent>;
        let service: PatientService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UmturnosTestModule],
                declarations: [PatientDeleteDialogComponent]
            })
                .overrideTemplate(PatientDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PatientDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatientService);
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
