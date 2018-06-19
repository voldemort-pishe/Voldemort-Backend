/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { AbstractAuditingEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity-delete-dialog.component';
import { AbstractAuditingEntityService } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity.service';

describe('Component Tests', () => {

    describe('AbstractAuditingEntity Management Delete Component', () => {
        let comp: AbstractAuditingEntityDeleteDialogComponent;
        let fixture: ComponentFixture<AbstractAuditingEntityDeleteDialogComponent>;
        let service: AbstractAuditingEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [AbstractAuditingEntityDeleteDialogComponent],
                providers: [
                    AbstractAuditingEntityService
                ]
            })
            .overrideTemplate(AbstractAuditingEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbstractAuditingEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbstractAuditingEntityService);
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
