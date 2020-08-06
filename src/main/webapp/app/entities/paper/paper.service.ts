import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaper } from 'app/shared/model/paper.model';

type EntityResponseType = HttpResponse<IPaper>;
type EntityArrayResponseType = HttpResponse<IPaper[]>;

@Injectable({ providedIn: 'root' })
export class PaperService {
  public resourceUrl = SERVER_API_URL + 'api/papers';

  constructor(protected http: HttpClient) {}

  create(paper: IPaper): Observable<EntityResponseType> {
    return this.http.post<IPaper>(this.resourceUrl, paper, { observe: 'response' });
  }

  update(paper: IPaper): Observable<EntityResponseType> {
    return this.http.put<IPaper>(this.resourceUrl, paper, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaper>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaper[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
