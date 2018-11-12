/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import VesselTypeComponentsPage from './vessel-type.page-object';
import { VesselTypeDeleteDialog } from './vessel-type.page-object';
import VesselTypeUpdatePage from './vessel-type-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('VesselType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vesselTypeUpdatePage: VesselTypeUpdatePage;
  let vesselTypeComponentsPage: VesselTypeComponentsPage;
  let vesselTypeDeleteDialog: VesselTypeDeleteDialog;

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

  it('should load VesselTypes', async () => {
    await navBarPage.getEntityPage('vessel-type');
    vesselTypeComponentsPage = new VesselTypeComponentsPage();
    expect(await vesselTypeComponentsPage.getTitle().getText()).to.match(/Vessel Types/);
  });

  it('should load create VesselType page', async () => {
    await vesselTypeComponentsPage.clickOnCreateButton();
    vesselTypeUpdatePage = new VesselTypeUpdatePage();
    expect(await vesselTypeUpdatePage.getPageTitle().getAttribute('id')).to.match(/avoApp.vesselType.home.createOrEditLabel/);
  });

  it('should create and save VesselTypes', async () => {
    const nbButtonsBeforeCreate = await vesselTypeComponentsPage.countDeleteButtons();

    await vesselTypeUpdatePage.setNameInput('name');
    expect(await vesselTypeUpdatePage.getNameInput()).to.match(/name/);
    await vesselTypeUpdatePage.typeSelectLastOption();
    const selectedRecurring = await vesselTypeUpdatePage.getRecurringInput().isSelected();
    if (selectedRecurring) {
      await vesselTypeUpdatePage.getRecurringInput().click();
      expect(await vesselTypeUpdatePage.getRecurringInput().isSelected()).to.be.false;
    } else {
      await vesselTypeUpdatePage.getRecurringInput().click();
      expect(await vesselTypeUpdatePage.getRecurringInput().isSelected()).to.be.true;
    }
    await vesselTypeUpdatePage.setDayOfMonthInput('5');
    expect(await vesselTypeUpdatePage.getDayOfMonthInput()).to.eq('5');
    await vesselTypeUpdatePage.setDayOfWeekInput('5');
    expect(await vesselTypeUpdatePage.getDayOfWeekInput()).to.eq('5');
    await vesselTypeUpdatePage.setMonthInput('5');
    expect(await vesselTypeUpdatePage.getMonthInput()).to.eq('5');
    await waitUntilDisplayed(vesselTypeUpdatePage.getSaveButton());
    await vesselTypeUpdatePage.save();
    await waitUntilHidden(vesselTypeUpdatePage.getSaveButton());
    expect(await vesselTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

    await vesselTypeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await vesselTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last VesselType', async () => {
    await vesselTypeComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await vesselTypeComponentsPage.countDeleteButtons();
    await vesselTypeComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    vesselTypeDeleteDialog = new VesselTypeDeleteDialog();
    expect(await vesselTypeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avoApp.vesselType.delete.question/);
    await vesselTypeDeleteDialog.clickOnConfirmButton();

    await vesselTypeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await vesselTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
