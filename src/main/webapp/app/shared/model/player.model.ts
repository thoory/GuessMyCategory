import { Moment } from 'moment';
import { IScore } from 'app/shared/model/score.model';
import { IParty } from 'app/shared/model/party.model';
import { ISession } from 'app/shared/model/session.model';

export interface IPlayer {
  id?: number;
  dateCreated?: Moment;
  name?: string;
  img?: string;
  score?: IScore;
  party?: IParty;
  sessions?: ISession[];
}

export class Player implements IPlayer {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public name?: string,
    public img?: string,
    public score?: IScore,
    public party?: IParty,
    public sessions?: ISession[]
  ) {}
}
