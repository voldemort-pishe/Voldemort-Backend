/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { PlanEntityComponent } from '../../../../../../main/webapp/app/entities/plan-entity/plan-entity.component';
import { PlanEntityService } from '../../../../../../main/webapp/app/entities/plan-entity/plan-entity.service';
import { PlanEntity } from '../../../../../../main/webapp/app/entities/plan-entity/plan-entity.model';

describe('Component Tests', () => {

    describe('PlanEntity Management Component', () => {
        let comp: PlanEntityComponent;
        let fixture: ComponentFixture<PlanEntityComponent>;
        let service: PlanEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [PlanEntityComponent],
                providers: [
                    PlanEntityService
                ]
            })
            .overrideTemplate(PlanEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlanEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PlanEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.planEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
