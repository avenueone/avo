import { ICalendarAttribute } from 'app/shared/model//calendar-attribute.model';
import { ICampaign } from 'app/shared/model//campaign.model';

export interface ICalendar {
  id?: string;
  name?: string;
  description?: string;
  calendarattributes?: ICalendarAttribute[];
  campaigns?: ICampaign[];
}

export const defaultValue: Readonly<ICalendar> = {};
