import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './campaign-attribute.reducer';
import { ICampaignAttribute } from 'app/shared/model/campaign-attribute.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICampaignAttributeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CampaignAttributeDetail extends React.Component<ICampaignAttributeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { campaignAttributeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="avoApp.campaignAttribute.detail.title">CampaignAttribute</Translate> [<b>{campaignAttributeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="avoApp.campaignAttribute.name">Name</Translate>
              </span>
            </dt>
            <dd>{campaignAttributeEntity.name}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="avoApp.campaignAttribute.type">Type</Translate>
              </span>
            </dt>
            <dd>{campaignAttributeEntity.type}</dd>
            <dt>
              <span id="value">
                <Translate contentKey="avoApp.campaignAttribute.value">Value</Translate>
              </span>
            </dt>
            <dd>{campaignAttributeEntity.value}</dd>
          </dl>
          <Button tag={Link} to="/entity/campaign-attribute" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/campaign-attribute/${campaignAttributeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ campaignAttribute }: IRootState) => ({
  campaignAttributeEntity: campaignAttribute.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CampaignAttributeDetail);
