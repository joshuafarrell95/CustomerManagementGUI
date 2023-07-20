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
import javafx.event.ActionEvent;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;

public class FXMLController implements Initializable {

    private final boolean IS_LIVE_LIST_ENABLED = true;
    
    /* Observable ArrayList<Customer> used for the TableView object */
    private ObservableList<Customer> customers = FXCollections.observableArrayList(CustomerDAO.getAllCustomers());
    
    /* GUI fields */
    @FXML
    private Button btnSearchID;

    @FXML
    private Button btnDeleteID;

    @FXML
    private Button btnAdd;

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
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtMobile;
    
    /* onMouseClicked Events */
    @FXML
    private void btnAdd_onMouseClicked(MouseEvent event) {
		String tempID = txtID.getText();
        String tempName = txtName.getText();
        String tempEmail = txtEmail.getText();
        String tempMobile = txtMobile.getText();
        
		if (tempID.isEmpty()){
		}
		
		clearTextBoxes("Delete"); /* Clear ID text box */
        CustomerDAO.insertCustomer(tempName, tempEmail, tempMobile);
        clearTextBoxes("Add");
        
        if (IS_LIVE_LIST_ENABLED) {
            refreshList();
        }
    }

    @FXML
    private void menuFileEditCreateDB_onAction(ActionEvent event) {
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
            int tempID = Integer.valueOf(txtID.getText());
            
            CustomerDAO.deleteCustomerByID(tempID);
            if (IS_LIVE_LIST_ENABLED) {
                refreshList();
            }
        } catch (NullPointerException ex) {         // Used if the customer does not exist in the database
            String title = "Null Pointer Exception";
            String message = "Customer id is not found in database.";
            displayAlert(title, message);
        }
    }

    @FXML
    private void btnSearchID_onMouseClicked(MouseEvent event) {
        try {
            int tempID = Integer.valueOf(txtID.getText());
            
            Customer c = CustomerDAO.searchCustomerByID(tempID);
            
            txtID.setText(Integer.toString(tempID));
            txtName.setText(c.getName());
            txtEmail.setText(c.getEmail());
            txtMobile.setText(c.getMobile());
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
    private void menuFileEditForceUpdate_onAction(ActionEvent event) {
        refreshList();
    }

    @FXML
    private void btnUpdate_onMouseClicked(MouseEvent event) {
        int tempId = -1;
        try {
            tempId = Integer.valueOf(txtID.getText());
        } catch (NumberFormatException ex) {}
        String tempName = txtName.getText();
        String tempEmail = txtEmail.getText();
        String tempMobile = txtMobile.getText();
        
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
            
            txtID.setText(Integer.toString(c.getId()));
            txtName.setText(c.getName());
            txtEmail.setText(c.getEmail());
            txtMobile.setText(c.getMobile());     
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
	
	@FXML
	private void menuFileClose_onAction(ActionEvent event) {
		
	}
	
	@FXML
	private void checkMenuFileHelpAbout_onAction(ActionEvent event) {
	
	}
	
	@FXML
	private void menuFileHelpAbout_onAction(ActionEvent event) {
	
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
                txtName.clear();
                txtEmail.clear();
                txtMobile.clear();
            } case "Delete", "Search" -> {
                txtID.clear();
            } case "Update" -> {
                txtID.clear();
                txtName.clear();
                txtEmail.clear();
                txtMobile.clear();
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
}
