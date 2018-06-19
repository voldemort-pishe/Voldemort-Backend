/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { UserPermissionEntityDialogComponent } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity-dialog.component';
import { UserPermissionEntityService } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity.service';
import { UserPermissionEntity } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity.model';
import { UserAuthorityEntityService } from '../../../../../../main/webapp/app/entities/user-authority-entity';

describe('Component Tests', () => {

    describe('UserPermissionEntity Management Dialog Component', () => {
        let comp: UserPermissionEntityDialogComponent;
        let fixture: ComponentFixture<UserPermissionEntityDialogComponent>;
        let service: UserPermissionEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserPermissionEntityDialogComponent],
                providers: [
                    UserAuthorityEntityService,
                    UserPermissionEntityService
                ]
            })
            .overrideTemplate(UserPermissionEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPermissionEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPermissionEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserPermissionEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userPermissionEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userPermissionEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserPermissionEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userPermissionEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userPermissionEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
