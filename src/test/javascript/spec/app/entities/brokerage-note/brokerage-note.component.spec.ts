import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BrokerageNoteComponent } from 'app/entities/brokerage-note/brokerage-note.component';
import { BrokerageNoteService } from 'app/entities/brokerage-note/brokerage-note.service';
import { BrokerageNote } from 'app/shared/model/brokerage-note.model';

describe('Component Tests', () => {
  describe('BrokerageNote Management Component', () => {
    let comp: BrokerageNoteComponent;
    let fixture: ComponentFixture<BrokerageNoteComponent>;
    let service: BrokerageNoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BrokerageNoteComponent],
      })
        .overrideTemplate(BrokerageNoteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BrokerageNoteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BrokerageNoteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BrokerageNote(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.brokerageNotes && comp.brokerageNotes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
