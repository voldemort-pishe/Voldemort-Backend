/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateScheduleEntityComponent } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity.component';
import { CandidateScheduleEntityService } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity.service';
import { CandidateScheduleEntity } from '../../../../../../main/webapp/app/entities/candidate-schedule-entity/candidate-schedule-entity.model';

describe('Component Tests', () => {

    describe('CandidateScheduleEntity Management Component', () => {
        let comp: CandidateScheduleEntityComponent;
        let fixture: ComponentFixture<CandidateScheduleEntityComponent>;
        let service: CandidateScheduleEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateScheduleEntityComponent],
                providers: [
                    CandidateScheduleEntityService
                ]
            })
            .overrideTemplate(CandidateScheduleEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateScheduleEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateScheduleEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CandidateScheduleEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.candidateScheduleEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
