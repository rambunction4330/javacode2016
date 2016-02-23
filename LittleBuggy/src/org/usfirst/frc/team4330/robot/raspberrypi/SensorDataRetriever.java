package org.usfirst.frc.team4330.robot.raspberrypi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SensorDataRetriever {
	
	private String host = "raspberrypi.local";
	private int port = 9001;
	
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private boolean active = false;
	private static final byte[] GET_DATA_COMMAND = "DATA\n".getBytes();
	private static final byte[] STOP_COMMAND = "STOP\n".getBytes();
	
	public SensorDataRetriever() {
		
	}
	
	public SensorDataRetriever(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void startUp() throws IOException {
		socket = new Socket(host, port);
		is = socket.getInputStream();
		os = socket.getOutputStream();
		active = true;
	}
	
	public void shutDown() throws IOException {
		active = false;
		
		if ( os != null ) {
			os.write(STOP_COMMAND);
			os.flush();
			os.close();
		}
		if ( is != null ) {
			is.close();
		}
		if ( socket != null ) {
			socket.close();
		}
	}

	public Map<String, String> retrieveData() {
		if ( !active ) {
			throw new RuntimeException("Please call the " + this.getClass().getName() + ".startUp() method before calling retrieveData()");
		}
		try {
			
			// send a request to server for data
			os.write(GET_DATA_COMMAND);
			os.flush();
			
			// read the response from the server
			ByteArrayOutputStream binaryData = new ByteArrayOutputStream();
			int lastChar = Integer.MIN_VALUE;
			int endLine = '\n';
			while ( true ) {
				int thisChar = is.read();
				if ( lastChar == endLine && thisChar == endLine ) {
					break;
				}
				binaryData.write(thisChar);
				lastChar = thisChar;
			}
			
			// get the data as a string
			String data = binaryData.toString();
			Map<String,String> map = new HashMap<String,String>();
			
			// parse the data into map
			LineNumberReader reader = new LineNumberReader(new StringReader(data));
			String line = null;
			while((line = reader.readLine()) != null) {
				int index = line.indexOf("=");
				if ( index == -1 ) {
					continue;
				}
				String key = line.substring(0, index);
				String value = line.substring(index + 1);
				map.put(key, value);
			}
			
			return map;
		} catch ( Exception e ) {
			// TODO find out how to handle exceptions on robot
			System.err.println("Error communicating to raspberry pi. " + e.getMessage());
			return new HashMap<String, String>();
		}
	}

}
