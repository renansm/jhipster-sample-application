import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPapel } from 'app/shared/model/papel.model';

type EntityResponseType = HttpResponse<IPapel>;
type EntityArrayResponseType = HttpResponse<IPapel[]>;

@Injectable({ providedIn: 'root' })
export class PapelService {
  public resourceUrl = SERVER_API_URL + 'api/papels';

  constructor(protected http: HttpClient) {}

  create(papel: IPapel): Observable<EntityResponseType> {
    return this.http.post<IPapel>(this.resourceUrl, papel, { observe: 'response' });
  }

  update(papel: IPapel): Observable<EntityResponseType> {
    return this.http.put<IPapel>(this.resourceUrl, papel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPapel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPapel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
