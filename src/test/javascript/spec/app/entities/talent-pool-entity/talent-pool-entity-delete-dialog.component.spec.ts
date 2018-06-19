/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { TalentPoolEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity-delete-dialog.component';
import { TalentPoolEntityService } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity.service';

describe('Component Tests', () => {

    describe('TalentPoolEntity Management Delete Component', () => {
        let comp: TalentPoolEntityDeleteDialogComponent;
        let fixture: ComponentFixture<TalentPoolEntityDeleteDialogComponent>;
        let service: TalentPoolEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [TalentPoolEntityDeleteDialogComponent],
                providers: [
                    TalentPoolEntityService
                ]
            })
            .overrideTemplate(TalentPoolEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TalentPoolEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TalentPoolEntityService);
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
