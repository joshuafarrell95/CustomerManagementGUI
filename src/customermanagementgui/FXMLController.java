package customermanagementgui;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;

public class FXMLController implements Initializable {

    private final boolean IS_LIVE_LIST_ENABLED = true;
	
	/* Observable ArrayList<Customer> used for the TableView object */
    private ObservableList<Customer> customers = FXCollections.observableArrayList(CustomerDAO.getAllCustomers());
    
    /* GUI fields */
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCreateDB;

    @FXML
    private Button btnDeleteID;

    @FXML
    private Button btnSearchID;

    @FXML
    private Button btnShowAllCustomers;

    @FXML
    private Button btnUpdate;
	
    @FXML
    private TableView<Customer> tblCustomers;
    
    @FXML
    private TableColumn<Customer, Integer> colId;
    
    @FXML
    private TableColumn<Customer, String> colName;
    
    @FXML
    private TableColumn<Customer, String> colEmail;
    
    @FXML
    private TableColumn<Customer, String> colMobile;
    
    @FXML
    private TextField txtAddEmail;

    @FXML
    private TextField txtAddMobile;

    @FXML
    private TextField txtAddName;

    @FXML
    private TextField txtSearchDeleteID;

    @FXML
    private TextField txtUpdateEmail;

    @FXML
    private TextField txtUpdateID;

    @FXML
    private TextField txtUpdateMobile;

    @FXML
    private TextField txtUpdateName;
	
    /* onMouseClicked Events */
    @FXML
    private void btnAdd_onMouseClicked(MouseEvent event) {
        String tempName = txtAddName.getText();
        String tempEmail = txtAddEmail.getText();
        String tempMobile = txtAddMobile.getText();
        
        CustomerDAO.insertCustomer(tempName, tempEmail, tempMobile);
        clearTextBoxes("Add");
        
        if (IS_LIVE_LIST_ENABLED) {
            refreshList();
        }
    }

    @FXML
    private void btnCreateDB_onMouseClicked(MouseEvent event) {
        try {
            CustomerDAO.createaCustomerDB();
            if (IS_LIVE_LIST_ENABLED) {
                refreshList();
            }
        } catch (SQLException ex) {
            String title = "SQL Exception";
            String message = "Please start your MySQL database engine.";
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
			alert.setContentText(message);
            alert.show();
            
            System.out.println("ERROR: " + title + " - " + message);
        }
    }

    @FXML
    private void btnDeleteID_onMouseClicked(MouseEvent event) {
        try {
            int tempID = Integer.valueOf(txtSearchDeleteID.getText());
            
            CustomerDAO.deleteCustomerByID(tempID);
            if (IS_LIVE_LIST_ENABLED) {
                refreshList();
            }
        } catch (NullPointerException ex) {         // Used if the customer does not exist in the database
            Alert alert = new Alert(AlertType.ERROR);
            String title = "Null Pointer Exception";
            String message = "Customer id is not found in database.";
            
            alert.setTitle(title);
			alert.setContentText(message);
            alert.show();
            
            System.out.println("ERROR: " + title + " - " + message);
		//} catch (InvocationTargetException ex) {
		//	clearTextBoxes("Update");
		//
		//	String title = "Invocation Target Exception";
		//	String message = "Search text box must not be blank";
		//	
		//	displayAlert(title, message);
        }
    }

    @FXML
    private void btnSearchID_onMouseClicked(MouseEvent event) {
        try {
            int tempID = Integer.valueOf(txtSearchDeleteID.getText());
            
            Customer c = CustomerDAO.searchCustomerByID(tempID);
            
            txtUpdateID.setText(Integer.toString(tempID));
            txtUpdateName.setText(c.getName());
            txtUpdateEmail.setText(c.getEmail());
            txtUpdateMobile.setText(c.getMobile());
        } catch (NullPointerException ex) {         // Used if the customer does not exist in the database
            clearTextBoxes("Update");
            
            String title = "Null Pointer Exception";
            String message = "Customer id is not found in database.";
            
            displayAlert(title, message);
			refreshList();
        } finally {
            clearTextBoxes("Search");
        }
    }

    @FXML
    private void btnShowAllCustomers_onMouseClicked(MouseEvent event) {
        refreshList();
    }

    @FXML
    private void btnUpdate_onMouseClicked(MouseEvent event) {
        int tempId = -1;
        try {
            tempId = Integer.valueOf(txtUpdateID.getText());
        } catch (NumberFormatException ex) {}
        String tempName = txtUpdateName.getText();
        String tempEmail = txtUpdateEmail.getText();
        String tempMobile = txtUpdateMobile.getText();
        
        if (tempId > 0) {
            CustomerDAO.updateCustomer(tempId, tempName, tempEmail, tempMobile);
            clearTextBoxes("Update");
        }
        if (IS_LIVE_LIST_ENABLED) {
            refreshList();
        }
    }
	
	@FXML
	private void tblCustomers_onMouseClicked(MouseEvent event) {
		try {
			Customer c = tblCustomers.getSelectionModel().getSelectedItem();
			
			clearTextBoxes("Update");
			
			txtUpdateID.setText(Integer.toString(c.getId()));
			txtUpdateName.setText(c.getName());
			txtUpdateEmail.setText(c.getEmail());
			txtUpdateMobile.setText(c.getMobile());		
        } catch (NullPointerException ex) {         // Used if the customer does not exist in the database
            clearTextBoxes("Update");
            
            String title = "Null Pointer Exception";
            String message = "Customer id is not found in database.";
            
            displayAlert(title, message);
			refreshList();
        } finally {
            //clearTextBoxes("Search");
        }
	}
    
	/* Utilities */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        colMobile.setCellValueFactory(new PropertyValueFactory<Customer, String>("mobile"));
    
		
        if (IS_LIVE_LIST_ENABLED) {
            refreshList();
        }
    }
    
    public void refreshList() {
        customers.clear();
        customers = FXCollections.observableArrayList(CustomerDAO.getAllCustomers());
        tblCustomers.setItems(customers);
    }
	
	private void clearTextBoxes(String mode) {
        switch (mode) {
            case "Add" -> {
                txtAddName.clear();
                txtAddEmail.clear();
                txtAddMobile.clear();
            } case "Delete", "Search" -> {
                txtSearchDeleteID.clear();
            } case "Update" -> {
                txtUpdateID.clear();
                txtUpdateName.clear();
                txtUpdateEmail.clear();
                txtUpdateMobile.clear();
            } case "All" -> {
                clearTextBoxes("Add");
                clearTextBoxes("Delete");
                clearTextBoxes("Update");
            } default -> {}
        }
    }
	
	private void displayAlert(String title, String message) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(title);
            alert.setContentText(message);
            alert.show();
            
            System.out.println("ERROR: " + title + " - " + message);
	}
	
	//private void exportRecord(Customer customer) throws IOException {
		
	
	//}
}
