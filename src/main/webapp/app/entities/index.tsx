import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Vessel from './vessel';
import Campaign from './campaign';
import Calendar from './calendar';
import CampaignAttribute from './campaign-attribute';
import CalendarAttribute from './calendar-attribute';
import VesselAttribute from './vessel-attribute';
import Container from './container';
import VesselType from './vessel-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/vessel`} component={Vessel} />
      <ErrorBoundaryRoute path={`${match.url}/campaign`} component={Campaign} />
      <ErrorBoundaryRoute path={`${match.url}/calendar`} component={Calendar} />
      <ErrorBoundaryRoute path={`${match.url}/campaign-attribute`} component={CampaignAttribute} />
      <ErrorBoundaryRoute path={`${match.url}/calendar-attribute`} component={CalendarAttribute} />
      <ErrorBoundaryRoute path={`${match.url}/vessel-attribute`} component={VesselAttribute} />
      <ErrorBoundaryRoute path={`${match.url}/container`} component={Container} />
      <ErrorBoundaryRoute path={`${match.url}/vessel-type`} component={VesselType} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
