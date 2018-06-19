/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { FeedbackEntityComponent } from '../../../../../../main/webapp/app/entities/feedback-entity/feedback-entity.component';
import { FeedbackEntityService } from '../../../../../../main/webapp/app/entities/feedback-entity/feedback-entity.service';
import { FeedbackEntity } from '../../../../../../main/webapp/app/entities/feedback-entity/feedback-entity.model';

describe('Component Tests', () => {

    describe('FeedbackEntity Management Component', () => {
        let comp: FeedbackEntityComponent;
        let fixture: ComponentFixture<FeedbackEntityComponent>;
        let service: FeedbackEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [FeedbackEntityComponent],
                providers: [
                    FeedbackEntityService
                ]
            })
            .overrideTemplate(FeedbackEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeedbackEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeedbackEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FeedbackEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.feedbackEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
