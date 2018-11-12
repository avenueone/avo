/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CampaignComponentsPage from './campaign.page-object';
import { CampaignDeleteDialog } from './campaign.page-object';
import CampaignUpdatePage from './campaign-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Campaign e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let campaignUpdatePage: CampaignUpdatePage;
  let campaignComponentsPage: CampaignComponentsPage;
  let campaignDeleteDialog: CampaignDeleteDialog;

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

  it('should load Campaigns', async () => {
    await navBarPage.getEntityPage('campaign');
    campaignComponentsPage = new CampaignComponentsPage();
    expect(await campaignComponentsPage.getTitle().getText()).to.match(/Campaigns/);
  });

  it('should load create Campaign page', async () => {
    await campaignComponentsPage.clickOnCreateButton();
    campaignUpdatePage = new CampaignUpdatePage();
    expect(await campaignUpdatePage.getPageTitle().getAttribute('id')).to.match(/avoApp.campaign.home.createOrEditLabel/);
  });

  it('should create and save Campaigns', async () => {
    const nbButtonsBeforeCreate = await campaignComponentsPage.countDeleteButtons();

    await campaignUpdatePage.setNameInput('name');
    expect(await campaignUpdatePage.getNameInput()).to.match(/name/);
    await campaignUpdatePage.setStartDateInput('01-01-2001');
    expect(await campaignUpdatePage.getStartDateInput()).to.eq('2001-01-01');
    await campaignUpdatePage.setEndDateInput('01-01-2001');
    expect(await campaignUpdatePage.getEndDateInput()).to.eq('2001-01-01');
    await campaignUpdatePage.campaignAttributeSelectLastOption();
    await campaignUpdatePage.calendarSelectLastOption();
    await waitUntilDisplayed(campaignUpdatePage.getSaveButton());
    await campaignUpdatePage.save();
    await waitUntilHidden(campaignUpdatePage.getSaveButton());
    expect(await campaignUpdatePage.getSaveButton().isPresent()).to.be.false;

    await campaignComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await campaignComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Campaign', async () => {
    await campaignComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await campaignComponentsPage.countDeleteButtons();
    await campaignComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    campaignDeleteDialog = new CampaignDeleteDialog();
    expect(await campaignDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avoApp.campaign.delete.question/);
    await campaignDeleteDialog.clickOnConfirmButton();

    await campaignComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await campaignComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
