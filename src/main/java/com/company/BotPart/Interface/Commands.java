package com.company.BotPart.Interface;

public interface Commands {

     enum MyCommands{
         HELP("/help"), MP4_MP3("/getAudio"), YOUTUBE_LOADER("/loadByURL"), JUST_TEXT("/justText"), START("/start");

         private String command;
         MyCommands(String command) {
             this.command = command;
         }

         public String getCommand() {
             return command;
         }
    }
}
