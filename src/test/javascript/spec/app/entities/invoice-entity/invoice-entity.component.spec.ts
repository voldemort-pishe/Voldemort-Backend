/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { InvoiceEntityComponent } from '../../../../../../main/webapp/app/entities/invoice-entity/invoice-entity.component';
import { InvoiceEntityService } from '../../../../../../main/webapp/app/entities/invoice-entity/invoice-entity.service';
import { InvoiceEntity } from '../../../../../../main/webapp/app/entities/invoice-entity/invoice-entity.model';

describe('Component Tests', () => {

    describe('InvoiceEntity Management Component', () => {
        let comp: InvoiceEntityComponent;
        let fixture: ComponentFixture<InvoiceEntityComponent>;
        let service: InvoiceEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [InvoiceEntityComponent],
                providers: [
                    InvoiceEntityService
                ]
            })
            .overrideTemplate(InvoiceEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvoiceEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InvoiceEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.invoiceEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
