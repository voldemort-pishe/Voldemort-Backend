/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { CompanyPipelineEntityDialogComponent } from '../../../../../../main/webapp/app/entities/company-pipeline-entity/company-pipeline-entity-dialog.component';
import { CompanyPipelineEntityService } from '../../../../../../main/webapp/app/entities/company-pipeline-entity/company-pipeline-entity.service';
import { CompanyPipelineEntity } from '../../../../../../main/webapp/app/entities/company-pipeline-entity/company-pipeline-entity.model';
import { CompanyEntityService } from '../../../../../../main/webapp/app/entities/company-entity';

describe('Component Tests', () => {

    describe('CompanyPipelineEntity Management Dialog Component', () => {
        let comp: CompanyPipelineEntityDialogComponent;
        let fixture: ComponentFixture<CompanyPipelineEntityDialogComponent>;
        let service: CompanyPipelineEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CompanyPipelineEntityDialogComponent],
                providers: [
                    CompanyEntityService,
                    CompanyPipelineEntityService
                ]
            })
            .overrideTemplate(CompanyPipelineEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyPipelineEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyPipelineEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyPipelineEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.companyPipelineEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyPipelineEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyPipelineEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.companyPipelineEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyPipelineEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
