/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UmturnosTestModule } from '../../../test.module';
import { ClinicHistoryUpdateComponent } from 'app/entities/clinic-history/clinic-history-update.component';
import { ClinicHistoryService } from 'app/entities/clinic-history/clinic-history.service';
import { ClinicHistory } from 'app/shared/model/clinic-history.model';

describe('Component Tests', () => {
    describe('ClinicHistory Management Update Component', () => {
        let comp: ClinicHistoryUpdateComponent;
        let fixture: ComponentFixture<ClinicHistoryUpdateComponent>;
        let service: ClinicHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UmturnosTestModule],
                declarations: [ClinicHistoryUpdateComponent]
            })
                .overrideTemplate(ClinicHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClinicHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClinicHistoryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClinicHistory(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clinicHistory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClinicHistory();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clinicHistory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
