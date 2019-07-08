package com.company.BotPart.ConvectorBotCommands;

import com.company.BotPart.Abstract.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class SendInfoMassageCommand extends BotCommand {
    public SendInfoMassageCommand() {
        super("/justText", "");
    }

    @Override
    public void execute(AbsSender absSender, Chat chat, String user, String args) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chat.getId());
        sendMessage.setText(args);
        try {
           absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("I cannot write this massage" + sendMessage.getText());
        }

    }
}
