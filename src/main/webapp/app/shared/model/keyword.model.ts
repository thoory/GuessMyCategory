import { IVideo } from 'app/shared/model/video.model';

export interface IKeyword {
  id?: number;
  keyword?: string;
  videos?: IVideo[];
}

export class Keyword implements IKeyword {
  constructor(public id?: number, public keyword?: string, public videos?: IVideo[]) {}
}
