/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { JobEntityDetailComponent } from '../../../../../../main/webapp/app/entities/job-entity/job-entity-detail.component';
import { JobEntityService } from '../../../../../../main/webapp/app/entities/job-entity/job-entity.service';
import { JobEntity } from '../../../../../../main/webapp/app/entities/job-entity/job-entity.model';

describe('Component Tests', () => {

    describe('JobEntity Management Detail Component', () => {
        let comp: JobEntityDetailComponent;
        let fixture: ComponentFixture<JobEntityDetailComponent>;
        let service: JobEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [JobEntityDetailComponent],
                providers: [
                    JobEntityService
                ]
            })
            .overrideTemplate(JobEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new JobEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.jobEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
