package io.traderx.darkness.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class IOHelper {

	public static ArrayList<String> readLines(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			ArrayList<String> lines = new ArrayList<String>();
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
			return lines;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void writeLines(ArrayList<String> lines, String path, boolean append) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, append)));
			for (int i = 0; i < lines.size(); i++) {
				out.println(lines.get(i));
			}
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void writeLine(String line, String path, boolean append) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, append)));
			out.println(line);
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static ArrayList<String> ls(String path) {
		try {
			File f = new File(path);
			if (f.isDirectory()) {
				ArrayList<String> names = new ArrayList<String>();
				File[] files = f.listFiles();
				for (int i = 0; i < files.length; i++) {
					names.add(files[i].getName());
				}
				return names;
			} else {
				System.out.println("[" + path + "] is not a directory.");
				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> ls(String path, String option) {
		return null;
	}

}