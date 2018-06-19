/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { UserAuthorityEntityDetailComponent } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity-detail.component';
import { UserAuthorityEntityService } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity.service';
import { UserAuthorityEntity } from '../../../../../../main/webapp/app/entities/user-authority-entity/user-authority-entity.model';

describe('Component Tests', () => {

    describe('UserAuthorityEntity Management Detail Component', () => {
        let comp: UserAuthorityEntityDetailComponent;
        let fixture: ComponentFixture<UserAuthorityEntityDetailComponent>;
        let service: UserAuthorityEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserAuthorityEntityDetailComponent],
                providers: [
                    UserAuthorityEntityService
                ]
            })
            .overrideTemplate(UserAuthorityEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAuthorityEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAuthorityEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserAuthorityEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userAuthorityEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
