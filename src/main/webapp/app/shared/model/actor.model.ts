import { ISession } from 'app/shared/model/session.model';
import { IAnswer } from 'app/shared/model/answer.model';
import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { IVideo } from 'app/shared/model/video.model';

export interface IActor {
  id?: number;
  name?: string;
  popularity?: number;
  contry?: string;
  link?: string;
  sessions?: ISession[];
  answers?: IAnswer[];
  images?: IOptionalImage;
  videos?: IVideo[];
}

export class Actor implements IActor {
  constructor(
    public id?: number,
    public name?: string,
    public popularity?: number,
    public contry?: string,
    public link?: string,
    public sessions?: ISession[],
    public answers?: IAnswer[],
    public images?: IOptionalImage,
    public videos?: IVideo[]
  ) {}
}
