/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateEntityDetailComponent } from '../../../../../../main/webapp/app/entities/candidate-entity/candidate-entity-detail.component';
import { CandidateEntityService } from '../../../../../../main/webapp/app/entities/candidate-entity/candidate-entity.service';
import { CandidateEntity } from '../../../../../../main/webapp/app/entities/candidate-entity/candidate-entity.model';

describe('Component Tests', () => {

    describe('CandidateEntity Management Detail Component', () => {
        let comp: CandidateEntityDetailComponent;
        let fixture: ComponentFixture<CandidateEntityDetailComponent>;
        let service: CandidateEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateEntityDetailComponent],
                providers: [
                    CandidateEntityService
                ]
            })
            .overrideTemplate(CandidateEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CandidateEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.candidateEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
