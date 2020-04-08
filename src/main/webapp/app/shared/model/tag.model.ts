import { ISession } from 'app/shared/model/session.model';
import { IAnswer } from 'app/shared/model/answer.model';
import { IVideo } from 'app/shared/model/video.model';

export interface ITag {
  id?: number;
  tag?: string;
  sessions?: ISession[];
  answers?: IAnswer[];
  videos?: IVideo[];
}

export class Tag implements ITag {
  constructor(
    public id?: number,
    public tag?: string,
    public sessions?: ISession[],
    public answers?: IAnswer[],
    public videos?: IVideo[]
  ) {}
}
