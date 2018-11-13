import { Moment } from 'moment';
import { IContainer } from 'app/shared/model//container.model';
import { IVesselAttribute } from 'app/shared/model//vessel-attribute.model';
import { IVesselType } from 'app/shared/model//vessel-type.model';
import { ICampaign } from 'app/shared/model//campaign.model';

export interface IVessel {
  id?: string;
  name?: string;
  startDate?: Moment;
  endDate?: Moment;
  description?: string;
  containers?: IContainer[];
  vesselattributes?: IVesselAttribute[];
  vesseltypes?: IVesselType[];
  campaign?: ICampaign;
}

export const defaultValue: Readonly<IVessel> = {};
