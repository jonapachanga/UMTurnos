/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UmturnosTestModule } from '../../../test.module';
import { ClinicUpdateComponent } from 'app/entities/clinic/clinic-update.component';
import { ClinicService } from 'app/entities/clinic/clinic.service';
import { Clinic } from 'app/shared/model/clinic.model';

describe('Component Tests', () => {
    describe('Clinic Management Update Component', () => {
        let comp: ClinicUpdateComponent;
        let fixture: ComponentFixture<ClinicUpdateComponent>;
        let service: ClinicService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UmturnosTestModule],
                declarations: [ClinicUpdateComponent]
            })
                .overrideTemplate(ClinicUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClinicUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClinicService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Clinic(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clinic = entity;
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
                    const entity = new Clinic();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clinic = entity;
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
