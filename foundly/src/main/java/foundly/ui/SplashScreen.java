package foundly.ui;

import foundly.ui.controller.SplashController;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Preloader{


	private Stage preloaderStage;
	private Scene scene;
	
	public void init() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("views/splash.fxml"));
		scene = new Scene(root);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.preloaderStage = stage;
		preloaderStage.setScene(scene);
		preloaderStage.initStyle(StageStyle.UNDECORATED);
		preloaderStage.show();
	}
	
	public void handleApplicationNotification(PreloaderNotification info) {
		System.out.println(info);
		if(info instanceof ProgressNotification) {
			double progress = ((ProgressNotification) info).getProgress();
			SplashController.label.setText("Laster " + progress + "%");
			SplashController.progress.setProgress(progress);
		}
	}
	
	public void handleStateChangeNotification(StateChangeNotification info) {
		
		StateChangeNotification.Type type = info.getType();
		
		switch(type) {
			case BEFORE_START:
				System.out.println("Before start");
				preloaderStage.hide();
				break;
		}
	}

}
