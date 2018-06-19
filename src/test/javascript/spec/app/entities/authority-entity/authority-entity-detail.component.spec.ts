/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { AuthorityEntityDetailComponent } from '../../../../../../main/webapp/app/entities/authority-entity/authority-entity-detail.component';
import { AuthorityEntityService } from '../../../../../../main/webapp/app/entities/authority-entity/authority-entity.service';
import { AuthorityEntity } from '../../../../../../main/webapp/app/entities/authority-entity/authority-entity.model';

describe('Component Tests', () => {

    describe('AuthorityEntity Management Detail Component', () => {
        let comp: AuthorityEntityDetailComponent;
        let fixture: ComponentFixture<AuthorityEntityDetailComponent>;
        let service: AuthorityEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [AuthorityEntityDetailComponent],
                providers: [
                    AuthorityEntityService
                ]
            })
            .overrideTemplate(AuthorityEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuthorityEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuthorityEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AuthorityEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.authorityEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
