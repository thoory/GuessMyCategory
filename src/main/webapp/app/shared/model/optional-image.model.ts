export interface IOptionalImage {
  id?: number;
  link?: string;
}

export class OptionalImage implements IOptionalImage {
  constructor(public id?: number, public link?: string) {}
}
