package control;

import java.util.Vector;

import dataManager.*;

public class PeerController {
	boolean canSend = true;
	Peer peer = new Peer();
	FakeDataGenerator generator = new FakeDataGenerator();
	OverwhelmingHandler circuitBreaker = new OverwhelmingHandler();

	public PeerController()throws Exception {

	}

	public PeerController(String uname) throws Exception {
		peer.setUsername(uname);
	}

	public void login() throws Exception {
		peer.login();
	}

	public String receive() throws Exception {
		String received = peer.receive();
		if (received == null)
			return null;
		else if (received.equals("-1"))
			canSend = false;
		else if (received.equals("1"))
			canSend = true;
		else
			circuitBreaker.addToQueue(received);
		return received;
	}

	public boolean sendData(String data) throws Exception {

		if (!canSend) {
			return false;

		} else {
			peer.sendData(data.getBytes());
			return true;

		}
	}

	public boolean sendFakeData() throws Exception {

		if (!canSend) {
			return false;

		} else {
			peer.sendData(generator.generate().getBytes());
			return true;
		}
	}

	public void leave() throws Exception {
		peer.disconnect();
	}

	public boolean isConnected() {

		return peer.isConnected();
	}

	public Vector<String> getList() {
		// TODO Auto-generated method stub
		return peer.getPeers();
	}
}
