package com.company.BotPart.ConvectorBotCommands;

import com.company.BotPart.Abstract.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class StartCommand extends BotCommand {

    public StartCommand() {
        super("/start", "");
    }

    @Override
    public void execute(AbsSender absSender, Chat chat, String user, String args) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText("Hello, " + user +
                ", my name is VideoConvectorBot\nI can help you to convert your MP4 file in MP3.\n" +
                "Please, enter /help to get some more information");

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
}
