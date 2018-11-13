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
import { getEntity, updateEntity, createEntity, reset } from './container.reducer';
import { IContainer } from 'app/shared/model/container.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IContainerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IContainerUpdateState {
  isNew: boolean;
  vesselId: string;
}

export class ContainerUpdate extends React.Component<IContainerUpdateProps, IContainerUpdateState> {
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
      const { containerEntity } = this.props;
      const entity = {
        ...containerEntity,
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
    this.props.history.push('/entity/container');
  };

  render() {
    const { containerEntity, vessels, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="avoApp.container.home.createOrEditLabel">
              <Translate contentKey="avoApp.container.home.createOrEditLabel">Create or edit a Container</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : containerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="container-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="avoApp.container.name">Name</Translate>
                  </Label>
                  <AvField id="container-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="vessel.id">
                    <Translate contentKey="avoApp.container.vessel">Vessel</Translate>
                  </Label>
                  <AvInput id="container-vessel" type="select" className="form-control" name="vessel.id">
                    <option value="" key="0" />
                    {vessels
                      ? vessels.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/container" replace color="info">
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
  containerEntity: storeState.container.entity,
  loading: storeState.container.loading,
  updating: storeState.container.updating,
  updateSuccess: storeState.container.updateSuccess
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
)(ContainerUpdate);
