/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UmturnosTestModule } from '../../../test.module';
import { TurnUpdateComponent } from 'app/entities/turn/turn-update.component';
import { TurnService } from 'app/entities/turn/turn.service';
import { Turn } from 'app/shared/model/turn.model';

describe('Component Tests', () => {
    describe('Turn Management Update Component', () => {
        let comp: TurnUpdateComponent;
        let fixture: ComponentFixture<TurnUpdateComponent>;
        let service: TurnService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UmturnosTestModule],
                declarations: [TurnUpdateComponent]
            })
                .overrideTemplate(TurnUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TurnUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TurnService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Turn(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.turn = entity;
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
                    const entity = new Turn();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.turn = entity;
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
