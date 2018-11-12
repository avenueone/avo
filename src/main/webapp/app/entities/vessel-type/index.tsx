import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VesselType from './vessel-type';
import VesselTypeDetail from './vessel-type-detail';
import VesselTypeUpdate from './vessel-type-update';
import VesselTypeDeleteDialog from './vessel-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VesselTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VesselTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VesselTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={VesselType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VesselTypeDeleteDialog} />
  </>
);

export default Routes;
