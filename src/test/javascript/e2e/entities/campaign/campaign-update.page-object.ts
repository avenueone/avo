import { element, by, ElementFinder } from 'protractor';

export default class CampaignUpdatePage {
  pageTitle: ElementFinder = element(by.id('avoApp.campaign.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#campaign-name'));
  startDateInput: ElementFinder = element(by.css('input#campaign-startDate'));
  endDateInput: ElementFinder = element(by.css('input#campaign-endDate'));
  campaignAttributeSelect: ElementFinder = element(by.css('select#campaign-campaignAttribute'));
  calendarSelect: ElementFinder = element(by.css('select#campaign-calendar'));

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

  async campaignAttributeSelectLastOption() {
    await this.campaignAttributeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async campaignAttributeSelectOption(option) {
    await this.campaignAttributeSelect.sendKeys(option);
  }

  getCampaignAttributeSelect() {
    return this.campaignAttributeSelect;
  }

  async getCampaignAttributeSelectedOption() {
    return this.campaignAttributeSelect.element(by.css('option:checked')).getText();
  }

  async calendarSelectLastOption() {
    await this.calendarSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async calendarSelectOption(option) {
    await this.calendarSelect.sendKeys(option);
  }

  getCalendarSelect() {
    return this.calendarSelect;
  }

  async getCalendarSelectedOption() {
    return this.calendarSelect.element(by.css('option:checked')).getText();
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
