import { element, by, ElementFinder } from 'protractor';

export default class VesselUpdatePage {
  pageTitle: ElementFinder = element(by.id('avoApp.vessel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#vessel-name'));
  startDateInput: ElementFinder = element(by.css('input#vessel-startDate'));
  endDateInput: ElementFinder = element(by.css('input#vessel-endDate'));
  descriptionInput: ElementFinder = element(by.css('input#vessel-description'));
  containerSelect: ElementFinder = element(by.css('select#vessel-container'));
  vesseltypeSelect: ElementFinder = element(by.css('select#vessel-vesseltype'));
  campaignSelect: ElementFinder = element(by.css('select#vessel-campaign'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setStartDateInput(startDate) {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput() {
    return this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate) {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput() {
    return this.endDateInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async containerSelectLastOption() {
    await this.containerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async containerSelectOption(option) {
    await this.containerSelect.sendKeys(option);
  }

  getContainerSelect() {
    return this.containerSelect;
  }

  async getContainerSelectedOption() {
    return this.containerSelect.element(by.css('option:checked')).getText();
  }

  async vesseltypeSelectLastOption() {
    await this.vesseltypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async vesseltypeSelectOption(option) {
    await this.vesseltypeSelect.sendKeys(option);
  }

  getVesseltypeSelect() {
    return this.vesseltypeSelect;
  }

  async getVesseltypeSelectedOption() {
    return this.vesseltypeSelect.element(by.css('option:checked')).getText();
  }

  async campaignSelectLastOption() {
    await this.campaignSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async campaignSelectOption(option) {
    await this.campaignSelect.sendKeys(option);
  }

  getCampaignSelect() {
    return this.campaignSelect;
  }

  async getCampaignSelectedOption() {
    return this.campaignSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
