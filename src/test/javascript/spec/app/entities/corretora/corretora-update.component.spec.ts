import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CorretoraUpdateComponent } from 'app/entities/corretora/corretora-update.component';
import { CorretoraService } from 'app/entities/corretora/corretora.service';
import { Corretora } from 'app/shared/model/corretora.model';

describe('Component Tests', () => {
  describe('Corretora Management Update Component', () => {
    let comp: CorretoraUpdateComponent;
    let fixture: ComponentFixture<CorretoraUpdateComponent>;
    let service: CorretoraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CorretoraUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CorretoraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CorretoraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CorretoraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Corretora(123);
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
        const entity = new Corretora();
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
