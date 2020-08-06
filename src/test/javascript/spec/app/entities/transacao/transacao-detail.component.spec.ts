import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransacaoDetailComponent } from 'app/entities/transacao/transacao-detail.component';
import { Transacao } from 'app/shared/model/transacao.model';

describe('Component Tests', () => {
  describe('Transacao Management Detail Component', () => {
    let comp: TransacaoDetailComponent;
    let fixture: ComponentFixture<TransacaoDetailComponent>;
    const route = ({ data: of({ transacao: new Transacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TransacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TransacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
