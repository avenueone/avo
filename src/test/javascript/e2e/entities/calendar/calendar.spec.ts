/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CalendarComponentsPage from './calendar.page-object';
import { CalendarDeleteDialog } from './calendar.page-object';
import CalendarUpdatePage from './calendar-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Calendar e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let calendarUpdatePage: CalendarUpdatePage;
  let calendarComponentsPage: CalendarComponentsPage;
  let calendarDeleteDialog: CalendarDeleteDialog;

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

  it('should load Calendars', async () => {
    await navBarPage.getEntityPage('calendar');
    calendarComponentsPage = new CalendarComponentsPage();
    expect(await calendarComponentsPage.getTitle().getText()).to.match(/Calendars/);
  });

  it('should load create Calendar page', async () => {
    await calendarComponentsPage.clickOnCreateButton();
    calendarUpdatePage = new CalendarUpdatePage();
    expect(await calendarUpdatePage.getPageTitle().getAttribute('id')).to.match(/avoApp.calendar.home.createOrEditLabel/);
  });

  it('should create and save Calendars', async () => {
    const nbButtonsBeforeCreate = await calendarComponentsPage.countDeleteButtons();

    await calendarUpdatePage.setNameInput('name');
    expect(await calendarUpdatePage.getNameInput()).to.match(/name/);
    await calendarUpdatePage.setDescriptionInput('description');
    expect(await calendarUpdatePage.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(calendarUpdatePage.getSaveButton());
    await calendarUpdatePage.save();
    await waitUntilHidden(calendarUpdatePage.getSaveButton());
    expect(await calendarUpdatePage.getSaveButton().isPresent()).to.be.false;

    await calendarComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await calendarComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Calendar', async () => {
    await calendarComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await calendarComponentsPage.countDeleteButtons();
    await calendarComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    calendarDeleteDialog = new CalendarDeleteDialog();
    expect(await calendarDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avoApp.calendar.delete.question/);
    await calendarDeleteDialog.clickOnConfirmButton();

    await calendarComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await calendarComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
