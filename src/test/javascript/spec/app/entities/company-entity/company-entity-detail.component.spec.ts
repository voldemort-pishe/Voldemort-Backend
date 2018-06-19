/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { CompanyEntityDetailComponent } from '../../../../../../main/webapp/app/entities/company-entity/company-entity-detail.component';
import { CompanyEntityService } from '../../../../../../main/webapp/app/entities/company-entity/company-entity.service';
import { CompanyEntity } from '../../../../../../main/webapp/app/entities/company-entity/company-entity.model';

describe('Component Tests', () => {

    describe('CompanyEntity Management Detail Component', () => {
        let comp: CompanyEntityDetailComponent;
        let fixture: ComponentFixture<CompanyEntityDetailComponent>;
        let service: CompanyEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CompanyEntityDetailComponent],
                providers: [
                    CompanyEntityService
                ]
            })
            .overrideTemplate(CompanyEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CompanyEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
