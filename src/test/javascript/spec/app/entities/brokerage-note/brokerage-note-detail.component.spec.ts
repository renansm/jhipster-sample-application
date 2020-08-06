import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BrokerageNoteDetailComponent } from 'app/entities/brokerage-note/brokerage-note-detail.component';
import { BrokerageNote } from 'app/shared/model/brokerage-note.model';

describe('Component Tests', () => {
  describe('BrokerageNote Management Detail Component', () => {
    let comp: BrokerageNoteDetailComponent;
    let fixture: ComponentFixture<BrokerageNoteDetailComponent>;
    const route = ({ data: of({ brokerageNote: new BrokerageNote(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BrokerageNoteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BrokerageNoteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BrokerageNoteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load brokerageNote on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.brokerageNote).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
