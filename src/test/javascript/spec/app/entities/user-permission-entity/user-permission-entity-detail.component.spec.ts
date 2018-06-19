/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { UserPermissionEntityDetailComponent } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity-detail.component';
import { UserPermissionEntityService } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity.service';
import { UserPermissionEntity } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity.model';

describe('Component Tests', () => {

    describe('UserPermissionEntity Management Detail Component', () => {
        let comp: UserPermissionEntityDetailComponent;
        let fixture: ComponentFixture<UserPermissionEntityDetailComponent>;
        let service: UserPermissionEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserPermissionEntityDetailComponent],
                providers: [
                    UserPermissionEntityService
                ]
            })
            .overrideTemplate(UserPermissionEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPermissionEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPermissionEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserPermissionEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userPermissionEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
