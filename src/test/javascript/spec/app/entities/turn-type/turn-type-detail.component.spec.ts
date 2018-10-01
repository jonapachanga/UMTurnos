/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UmturnosTestModule } from '../../../test.module';
import { TurnTypeDetailComponent } from 'app/entities/turn-type/turn-type-detail.component';
import { TurnType } from 'app/shared/model/turn-type.model';

describe('Component Tests', () => {
    describe('TurnType Management Detail Component', () => {
        let comp: TurnTypeDetailComponent;
        let fixture: ComponentFixture<TurnTypeDetailComponent>;
        const route = ({ data: of({ turnType: new TurnType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UmturnosTestModule],
                declarations: [TurnTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TurnTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TurnTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.turnType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
