@echo off
REM java -classpath classes wholeworthsgrocery.WholeWorthsGrocery
REM java --module-path %PATH_TO_FX% --add-modules=javafx.base,javafx.controls,javafx.fxml -jar app/WholeWorthsGrocery.jar
REM java -classpath lib/mysql-connector-java.jar;. -jar app/CustomerManagement.jar
REM java -jar app/CustomerManagementGUI.jar
jre\bin\java -jar app/CustomerManagementGUI.jar
pause
