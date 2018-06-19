/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { UserAuthorityEntityComponent } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity.component';
import { UserAuthorityEntityService } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity.service';
import { UserAuthorityEntity } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity.model';

describe('Component Tests', () => {

    describe('UserAuthorityEntity Management Component', () => {
        let comp: UserAuthorityEntityComponent;
        let fixture: ComponentFixture<UserAuthorityEntityComponent>;
        let service: UserAuthorityEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserAuthorityEntityComponent],
                providers: [
                    UserAuthorityEntityService
                ]
            })
            .overrideTemplate(UserAuthorityEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAuthorityEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAuthorityEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserAuthorityEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userAuthorityEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
