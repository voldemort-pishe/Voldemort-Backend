/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { EvaluationCriteriaEntityDetailComponent } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity-detail.component';
import { EvaluationCriteriaEntityService } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity.service';
import { EvaluationCriteriaEntity } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity.model';

describe('Component Tests', () => {

    describe('EvaluationCriteriaEntity Management Detail Component', () => {
        let comp: EvaluationCriteriaEntityDetailComponent;
        let fixture: ComponentFixture<EvaluationCriteriaEntityDetailComponent>;
        let service: EvaluationCriteriaEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [EvaluationCriteriaEntityDetailComponent],
                providers: [
                    EvaluationCriteriaEntityService
                ]
            })
            .overrideTemplate(EvaluationCriteriaEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EvaluationCriteriaEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationCriteriaEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EvaluationCriteriaEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.evaluationCriteriaEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
