import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INotaCorretagem } from 'app/shared/model/nota-corretagem.model';

type EntityResponseType = HttpResponse<INotaCorretagem>;
type EntityArrayResponseType = HttpResponse<INotaCorretagem[]>;

@Injectable({ providedIn: 'root' })
export class NotaCorretagemService {
  public resourceUrl = SERVER_API_URL + 'api/nota-corretagems';

  constructor(protected http: HttpClient) {}

  create(notaCorretagem: INotaCorretagem): Observable<EntityResponseType> {
    return this.http.post<INotaCorretagem>(this.resourceUrl, notaCorretagem, { observe: 'response' });
  }

  update(notaCorretagem: INotaCorretagem): Observable<EntityResponseType> {
    return this.http.put<INotaCorretagem>(this.resourceUrl, notaCorretagem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INotaCorretagem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INotaCorretagem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
