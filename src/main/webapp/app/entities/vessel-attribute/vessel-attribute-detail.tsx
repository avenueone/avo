import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vessel-attribute.reducer';
import { IVesselAttribute } from 'app/shared/model/vessel-attribute.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVesselAttributeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VesselAttributeDetail extends React.Component<IVesselAttributeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vesselAttributeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="avoApp.vesselAttribute.detail.title">VesselAttribute</Translate> [<b>{vesselAttributeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="avoApp.vesselAttribute.name">Name</Translate>
              </span>
            </dt>
            <dd>{vesselAttributeEntity.name}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="avoApp.vesselAttribute.type">Type</Translate>
              </span>
            </dt>
            <dd>{vesselAttributeEntity.type}</dd>
            <dt>
              <span id="value">
                <Translate contentKey="avoApp.vesselAttribute.value">Value</Translate>
              </span>
            </dt>
            <dd>{vesselAttributeEntity.value}</dd>
            <dt>
              <Translate contentKey="avoApp.vesselAttribute.vessel">Vessel</Translate>
            </dt>
            <dd>{vesselAttributeEntity.vessel ? vesselAttributeEntity.vessel.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/vessel-attribute" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/vessel-attribute/${vesselAttributeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ vesselAttribute }: IRootState) => ({
  vesselAttributeEntity: vesselAttribute.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VesselAttributeDetail);
