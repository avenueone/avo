import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './calendar-attribute.reducer';
import { ICalendarAttribute } from 'app/shared/model/calendar-attribute.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICalendarAttributeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CalendarAttributeDetail extends React.Component<ICalendarAttributeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { calendarAttributeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="avoApp.calendarAttribute.detail.title">CalendarAttribute</Translate> [<b>{calendarAttributeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="avoApp.calendarAttribute.name">Name</Translate>
              </span>
            </dt>
            <dd>{calendarAttributeEntity.name}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="avoApp.calendarAttribute.type">Type</Translate>
              </span>
            </dt>
            <dd>{calendarAttributeEntity.type}</dd>
            <dt>
              <span id="value">
                <Translate contentKey="avoApp.calendarAttribute.value">Value</Translate>
              </span>
            </dt>
            <dd>{calendarAttributeEntity.value}</dd>
            <dt>
              <Translate contentKey="avoApp.calendarAttribute.calender">Calender</Translate>
            </dt>
            <dd>{calendarAttributeEntity.calender ? calendarAttributeEntity.calender.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/calendar-attribute" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/calendar-attribute/${calendarAttributeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ calendarAttribute }: IRootState) => ({
  calendarAttributeEntity: calendarAttribute.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CalendarAttributeDetail);
