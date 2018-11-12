import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Vessel from './vessel';
import VesselDetail from './vessel-detail';
import VesselUpdate from './vessel-update';
import VesselDeleteDialog from './vessel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VesselUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VesselUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VesselDetail} />
      <ErrorBoundaryRoute path={match.url} component={Vessel} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VesselDeleteDialog} />
  </>
);

export default Routes;
