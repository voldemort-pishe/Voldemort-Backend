/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VoldemortTestModule } from '../../../test.module';
import { FileEntityDetailComponent } from '../../../../../../main/webapp/app/entities/file-entity/file-entity-detail.component';
import { FileEntityService } from '../../../../../../main/webapp/app/entities/file-entity/file-entity.service';
import { FileEntity } from '../../../../../../main/webapp/app/entities/file-entity/file-entity.model';

describe('Component Tests', () => {

    describe('FileEntity Management Detail Component', () => {
        let comp: FileEntityDetailComponent;
        let fixture: ComponentFixture<FileEntityDetailComponent>;
        let service: FileEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoldemortTestModule],
                declarations: [FileEntityDetailComponent],
                providers: [
                    FileEntityService
                ]
            })
            .overrideTemplate(FileEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FileEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FileEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.fileEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
