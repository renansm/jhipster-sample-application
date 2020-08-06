import { INotaCorretagem } from 'app/shared/model/nota-corretagem.model';

export interface ICorretora {
  id?: number;
  nome?: string;
  notaCorretagems?: INotaCorretagem[];
}

export class Corretora implements ICorretora {
  constructor(public id?: number, public nome?: string, public notaCorretagems?: INotaCorretagem[]) {}
}
