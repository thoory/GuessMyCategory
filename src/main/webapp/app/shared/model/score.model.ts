import { Moment } from 'moment';

export interface IScore {
  id?: number;
  dateCreated?: Moment;
  answerTimeAvg?: number;
  correctAnswer?: number;
  propositionTotal?: number;
}

export class Score implements IScore {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public answerTimeAvg?: number,
    public correctAnswer?: number,
    public propositionTotal?: number
  ) {}
}
