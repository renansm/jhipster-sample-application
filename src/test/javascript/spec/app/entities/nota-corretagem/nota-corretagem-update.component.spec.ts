import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { NotaCorretagemUpdateComponent } from 'app/entities/nota-corretagem/nota-corretagem-update.component';
import { NotaCorretagemService } from 'app/entities/nota-corretagem/nota-corretagem.service';
import { NotaCorretagem } from 'app/shared/model/nota-corretagem.model';

describe('Component Tests', () => {
  describe('NotaCorretagem Management Update Component', () => {
    let comp: NotaCorretagemUpdateComponent;
    let fixture: ComponentFixture<NotaCorretagemUpdateComponent>;
    let service: NotaCorretagemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [NotaCorretagemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NotaCorretagemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotaCorretagemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotaCorretagemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NotaCorretagem(123);
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
        const entity = new NotaCorretagem();
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
