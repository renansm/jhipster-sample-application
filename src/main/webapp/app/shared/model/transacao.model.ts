import { Moment } from 'moment';
import { IPapel } from 'app/shared/model/papel.model';
import { INotaCorretagem } from 'app/shared/model/nota-corretagem.model';
import { TipoTransacao } from 'app/shared/model/enumerations/tipo-transacao.model';

export interface ITransacao {
  id?: number;
  quantidade?: number;
  valor?: number;
  data?: Moment;
  tipo?: TipoTransacao;
  papel?: IPapel;
  notaCorretagem?: INotaCorretagem;
}

export class Transacao implements ITransacao {
  constructor(
    public id?: number,
    public quantidade?: number,
    public valor?: number,
    public data?: Moment,
    public tipo?: TipoTransacao,
    public papel?: IPapel,
    public notaCorretagem?: INotaCorretagem
  ) {}
}
