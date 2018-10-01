/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UmturnosTestModule } from '../../../test.module';
import { ClinicDetailComponent } from 'app/entities/clinic/clinic-detail.component';
import { Clinic } from 'app/shared/model/clinic.model';

describe('Component Tests', () => {
    describe('Clinic Management Detail Component', () => {
        let comp: ClinicDetailComponent;
        let fixture: ComponentFixture<ClinicDetailComponent>;
        const route = ({ data: of({ clinic: new Clinic(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UmturnosTestModule],
                declarations: [ClinicDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClinicDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClinicDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clinic).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
