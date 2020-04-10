import { Moment } from 'moment';
import { IVideo } from 'app/shared/model/video.model';
import { IScore } from 'app/shared/model/score.model';
import { IGame } from 'app/shared/model/game.model';

export interface ISession {
  id?: number;
  dateCreated?: Moment;
  maxAnswer?: number;
  maxTime?: number;
  video?: IVideo;
  scores?: IScore[];
  games?: IGame;
}

export class Session implements ISession {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public maxAnswer?: number,
    public maxTime?: number,
    public video?: IVideo,
    public scores?: IScore[],
    public games?: IGame
  ) {}
}
