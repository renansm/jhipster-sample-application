import { IPaper } from 'app/shared/model/paper.model';

export interface IPosition {
  id?: number;
  paper?: IPaper;
}

export class Position implements IPosition {
  constructor(public id?: number, public paper?: IPaper) {}
}
