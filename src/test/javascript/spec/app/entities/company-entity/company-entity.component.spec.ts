/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { CompanyEntityComponent } from '../../../../../../main/webapp/app/entities/company-entity/company-entity.component';
import { CompanyEntityService } from '../../../../../../main/webapp/app/entities/company-entity/company-entity.service';
import { CompanyEntity } from '../../../../../../main/webapp/app/entities/company-entity/company-entity.model';

describe('Component Tests', () => {

    describe('CompanyEntity Management Component', () => {
        let comp: CompanyEntityComponent;
        let fixture: ComponentFixture<CompanyEntityComponent>;
        let service: CompanyEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CompanyEntityComponent],
                providers: [
                    CompanyEntityService
                ]
            })
            .overrideTemplate(CompanyEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CompanyEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
