/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { PaymentTransactionEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity-delete-dialog.component';
import { PaymentTransactionEntityService } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity.service';

describe('Component Tests', () => {

    describe('PaymentTransactionEntity Management Delete Component', () => {
        let comp: PaymentTransactionEntityDeleteDialogComponent;
        let fixture: ComponentFixture<PaymentTransactionEntityDeleteDialogComponent>;
        let service: PaymentTransactionEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [PaymentTransactionEntityDeleteDialogComponent],
                providers: [
                    PaymentTransactionEntityService
                ]
            })
            .overrideTemplate(PaymentTransactionEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentTransactionEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentTransactionEntityService);
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
