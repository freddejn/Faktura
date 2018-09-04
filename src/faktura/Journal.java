package faktura;

import javafx.beans.property.SimpleStringProperty;

/**
 * Used as data model for the journal entries.
 */
public class Journal {

  private SimpleStringProperty accountNumber;
  private SimpleStringProperty accountName;
  private SimpleStringProperty debit;
  private SimpleStringProperty credit;

  /**
   * Construction for Journal
   * @param accountNumber account number as a String
   * @param accountName account name as a String
   * @param debit debit value as a String
   * @param credit credit value as a String
   */
  Journal(String accountNumber, String accountName, String debit, String credit){
    this.accountNumber = new SimpleStringProperty(accountNumber);
    this.accountName = new SimpleStringProperty(accountName);
    this.debit = new SimpleStringProperty(debit);
    this.credit = new SimpleStringProperty(credit);
  }

  public SimpleStringProperty accountNumberProperty() {
    return accountNumber;
  }

  public SimpleStringProperty accountNameProperty() {
    return accountName;
  }

  public SimpleStringProperty debitProperty() {
    return debit;
  }

  public SimpleStringProperty creditProperty() {
    return credit;
  }
}
