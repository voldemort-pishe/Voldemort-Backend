/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { CandidateEntityComponent } from '../../../../../../main/webapp/app/entities/candidate-entity/candidate-entity.component';
import { CandidateEntityService } from '../../../../../../main/webapp/app/entities/candidate-entity/candidate-entity.service';
import { CandidateEntity } from '../../../../../../main/webapp/app/entities/candidate-entity/candidate-entity.model';

describe('Component Tests', () => {

    describe('CandidateEntity Management Component', () => {
        let comp: CandidateEntityComponent;
        let fixture: ComponentFixture<CandidateEntityComponent>;
        let service: CandidateEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CandidateEntityComponent],
                providers: [
                    CandidateEntityService
                ]
            })
            .overrideTemplate(CandidateEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CandidateEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.candidateEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
