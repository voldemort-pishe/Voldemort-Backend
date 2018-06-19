/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { PlanEntityDetailComponent } from '../../../../../../main/webapp/app/entities/plan-entity/plan-entity-detail.component';
import { PlanEntityService } from '../../../../../../main/webapp/app/entities/plan-entity/plan-entity.service';
import { PlanEntity } from '../../../../../../main/webapp/app/entities/plan-entity/plan-entity.model';

describe('Component Tests', () => {

    describe('PlanEntity Management Detail Component', () => {
        let comp: PlanEntityDetailComponent;
        let fixture: ComponentFixture<PlanEntityDetailComponent>;
        let service: PlanEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [PlanEntityDetailComponent],
                providers: [
                    PlanEntityService
                ]
            })
            .overrideTemplate(PlanEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlanEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PlanEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.planEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
