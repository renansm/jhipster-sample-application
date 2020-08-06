import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransacao } from 'app/shared/model/transacao.model';

type EntityResponseType = HttpResponse<ITransacao>;
type EntityArrayResponseType = HttpResponse<ITransacao[]>;

@Injectable({ providedIn: 'root' })
export class TransacaoService {
  public resourceUrl = SERVER_API_URL + 'api/transacaos';

  constructor(protected http: HttpClient) {}

  create(transacao: ITransacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transacao);
    return this.http
      .post<ITransacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transacao: ITransacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transacao);
    return this.http
      .put<ITransacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transacao: ITransacao): ITransacao {
    const copy: ITransacao = Object.assign({}, transacao, {
      data: transacao.data && transacao.data.isValid() ? transacao.data.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transacao: ITransacao) => {
        transacao.data = transacao.data ? moment(transacao.data) : undefined;
      });
    }
    return res;
  }
}
