/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { CompanyPipelineEntityDetailComponent } from '../../../../../../main/webapp/app/entities/company-pipeline-entity/company-pipeline-entity-detail.component';
import { CompanyPipelineEntityService } from '../../../../../../main/webapp/app/entities/company-pipeline-entity/company-pipeline-entity.service';
import { CompanyPipelineEntity } from '../../../../../../main/webapp/app/entities/company-pipeline-entity/company-pipeline-entity.model';

describe('Component Tests', () => {

    describe('CompanyPipelineEntity Management Detail Component', () => {
        let comp: CompanyPipelineEntityDetailComponent;
        let fixture: ComponentFixture<CompanyPipelineEntityDetailComponent>;
        let service: CompanyPipelineEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CompanyPipelineEntityDetailComponent],
                providers: [
                    CompanyPipelineEntityService
                ]
            })
            .overrideTemplate(CompanyPipelineEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyPipelineEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyPipelineEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CompanyPipelineEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyPipelineEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
