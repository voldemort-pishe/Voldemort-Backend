/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateEvaluationCriteriaEntityComponent } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity.component';
import { CandidateEvaluationCriteriaEntityService } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity.service';
import { CandidateEvaluationCriteriaEntity } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity.model';

describe('Component Tests', () => {

    describe('CandidateEvaluationCriteriaEntity Management Component', () => {
        let comp: CandidateEvaluationCriteriaEntityComponent;
        let fixture: ComponentFixture<CandidateEvaluationCriteriaEntityComponent>;
        let service: CandidateEvaluationCriteriaEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateEvaluationCriteriaEntityComponent],
                providers: [
                    CandidateEvaluationCriteriaEntityService
                ]
            })
            .overrideTemplate(CandidateEvaluationCriteriaEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateEvaluationCriteriaEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateEvaluationCriteriaEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CandidateEvaluationCriteriaEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.candidateEvaluationCriteriaEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
