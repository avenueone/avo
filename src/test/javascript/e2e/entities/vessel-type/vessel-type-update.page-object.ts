import { element, by, ElementFinder } from 'protractor';

export default class VesselTypeUpdatePage {
  pageTitle: ElementFinder = element(by.id('avoApp.vesselType.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#vessel-type-name'));
  typeSelect: ElementFinder = element(by.css('select#vessel-type-type'));
  recurringInput: ElementFinder = element(by.css('input#vessel-type-recurring'));
  dayOfMonthInput: ElementFinder = element(by.css('input#vessel-type-dayOfMonth'));
  dayOfWeekInput: ElementFinder = element(by.css('input#vessel-type-dayOfWeek'));
  monthInput: ElementFinder = element(by.css('input#vessel-type-month'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption() {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  getRecurringInput() {
    return this.recurringInput;
  }
  async setDayOfMonthInput(dayOfMonth) {
    await this.dayOfMonthInput.sendKeys(dayOfMonth);
  }

  async getDayOfMonthInput() {
    return this.dayOfMonthInput.getAttribute('value');
  }

  async setDayOfWeekInput(dayOfWeek) {
    await this.dayOfWeekInput.sendKeys(dayOfWeek);
  }

  async getDayOfWeekInput() {
    return this.dayOfWeekInput.getAttribute('value');
  }

  async setMonthInput(month) {
    await this.monthInput.sendKeys(month);
  }

  async getMonthInput() {
    return this.monthInput.getAttribute('value');
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
