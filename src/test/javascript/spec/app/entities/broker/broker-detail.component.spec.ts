import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BrokerDetailComponent } from 'app/entities/broker/broker-detail.component';
import { Broker } from 'app/shared/model/broker.model';

describe('Component Tests', () => {
  describe('Broker Management Detail Component', () => {
    let comp: BrokerDetailComponent;
    let fixture: ComponentFixture<BrokerDetailComponent>;
    const route = ({ data: of({ broker: new Broker(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BrokerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BrokerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BrokerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load broker on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.broker).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
