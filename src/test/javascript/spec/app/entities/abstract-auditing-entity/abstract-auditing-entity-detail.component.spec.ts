/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { AbstractAuditingEntityDetailComponent } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity-detail.component';
import { AbstractAuditingEntityService } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity.service';
import { AbstractAuditingEntity } from '../../../../../../main/webapp/app/entities/abstract-auditing-entity/abstract-auditing-entity.model';

describe('Component Tests', () => {

    describe('AbstractAuditingEntity Management Detail Component', () => {
        let comp: AbstractAuditingEntityDetailComponent;
        let fixture: ComponentFixture<AbstractAuditingEntityDetailComponent>;
        let service: AbstractAuditingEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [AbstractAuditingEntityDetailComponent],
                providers: [
                    AbstractAuditingEntityService
                ]
            })
            .overrideTemplate(AbstractAuditingEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbstractAuditingEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbstractAuditingEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AbstractAuditingEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.abstractAuditingEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
