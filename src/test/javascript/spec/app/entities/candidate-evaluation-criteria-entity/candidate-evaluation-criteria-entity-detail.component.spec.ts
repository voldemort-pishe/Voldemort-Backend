/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateEvaluationCriteriaEntityDetailComponent } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity-detail.component';
import { CandidateEvaluationCriteriaEntityService } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity.service';
import { CandidateEvaluationCriteriaEntity } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity.model';

describe('Component Tests', () => {

    describe('CandidateEvaluationCriteriaEntity Management Detail Component', () => {
        let comp: CandidateEvaluationCriteriaEntityDetailComponent;
        let fixture: ComponentFixture<CandidateEvaluationCriteriaEntityDetailComponent>;
        let service: CandidateEvaluationCriteriaEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateEvaluationCriteriaEntityDetailComponent],
                providers: [
                    CandidateEvaluationCriteriaEntityService
                ]
            })
            .overrideTemplate(CandidateEvaluationCriteriaEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateEvaluationCriteriaEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateEvaluationCriteriaEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CandidateEvaluationCriteriaEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.candidateEvaluationCriteriaEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
