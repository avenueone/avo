import { ICampaign } from 'app/shared/model//campaign.model';

export interface ICampaignAttribute {
  id?: string;
  name?: string;
  type?: string;
  value?: string;
  campaigns?: ICampaign[];
}

export const defaultValue: Readonly<ICampaignAttribute> = {};
