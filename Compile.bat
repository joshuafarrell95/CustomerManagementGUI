@echo off
javac --module-path %PATH_TO_FX% --add-modules=javafx.base,javafx.controls,javafx.fxml src\customermanagementgui\*.java -d classes
copy src\customermanagementgui\*.fxml classes\customermanagementgui\*.fxml
pause
