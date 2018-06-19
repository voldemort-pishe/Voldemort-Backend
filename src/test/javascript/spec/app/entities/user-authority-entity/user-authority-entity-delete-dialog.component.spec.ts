/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { UserAuthorityEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity-delete-dialog.component';
import { UserAuthorityEntityService } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity.service';

describe('Component Tests', () => {

    describe('UserAuthorityEntity Management Delete Component', () => {
        let comp: UserAuthorityEntityDeleteDialogComponent;
        let fixture: ComponentFixture<UserAuthorityEntityDeleteDialogComponent>;
        let service: UserAuthorityEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserAuthorityEntityDeleteDialogComponent],
                providers: [
                    UserAuthorityEntityService
                ]
            })
            .overrideTemplate(UserAuthorityEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAuthorityEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAuthorityEntityService);
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
