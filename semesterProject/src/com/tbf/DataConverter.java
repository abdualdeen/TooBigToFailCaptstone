package com.tbf;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.google.gson.*;
import com.thoughtworks.xstream.*;

/**
 * 
 * DataConverter iterates through the Persons and the Assets file. It uses the
 * different classes to sort the data then writers out that data into json
 * files.
 *
 */
public class DataConverter {

	public static void personToXML(List<Person> list) {
		FileWriter writerXML = null;
		try {
			writerXML = new FileWriter("data/Persons.xml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		XStream person = new XStream();
		String toXML = person.toXML(list);
		try {
			writerXML.write(toXML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * Closing the FileWriter method and the inputFile that opened Persons.dat.
		 */
		try {
			writerXML.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void personToJson(List<Person> list) {
		FileWriter writerJson = null;
		try {
			writerJson = new FileWriter("data/Persons.json");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Gson person = new GsonBuilder().setPrettyPrinting().create();
		String toJson = person.toJson(list);
		try {
			writerJson.write(toJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writerJson.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void assetToXML(List<Asset> list) {
		FileWriter writerXML = null;
		try {
			writerXML = new FileWriter("data/Assets.xml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		XStream asset = new XStream();
		String toXML = asset.toXML(list);
		try {
			writerXML.write(toXML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writerXML.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void assetToJson(List<Asset> list) {
		FileWriter writerJson = null;
		try {
			writerJson = new FileWriter("data/Assets.json");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Gson asset = new GsonBuilder().setPrettyPrinting().create();
			String toJson = asset.toJson(list);
			writerJson.write(toJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writerJson.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public static void main(String[] args) {
		List<Person> persList = LoadNParse.parsePersonsFile();
		List<Asset> assList = LoadNParse.parseAssetsFile();
		personToXML(persList);
		personToJson(persList);
		assetToXML(assList);
		assetToJson(assList);

	}

}
