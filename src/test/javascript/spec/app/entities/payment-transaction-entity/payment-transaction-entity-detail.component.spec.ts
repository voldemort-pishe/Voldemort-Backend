/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { PaymentTransactionEntityDetailComponent } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity-detail.component';
import { PaymentTransactionEntityService } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity.service';
import { PaymentTransactionEntity } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity.model';

describe('Component Tests', () => {

    describe('PaymentTransactionEntity Management Detail Component', () => {
        let comp: PaymentTransactionEntityDetailComponent;
        let fixture: ComponentFixture<PaymentTransactionEntityDetailComponent>;
        let service: PaymentTransactionEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [PaymentTransactionEntityDetailComponent],
                providers: [
                    PaymentTransactionEntityService
                ]
            })
            .overrideTemplate(PaymentTransactionEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentTransactionEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentTransactionEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PaymentTransactionEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.paymentTransactionEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
