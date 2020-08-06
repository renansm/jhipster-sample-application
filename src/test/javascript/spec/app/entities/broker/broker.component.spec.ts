import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BrokerComponent } from 'app/entities/broker/broker.component';
import { BrokerService } from 'app/entities/broker/broker.service';
import { Broker } from 'app/shared/model/broker.model';

describe('Component Tests', () => {
  describe('Broker Management Component', () => {
    let comp: BrokerComponent;
    let fixture: ComponentFixture<BrokerComponent>;
    let service: BrokerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BrokerComponent],
      })
        .overrideTemplate(BrokerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BrokerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BrokerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Broker(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.brokers && comp.brokers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
