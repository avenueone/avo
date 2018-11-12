import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './campaign.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICampaignDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CampaignDetail extends React.Component<ICampaignDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { campaignEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="avoApp.campaign.detail.title">Campaign</Translate> [<b>{campaignEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="avoApp.campaign.name">Name</Translate>
              </span>
            </dt>
            <dd>{campaignEntity.name}</dd>
            <dt>
              <span id="startDate">
                <Translate contentKey="avoApp.campaign.startDate">Start Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={campaignEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">
                <Translate contentKey="avoApp.campaign.endDate">End Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={campaignEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="avoApp.campaign.campaignAttribute">Campaign Attribute</Translate>
            </dt>
            <dd>{campaignEntity.campaignAttribute ? campaignEntity.campaignAttribute.id : ''}</dd>
            <dt>
              <Translate contentKey="avoApp.campaign.calendar">Calendar</Translate>
            </dt>
            <dd>{campaignEntity.calendar ? campaignEntity.calendar.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/campaign" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/campaign/${campaignEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ campaign }: IRootState) => ({
  campaignEntity: campaign.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CampaignDetail);
