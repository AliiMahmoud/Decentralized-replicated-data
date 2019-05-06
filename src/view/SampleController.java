package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SampleController {

	@FXML
	private TextField username;
	@FXML
	private Button loginBtn;
	@FXML
	private Text warning;
	
	public SampleController() throws Exception {
		// TODO Auto-generated constructor stub
	}
	public void login() throws Exception
	{
		if(username.getText().trim().equals(""))
		{
			warning.setVisible(true);
		}
		else
		{
			Main.setRoot("app.fxml");
			Main.username = username.getText().trim();
		}
	}
}
