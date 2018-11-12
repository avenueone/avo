/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CampaignAttributeComponentsPage from './campaign-attribute.page-object';
import { CampaignAttributeDeleteDialog } from './campaign-attribute.page-object';
import CampaignAttributeUpdatePage from './campaign-attribute-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('CampaignAttribute e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let campaignAttributeUpdatePage: CampaignAttributeUpdatePage;
  let campaignAttributeComponentsPage: CampaignAttributeComponentsPage;
  let campaignAttributeDeleteDialog: CampaignAttributeDeleteDialog;

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

  it('should load CampaignAttributes', async () => {
    await navBarPage.getEntityPage('campaign-attribute');
    campaignAttributeComponentsPage = new CampaignAttributeComponentsPage();
    expect(await campaignAttributeComponentsPage.getTitle().getText()).to.match(/Campaign Attributes/);
  });

  it('should load create CampaignAttribute page', async () => {
    await campaignAttributeComponentsPage.clickOnCreateButton();
    campaignAttributeUpdatePage = new CampaignAttributeUpdatePage();
    expect(await campaignAttributeUpdatePage.getPageTitle().getAttribute('id')).to.match(/avoApp.campaignAttribute.home.createOrEditLabel/);
  });

  it('should create and save CampaignAttributes', async () => {
    const nbButtonsBeforeCreate = await campaignAttributeComponentsPage.countDeleteButtons();

    await campaignAttributeUpdatePage.setNameInput('name');
    expect(await campaignAttributeUpdatePage.getNameInput()).to.match(/name/);
    await campaignAttributeUpdatePage.setTypeInput('type');
    expect(await campaignAttributeUpdatePage.getTypeInput()).to.match(/type/);
    await campaignAttributeUpdatePage.setValueInput('value');
    expect(await campaignAttributeUpdatePage.getValueInput()).to.match(/value/);
    await waitUntilDisplayed(campaignAttributeUpdatePage.getSaveButton());
    await campaignAttributeUpdatePage.save();
    await waitUntilHidden(campaignAttributeUpdatePage.getSaveButton());
    expect(await campaignAttributeUpdatePage.getSaveButton().isPresent()).to.be.false;

    await campaignAttributeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await campaignAttributeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last CampaignAttribute', async () => {
    await campaignAttributeComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await campaignAttributeComponentsPage.countDeleteButtons();
    await campaignAttributeComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    campaignAttributeDeleteDialog = new CampaignAttributeDeleteDialog();
    expect(await campaignAttributeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avoApp.campaignAttribute.delete.question/);
    await campaignAttributeDeleteDialog.clickOnConfirmButton();

    await campaignAttributeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await campaignAttributeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
