import { Moment } from 'moment';
import { IVessel } from 'app/shared/model//vessel.model';
import { ICampaignAttribute } from 'app/shared/model//campaign-attribute.model';
import { ICalendar } from 'app/shared/model//calendar.model';

export interface ICampaign {
  id?: string;
  name?: string;
  startDate?: Moment;
  endDate?: Moment;
  vessels?: IVessel[];
  campaignAttribute?: ICampaignAttribute;
  calendar?: ICalendar;
}

export const defaultValue: Readonly<ICampaign> = {};
