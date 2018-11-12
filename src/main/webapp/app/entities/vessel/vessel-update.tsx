import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IContainer } from 'app/shared/model/container.model';
import { getEntities as getContainers } from 'app/entities/container/container.reducer';
import { IVesselType } from 'app/shared/model/vessel-type.model';
import { getEntities as getVesselTypes } from 'app/entities/vessel-type/vessel-type.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
import { getEntities as getCampaigns } from 'app/entities/campaign/campaign.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vessel.reducer';
import { IVessel } from 'app/shared/model/vessel.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVesselUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVesselUpdateState {
  isNew: boolean;
  idsvesseltype: any[];
  containerId: string;
  campaignId: string;
}

export class VesselUpdate extends React.Component<IVesselUpdateProps, IVesselUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsvesseltype: [],
      containerId: '0',
      campaignId: '0',
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

    this.props.getContainers();
    this.props.getVesselTypes();
    this.props.getCampaigns();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { vesselEntity } = this.props;
      const entity = {
        ...vesselEntity,
        ...values,
        vesseltypes: mapIdList(values.vesseltypes)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/vessel');
  };

  render() {
    const { vesselEntity, containers, vesselTypes, campaigns, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="avoApp.vessel.home.createOrEditLabel">
              <Translate contentKey="avoApp.vessel.home.createOrEditLabel">Create or edit a Vessel</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vesselEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="vessel-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="avoApp.vessel.name">Name</Translate>
                  </Label>
                  <AvField
                    id="vessel-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      minLength: { value: 4, errorMessage: translate('entity.validation.minlength', { min: 4 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="startDateLabel" for="startDate">
                    <Translate contentKey="avoApp.vessel.startDate">Start Date</Translate>
                  </Label>
                  <AvField
                    id="vessel-startDate"
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
                    <Translate contentKey="avoApp.vessel.endDate">End Date</Translate>
                  </Label>
                  <AvField
                    id="vessel-endDate"
                    type="date"
                    className="form-control"
                    name="endDate"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="avoApp.vessel.description">Description</Translate>
                  </Label>
                  <AvField id="vessel-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label for="container.id">
                    <Translate contentKey="avoApp.vessel.container">Container</Translate>
                  </Label>
                  <AvInput id="vessel-container" type="select" className="form-control" name="container.id">
                    <option value="" key="0" />
                    {containers
                      ? containers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="vesselTypes">
                    <Translate contentKey="avoApp.vessel.vesseltype">Vesseltype</Translate>
                  </Label>
                  <AvInput
                    id="vessel-vesseltype"
                    type="select"
                    multiple
                    className="form-control"
                    name="vesseltypes"
                    value={vesselEntity.vesseltypes && vesselEntity.vesseltypes.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {vesselTypes
                      ? vesselTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="campaign.id">
                    <Translate contentKey="avoApp.vessel.campaign">Campaign</Translate>
                  </Label>
                  <AvInput id="vessel-campaign" type="select" className="form-control" name="campaign.id">
                    <option value="" key="0" />
                    {campaigns
                      ? campaigns.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vessel" replace color="info">
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
  containers: storeState.container.entities,
  vesselTypes: storeState.vesselType.entities,
  campaigns: storeState.campaign.entities,
  vesselEntity: storeState.vessel.entity,
  loading: storeState.vessel.loading,
  updating: storeState.vessel.updating,
  updateSuccess: storeState.vessel.updateSuccess
});

const mapDispatchToProps = {
  getContainers,
  getVesselTypes,
  getCampaigns,
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
)(VesselUpdate);
