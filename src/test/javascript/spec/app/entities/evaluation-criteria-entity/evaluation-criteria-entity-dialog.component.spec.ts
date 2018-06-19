/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { EvaluationCriteriaEntityDialogComponent } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity-dialog.component';
import { EvaluationCriteriaEntityService } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity.service';
import { EvaluationCriteriaEntity } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity.model';
import { CompanyEntityService } from '../../../../../../main/webapp/app/entities/company-entity';

describe('Component Tests', () => {

    describe('EvaluationCriteriaEntity Management Dialog Component', () => {
        let comp: EvaluationCriteriaEntityDialogComponent;
        let fixture: ComponentFixture<EvaluationCriteriaEntityDialogComponent>;
        let service: EvaluationCriteriaEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [EvaluationCriteriaEntityDialogComponent],
                providers: [
                    CompanyEntityService,
                    EvaluationCriteriaEntityService
                ]
            })
            .overrideTemplate(EvaluationCriteriaEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EvaluationCriteriaEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationCriteriaEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EvaluationCriteriaEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.evaluationCriteriaEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'evaluationCriteriaEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EvaluationCriteriaEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.evaluationCriteriaEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'evaluationCriteriaEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
