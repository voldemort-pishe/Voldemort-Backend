/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoldemortTestModule } from '../../../test.module';
import { PaymentTransactionEntityDialogComponent } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity-dialog.component';
import { PaymentTransactionEntityService } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity.service';
import { PaymentTransactionEntity } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity.model';
import { InvoiceEntityService } from '../../../../../../main/webapp/app/entities/invoice-entity';

describe('Component Tests', () => {

    describe('PaymentTransactionEntity Management Dialog Component', () => {
        let comp: PaymentTransactionEntityDialogComponent;
        let fixture: ComponentFixture<PaymentTransactionEntityDialogComponent>;
        let service: PaymentTransactionEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [PaymentTransactionEntityDialogComponent],
                providers: [
                    InvoiceEntityService,
                    PaymentTransactionEntityService
                ]
            })
            .overrideTemplate(PaymentTransactionEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentTransactionEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentTransactionEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PaymentTransactionEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.paymentTransactionEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'paymentTransactionEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PaymentTransactionEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.paymentTransactionEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'paymentTransactionEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
