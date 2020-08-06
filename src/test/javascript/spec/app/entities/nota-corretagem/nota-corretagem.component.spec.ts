import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { NotaCorretagemComponent } from 'app/entities/nota-corretagem/nota-corretagem.component';
import { NotaCorretagemService } from 'app/entities/nota-corretagem/nota-corretagem.service';
import { NotaCorretagem } from 'app/shared/model/nota-corretagem.model';

describe('Component Tests', () => {
  describe('NotaCorretagem Management Component', () => {
    let comp: NotaCorretagemComponent;
    let fixture: ComponentFixture<NotaCorretagemComponent>;
    let service: NotaCorretagemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [NotaCorretagemComponent],
      })
        .overrideTemplate(NotaCorretagemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotaCorretagemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotaCorretagemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NotaCorretagem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.notaCorretagems && comp.notaCorretagems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
