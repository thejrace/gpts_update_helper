/* Gitaş - Obarey Inc 2018 */
package gpts_update_helper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main extends Application {

    public static String STATIC_LOCATION;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                if( Common.checkFile( "gpts_config.json" ) ){
                    JSONObject config = new JSONObject( Common.readJSONData("gpts_config.json"));
                    STATIC_LOCATION = config.getString("static_dir");
                } else {
                    Platform.exit();
                }

                try {
                    Thread.sleep(150);
                } catch( InterruptedException e ){
                    e.printStackTrace();
                }
                System.out.println("RENAME THREAD START");
                Path sourcePath      = Paths.get(STATIC_LOCATION + "GPTS_new.exe");
                Path destinationPath = Paths.get(STATIC_LOCATION + "GPTS.exe");
                try {
                    Files.move(sourcePath, destinationPath,
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    //moving file failed.
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch( InterruptedException e ){
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Platform.exit();
                    }
                });
            }
        });
        th.setDaemon(true);
        th.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
