import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vessel.reducer';
import { IVessel } from 'app/shared/model/vessel.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVesselDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VesselDetail extends React.Component<IVesselDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vesselEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="avoApp.vessel.detail.title">Vessel</Translate> [<b>{vesselEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="avoApp.vessel.name">Name</Translate>
              </span>
            </dt>
            <dd>{vesselEntity.name}</dd>
            <dt>
              <span id="startDate">
                <Translate contentKey="avoApp.vessel.startDate">Start Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={vesselEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">
                <Translate contentKey="avoApp.vessel.endDate">End Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={vesselEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="description">
                <Translate contentKey="avoApp.vessel.description">Description</Translate>
              </span>
            </dt>
            <dd>{vesselEntity.description}</dd>
            <dt>
              <Translate contentKey="avoApp.vessel.vesseltype">Vesseltype</Translate>
            </dt>
            <dd>
              {vesselEntity.vesseltypes
                ? vesselEntity.vesseltypes.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === vesselEntity.vesseltypes.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="avoApp.vessel.campaign">Campaign</Translate>
            </dt>
            <dd>{vesselEntity.campaign ? vesselEntity.campaign.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/vessel" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/vessel/${vesselEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ vessel }: IRootState) => ({
  vesselEntity: vessel.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VesselDetail);
