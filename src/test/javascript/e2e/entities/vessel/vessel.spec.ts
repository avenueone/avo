/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import VesselComponentsPage from './vessel.page-object';
import { VesselDeleteDialog } from './vessel.page-object';
import VesselUpdatePage from './vessel-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Vessel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vesselUpdatePage: VesselUpdatePage;
  let vesselComponentsPage: VesselComponentsPage;
  let vesselDeleteDialog: VesselDeleteDialog;

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

  it('should load Vessels', async () => {
    await navBarPage.getEntityPage('vessel');
    vesselComponentsPage = new VesselComponentsPage();
    expect(await vesselComponentsPage.getTitle().getText()).to.match(/Vessels/);
  });

  it('should load create Vessel page', async () => {
    await vesselComponentsPage.clickOnCreateButton();
    vesselUpdatePage = new VesselUpdatePage();
    expect(await vesselUpdatePage.getPageTitle().getAttribute('id')).to.match(/avoApp.vessel.home.createOrEditLabel/);
  });

  it('should create and save Vessels', async () => {
    const nbButtonsBeforeCreate = await vesselComponentsPage.countDeleteButtons();

    await vesselUpdatePage.setNameInput('name');
    expect(await vesselUpdatePage.getNameInput()).to.match(/name/);
    await vesselUpdatePage.setStartDateInput('01-01-2001');
    expect(await vesselUpdatePage.getStartDateInput()).to.eq('2001-01-01');
    await vesselUpdatePage.setEndDateInput('01-01-2001');
    expect(await vesselUpdatePage.getEndDateInput()).to.eq('2001-01-01');
    await vesselUpdatePage.setDescriptionInput('description');
    expect(await vesselUpdatePage.getDescriptionInput()).to.match(/description/);
    await vesselUpdatePage.containerSelectLastOption();
    // vesselUpdatePage.vesseltypeSelectLastOption();
    await vesselUpdatePage.campaignSelectLastOption();
    await waitUntilDisplayed(vesselUpdatePage.getSaveButton());
    await vesselUpdatePage.save();
    await waitUntilHidden(vesselUpdatePage.getSaveButton());
    expect(await vesselUpdatePage.getSaveButton().isPresent()).to.be.false;

    await vesselComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await vesselComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Vessel', async () => {
    await vesselComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await vesselComponentsPage.countDeleteButtons();
    await vesselComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    vesselDeleteDialog = new VesselDeleteDialog();
    expect(await vesselDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avoApp.vessel.delete.question/);
    await vesselDeleteDialog.clickOnConfirmButton();

    await vesselComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await vesselComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
