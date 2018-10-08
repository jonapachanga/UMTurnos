/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UmturnosTestModule } from '../../../test.module';
import { ClinicHistoryDetailComponent } from 'app/entities/clinic-history/clinic-history-detail.component';
import { ClinicHistory } from 'app/shared/model/clinic-history.model';

describe('Component Tests', () => {
    describe('ClinicHistory Management Detail Component', () => {
        let comp: ClinicHistoryDetailComponent;
        let fixture: ComponentFixture<ClinicHistoryDetailComponent>;
        const route = ({ data: of({ clinicHistory: new ClinicHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UmturnosTestModule],
                declarations: [ClinicHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClinicHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClinicHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clinicHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
