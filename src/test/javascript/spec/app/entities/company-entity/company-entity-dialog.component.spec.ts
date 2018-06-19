/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { CompanyEntityDialogComponent } from '../../../../../../main/webapp/app/entities/company-entity/company-entity-dialog.component';
import { CompanyEntityService } from '../../../../../../main/webapp/app/entities/company-entity/company-entity.service';
import { CompanyEntity } from '../../../../../../main/webapp/app/entities/company-entity/company-entity.model';
import { UserEntityService } from '../../../../../../main/webapp/app/entities/user-entity';

describe('Component Tests', () => {

    describe('CompanyEntity Management Dialog Component', () => {
        let comp: CompanyEntityDialogComponent;
        let fixture: ComponentFixture<CompanyEntityDialogComponent>;
        let service: CompanyEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CompanyEntityDialogComponent],
                providers: [
                    UserEntityService,
                    CompanyEntityService
                ]
            })
            .overrideTemplate(CompanyEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.companyEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.companyEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
