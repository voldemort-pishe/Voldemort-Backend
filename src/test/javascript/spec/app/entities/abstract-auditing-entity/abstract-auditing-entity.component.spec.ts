/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { AbstractAuditingEntityComponent } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity.component';
import { AbstractAuditingEntityService } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity.service';
import { AbstractAuditingEntity } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity.model';

describe('Component Tests', () => {

    describe('AbstractAuditingEntity Management Component', () => {
        let comp: AbstractAuditingEntityComponent;
        let fixture: ComponentFixture<AbstractAuditingEntityComponent>;
        let service: AbstractAuditingEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [AbstractAuditingEntityComponent],
                providers: [
                    AbstractAuditingEntityService
                ]
            })
            .overrideTemplate(AbstractAuditingEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbstractAuditingEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbstractAuditingEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AbstractAuditingEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.abstractAuditingEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
