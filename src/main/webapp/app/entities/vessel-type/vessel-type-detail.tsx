import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vessel-type.reducer';
import { IVesselType } from 'app/shared/model/vessel-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVesselTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VesselTypeDetail extends React.Component<IVesselTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vesselTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="avoApp.vesselType.detail.title">VesselType</Translate> [<b>{vesselTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="avoApp.vesselType.name">Name</Translate>
              </span>
            </dt>
            <dd>{vesselTypeEntity.name}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="avoApp.vesselType.type">Type</Translate>
              </span>
            </dt>
            <dd>{vesselTypeEntity.type}</dd>
            <dt>
              <span id="recurring">
                <Translate contentKey="avoApp.vesselType.recurring">Recurring</Translate>
              </span>
            </dt>
            <dd>{vesselTypeEntity.recurring ? 'true' : 'false'}</dd>
            <dt>
              <span id="dayOfMonth">
                <Translate contentKey="avoApp.vesselType.dayOfMonth">Day Of Month</Translate>
              </span>
            </dt>
            <dd>{vesselTypeEntity.dayOfMonth}</dd>
            <dt>
              <span id="dayOfWeek">
                <Translate contentKey="avoApp.vesselType.dayOfWeek">Day Of Week</Translate>
              </span>
            </dt>
            <dd>{vesselTypeEntity.dayOfWeek}</dd>
            <dt>
              <span id="month">
                <Translate contentKey="avoApp.vesselType.month">Month</Translate>
              </span>
            </dt>
            <dd>{vesselTypeEntity.month}</dd>
          </dl>
          <Button tag={Link} to="/entity/vessel-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/vessel-type/${vesselTypeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ vesselType }: IRootState) => ({
  vesselTypeEntity: vesselType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VesselTypeDetail);
