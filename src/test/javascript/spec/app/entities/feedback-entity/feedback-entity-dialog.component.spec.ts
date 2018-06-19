/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { FeedbackEntityDialogComponent } from '../../../../../../main/webapp/app/entities/feedback-entity/feedback-entity-dialog.component';
import { FeedbackEntityService } from '../../../../../../main/webapp/app/entities/feedback-entity/feedback-entity.service';
import { FeedbackEntity } from '../../../../../../main/webapp/app/entities/feedback-entity/feedback-entity.model';
import { CandidateEntityService } from '../../../../../../main/webapp/app/entities/candidate-entity';

describe('Component Tests', () => {

    describe('FeedbackEntity Management Dialog Component', () => {
        let comp: FeedbackEntityDialogComponent;
        let fixture: ComponentFixture<FeedbackEntityDialogComponent>;
        let service: FeedbackEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [FeedbackEntityDialogComponent],
                providers: [
                    CandidateEntityService,
                    FeedbackEntityService
                ]
            })
            .overrideTemplate(FeedbackEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeedbackEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeedbackEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FeedbackEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.feedbackEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'feedbackEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FeedbackEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.feedbackEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'feedbackEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
