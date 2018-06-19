/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { CompanyPipelineEntityComponent } from '../../../../../../main/webapp/app/entities/company-pipeline-entity/company-pipeline-entity.component';
import { CompanyPipelineEntityService } from '../../../../../../main/webapp/app/entities/company-pipeline-entity/company-pipeline-entity.service';
import { CompanyPipelineEntity } from '../../../../../../main/webapp/app/entities/company-pipeline-entity/company-pipeline-entity.model';

describe('Component Tests', () => {

    describe('CompanyPipelineEntity Management Component', () => {
        let comp: CompanyPipelineEntityComponent;
        let fixture: ComponentFixture<CompanyPipelineEntityComponent>;
        let service: CompanyPipelineEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CompanyPipelineEntityComponent],
                providers: [
                    CompanyPipelineEntityService
                ]
            })
            .overrideTemplate(CompanyPipelineEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyPipelineEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyPipelineEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CompanyPipelineEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyPipelineEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
