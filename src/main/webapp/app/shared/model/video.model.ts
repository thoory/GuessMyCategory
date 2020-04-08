import { Moment } from 'moment';
import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { IActor } from 'app/shared/model/actor.model';
import { IKeyword } from 'app/shared/model/keyword.model';
import { ITag } from 'app/shared/model/tag.model';

export interface IVideo {
  id?: number;
  iframe?: string;
  title?: string;
  duration?: number;
  view?: number;
  like?: number;
  dislike?: number;
  dateCreated?: Moment;
  optionalImages?: IOptionalImage;
  actors?: IActor[];
  keywords?: IKeyword[];
  tags?: ITag[];
}

export class Video implements IVideo {
  constructor(
    public id?: number,
    public iframe?: string,
    public title?: string,
    public duration?: number,
    public view?: number,
    public like?: number,
    public dislike?: number,
    public dateCreated?: Moment,
    public optionalImages?: IOptionalImage,
    public actors?: IActor[],
    public keywords?: IKeyword[],
    public tags?: ITag[]
  ) {}
}
