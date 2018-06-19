/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { CommentEntityDialogComponent } from '../../../../../../main/webapp/app/entities/comment-entity/comment-entity-dialog.component';
import { CommentEntityService } from '../../../../../../main/webapp/app/entities/comment-entity/comment-entity.service';
import { CommentEntity } from '../../../../../../main/webapp/app/entities/comment-entity/comment-entity.model';
import { CandidateEntityService } from '../../../../../../main/webapp/app/entities/candidate-entity';

describe('Component Tests', () => {

    describe('CommentEntity Management Dialog Component', () => {
        let comp: CommentEntityDialogComponent;
        let fixture: ComponentFixture<CommentEntityDialogComponent>;
        let service: CommentEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CommentEntityDialogComponent],
                providers: [
                    CandidateEntityService,
                    CommentEntityService
                ]
            })
            .overrideTemplate(CommentEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CommentEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.commentEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'commentEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CommentEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.commentEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'commentEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
