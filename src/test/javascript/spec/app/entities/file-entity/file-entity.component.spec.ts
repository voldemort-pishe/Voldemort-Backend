/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VoldemortTestModule } from '../../../test.module';
import { FileEntityComponent } from '../../../../../../main/webapp/app/entities/file-entity/file-entity.component';
import { FileEntityService } from '../../../../../../main/webapp/app/entities/file-entity/file-entity.service';
import { FileEntity } from '../../../../../../main/webapp/app/entities/file-entity/file-entity.model';

describe('Component Tests', () => {

    describe('FileEntity Management Component', () => {
        let comp: FileEntityComponent;
        let fixture: ComponentFixture<FileEntityComponent>;
        let service: FileEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [FileEntityComponent],
                providers: [
                    FileEntityService
                ]
            })
            .overrideTemplate(FileEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FileEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FileEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.fileEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
