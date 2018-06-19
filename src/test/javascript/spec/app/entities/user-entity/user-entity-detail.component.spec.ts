/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { UserEntityDetailComponent } from '../../../../../../main/webapp/app/entities/user-entity/user-entity-detail.component';
import { UserEntityService } from '../../../../../../main/webapp/app/entities/user-entity/user-entity.service';
import { UserEntity } from '../../../../../../main/webapp/app/entities/user-entity/user-entity.model';

describe('Component Tests', () => {

    describe('UserEntity Management Detail Component', () => {
        let comp: UserEntityDetailComponent;
        let fixture: ComponentFixture<UserEntityDetailComponent>;
        let service: UserEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserEntityDetailComponent],
                providers: [
                    UserEntityService
                ]
            })
            .overrideTemplate(UserEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
