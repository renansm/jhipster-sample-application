import { IPosition } from 'app/shared/model/position.model';
import { ITransaction } from 'app/shared/model/transaction.model';

export interface IPaper {
  id?: number;
  code?: string;
  name?: string;
  marketValue?: number;
  positions?: IPosition[];
  transactions?: ITransaction[];
}

export class Paper implements IPaper {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public marketValue?: number,
    public positions?: IPosition[],
    public transactions?: ITransaction[]
  ) {}
}
