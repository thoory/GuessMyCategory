import { Moment } from 'moment';
import { IPlayer } from 'app/shared/model/player.model';
import { ISession } from 'app/shared/model/session.model';
import { IKeyword } from 'app/shared/model/keyword.model';
import { ITag } from 'app/shared/model/tag.model';
import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { IActor } from 'app/shared/model/actor.model';

export interface IAnswer {
  id?: number;
  dateCreated?: Moment;
  player?: IPlayer;
  sessions?: ISession[];
  keywords?: IKeyword;
  tags?: ITag;
  optionalImages?: IOptionalImage;
  actors?: IActor;
}

export class Answer implements IAnswer {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public player?: IPlayer,
    public sessions?: ISession[],
    public keywords?: IKeyword,
    public tags?: ITag,
    public optionalImages?: IOptionalImage,
    public actors?: IActor
  ) {}
}
