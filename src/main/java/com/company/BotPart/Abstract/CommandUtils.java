package com.company.BotPart.Abstract;

import com.company.BotPart.Interface.Commands;

public abstract class CommandUtils {

    public static Commands.MyCommands getCommand(String command){
        if(command.equals("/getAudio")) return Commands.MyCommands.MP4_MP3;
        if(command.equals("/help")) return Commands.MyCommands.HELP;
        if(command.equals("/start")) return Commands.MyCommands.START;
        if(command.equals("/loadByURL")) return Commands.MyCommands.YOUTUBE_LOADER;
        else return Commands.MyCommands.JUST_TEXT;
    }

}
