import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PosicaoDetailComponent } from 'app/entities/posicao/posicao-detail.component';
import { Posicao } from 'app/shared/model/posicao.model';

describe('Component Tests', () => {
  describe('Posicao Management Detail Component', () => {
    let comp: PosicaoDetailComponent;
    let fixture: ComponentFixture<PosicaoDetailComponent>;
    const route = ({ data: of({ posicao: new Posicao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PosicaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PosicaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PosicaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load posicao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.posicao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
