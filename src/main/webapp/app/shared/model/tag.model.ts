import { IVideo } from 'app/shared/model/video.model';

export interface ITag {
  id?: number;
  tag?: string;
  videos?: IVideo[];
}

export class Tag implements ITag {
  constructor(public id?: number, public tag?: string, public videos?: IVideo[]) {}
}
