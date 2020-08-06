import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CorretoraComponent } from 'app/entities/corretora/corretora.component';
import { CorretoraService } from 'app/entities/corretora/corretora.service';
import { Corretora } from 'app/shared/model/corretora.model';

describe('Component Tests', () => {
  describe('Corretora Management Component', () => {
    let comp: CorretoraComponent;
    let fixture: ComponentFixture<CorretoraComponent>;
    let service: CorretoraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CorretoraComponent],
      })
        .overrideTemplate(CorretoraComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CorretoraComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CorretoraService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Corretora(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.corretoras && comp.corretoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
