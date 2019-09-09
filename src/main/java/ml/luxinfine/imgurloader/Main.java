package main.java.ml.luxinfine.imgurloader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        Config config = new Config();

        String pathToLoadFolder = config.pathToLoadFolder;
        String pathToUselessImage = config.pathToUselessImage;
        String siteName = config.siteName;
        String fileExtension = config.fileExtension;
        String characterDictionary = config.characterDictionary;
        Integer loadedImageCount = config.loadedImageCount;
        Integer filenameLength = config.filenameLength;

        for (int i = 0; i < loadedImageCount; i++) {
            String randfilename = getRandom(filenameLength, characterDictionary);
            String site = siteName + randfilename + fileExtension;
            String loadfilepath = pathToLoadFolder + randfilename + fileExtension;
            main.downloadUsingStream(site, loadfilepath);
            main.removeUseless(loadfilepath, pathToUselessImage);
            System.out.println("Изображение " + randfilename + fileExtension + " успешно загружено");
        }

        System.out.println("Программа успешно выполнена");
    }

    private static void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while((count = bis.read(buffer,0,1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    private static void removeUseless(String uselessfilename, String pathtouselessimage) {
        File loadedefile = new File(uselessfilename);
        File uselessfile = new File(pathtouselessimage);
        if (loadedefile.length() == uselessfile.length()) {
            loadedefile.delete();
        }
    }

    private static String getRandom(Integer filenameLength, String characterDictionary) {
        String filename = "";
        Random r = new Random();
        for (int i = 0; i < filenameLength; i++)
            filename = filename + characterDictionary.charAt(r.nextInt(characterDictionary.length()));
        return filename;
    }
}
