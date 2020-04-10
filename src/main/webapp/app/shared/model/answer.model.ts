import { Moment } from 'moment';
import { IScore } from 'app/shared/model/score.model';

export interface IAnswer {
  id?: number;
  dateCreated?: Moment;
  time?: number;
  score?: IScore;
}

export class Answer implements IAnswer {
  constructor(public id?: number, public dateCreated?: Moment, public time?: number, public score?: IScore) {}
}
