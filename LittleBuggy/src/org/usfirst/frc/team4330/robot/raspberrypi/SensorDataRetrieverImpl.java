package org.usfirst.frc.team4330.robot.raspberrypi;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SensorDataRetrieverImpl implements SensorDataRetriever {
	
	// TODO determine correct ip and port for talking to raspberry pi
	private String host = "localhost";   // 10.43.30.X
	private int port = 9001;

	@Override
	public Map<String, String> retrieveData() {
		try {
			Socket socket = new Socket(host, port);
			InputStream is = socket.getInputStream();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int i = 0;
			while ( (i = is.read()) != -1 ) {
				os.write(i);
			}
			is.close();
			socket.close();
			
			// get the data
			String data = os.toString();
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
