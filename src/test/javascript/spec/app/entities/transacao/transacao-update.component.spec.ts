import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransacaoUpdateComponent } from 'app/entities/transacao/transacao-update.component';
import { TransacaoService } from 'app/entities/transacao/transacao.service';
import { Transacao } from 'app/shared/model/transacao.model';

describe('Component Tests', () => {
  describe('Transacao Management Update Component', () => {
    let comp: TransacaoUpdateComponent;
    let fixture: ComponentFixture<TransacaoUpdateComponent>;
    let service: TransacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TransacaoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TransacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Transacao(123);
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
        const entity = new Transacao();
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
