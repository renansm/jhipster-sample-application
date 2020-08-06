import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBrokerageNote } from 'app/shared/model/brokerage-note.model';

type EntityResponseType = HttpResponse<IBrokerageNote>;
type EntityArrayResponseType = HttpResponse<IBrokerageNote[]>;

@Injectable({ providedIn: 'root' })
export class BrokerageNoteService {
  public resourceUrl = SERVER_API_URL + 'api/brokerage-notes';

  constructor(protected http: HttpClient) {}

  create(brokerageNote: IBrokerageNote): Observable<EntityResponseType> {
    return this.http.post<IBrokerageNote>(this.resourceUrl, brokerageNote, { observe: 'response' });
  }

  update(brokerageNote: IBrokerageNote): Observable<EntityResponseType> {
    return this.http.put<IBrokerageNote>(this.resourceUrl, brokerageNote, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBrokerageNote>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBrokerageNote[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
