import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BrokerageNoteUpdateComponent } from 'app/entities/brokerage-note/brokerage-note-update.component';
import { BrokerageNoteService } from 'app/entities/brokerage-note/brokerage-note.service';
import { BrokerageNote } from 'app/shared/model/brokerage-note.model';

describe('Component Tests', () => {
  describe('BrokerageNote Management Update Component', () => {
    let comp: BrokerageNoteUpdateComponent;
    let fixture: ComponentFixture<BrokerageNoteUpdateComponent>;
    let service: BrokerageNoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BrokerageNoteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BrokerageNoteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BrokerageNoteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BrokerageNoteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BrokerageNote(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BrokerageNote();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
