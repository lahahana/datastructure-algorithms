/**
* @author lahahana
*/

package com.github.lahahana.dsa;

import java.net.InetAddress;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ConsistentHashCircle {

	TreeMap<Integer, Server> instances = new TreeMap<>();

	int[] circle = new int[Integer.MAX_VALUE];
	
	int virtualServerThreshold = 3;

	int serverInstanceNum = 0;


	public synchronized void initialize(List<Server> servers) {
		for (Server server : servers) {
			int key = hash(server);
			instances.put(key, server);
			circle[key] = key;
		}
		this.serverInstanceNum = servers.size();
	}
	
	public synchronized Server addServer(InetAddress inetAddress) {
		int key = hash(inetAddress);
		Server server = new Server(key, inetAddress);
		circle[key] = key;
		instances.put(key, server);
		transferData(key);
		for(int i = 0; i < virtualServerThreshold; i++) {
			int vKey = virtualServerHash(inetAddress);
			instances.put(vKey, server);
			transferData(vKey);
		}
		return server;
	}
	
	public synchronized void removeServer(InetAddress inetAddress) {
		
	}

	public Object get(int key) {
		Server server = getTargetServer(key);
		return getData(server, key);
	}

	public Object save(Object object) {
		Server server = getTargetServer(hash(object));
		return saveData(server, object);
	}

	private Object getData(Server server, int key) {
		// Mock get data operation
		return null;
	}

	private Object saveData(Server server, Object object) {
		// Mock save data operation
		return null;
	}

	private Server getTargetServer(int key) {
		int rearServerKey = getRearServerKey(key);
		return instances.get(rearServerKey);
	}

	private int getFrontServerKey(int key) {
		Entry<Integer, Server> entry = instances.lowerEntry(key);
		return entry == null ? instances.lastKey() : entry.getKey();
	}

	private int getRearServerKey(int key) {
		Entry<Integer, Server> entry = instances.higherEntry(key);
		return entry == null ? instances.firstKey() : entry.getKey();
	}
	
	private void transferData(int newServerKey) {
		int frontServerKey = getFrontServerKey(newServerKey);
		int rearServerKey = getRearServerKey(newServerKey);
		Server sourceServer = instances.get(rearServerKey);
		Server targetServer = instances.get(newServerKey);
		for(int i = frontServerKey; i < newServerKey; i++) {
			//should in  batches 
			Object object = getData(sourceServer, i);
			saveData(targetServer, object);
		}
	}

	private int hash(Object object) {
		return object.hashCode() % Integer.MAX_VALUE;
	}
	
	private int virtualServerHash(InetAddress inetAddress) {
		return 0;
	}

	static class Server implements Comparable<Server> {
		private int key;
		private InetAddress inetAddress;
		
		public Server(int key, InetAddress inetAddress) {
			super();
			this.key = key;
			this.inetAddress = inetAddress;
		}

		@Override
		public int compareTo(Server arg0) {
			return key - arg0.key;
		}
	}
}
