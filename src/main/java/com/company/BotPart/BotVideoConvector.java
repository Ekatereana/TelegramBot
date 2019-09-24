package com.company.BotPart;


import com.company.BotPart.Abstract.CommandUtils;
import com.company.BotPart.ConvectorBotCommands.SendInfoMassageCommand;
import com.company.BotPart.ConvectorBotCommands.SendListOfCommands;
import com.company.BotPart.ConvectorBotCommands.StartCommand;
import com.company.BotPart.Interface.Commands;
import com.company.ConvectorPart.Convector;
import com.company.ConvectorPart.YouTubeLoader;
import org.jsoup.Jsoup;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.objects.Audio;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.Video;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class BotVideoConvector extends TelegramLongPollingBot {
    private final String token = "846906410:AAHB56LYsu8aM6dJW9aWWU2ECEOy-QYuQbA";
    private final String botName = "VideoConvectorBot";


    public void onUpdateReceived(Update update) {
        Message up = update.getMessage();

        Thread thread = new Thread(() -> {
            if (up.getAudio() != null) {
                returnMP3(up.getChatId().toString(), up.getAudio());
            } else {
                if (up.hasText()) {
                    String message = update.getMessage().getText();
                    Commands.MyCommands command = CommandUtils.getCommand(message);
                    switch (command) {
                        case START:
                            StartCommand startCommand = new StartCommand();
                            startCommand.execute(this, up.getChat(), up.getChat().getUserName(), null);
                            break;
                        case HELP:
                            SendListOfCommands sendListOfCommands = new SendListOfCommands();
                            sendListOfCommands.execute(this,
                                    up.getChat(), up.getChat().getUserName(), null);
                            break;
                        case MP4_MP3:
                            SendInfoMassageCommand sendInfo = new SendInfoMassageCommand();
                            sendInfo.execute(this, up.getChat(), up.getChat().getUserName(),
                                    "Add your file to convert it");
                            break;
                        case JUST_TEXT:
                            if (message.contains("youtu") && message.contains("be") && message.contains("http")) {
                                Thread littleDownThread = new Thread(()->{
                                    System.out.println(up.getChat().getUserName().toUpperCase() +"\n");
                                    YouTubeLoader youTubeLoader = new YouTubeLoader();
                                    try {
                                        SendAudio sendAudio = new SendAudio();
                                        File outputFile = new File(youTubeLoader.download(message));
                                        sendAudio.setChatId(up.getChatId());
                                        sendAudio.setNewAudio(outputFile);
                                        sendAudio(sendAudio);
                                        outputFile.delete();

                                    } catch (Throwable throwable) {
                                        throwable.printStackTrace();
                                    }
                                });

                                littleDownThread.start();

                            } else {
                                SendInfoMassageCommand sendInf = new SendInfoMassageCommand();
                                sendInf.execute(this, up.getChat(), up.getChat().getUserName(),
                                        "Unknown command: " + message
                                                + "\nTry again, please");
                            }
                            break;
                        case YOUTUBE_LOADER:
                            SendInfoMassageCommand sendMess = new SendInfoMassageCommand();
                            sendMess.execute(this, up.getChat(), up.getChat().getUserName(), "Enter your URL, please: ");
                            break;
                    }
                } else {
                    if (up.getVideo() != null) {
                        convertMP4(up.getChatId().toString(), up.getVideo(), up.getChat().getUserName());
                    }
                }
            }
        });
        thread.start();
    }

    private void convertMP4(String chatId, Video video, String userName) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(chatId);
        System.out.println();

        GetFile getFile = new GetFile();
        getFile.setFileId(video.getFileId());
        try {
            String filePath = execute(getFile).getFileUrl(getBotToken());
            //input stream for file
            URL telegramS = new URL(filePath);
            System.out.println(filePath);
            ReadableByteChannel rbc = Channels.newChannel(telegramS.openStream());
            File basic = new File( userName.toUpperCase() + ".mp4");
            FileOutputStream fos = new FileOutputStream(basic);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            File result = Convector.convert(basic, "user" + userName.toUpperCase());
            sendAudio.setNewAudio(result);
            fos.close();
            basic.delete();
            sendAudio(sendAudio);
            result.delete();
        } catch (TelegramApiException | IOException e) {

        }

    }

    public synchronized void returnMP3(String chatId, Audio audio) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(chatId);
        String audioId = audio.getFileId();

        GetFile getFile = new GetFile();
        getFile.setFileId(audioId);
        String filePath = "";
        try {
            filePath = execute(getFile).getFileUrl(getBotToken());
            sendAudio.setNewAudio("userAudio.mp3", new URL(
                    filePath).openStream());
            sendAudio(sendAudio);
        } catch (TelegramApiException | IOException e) {

        }

    }

    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
