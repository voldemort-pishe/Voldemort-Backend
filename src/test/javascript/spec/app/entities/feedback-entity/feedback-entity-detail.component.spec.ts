/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { FeedbackEntityDetailComponent } from '../../../../../../main/webapp/app/entities/feedback-entity/feedback-entity-detail.component';
import { FeedbackEntityService } from '../../../../../../main/webapp/app/entities/feedback-entity/feedback-entity.service';
import { FeedbackEntity } from '../../../../../../main/webapp/app/entities/feedback-entity/feedback-entity.model';

describe('Component Tests', () => {

    describe('FeedbackEntity Management Detail Component', () => {
        let comp: FeedbackEntityDetailComponent;
        let fixture: ComponentFixture<FeedbackEntityDetailComponent>;
        let service: FeedbackEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [FeedbackEntityDetailComponent],
                providers: [
                    FeedbackEntityService
                ]
            })
            .overrideTemplate(FeedbackEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeedbackEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeedbackEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FeedbackEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.feedbackEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
