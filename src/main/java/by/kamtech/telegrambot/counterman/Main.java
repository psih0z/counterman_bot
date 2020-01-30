package by.kamtech.telegrambot.counterman;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {

        String configFile = "application.properties";

        if (args.length > 0) {
            configFile = args[0];
        }

        Context.init(configFile);

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {

            CountemanBot bot = new CountemanBot();
            telegramBotsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}
