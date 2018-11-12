import { Moment } from 'moment';
import { IVesselAttribute } from 'app/shared/model//vessel-attribute.model';
import { IContainer } from 'app/shared/model//container.model';
import { IVesselType } from 'app/shared/model//vessel-type.model';
import { ICampaign } from 'app/shared/model//campaign.model';

export interface IVessel {
  id?: string;
  name?: string;
  startDate?: Moment;
  endDate?: Moment;
  description?: string;
  vesselattributes?: IVesselAttribute[];
  container?: IContainer;
  vesseltypes?: IVesselType[];
  campaign?: ICampaign;
}

export const defaultValue: Readonly<IVessel> = {};
