import { Moment } from 'moment';
import { IPaper } from 'app/shared/model/paper.model';
import { IBrokerageNote } from 'app/shared/model/brokerage-note.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';

export interface ITransaction {
  id?: number;
  quantity?: string;
  value?: number;
  date?: Moment;
  type?: TransactionType;
  paper?: IPaper;
  brokerageNote?: IBrokerageNote;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public quantity?: string,
    public value?: number,
    public date?: Moment,
    public type?: TransactionType,
    public paper?: IPaper,
    public brokerageNote?: IBrokerageNote
  ) {}
}
