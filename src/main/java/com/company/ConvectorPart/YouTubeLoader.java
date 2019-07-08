package com.company.ConvectorPart;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class YouTubeLoader {


    private static final Logger log = Logger.getLogger(YouTubeLoader.class.getCanonicalName());

    public String download(String url) {

        String myPath = null;
        try {


             myPath = "C:\\Users\\ekate\\Music\\" + Jsoup.connect(url).get().title().replace(" ", "") +".mp3";
            ProcessBuilder pb = new ProcessBuilder("C:\\Users\\ekate\\youtube-dl.exe",
                    "-x", "--audio-format", "mp3", "--audio-quality",
                    "0", "-o", myPath, url);
            pb.directory(new File("C:\\Users\\ekate"));
            pb.redirectErrorStream(true);

            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
            p.waitFor();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myPath;
    }


}


