import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVessel } from 'app/shared/model/vessel.model';
import { getEntities as getVessels } from 'app/entities/vessel/vessel.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vessel-type.reducer';
import { IVesselType } from 'app/shared/model/vessel-type.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVesselTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVesselTypeUpdateState {
  isNew: boolean;
  vesselId: string;
}

export class VesselTypeUpdate extends React.Component<IVesselTypeUpdateProps, IVesselTypeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      vesselId: '0',
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

    this.props.getVessels();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { vesselTypeEntity } = this.props;
      const entity = {
        ...vesselTypeEntity,
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
    this.props.history.push('/entity/vessel-type');
  };

  render() {
    const { vesselTypeEntity, vessels, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="avoApp.vesselType.home.createOrEditLabel">
              <Translate contentKey="avoApp.vesselType.home.createOrEditLabel">Create or edit a VesselType</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vesselTypeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="vessel-type-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="avoApp.vesselType.name">Name</Translate>
                  </Label>
                  <AvField
                    id="vessel-type-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel">
                    <Translate contentKey="avoApp.vesselType.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="vessel-type-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && vesselTypeEntity.type) || 'FLYER'}
                  >
                    <option value="FLYER">
                      <Translate contentKey="avoApp.VesselTypeEnum.FLYER" />
                    </option>
                    <option value="WEB">
                      <Translate contentKey="avoApp.VesselTypeEnum.WEB" />
                    </option>
                    <option value="EMAIL">
                      <Translate contentKey="avoApp.VesselTypeEnum.EMAIL" />
                    </option>
                    <option value="VR">
                      <Translate contentKey="avoApp.VesselTypeEnum.VR" />
                    </option>
                    <option value="COUPON">
                      <Translate contentKey="avoApp.VesselTypeEnum.COUPON" />
                    </option>
                    <option value="SIGNAGE">
                      <Translate contentKey="avoApp.VesselTypeEnum.SIGNAGE" />
                    </option>
                    <option value="SHELFTAG">
                      <Translate contentKey="avoApp.VesselTypeEnum.SHELFTAG" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="recurringLabel" check>
                    <AvInput id="vessel-type-recurring" type="checkbox" className="form-control" name="recurring" />
                    <Translate contentKey="avoApp.vesselType.recurring">Recurring</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="dayOfMonthLabel" for="dayOfMonth">
                    <Translate contentKey="avoApp.vesselType.dayOfMonth">Day Of Month</Translate>
                  </Label>
                  <AvField id="vessel-type-dayOfMonth" type="string" className="form-control" name="dayOfMonth" />
                </AvGroup>
                <AvGroup>
                  <Label id="dayOfWeekLabel" for="dayOfWeek">
                    <Translate contentKey="avoApp.vesselType.dayOfWeek">Day Of Week</Translate>
                  </Label>
                  <AvField id="vessel-type-dayOfWeek" type="string" className="form-control" name="dayOfWeek" />
                </AvGroup>
                <AvGroup>
                  <Label id="monthLabel" for="month">
                    <Translate contentKey="avoApp.vesselType.month">Month</Translate>
                  </Label>
                  <AvField id="vessel-type-month" type="string" className="form-control" name="month" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vessel-type" replace color="info">
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
  vessels: storeState.vessel.entities,
  vesselTypeEntity: storeState.vesselType.entity,
  loading: storeState.vesselType.loading,
  updating: storeState.vesselType.updating,
  updateSuccess: storeState.vesselType.updateSuccess
});

const mapDispatchToProps = {
  getVessels,
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
)(VesselTypeUpdate);
