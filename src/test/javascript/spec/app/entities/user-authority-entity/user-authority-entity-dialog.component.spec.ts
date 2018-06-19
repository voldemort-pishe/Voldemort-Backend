/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { UserAuthorityEntityDialogComponent } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity-dialog.component';
import { UserAuthorityEntityService } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity.service';
import { UserAuthorityEntity } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity.model';
import { UserEntityService } from '../../../../../../main/webapp/app/entities/user-entity';

describe('Component Tests', () => {

    describe('UserAuthorityEntity Management Dialog Component', () => {
        let comp: UserAuthorityEntityDialogComponent;
        let fixture: ComponentFixture<UserAuthorityEntityDialogComponent>;
        let service: UserAuthorityEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserAuthorityEntityDialogComponent],
                providers: [
                    UserEntityService,
                    UserAuthorityEntityService
                ]
            })
            .overrideTemplate(UserAuthorityEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAuthorityEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAuthorityEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserAuthorityEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userAuthorityEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userAuthorityEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserAuthorityEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userAuthorityEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userAuthorityEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
