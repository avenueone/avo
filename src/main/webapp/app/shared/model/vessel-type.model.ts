import { IVessel } from 'app/shared/model//vessel.model';

export const enum VesselTypeEnum {
  FLYER = 'FLYER',
  WEB = 'WEB',
  EMAIL = 'EMAIL',
  VR = 'VR',
  COUPON = 'COUPON',
  SIGNAGE = 'SIGNAGE',
  SHELFTAG = 'SHELFTAG'
}

export interface IVesselType {
  id?: string;
  name?: string;
  type?: VesselTypeEnum;
  recurring?: boolean;
  dayOfMonth?: number;
  dayOfWeek?: number;
  month?: number;
  vessels?: IVessel[];
}

export const defaultValue: Readonly<IVesselType> = {
  recurring: false
};
