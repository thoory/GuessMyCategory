import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IAnswer } from 'app/shared/model/answer.model';
import { ISession } from 'app/shared/model/session.model';

export interface IScore {
  id?: number;
  dateCreated?: Moment;
  answerTimeAvg?: number;
  correctAnswer?: number;
  propositionTotal?: number;
  user?: IUser;
  answers?: IAnswer[];
  session?: ISession;
}

export class Score implements IScore {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public answerTimeAvg?: number,
    public correctAnswer?: number,
    public propositionTotal?: number,
    public user?: IUser,
    public answers?: IAnswer[],
    public session?: ISession
  ) {}
}
