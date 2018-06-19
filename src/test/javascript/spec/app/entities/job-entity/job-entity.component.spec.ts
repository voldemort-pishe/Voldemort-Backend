/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { JobEntityComponent } from '../../../../../../main/webapp/app/entities/job-entity/job-entity.component';
import { JobEntityService } from '../../../../../../main/webapp/app/entities/job-entity/job-entity.service';
import { JobEntity } from '../../../../../../main/webapp/app/entities/job-entity/job-entity.model';

describe('Component Tests', () => {

    describe('JobEntity Management Component', () => {
        let comp: JobEntityComponent;
        let fixture: ComponentFixture<JobEntityComponent>;
        let service: JobEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [JobEntityComponent],
                providers: [
                    JobEntityService
                ]
            })
            .overrideTemplate(JobEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new JobEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.jobEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
