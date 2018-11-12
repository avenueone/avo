import { element, by, ElementFinder } from 'protractor';

export default class CalendarAttributeUpdatePage {
  pageTitle: ElementFinder = element(by.id('avoApp.calendarAttribute.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#calendar-attribute-name'));
  typeInput: ElementFinder = element(by.css('input#calendar-attribute-type'));
  valueInput: ElementFinder = element(by.css('input#calendar-attribute-value'));
  calenderSelect: ElementFinder = element(by.css('select#calendar-attribute-calender'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setTypeInput(type) {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput() {
    return this.typeInput.getAttribute('value');
  }

  async setValueInput(value) {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput() {
    return this.valueInput.getAttribute('value');
  }

  async calenderSelectLastOption() {
    await this.calenderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async calenderSelectOption(option) {
    await this.calenderSelect.sendKeys(option);
  }

  getCalenderSelect() {
    return this.calenderSelect;
  }

  async getCalenderSelectedOption() {
    return this.calenderSelect.element(by.css('option:checked')).getText();
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
