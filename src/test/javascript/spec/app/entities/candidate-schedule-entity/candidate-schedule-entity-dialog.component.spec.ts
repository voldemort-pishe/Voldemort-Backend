/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateScheduleEntityDialogComponent } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity-dialog.component';
import { CandidateScheduleEntityService } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity.service';
import { CandidateScheduleEntity } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity.model';
import { CandidateEntityService } from '../../../../../../main/webapp/app/entities/candidate-entity';

describe('Component Tests', () => {

    describe('CandidateScheduleEntity Management Dialog Component', () => {
        let comp: CandidateScheduleEntityDialogComponent;
        let fixture: ComponentFixture<CandidateScheduleEntityDialogComponent>;
        let service: CandidateScheduleEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateScheduleEntityDialogComponent],
                providers: [
                    CandidateEntityService,
                    CandidateScheduleEntityService
                ]
            })
            .overrideTemplate(CandidateScheduleEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateScheduleEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateScheduleEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CandidateScheduleEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.candidateScheduleEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'candidateScheduleEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CandidateScheduleEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.candidateScheduleEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'candidateScheduleEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
