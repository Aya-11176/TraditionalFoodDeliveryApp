package com.tradifooddelivery.utils;

import java.io.*;

/**
 * Utility class to save and load objects .
 */
public class FileManager {

    public static void saveObject(Object obj, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadObject(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
