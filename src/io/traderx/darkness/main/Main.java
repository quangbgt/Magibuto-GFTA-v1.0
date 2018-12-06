package io.traderx.darkness.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public Main() {
		String imei = this.getIMEI();
		System.out.println(imei);
	}

	public String getIMEI() {
		try {
			ProcessBuilder pb = new ProcessBuilder("wmic", "baseboard", "get", "serialnumber");
			Process process = pb.start();
			process.waitFor();
			String serialNumber = "";
			try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					if (line.length() < 1 || line.startsWith("SerialNumber")) {
						continue;
					}
					serialNumber = line;
					break;
				}
			}
			return serialNumber;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
