/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { TalentPoolEntityDetailComponent } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity-detail.component';
import { TalentPoolEntityService } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity.service';
import { TalentPoolEntity } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity.model';

describe('Component Tests', () => {

    describe('TalentPoolEntity Management Detail Component', () => {
        let comp: TalentPoolEntityDetailComponent;
        let fixture: ComponentFixture<TalentPoolEntityDetailComponent>;
        let service: TalentPoolEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [TalentPoolEntityDetailComponent],
                providers: [
                    TalentPoolEntityService
                ]
            })
            .overrideTemplate(TalentPoolEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TalentPoolEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TalentPoolEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TalentPoolEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.talentPoolEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
