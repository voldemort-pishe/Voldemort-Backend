/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { PaymentTransactionEntityComponent } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity.component';
import { PaymentTransactionEntityService } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity.service';
import { PaymentTransactionEntity } from '../../../../../../main/webapp/app/entities/payment-transaction-entity/payment-transaction-entity.model';

describe('Component Tests', () => {

    describe('PaymentTransactionEntity Management Component', () => {
        let comp: PaymentTransactionEntityComponent;
        let fixture: ComponentFixture<PaymentTransactionEntityComponent>;
        let service: PaymentTransactionEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [PaymentTransactionEntityComponent],
                providers: [
                    PaymentTransactionEntityService
                ]
            })
            .overrideTemplate(PaymentTransactionEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentTransactionEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentTransactionEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PaymentTransactionEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.paymentTransactionEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
