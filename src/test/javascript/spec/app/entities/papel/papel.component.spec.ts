import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PapelComponent } from 'app/entities/papel/papel.component';
import { PapelService } from 'app/entities/papel/papel.service';
import { Papel } from 'app/shared/model/papel.model';

describe('Component Tests', () => {
  describe('Papel Management Component', () => {
    let comp: PapelComponent;
    let fixture: ComponentFixture<PapelComponent>;
    let service: PapelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PapelComponent],
      })
        .overrideTemplate(PapelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PapelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PapelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Papel(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.papels && comp.papels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
