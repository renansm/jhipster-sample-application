import { IBrokerageNote } from 'app/shared/model/brokerage-note.model';

export interface IBroker {
  id?: number;
  name?: string;
  brokerageNotes?: IBrokerageNote[];
}

export class Broker implements IBroker {
  constructor(public id?: number, public name?: string, public brokerageNotes?: IBrokerageNote[]) {}
}
