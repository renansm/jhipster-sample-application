import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaperDetailComponent } from 'app/entities/paper/paper-detail.component';
import { Paper } from 'app/shared/model/paper.model';

describe('Component Tests', () => {
  describe('Paper Management Detail Component', () => {
    let comp: PaperDetailComponent;
    let fixture: ComponentFixture<PaperDetailComponent>;
    const route = ({ data: of({ paper: new Paper(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PaperDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaperDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaperDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paper on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paper).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
