import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PapelDetailComponent } from 'app/entities/papel/papel-detail.component';
import { Papel } from 'app/shared/model/papel.model';

describe('Component Tests', () => {
  describe('Papel Management Detail Component', () => {
    let comp: PapelDetailComponent;
    let fixture: ComponentFixture<PapelDetailComponent>;
    const route = ({ data: of({ papel: new Papel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PapelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PapelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PapelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load papel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.papel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
