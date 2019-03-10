package control;

import dataManager.*;

public class Node extends Thread {
	PeerHandler peer = new PeerHandler();
	FakeDataGenerator generator = new FakeDataGenerator();
	OverwhelmingHandler circuitBreaker = new OverwhelmingHandler();

	public Node() throws Exception {

	}

	public Node(String uname) throws Exception {
		peer.setUsername(uname);
	}

	public void login() throws Exception {
		peer.login();
	}

	public void run() {
		while (true) {
			try {
				String received = peer.receive();
				System.out.println(received);
				if (received.equals("-1"))
					generator.setCanGenerateFlag(false);
				else if (received.equals("1"))
					generator.setCanGenerateFlag(true);
				else {
					circuitBreaker.addToQueue(received);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public boolean sendData(String data) throws Exception {

		if (!generator.CanGenerate()) {
			return false;

		} else {
			peer.sendData(data.getBytes());
			return true;

		}
	}	
	public boolean sendFakeData() throws Exception {

		if (!generator.CanGenerate()) {
			return false;

		} else {
			peer.sendData(generator.generate().getBytes());
			return true;
		}
	}

	public void leave() throws Exception {
		peer.disconnect();
	}

}
