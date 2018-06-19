/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { TalentPoolEntityComponent } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity.component';
import { TalentPoolEntityService } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity.service';
import { TalentPoolEntity } from '../../../../../../main/webapp/app/entities/talent-pool-entity/talent-pool-entity.model';

describe('Component Tests', () => {

    describe('TalentPoolEntity Management Component', () => {
        let comp: TalentPoolEntityComponent;
        let fixture: ComponentFixture<TalentPoolEntityComponent>;
        let service: TalentPoolEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [TalentPoolEntityComponent],
                providers: [
                    TalentPoolEntityService
                ]
            })
            .overrideTemplate(TalentPoolEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TalentPoolEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TalentPoolEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TalentPoolEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.talentPoolEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
