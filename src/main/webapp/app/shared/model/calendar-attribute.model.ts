import { ICalendar } from 'app/shared/model//calendar.model';

export interface ICalendarAttribute {
  id?: string;
  name?: string;
  type?: string;
  value?: string;
  calender?: ICalendar;
}

export const defaultValue: Readonly<ICalendarAttribute> = {};
