/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { UserEntityComponent } from '../../../../../../main/webapp/app/entities/user-entity/user-entity.component';
import { UserEntityService } from '../../../../../../main/webapp/app/entities/user-entity/user-entity.service';
import { UserEntity } from '../../../../../../main/webapp/app/entities/user-entity/user-entity.model';

describe('Component Tests', () => {

    describe('UserEntity Management Component', () => {
        let comp: UserEntityComponent;
        let fixture: ComponentFixture<UserEntityComponent>;
        let service: UserEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [UserEntityComponent],
                providers: [
                    UserEntityService
                ]
            })
            .overrideTemplate(UserEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
