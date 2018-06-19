/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateEvaluationCriteriaEntityDialogComponent } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity-dialog.component';
import { CandidateEvaluationCriteriaEntityService } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity.service';
import { CandidateEvaluationCriteriaEntity } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity.model';
import { CandidateEntityService } from '../../../../../../main/webapp/app/entities/candidate-entity';

describe('Component Tests', () => {

    describe('CandidateEvaluationCriteriaEntity Management Dialog Component', () => {
        let comp: CandidateEvaluationCriteriaEntityDialogComponent;
        let fixture: ComponentFixture<CandidateEvaluationCriteriaEntityDialogComponent>;
        let service: CandidateEvaluationCriteriaEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateEvaluationCriteriaEntityDialogComponent],
                providers: [
                    CandidateEntityService,
                    CandidateEvaluationCriteriaEntityService
                ]
            })
            .overrideTemplate(CandidateEvaluationCriteriaEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateEvaluationCriteriaEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateEvaluationCriteriaEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CandidateEvaluationCriteriaEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.candidateEvaluationCriteriaEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'candidateEvaluationCriteriaEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CandidateEvaluationCriteriaEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.candidateEvaluationCriteriaEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'candidateEvaluationCriteriaEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
