import { element, by, ElementFinder } from 'protractor';

export default class VesselAttributeUpdatePage {
  pageTitle: ElementFinder = element(by.id('avoApp.vesselAttribute.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#vessel-attribute-name'));
  typeInput: ElementFinder = element(by.css('input#vessel-attribute-type'));
  valueInput: ElementFinder = element(by.css('input#vessel-attribute-value'));
  vesselSelect: ElementFinder = element(by.css('select#vessel-attribute-vessel'));

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

  async vesselSelectLastOption() {
    await this.vesselSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async vesselSelectOption(option) {
    await this.vesselSelect.sendKeys(option);
  }

  getVesselSelect() {
    return this.vesselSelect;
  }

  async getVesselSelectedOption() {
    return this.vesselSelect.element(by.css('option:checked')).getText();
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
