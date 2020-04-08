import { Moment } from 'moment';
import { ISession } from 'app/shared/model/session.model';

export interface IParty {
  id?: number;
  dateCreated?: Moment;
  code?: string;
  maxPlayer?: number;
  maxVideoTime?: number;
  sessions?: ISession;
}

export class Party implements IParty {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public code?: string,
    public maxPlayer?: number,
    public maxVideoTime?: number,
    public sessions?: ISession
  ) {}
}
