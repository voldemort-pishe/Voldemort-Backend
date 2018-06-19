/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { UserPermissionEntityComponent } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity.component';
import { UserPermissionEntityService } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity.service';
import { UserPermissionEntity } from '../../../../../../main/webapp/app/entities/user-permission-entity/user-permission-entity.model';

describe('Component Tests', () => {

    describe('UserPermissionEntity Management Component', () => {
        let comp: UserPermissionEntityComponent;
        let fixture: ComponentFixture<UserPermissionEntityComponent>;
        let service: UserPermissionEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserPermissionEntityComponent],
                providers: [
                    UserPermissionEntityService
                ]
            })
            .overrideTemplate(UserPermissionEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPermissionEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPermissionEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserPermissionEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userPermissionEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
