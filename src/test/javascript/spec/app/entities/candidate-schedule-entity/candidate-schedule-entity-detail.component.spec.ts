/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateScheduleEntityDetailComponent } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity-detail.component';
import { CandidateScheduleEntityService } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity.service';
import { CandidateScheduleEntity } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity.model';

describe('Component Tests', () => {

    describe('CandidateScheduleEntity Management Detail Component', () => {
        let comp: CandidateScheduleEntityDetailComponent;
        let fixture: ComponentFixture<CandidateScheduleEntityDetailComponent>;
        let service: CandidateScheduleEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateScheduleEntityDetailComponent],
                providers: [
                    CandidateScheduleEntityService
                ]
            })
            .overrideTemplate(CandidateScheduleEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateScheduleEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateScheduleEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CandidateScheduleEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.candidateScheduleEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
