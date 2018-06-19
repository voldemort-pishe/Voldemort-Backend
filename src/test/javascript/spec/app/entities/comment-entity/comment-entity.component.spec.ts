/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { CommentEntityComponent } from '../../../../../../main/webapp/app/entities/comment-entity/comment-entity.component';
import { CommentEntityService } from '../../../../../../main/webapp/app/entities/comment-entity/comment-entity.service';
import { CommentEntity } from '../../../../../../main/webapp/app/entities/comment-entity/comment-entity.model';

describe('Component Tests', () => {

    describe('CommentEntity Management Component', () => {
        let comp: CommentEntityComponent;
        let fixture: ComponentFixture<CommentEntityComponent>;
        let service: CommentEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [CommentEntityComponent],
                providers: [
                    CommentEntityService
                ]
            })
            .overrideTemplate(CommentEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CommentEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.commentEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
