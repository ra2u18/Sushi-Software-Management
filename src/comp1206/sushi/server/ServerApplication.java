package comp1206.sushi.server;

import comp1206.sushi.mock.MockServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class ServerApplication extends Application {


    public static void main(String[] argv){
        launch(argv);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new ServerWindow(new MockServer(), stage);
    }


}
