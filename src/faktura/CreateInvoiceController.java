package faktura;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateInvoiceController {

  @FXML
  Button confirmButton;
  @FXML
  TextField amountInvoiceInput;
  @FXML
  TextField numberOfInvoiceInput;
  @FXML
  TableView<Journal> resultTable;
  @FXML
  TableColumn<Journal, String> accountNumberCol;
  @FXML
  TableColumn<Journal, String> accountNameCol;
  @FXML
  TableColumn<Journal, String> debitCol;
  @FXML
  TableColumn<Journal, String> creditCol;

  /**
   * Initializes the table.
   * Sets listeners to the text property of * numberOfInvoice and amountInvoice for formatting.
   */
  @FXML
  public void initialize(){
    accountNameCol.setCellValueFactory(new PropertyValueFactory<>("accountName"));
    accountNumberCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
    debitCol.setCellValueFactory(new PropertyValueFactory<>("debit"));
    creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
    //accountNameCol.prefWidthProperty().bind(resultTable.widthProperty().divide(4));
    //accountNumberCol.prefWidthProperty().bind(resultTable.widthProperty().divide(4));
    //debitCol.prefWidthProperty().bind(resultTable.widthProperty().divide(4));
    //creditCol.prefWidthProperty().bind(resultTable.widthProperty().divide(4));

    numberOfInvoiceInput.textProperty().addListener((observable, oldValue, newValue) -> {
      if(!newValue.matches("\\d")){
        numberOfInvoiceInput.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });

    amountInvoiceInput.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d{0,9}([.]\\d{0,4})?")) {
        amountInvoiceInput.setText(oldValue);
      }
    });
  }

  @FXML
  public void onEnter(ActionEvent ae){
    showValues();
  }

  /**
   * Run on button press when resultButton has focus.
   * @param actionEvent event triggered when resultButton is pressed.
   */
  public void createInvoiceButtonPress(javafx.event.ActionEvent actionEvent) {
    showValues();
  }

  /**
   * Checks if any of the amountInvoiceInput or numberOfInvoiceInput is empty at submit.
   * @return boolean
   */
  private boolean valuesEntered() {
    String amount = amountInvoiceInput.getText();
    String number = numberOfInvoiceInput.getText();
    return !amount.equals("") && !number.equals("");
  }

  /**
   * Shows values in table if both of the input fields has values.
   * Shows an alert to the user if either of the input fields is empty.
   */
  private void showValues(){
    if(valuesEntered()){
      resultTable.setItems(getValues());
    }else{
      Alert alert = new Alert(AlertType.INFORMATION, "Antal fakturor eller belopp saknas!", ButtonType.OK);
      alert.showAndWait();
    }
  }

  /**
   * Sets the formatting for the table.
   * @return ObservableList
   */
  private ObservableList<Journal> getValues() {
    ObservableList<Journal> journalEntry = FXCollections.observableArrayList();
    String numberOfInvoices = numberOfInvoiceInput.getText();
    String amount = amountInvoiceInput.getText();
    double numberInvoicesDouble;
    double amountDouble;
    double receivableValue;
    try{
      numberInvoicesDouble = Double.parseDouble(numberOfInvoices);
      amountDouble = Double.parseDouble(amount);
      receivableValue = numberInvoicesDouble*amountDouble;
    } catch (java.lang.NumberFormatException e){
      return null;
    }

    double revenueValue = receivableValue * 0.8; // Calculates revenue
    double vat = receivableValue * 0.2; // Calculates VAT
    String defaultValue = "0.00";
    NumberFormat formatter = new DecimalFormat("#0.00");
    Journal  receivable = new Journal("1511", "Kundfordran", String.valueOf(formatter.format(receivableValue)), defaultValue);
    Journal  revenue = new Journal("3001", "Försäljning inom sverige", defaultValue, String.valueOf(formatter.format(revenueValue)));
    Journal  vatEntry = new Journal("2611", "Utgående moms", defaultValue, String.valueOf(formatter.format(vat)));
    journalEntry.add(receivable);
    journalEntry.add(revenue);
    journalEntry.add(vatEntry);
    return journalEntry;
  }
}
