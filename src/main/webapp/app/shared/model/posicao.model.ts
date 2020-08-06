import { IPapel } from 'app/shared/model/papel.model';

export interface IPosicao {
  id?: number;
  quantidade?: number;
  papel?: IPapel;
}

export class Posicao implements IPosicao {
  constructor(public id?: number, public quantidade?: number, public papel?: IPapel) {}
}
