/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { UserPermissionEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity-delete-dialog.component';
import { UserPermissionEntityService } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity.service';

describe('Component Tests', () => {

    describe('UserPermissionEntity Management Delete Component', () => {
        let comp: UserPermissionEntityDeleteDialogComponent;
        let fixture: ComponentFixture<UserPermissionEntityDeleteDialogComponent>;
        let service: UserPermissionEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserPermissionEntityDeleteDialogComponent],
                providers: [
                    UserPermissionEntityService
                ]
            })
            .overrideTemplate(UserPermissionEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPermissionEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPermissionEntityService);
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
