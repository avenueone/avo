import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CampaignAttribute from './campaign-attribute';
import CampaignAttributeDetail from './campaign-attribute-detail';
import CampaignAttributeUpdate from './campaign-attribute-update';
import CampaignAttributeDeleteDialog from './campaign-attribute-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CampaignAttributeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CampaignAttributeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CampaignAttributeDetail} />
      <ErrorBoundaryRoute path={match.url} component={CampaignAttribute} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CampaignAttributeDeleteDialog} />
  </>
);

export default Routes;
