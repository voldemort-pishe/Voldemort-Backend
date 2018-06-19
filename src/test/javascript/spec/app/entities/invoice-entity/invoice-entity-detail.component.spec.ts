/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { InvoiceEntityDetailComponent } from '../../../../../../main/webapp/app/entities/invoice-entity/invoice-entity-detail.component';
import { InvoiceEntityService } from '../../../../../../main/webapp/app/entities/invoice-entity/invoice-entity.service';
import { InvoiceEntity } from '../../../../../../main/webapp/app/entities/invoice-entity/invoice-entity.model';

describe('Component Tests', () => {

    describe('InvoiceEntity Management Detail Component', () => {
        let comp: InvoiceEntityDetailComponent;
        let fixture: ComponentFixture<InvoiceEntityDetailComponent>;
        let service: InvoiceEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [InvoiceEntityDetailComponent],
                providers: [
                    InvoiceEntityService
                ]
            })
            .overrideTemplate(InvoiceEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvoiceEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InvoiceEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.invoiceEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
