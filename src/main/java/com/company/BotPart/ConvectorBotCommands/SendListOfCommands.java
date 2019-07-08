package com.company.BotPart.ConvectorBotCommands;

import com.company.BotPart.Abstract.BotCommand;
import com.company.BotPart.Interface.Commands;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringJoiner;

public class SendListOfCommands extends BotCommand {

    public SendListOfCommands() {
        super("/help", "");
    }

    @Override
    public void execute(AbsSender absSender, Chat chat, String user, String args) {
        SendMessage listOfComm = new SendMessage();
        listOfComm.setChatId(chat.getId());
        StringJoiner list = new StringJoiner("\n");
        list.add("Let`s start, " + user + ", I can execute this commands: ");
        for (Commands.MyCommands com : Commands.MyCommands.class.getEnumConstants()) {
            if (!com.getCommand().equals("/start") && !com.getCommand().equals("/justText")) {
                list.add(com.getCommand() + ' ');
            }
        }
        listOfComm.setText(list.toString());

        try {
            absSender.execute(listOfComm);
        } catch (TelegramApiException e) {
            listOfComm.setText("We have a little problem. Don't panic)))");
            try {
                absSender.execute(listOfComm);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
            try (FileOutputStream logFile = new FileOutputStream("Logger.txt")) {
                logFile.write(e.toString().getBytes());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}

