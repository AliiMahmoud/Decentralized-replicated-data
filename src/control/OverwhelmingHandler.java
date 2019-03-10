package control;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Vector;
import dataManager.*;

public class OverwhelmingHandler {
	private static Vector<String> queue = new Vector<>();
	private static final int maxLen = 300;

	public OverwhelmingHandler() {

	}
	static Vector<String> getQueue() {
		return queue;
	}
	public static void addToQueue(String data) throws Exception {
		queue.addElement(data);
		if (queue.size() == maxLen) {
			messageToPeers(new byte[] { 2 });
			DataStorage.writeFile(queue, "output.txt");
			messageToPeers(new byte[] { 3 });
		}
	}

	private static void messageToPeers(byte[] bytes) throws Exception {
		MulticastSocket socket = new MulticastSocket(4446);
		InetAddress address = InetAddress.getByName("225.0.0.1");
		socket.joinGroup(address);
		DatagramPacket p = new DatagramPacket(bytes, bytes.length, address, 4446);
		socket.send(p);
		socket.leaveGroup(address);
		socket.close();
	}
}
