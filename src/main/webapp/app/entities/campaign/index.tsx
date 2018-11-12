import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Campaign from './campaign';
import CampaignDetail from './campaign-detail';
import CampaignUpdate from './campaign-update';
import CampaignDeleteDialog from './campaign-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CampaignUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CampaignUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CampaignDetail} />
      <ErrorBoundaryRoute path={match.url} component={Campaign} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CampaignDeleteDialog} />
  </>
);

export default Routes;
