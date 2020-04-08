import { Moment } from 'moment';
import { IPlayer } from 'app/shared/model/player.model';
import { IKeyword } from 'app/shared/model/keyword.model';
import { ITag } from 'app/shared/model/tag.model';
import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { IActor } from 'app/shared/model/actor.model';
import { IAnswer } from 'app/shared/model/answer.model';

export interface ISession {
  id?: number;
  dateCreated?: Moment;
  players?: IPlayer[];
  keywords?: IKeyword;
  tags?: ITag;
  optionalImages?: IOptionalImage;
  actors?: IActor;
  answers?: IAnswer;
}

export class Session implements ISession {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public players?: IPlayer[],
    public keywords?: IKeyword,
    public tags?: ITag,
    public optionalImages?: IOptionalImage,
    public actors?: IActor,
    public answers?: IAnswer
  ) {}
}
