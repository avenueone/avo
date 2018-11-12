import { IVessel } from 'app/shared/model//vessel.model';

export interface IVesselAttribute {
  id?: string;
  name?: string;
  type?: string;
  value?: string;
  vessel?: IVessel;
}

export const defaultValue: Readonly<IVesselAttribute> = {};
