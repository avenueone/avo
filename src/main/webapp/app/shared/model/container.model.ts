import { IVessel } from 'app/shared/model//vessel.model';

export interface IContainer {
  id?: string;
  name?: string;
  vessel?: IVessel;
}

export const defaultValue: Readonly<IContainer> = {};
