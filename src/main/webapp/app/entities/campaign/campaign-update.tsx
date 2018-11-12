import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICampaignAttribute } from 'app/shared/model/campaign-attribute.model';
import { getEntities as getCampaignAttributes } from 'app/entities/campaign-attribute/campaign-attribute.reducer';
import { ICalendar } from 'app/shared/model/calendar.model';
import { getEntities as getCalendars } from 'app/entities/calendar/calendar.reducer';
import { getEntity, updateEntity, createEntity, reset } from './campaign.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICampaignUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICampaignUpdateState {
  isNew: boolean;
  campaignAttributeId: string;
  calendarId: string;
}

export class CampaignUpdate extends React.Component<ICampaignUpdateProps, ICampaignUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      campaignAttributeId: '0',
      calendarId: '0',
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

    this.props.getCampaignAttributes();
    this.props.getCalendars();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { campaignEntity } = this.props;
      const entity = {
        ...campaignEntity,
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
    this.props.history.push('/entity/campaign');
  };

  render() {
    const { campaignEntity, campaignAttributes, calendars, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="avoApp.campaign.home.createOrEditLabel">
              <Translate contentKey="avoApp.campaign.home.createOrEditLabel">Create or edit a Campaign</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : campaignEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="campaign-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="avoApp.campaign.name">Name</Translate>
                  </Label>
                  <AvField id="campaign-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="startDateLabel" for="startDate">
                    <Translate contentKey="avoApp.campaign.startDate">Start Date</Translate>
                  </Label>
                  <AvField
                    id="campaign-startDate"
                    type="date"
                    className="form-control"
                    name="startDate"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endDateLabel" for="endDate">
                    <Translate contentKey="avoApp.campaign.endDate">End Date</Translate>
                  </Label>
                  <AvField
                    id="campaign-endDate"
                    type="date"
                    className="form-control"
                    name="endDate"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="campaignAttribute.id">
                    <Translate contentKey="avoApp.campaign.campaignAttribute">Campaign Attribute</Translate>
                  </Label>
                  <AvInput id="campaign-campaignAttribute" type="select" className="form-control" name="campaignAttribute.id">
                    <option value="" key="0" />
                    {campaignAttributes
                      ? campaignAttributes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="calendar.id">
                    <Translate contentKey="avoApp.campaign.calendar">Calendar</Translate>
                  </Label>
                  <AvInput id="campaign-calendar" type="select" className="form-control" name="calendar.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/campaign" replace color="info">
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
  campaignAttributes: storeState.campaignAttribute.entities,
  calendars: storeState.calendar.entities,
  campaignEntity: storeState.campaign.entity,
  loading: storeState.campaign.loading,
  updating: storeState.campaign.updating,
  updateSuccess: storeState.campaign.updateSuccess
});

const mapDispatchToProps = {
  getCampaignAttributes,
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
)(CampaignUpdate);
