import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ISession } from 'app/shared/model/session.model';

export interface IGame {
  id?: number;
  dateCreated?: Moment;
  code?: string;
  maxUser?: number;
  maxVideoTime?: number;
  users?: IUser[];
  sessions?: ISession[];
}

export class Game implements IGame {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public code?: string,
    public maxUser?: number,
    public maxVideoTime?: number,
    public users?: IUser[],
    public sessions?: ISession[]
  ) {}
}
