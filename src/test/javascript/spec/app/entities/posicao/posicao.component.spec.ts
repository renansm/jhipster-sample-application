import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PosicaoComponent } from 'app/entities/posicao/posicao.component';
import { PosicaoService } from 'app/entities/posicao/posicao.service';
import { Posicao } from 'app/shared/model/posicao.model';

describe('Component Tests', () => {
  describe('Posicao Management Component', () => {
    let comp: PosicaoComponent;
    let fixture: ComponentFixture<PosicaoComponent>;
    let service: PosicaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PosicaoComponent],
      })
        .overrideTemplate(PosicaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PosicaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PosicaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Posicao(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.posicaos && comp.posicaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
