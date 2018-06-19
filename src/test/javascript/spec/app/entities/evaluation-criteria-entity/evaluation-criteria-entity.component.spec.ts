/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { EvaluationCriteriaEntityComponent } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity.component';
import { EvaluationCriteriaEntityService } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity.service';
import { EvaluationCriteriaEntity } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity.model';

describe('Component Tests', () => {

    describe('EvaluationCriteriaEntity Management Component', () => {
        let comp: EvaluationCriteriaEntityComponent;
        let fixture: ComponentFixture<EvaluationCriteriaEntityComponent>;
        let service: EvaluationCriteriaEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [EvaluationCriteriaEntityComponent],
                providers: [
                    EvaluationCriteriaEntityService
                ]
            })
            .overrideTemplate(EvaluationCriteriaEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EvaluationCriteriaEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationCriteriaEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EvaluationCriteriaEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.evaluationCriteriaEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
