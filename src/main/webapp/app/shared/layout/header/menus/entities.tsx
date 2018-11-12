import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/vessel">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.vessel" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/campaign">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.campaign" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/calendar">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.calendar" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/campaign-attribute">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.campaignAttribute" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/calendar-attribute">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.calendarAttribute" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/vessel-attribute">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.vesselAttribute" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/container">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.container" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/vessel-type">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.vesselType" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
