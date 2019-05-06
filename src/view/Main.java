package view;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private static Stage roto;
	public static String username = "";
	@Override
	public void start(Stage primaryStage) {
		try {
			roto = primaryStage;
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("loginForm.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			roto.setTitle("LoginForm");
			roto.setScene(scene);
			roto.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void setRoot(String newFxml) throws Exception
	{
		roto.hide();
		roto = new Stage();
		AnchorPane root = (AnchorPane)FXMLLoader.load(Main.class.getResource(newFxml));
		Scene scene = new Scene(root,600,400);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		roto.setTitle("p2p application");
		roto.setScene(scene);
		roto.show();
	}
	public static void close() throws Exception
	{
		roto.hide();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
