import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { NotaCorretagemDetailComponent } from 'app/entities/nota-corretagem/nota-corretagem-detail.component';
import { NotaCorretagem } from 'app/shared/model/nota-corretagem.model';

describe('Component Tests', () => {
  describe('NotaCorretagem Management Detail Component', () => {
    let comp: NotaCorretagemDetailComponent;
    let fixture: ComponentFixture<NotaCorretagemDetailComponent>;
    const route = ({ data: of({ notaCorretagem: new NotaCorretagem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [NotaCorretagemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NotaCorretagemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotaCorretagemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load notaCorretagem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notaCorretagem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
