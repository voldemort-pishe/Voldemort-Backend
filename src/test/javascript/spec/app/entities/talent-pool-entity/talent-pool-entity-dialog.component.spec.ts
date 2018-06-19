/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { TalentPoolEntityDialogComponent } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity-dialog.component';
import { TalentPoolEntityService } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity.service';
import { TalentPoolEntity } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity.model';
import { UserEntityService } from '../../../../../../main/webapp/app/entities/user-entity';

describe('Component Tests', () => {

    describe('TalentPoolEntity Management Dialog Component', () => {
        let comp: TalentPoolEntityDialogComponent;
        let fixture: ComponentFixture<TalentPoolEntityDialogComponent>;
        let service: TalentPoolEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [TalentPoolEntityDialogComponent],
                providers: [
                    UserEntityService,
                    TalentPoolEntityService
                ]
            })
            .overrideTemplate(TalentPoolEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TalentPoolEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TalentPoolEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TalentPoolEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.talentPoolEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'talentPoolEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TalentPoolEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.talentPoolEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'talentPoolEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
