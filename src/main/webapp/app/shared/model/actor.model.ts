import { IOptionalImage } from 'app/shared/model/optional-image.model';
import { IVideo } from 'app/shared/model/video.model';

export interface IActor {
  id?: number;
  name?: string;
  popularity?: number;
  contry?: string;
  link?: string;
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
    public images?: IOptionalImage,
    public videos?: IVideo[]
  ) {}
}
