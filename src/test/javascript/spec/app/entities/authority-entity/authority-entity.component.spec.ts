/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { AuthorityEntityComponent } from '../../../../../../main/webapp/app/entities/authority-entity/authority-entity.component';
import { AuthorityEntityService } from '../../../../../../main/webapp/app/entities/authority-entity/authority-entity.service';
import { AuthorityEntity } from '../../../../../../main/webapp/app/entities/authority-entity/authority-entity.model';

describe('Component Tests', () => {

    describe('AuthorityEntity Management Component', () => {
        let comp: AuthorityEntityComponent;
        let fixture: ComponentFixture<AuthorityEntityComponent>;
        let service: AuthorityEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [AuthorityEntityComponent],
                providers: [
                    AuthorityEntityService
                ]
            })
            .overrideTemplate(AuthorityEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuthorityEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuthorityEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AuthorityEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.authorityEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
