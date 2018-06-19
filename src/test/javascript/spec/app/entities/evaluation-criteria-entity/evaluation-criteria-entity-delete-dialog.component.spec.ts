/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { EvaluationCriteriaEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity-delete-dialog.component';
import { EvaluationCriteriaEntityService } from '../../../../../../main/webapp/app/entities/evaluation-criteria-entity/evaluation-criteria-entity.service';

describe('Component Tests', () => {

    describe('EvaluationCriteriaEntity Management Delete Component', () => {
        let comp: EvaluationCriteriaEntityDeleteDialogComponent;
        let fixture: ComponentFixture<EvaluationCriteriaEntityDeleteDialogComponent>;
        let service: EvaluationCriteriaEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [EvaluationCriteriaEntityDeleteDialogComponent],
                providers: [
                    EvaluationCriteriaEntityService
                ]
            })
            .overrideTemplate(EvaluationCriteriaEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EvaluationCriteriaEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationCriteriaEntityService);
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
