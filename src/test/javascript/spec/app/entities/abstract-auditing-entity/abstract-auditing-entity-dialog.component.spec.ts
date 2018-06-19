/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { AbstractAuditingEntityDialogComponent } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity-dialog.component';
import { AbstractAuditingEntityService } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity.service';
import { AbstractAuditingEntity } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity.model';

describe('Component Tests', () => {

    describe('AbstractAuditingEntity Management Dialog Component', () => {
        let comp: AbstractAuditingEntityDialogComponent;
        let fixture: ComponentFixture<AbstractAuditingEntityDialogComponent>;
        let service: AbstractAuditingEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [AbstractAuditingEntityDialogComponent],
                providers: [
                    AbstractAuditingEntityService
                ]
            })
            .overrideTemplate(AbstractAuditingEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbstractAuditingEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbstractAuditingEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AbstractAuditingEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.abstractAuditingEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'abstractAuditingEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AbstractAuditingEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.abstractAuditingEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'abstractAuditingEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
