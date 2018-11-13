/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ContainerComponentsPage from './container.page-object';
import { ContainerDeleteDialog } from './container.page-object';
import ContainerUpdatePage from './container-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Container e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let containerUpdatePage: ContainerUpdatePage;
  let containerComponentsPage: ContainerComponentsPage;
  let containerDeleteDialog: ContainerDeleteDialog;

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

  it('should load Containers', async () => {
    await navBarPage.getEntityPage('container');
    containerComponentsPage = new ContainerComponentsPage();
    expect(await containerComponentsPage.getTitle().getText()).to.match(/Containers/);
  });

  it('should load create Container page', async () => {
    await containerComponentsPage.clickOnCreateButton();
    containerUpdatePage = new ContainerUpdatePage();
    expect(await containerUpdatePage.getPageTitle().getAttribute('id')).to.match(/avoApp.container.home.createOrEditLabel/);
  });

  it('should create and save Containers', async () => {
    const nbButtonsBeforeCreate = await containerComponentsPage.countDeleteButtons();

    await containerUpdatePage.setNameInput('name');
    expect(await containerUpdatePage.getNameInput()).to.match(/name/);
    await containerUpdatePage.vesselSelectLastOption();
    await waitUntilDisplayed(containerUpdatePage.getSaveButton());
    await containerUpdatePage.save();
    await waitUntilHidden(containerUpdatePage.getSaveButton());
    expect(await containerUpdatePage.getSaveButton().isPresent()).to.be.false;

    await containerComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await containerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Container', async () => {
    await containerComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await containerComponentsPage.countDeleteButtons();
    await containerComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    containerDeleteDialog = new ContainerDeleteDialog();
    expect(await containerDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avoApp.container.delete.question/);
    await containerDeleteDialog.clickOnConfirmButton();

    await containerComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await containerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
