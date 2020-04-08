import { ISession } from 'app/shared/model/session.model';
import { IAnswer } from 'app/shared/model/answer.model';
import { IVideo } from 'app/shared/model/video.model';

export interface IKeyword {
  id?: number;
  keyword?: string;
  sessions?: ISession[];
  answers?: IAnswer[];
  videos?: IVideo[];
}

export class Keyword implements IKeyword {
  constructor(
    public id?: number,
    public keyword?: string,
    public sessions?: ISession[],
    public answers?: IAnswer[],
    public videos?: IVideo[]
  ) {}
}
