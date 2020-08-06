import { IPosicao } from 'app/shared/model/posicao.model';
import { ITransacao } from 'app/shared/model/transacao.model';

export interface IPapel {
  id?: number;
  codigo?: string;
  nome?: string;
  precoMercado?: number;
  posicaos?: IPosicao[];
  transacaos?: ITransacao[];
}

export class Papel implements IPapel {
  constructor(
    public id?: number,
    public codigo?: string,
    public nome?: string,
    public precoMercado?: number,
    public posicaos?: IPosicao[],
    public transacaos?: ITransacao[]
  ) {}
}
