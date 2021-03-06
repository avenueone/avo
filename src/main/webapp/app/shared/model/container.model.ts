import { IVessel } from 'app/shared/model//vessel.model';

export interface IContainer {
  id?: string;
  name?: string;
  vessels?: IVessel[];
}

export const defaultValue: Readonly<IContainer> = {};
