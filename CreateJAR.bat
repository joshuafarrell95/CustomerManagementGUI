@echo off
mkdir app
jar --create --file=app/CustomerManagementGUI.jar --main-class=customermanagementgui.CustomerManagementGUI -m Manifest.txt -C classes .
mkdir app\lib
copy lib\mysql-connector-java.jar app\lib
pause
