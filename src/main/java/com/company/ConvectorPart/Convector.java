package com.company.ConvectorPart;

import java.io.*;


public class Convector {

    public static File convert(File video, String videoName) {
        String mp3File = "C:\\Users\\ekate\\Music\\" + videoName + "Convert.mp3";

        File newFile = new File(mp3File);

        try {
            String line;
            String cmd = "ffmpeg -i " + video.getAbsolutePath() + " " + mp3File;
            Process p = Runtime.getRuntime().exec(cmd);
            InputStreamReader reader = new InputStreamReader(p.getErrorStream());
            BufferedReader in = new BufferedReader(reader);
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            System.out.println("Video converted successfully!");
            in.close();
            reader.close();
            video.delete();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return newFile;
    }
}
