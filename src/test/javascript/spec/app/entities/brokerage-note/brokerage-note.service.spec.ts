import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BrokerageNoteService } from 'app/entities/brokerage-note/brokerage-note.service';
import { IBrokerageNote, BrokerageNote } from 'app/shared/model/brokerage-note.model';

describe('Service Tests', () => {
  describe('BrokerageNote Service', () => {
    let injector: TestBed;
    let service: BrokerageNoteService;
    let httpMock: HttpTestingController;
    let elemDefault: IBrokerageNote;
    let expectedResult: IBrokerageNote | IBrokerageNote[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BrokerageNoteService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new BrokerageNote(0, 'AAAAAAA', 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BrokerageNote', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BrokerageNote()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BrokerageNote', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            emoluments: 1,
            liquidation: 1,
            otherTaxes: 1,
            value: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BrokerageNote', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            emoluments: 1,
            liquidation: 1,
            otherTaxes: 1,
            value: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BrokerageNote', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
