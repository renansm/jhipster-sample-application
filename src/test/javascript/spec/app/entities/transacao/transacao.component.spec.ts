import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransacaoComponent } from 'app/entities/transacao/transacao.component';
import { TransacaoService } from 'app/entities/transacao/transacao.service';
import { Transacao } from 'app/shared/model/transacao.model';

describe('Component Tests', () => {
  describe('Transacao Management Component', () => {
    let comp: TransacaoComponent;
    let fixture: ComponentFixture<TransacaoComponent>;
    let service: TransacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TransacaoComponent],
      })
        .overrideTemplate(TransacaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransacaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransacaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Transacao(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transacaos && comp.transacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
