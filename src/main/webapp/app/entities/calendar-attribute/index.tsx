import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CalendarAttribute from './calendar-attribute';
import CalendarAttributeDetail from './calendar-attribute-detail';
import CalendarAttributeUpdate from './calendar-attribute-update';
import CalendarAttributeDeleteDialog from './calendar-attribute-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CalendarAttributeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CalendarAttributeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CalendarAttributeDetail} />
      <ErrorBoundaryRoute path={match.url} component={CalendarAttribute} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CalendarAttributeDeleteDialog} />
  </>
);

export default Routes;
