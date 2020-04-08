import { ISession } from 'app/shared/model/session.model';
import { IAnswer } from 'app/shared/model/answer.model';

export interface IOptionalImage {
  id?: number;
  link?: string;
  sessions?: ISession[];
  answers?: IAnswer[];
}

export class OptionalImage implements IOptionalImage {
  constructor(public id?: number, public link?: string, public sessions?: ISession[], public answers?: IAnswer[]) {}
}
