import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaperComponent } from 'app/entities/paper/paper.component';
import { PaperService } from 'app/entities/paper/paper.service';
import { Paper } from 'app/shared/model/paper.model';

describe('Component Tests', () => {
  describe('Paper Management Component', () => {
    let comp: PaperComponent;
    let fixture: ComponentFixture<PaperComponent>;
    let service: PaperService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PaperComponent],
      })
        .overrideTemplate(PaperComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaperComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaperService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Paper(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.papers && comp.papers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
