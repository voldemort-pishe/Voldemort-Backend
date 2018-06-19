/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateScheduleEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity-delete-dialog.component';
import { CandidateScheduleEntityService } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity.service';

describe('Component Tests', () => {

    describe('CandidateScheduleEntity Management Delete Component', () => {
        let comp: CandidateScheduleEntityDeleteDialogComponent;
        let fixture: ComponentFixture<CandidateScheduleEntityDeleteDialogComponent>;
        let service: CandidateScheduleEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateScheduleEntityDeleteDialogComponent],
                providers: [
                    CandidateScheduleEntityService
                ]
            })
            .overrideTemplate(CandidateScheduleEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateScheduleEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateScheduleEntityService);
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
