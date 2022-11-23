@echo off
echo "Creating custom JRE"
jlink --module-path ../jmods;%PATH_TO_FX_JMOD% --add-modules java.base,java.sql,java.sql.rowset,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --output jre
pause
