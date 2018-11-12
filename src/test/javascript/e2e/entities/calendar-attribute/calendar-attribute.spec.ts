/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CalendarAttributeComponentsPage from './calendar-attribute.page-object';
import { CalendarAttributeDeleteDialog } from './calendar-attribute.page-object';
import CalendarAttributeUpdatePage from './calendar-attribute-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('CalendarAttribute e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let calendarAttributeUpdatePage: CalendarAttributeUpdatePage;
  let calendarAttributeComponentsPage: CalendarAttributeComponentsPage;
  let calendarAttributeDeleteDialog: CalendarAttributeDeleteDialog;

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

  it('should load CalendarAttributes', async () => {
    await navBarPage.getEntityPage('calendar-attribute');
    calendarAttributeComponentsPage = new CalendarAttributeComponentsPage();
    expect(await calendarAttributeComponentsPage.getTitle().getText()).to.match(/Calendar Attributes/);
  });

  it('should load create CalendarAttribute page', async () => {
    await calendarAttributeComponentsPage.clickOnCreateButton();
    calendarAttributeUpdatePage = new CalendarAttributeUpdatePage();
    expect(await calendarAttributeUpdatePage.getPageTitle().getAttribute('id')).to.match(/avoApp.calendarAttribute.home.createOrEditLabel/);
  });

  it('should create and save CalendarAttributes', async () => {
    const nbButtonsBeforeCreate = await calendarAttributeComponentsPage.countDeleteButtons();

    await calendarAttributeUpdatePage.setNameInput('name');
    expect(await calendarAttributeUpdatePage.getNameInput()).to.match(/name/);
    await calendarAttributeUpdatePage.setTypeInput('type');
    expect(await calendarAttributeUpdatePage.getTypeInput()).to.match(/type/);
    await calendarAttributeUpdatePage.setValueInput('value');
    expect(await calendarAttributeUpdatePage.getValueInput()).to.match(/value/);
    await calendarAttributeUpdatePage.calenderSelectLastOption();
    await waitUntilDisplayed(calendarAttributeUpdatePage.getSaveButton());
    await calendarAttributeUpdatePage.save();
    await waitUntilHidden(calendarAttributeUpdatePage.getSaveButton());
    expect(await calendarAttributeUpdatePage.getSaveButton().isPresent()).to.be.false;

    await calendarAttributeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await calendarAttributeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last CalendarAttribute', async () => {
    await calendarAttributeComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await calendarAttributeComponentsPage.countDeleteButtons();
    await calendarAttributeComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    calendarAttributeDeleteDialog = new CalendarAttributeDeleteDialog();
    expect(await calendarAttributeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avoApp.calendarAttribute.delete.question/);
    await calendarAttributeDeleteDialog.clickOnConfirmButton();

    await calendarAttributeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await calendarAttributeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
