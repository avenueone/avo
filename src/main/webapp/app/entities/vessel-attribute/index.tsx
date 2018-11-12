import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VesselAttribute from './vessel-attribute';
import VesselAttributeDetail from './vessel-attribute-detail';
import VesselAttributeUpdate from './vessel-attribute-update';
import VesselAttributeDeleteDialog from './vessel-attribute-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VesselAttributeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VesselAttributeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VesselAttributeDetail} />
      <ErrorBoundaryRoute path={match.url} component={VesselAttribute} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VesselAttributeDeleteDialog} />
  </>
);

export default Routes;
