import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { translate, Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert, Label, Button } from 'reactstrap';
import { AvForm, AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import {getSession, login} from 'app/shared/reducers/authentication';

export interface IHomeProp extends StateProps, DispatchProps {
  loginError: boolean;
  handleLogin: Function;
}

export class Home extends React.Component<IHomeProp> {
  handleSubmit = (event, errors, { username, password, rememberMe }) => {
    const { handleLogin } = this.props;
    handleLogin(username, password, rememberMe);
  };

  handleLogin = (username, password, rememberMe = false) => {
    this.props.login(username, password, rememberMe);
  };


  componentDidMount() {
    this.props.getSession();
  }

  render() {
    const { account,loginError } = this.props;
    return (

      <Row >
        <Col md="3" lg="3">

        </Col>
        <Col md="9" lg="5">
          <h2>
            <Translate contentKey="home.title">Welcome, Avenue 1</Translate>
          </h2>
          <p className="lead">
            <Translate contentKey="home.subtitle">This is our temporary homepage</Translate>
          </p>
          {account && account.login ? (
            <div>
              <Alert color="success">
                <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                  You are logged in as user {account.login}.
                </Translate>
              </Alert>
            </div>

          ) : (


              <Col md="4" lg="5">
                <AvForm onSubmit={this.handleSubmit}>
                <AvField
                  name="username"
                  label={translate('global.form.username')}
                  placeholder={translate('global.form.username.placeholder')}
                  required
                  errorMessage="Username cannot be empty!"
                  autoFocus
                />
                <AvField
                  name="password"
                  type="password"
                  label={translate('login.form.password')}
                  placeholder={translate('login.form.password.placeholder')}
                  required
                  errorMessage="Password cannot be empty!"
                />
                <AvGroup check inline>
                  <Label className="form-check-label">
                    <AvInput type="checkbox" name="rememberMe" /> <Translate contentKey="login.form.rememberme">Remember me</Translate>
                  </Label>
                </AvGroup>


                <Button color="primary" type="submit">
                  <Translate contentKey="login.form.button">Sign in</Translate>
                </Button>
                </AvForm>
              </Col>

          )}

        </Col>
        <Col md="3" className="pad">
          <span className="hipster rounded" />
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

const mapDispatchToProps = { getSession, login };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
