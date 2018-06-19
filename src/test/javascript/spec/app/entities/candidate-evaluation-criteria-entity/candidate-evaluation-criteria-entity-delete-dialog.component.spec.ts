/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateEvaluationCriteriaEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity-delete-dialog.component';
import { CandidateEvaluationCriteriaEntityService } from '../../../../../../main/webapp/app/entities/candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity.service';

describe('Component Tests', () => {

    describe('CandidateEvaluationCriteriaEntity Management Delete Component', () => {
        let comp: CandidateEvaluationCriteriaEntityDeleteDialogComponent;
        let fixture: ComponentFixture<CandidateEvaluationCriteriaEntityDeleteDialogComponent>;
        let service: CandidateEvaluationCriteriaEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateEvaluationCriteriaEntityDeleteDialogComponent],
                providers: [
                    CandidateEvaluationCriteriaEntityService
                ]
            })
            .overrideTemplate(CandidateEvaluationCriteriaEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateEvaluationCriteriaEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateEvaluationCriteriaEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
