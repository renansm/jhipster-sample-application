import { ITransacao } from 'app/shared/model/transacao.model';
import { ICorretora } from 'app/shared/model/corretora.model';

export interface INotaCorretagem {
  id?: number;
  numero?: number;
  emolumentos?: number;
  liquidacao?: number;
  outrasTaxas?: number;
  valor?: number;
  transacaos?: ITransacao[];
  corretora?: ICorretora;
}

export class NotaCorretagem implements INotaCorretagem {
  constructor(
    public id?: number,
    public numero?: number,
    public emolumentos?: number,
    public liquidacao?: number,
    public outrasTaxas?: number,
    public valor?: number,
    public transacaos?: ITransacao[],
    public corretora?: ICorretora
  ) {}
}
