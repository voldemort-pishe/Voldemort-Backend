/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { CommentEntityDetailComponent } from '../../../../../../main/webapp/app/entities/comment-entity/comment-entity-detail.component';
import { CommentEntityService } from '../../../../../../main/webapp/app/entities/comment-entity/comment-entity.service';
import { CommentEntity } from '../../../../../../main/webapp/app/entities/comment-entity/comment-entity.model';

describe('Component Tests', () => {

    describe('CommentEntity Management Detail Component', () => {
        let comp: CommentEntityDetailComponent;
        let fixture: ComponentFixture<CommentEntityDetailComponent>;
        let service: CommentEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CommentEntityDetailComponent],
                providers: [
                    CommentEntityService
                ]
            })
            .overrideTemplate(CommentEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CommentEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.commentEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
