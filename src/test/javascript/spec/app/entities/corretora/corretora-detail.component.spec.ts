import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CorretoraDetailComponent } from 'app/entities/corretora/corretora-detail.component';
import { Corretora } from 'app/shared/model/corretora.model';

describe('Component Tests', () => {
  describe('Corretora Management Detail Component', () => {
    let comp: CorretoraDetailComponent;
    let fixture: ComponentFixture<CorretoraDetailComponent>;
    const route = ({ data: of({ corretora: new Corretora(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CorretoraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CorretoraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CorretoraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load corretora on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.corretora).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
