import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICalendar } from 'app/shared/model/calendar.model';
import { getEntities as getCalendars } from 'app/entities/calendar/calendar.reducer';
import { getEntity, updateEntity, createEntity, reset } from './calendar-attribute.reducer';
import { ICalendarAttribute } from 'app/shared/model/calendar-attribute.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICalendarAttributeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICalendarAttributeUpdateState {
  isNew: boolean;
  calenderId: string;
}

export class CalendarAttributeUpdate extends React.Component<ICalendarAttributeUpdateProps, ICalendarAttributeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      calenderId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCalendars();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { calendarAttributeEntity } = this.props;
      const entity = {
        ...calendarAttributeEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/calendar-attribute');
  };

  render() {
    const { calendarAttributeEntity, calendars, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="avoApp.calendarAttribute.home.createOrEditLabel">
              <Translate contentKey="avoApp.calendarAttribute.home.createOrEditLabel">Create or edit a CalendarAttribute</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : calendarAttributeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="calendar-attribute-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="avoApp.calendarAttribute.name">Name</Translate>
                  </Label>
                  <AvField id="calendar-attribute-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel" for="type">
                    <Translate contentKey="avoApp.calendarAttribute.type">Type</Translate>
                  </Label>
                  <AvField id="calendar-attribute-type" type="text" name="type" />
                </AvGroup>
                <AvGroup>
                  <Label id="valueLabel" for="value">
                    <Translate contentKey="avoApp.calendarAttribute.value">Value</Translate>
                  </Label>
                  <AvField id="calendar-attribute-value" type="text" name="value" />
                </AvGroup>
                <AvGroup>
                  <Label for="calender.id">
                    <Translate contentKey="avoApp.calendarAttribute.calender">Calender</Translate>
                  </Label>
                  <AvInput id="calendar-attribute-calender" type="select" className="form-control" name="calender.id">
                    <option value="" key="0" />
                    {calendars
                      ? calendars.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/calendar-attribute" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  calendars: storeState.calendar.entities,
  calendarAttributeEntity: storeState.calendarAttribute.entity,
  loading: storeState.calendarAttribute.loading,
  updating: storeState.calendarAttribute.updating,
  updateSuccess: storeState.calendarAttribute.updateSuccess
});

const mapDispatchToProps = {
  getCalendars,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CalendarAttributeUpdate);
