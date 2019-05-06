package view;

import java.util.Random;
import java.util.Vector;

import control.Peer;
import control.PeerController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class AppController {

	Thread backgroundReceiving;
	Thread autoSending;
	private PeerController node;
	@FXML
	private Text username;
	@FXML
	private Text data;
	@FXML
	private TextArea output;
	@FXML
	private TextArea input;
	@FXML
	private TextArea peers;
	@FXML
	private Button leave;
	@FXML
	private Button join;
	@FXML
	private Button sendData;
	@FXML
	private Button sendFakeData;
	boolean done = false;

	public AppController() {
	}

	public void setUsername() throws Exception {
		// Platform.start instead
		if (!done) {
			username.setText(Main.username);
			username.setVisible(true);
			leave.setDisable(true);
			sendData.setDisable(true);
			sendFakeData.setDisable(true);
			join.setDisable(false);
			done = true;
		}
	}

	public void exit() throws Exception {
		node.leave();
		done = false;
		setUsername();
	}

	@FXML
	public void exitApplication(ActionEvent event) {
		Platform.exit();
	}

	public void generateFakeData() throws Exception {
		autoSending = new Thread() {
			Random r = new Random();

			public void run() {
				while (node.isConnected()) {
					try {
						node.sendFakeData();
						int sec = r.nextInt(2500);
						sleep(sec);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};
		autoSending.start();
	}

	public void writtenInput() throws Exception {
		node.sendData(input.getText());
		input.setText("");
	}

	public void joinGroup() throws Exception {

		node = new PeerController(username.getText());
		node.login();
		leave.setDisable(false);
		sendData.setDisable(false);
		sendFakeData.setDisable(false);
		join.setDisable(true);
		showAll();
		backgroundReceiving = new Thread() {
			public void run() {
				while (true) {
					try {
						String data;
						data = node.receive();
						if (data == null)
							break;
						if (data.equals("-1"))
							output.setText(output.getText() + " \n" + " please wait, network is busy !!");
						else if (data.equals("1")) {
							output.setText(output.getText() + " \n" + " now, You can send anything ");
						} else
							output.setText(output.getText() + " \n" + data.trim());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};
		backgroundReceiving.start();
	}

	public void showAll() throws Exception {

		setUsername();
		if(node == null)
			return;
		peers.setText("");
		Vector<String> others = node.getList();
		for (int i = 0; i < others.size(); ++i) {
			peers.setText(peers.getText() + "\n" + others.get(i));
		}
	}

}
