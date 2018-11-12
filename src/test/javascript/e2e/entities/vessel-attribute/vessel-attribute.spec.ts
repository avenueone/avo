/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import VesselAttributeComponentsPage from './vessel-attribute.page-object';
import { VesselAttributeDeleteDialog } from './vessel-attribute.page-object';
import VesselAttributeUpdatePage from './vessel-attribute-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('VesselAttribute e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vesselAttributeUpdatePage: VesselAttributeUpdatePage;
  let vesselAttributeComponentsPage: VesselAttributeComponentsPage;
  let vesselAttributeDeleteDialog: VesselAttributeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();

    await waitUntilDisplayed(navBarPage.entityMenu);
  });

  it('should load VesselAttributes', async () => {
    await navBarPage.getEntityPage('vessel-attribute');
    vesselAttributeComponentsPage = new VesselAttributeComponentsPage();
    expect(await vesselAttributeComponentsPage.getTitle().getText()).to.match(/Vessel Attributes/);
  });

  it('should load create VesselAttribute page', async () => {
    await vesselAttributeComponentsPage.clickOnCreateButton();
    vesselAttributeUpdatePage = new VesselAttributeUpdatePage();
    expect(await vesselAttributeUpdatePage.getPageTitle().getAttribute('id')).to.match(/avoApp.vesselAttribute.home.createOrEditLabel/);
  });

  it('should create and save VesselAttributes', async () => {
    const nbButtonsBeforeCreate = await vesselAttributeComponentsPage.countDeleteButtons();

    await vesselAttributeUpdatePage.setNameInput('name');
    expect(await vesselAttributeUpdatePage.getNameInput()).to.match(/name/);
    await vesselAttributeUpdatePage.setTypeInput('type');
    expect(await vesselAttributeUpdatePage.getTypeInput()).to.match(/type/);
    await vesselAttributeUpdatePage.setValueInput('value');
    expect(await vesselAttributeUpdatePage.getValueInput()).to.match(/value/);
    await vesselAttributeUpdatePage.vesselSelectLastOption();
    await waitUntilDisplayed(vesselAttributeUpdatePage.getSaveButton());
    await vesselAttributeUpdatePage.save();
    await waitUntilHidden(vesselAttributeUpdatePage.getSaveButton());
    expect(await vesselAttributeUpdatePage.getSaveButton().isPresent()).to.be.false;

    await vesselAttributeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await vesselAttributeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last VesselAttribute', async () => {
    await vesselAttributeComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await vesselAttributeComponentsPage.countDeleteButtons();
    await vesselAttributeComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    vesselAttributeDeleteDialog = new VesselAttributeDeleteDialog();
    expect(await vesselAttributeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avoApp.vesselAttribute.delete.question/);
    await vesselAttributeDeleteDialog.clickOnConfirmButton();

    await vesselAttributeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await vesselAttributeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
