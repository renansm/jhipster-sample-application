import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPosicao } from 'app/shared/model/posicao.model';

type EntityResponseType = HttpResponse<IPosicao>;
type EntityArrayResponseType = HttpResponse<IPosicao[]>;

@Injectable({ providedIn: 'root' })
export class PosicaoService {
  public resourceUrl = SERVER_API_URL + 'api/posicaos';

  constructor(protected http: HttpClient) {}

  create(posicao: IPosicao): Observable<EntityResponseType> {
    return this.http.post<IPosicao>(this.resourceUrl, posicao, { observe: 'response' });
  }

  update(posicao: IPosicao): Observable<EntityResponseType> {
    return this.http.put<IPosicao>(this.resourceUrl, posicao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPosicao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPosicao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
