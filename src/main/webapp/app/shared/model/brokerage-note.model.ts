import { ITransaction } from 'app/shared/model/transaction.model';
import { IBroker } from 'app/shared/model/broker.model';

export interface IBrokerageNote {
  id?: number;
  number?: string;
  emoluments?: number;
  liquidation?: number;
  otherTaxes?: number;
  value?: number;
  transactions?: ITransaction[];
  broker?: IBroker;
}

export class BrokerageNote implements IBrokerageNote {
  constructor(
    public id?: number,
    public number?: string,
    public emoluments?: number,
    public liquidation?: number,
    public otherTaxes?: number,
    public value?: number,
    public transactions?: ITransaction[],
    public broker?: IBroker
  ) {}
}
