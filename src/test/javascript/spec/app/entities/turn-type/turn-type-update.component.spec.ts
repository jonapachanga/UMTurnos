/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UmturnosTestModule } from '../../../test.module';
import { TurnTypeUpdateComponent } from 'app/entities/turn-type/turn-type-update.component';
import { TurnTypeService } from 'app/entities/turn-type/turn-type.service';
import { TurnType } from 'app/shared/model/turn-type.model';

describe('Component Tests', () => {
    describe('TurnType Management Update Component', () => {
        let comp: TurnTypeUpdateComponent;
        let fixture: ComponentFixture<TurnTypeUpdateComponent>;
        let service: TurnTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UmturnosTestModule],
                declarations: [TurnTypeUpdateComponent]
            })
                .overrideTemplate(TurnTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TurnTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TurnTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TurnType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.turnType = entity;
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
                    const entity = new TurnType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.turnType = entity;
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
