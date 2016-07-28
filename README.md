****************************************** Notes  *****************************************************
I have used Maven assembly mechanism, Spring boot and REST WebServices to create this application.
This means the application can be run as a standalone application.
Follow the steps below to run this application
 - Navigate to the project directory "elevator-app" on Commandline and the run the maven command below
 - $ mvn clean install
 - This creates the fat jars, now navigate to folder "../elevator-app/target/elevator-app-1.0/bin" and run the command below
 - For unix -> $ sh devElevatorApp.sh         For Windows -> $ devElevatorApp.bat
 - This will start the applicationm you could now test it directly in the console by entering the parameters or use the webservice
 - To call the webservice using a rest client use the URL below
URL - > http://localhost:55511/elevator/result?fileName=C:/ElevatorRequestFile.txt&elevatorMode=a


Alternatively, run it directly through IDE like Intellij by creating a debug config using following parameter
 Main class: com.baml.app.ElevatorApp
 VM Options: -Delevator.app.environment=dev -Delevator.app.http.port=55511
 Program arguments: com.baml.config.ElevatorConfig
******************************************************************************************************