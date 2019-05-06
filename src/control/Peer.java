package control;

import java.net.*;
import java.util.*;

import javafx.scene.layout.Background;

public class Peer {
	private Vector <String> peers = new Vector<>();
	private String username = "Guest";
	private MulticastSocket socket;
	private final InetAddress groupAddress = InetAddress.getByName("225.0.0.1");
	private final int port = 4446;
	private final String myIpAdderss = InetAddress.getLocalHost().getHostAddress();;

	public Peer() throws Exception 
	{
		socket = new MulticastSocket(port);
	}

	public Peer(String username) throws Exception 
	{

		this();
		this.username = username;
	}

	public void login(String name) throws Exception 
	{
		username = name;
		connect();
	}

	public void login() throws Exception 
	{
		connect();
	}

	public String receive() throws Exception 
	{

		String received = "";
		if (socket.isClosed())
			return null;

		DatagramPacket packet;
		byte[] buf = new byte[256];
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		// circuitBreaker is trying to tell the peer -> slow down
		if (packet.getData()[0] == 2) 
		{
			return "-1";
		}
		// circuitBreaker is trying to tell the peer -> now, you can send
		else if (packet.getData()[0] == 3) 
		{
			return "1";
		}
		
		else if (packet.getData()[0] == 11) 
		{
			String newUsername = new String(packet.getData(), 1, packet.getLength());
			newUsername = newUsername.trim();
			if (!peers.contains(newUsername) && !username.equals(newUsername))
			{	
				peers.add(newUsername);
			}
		}
		else if (packet.getData()[0] == 1) 
		{
			String newUsername = new String(packet.getData(), 1, packet.getLength());
			newUsername = newUsername.trim();
			if (!peers.contains(newUsername) && !newUsername.equals(username))
			{	
				peers.add(newUsername);
				received += newUsername + " has joined the group ";			
			}
			if(!username.equals(newUsername))
			{
				sendSignal(new byte[] {11});
			}
		}
		else if (packet.getData()[0] == 0) 
		{
			String newUsername = new String(packet.getData(), 1, packet.getLength());
			newUsername = newUsername.trim();
			if (peers.contains(newUsername)) 
			{
				received += newUsername + " has left the group ";
				peers.remove(newUsername);
				if (username.equals(newUsername)) 
				{	
					return null;
				}
			}
		} 
		else 
		{
			received += new String(packet.getData());
		}
		return received;
	}

	public void disconnect() throws Exception {
		sendSignal(new byte[] { 0 });
		socket.leaveGroup(groupAddress);
		socket.close();
	}

	public void connect() throws Exception {
		socket = new MulticastSocket(port);
		socket.joinGroup(groupAddress);
		sendSignal(new byte[] { 1 });
	}

	public static byte[] concatenate(byte[] a, byte[] b) {
		byte[] ret = new byte[a.length + b.length];
		int i = 0;
		for (byte cb : a)
			ret[i++] = cb;
		for (byte cb : b)
			ret[i++] = cb;
		return ret;
	}

	public void sendData(byte[] data) throws Exception {
		data = concatenate((username + ": ").getBytes(), data);
		DatagramPacket p = new DatagramPacket(data, data.length, groupAddress, port);
		socket.send(p);
	}

	private void sendSignal(byte[] bytes) throws Exception {
		String dString = username;
		byte[] buff = concatenate(bytes, dString.getBytes());
		DatagramPacket p = new DatagramPacket(buff, buff.length, groupAddress, port);
		socket.send(p);
	}

	public Vector<String> getPeers() {
		return peers;
	}

	public void setPeers(Vector<String> peers) {
		this.peers = peers;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isConnected() {
		return !socket.isClosed();
	}

}
