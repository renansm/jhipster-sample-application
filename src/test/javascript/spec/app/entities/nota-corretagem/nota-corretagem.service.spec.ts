import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { NotaCorretagemService } from 'app/entities/nota-corretagem/nota-corretagem.service';
import { INotaCorretagem, NotaCorretagem } from 'app/shared/model/nota-corretagem.model';

describe('Service Tests', () => {
  describe('NotaCorretagem Service', () => {
    let injector: TestBed;
    let service: NotaCorretagemService;
    let httpMock: HttpTestingController;
    let elemDefault: INotaCorretagem;
    let expectedResult: INotaCorretagem | INotaCorretagem[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NotaCorretagemService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new NotaCorretagem(0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a NotaCorretagem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new NotaCorretagem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NotaCorretagem', () => {
        const returnedFromService = Object.assign(
          {
            numero: 1,
            emolumentos: 1,
            liquidacao: 1,
            outrasTaxas: 1,
            valor: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of NotaCorretagem', () => {
        const returnedFromService = Object.assign(
          {
            numero: 1,
            emolumentos: 1,
            liquidacao: 1,
            outrasTaxas: 1,
            valor: 1,
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

      it('should delete a NotaCorretagem', () => {
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
