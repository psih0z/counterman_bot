package by.kamtech.telegrambot.counterman;

import by.kamtech.telegrambot.counterman.config.Keys;
import by.kamtech.telegrambot.counterman.db.DatabaseManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class CountemanBot extends TelegramLongPollingBot {

    private static final int STARTSTATE = 0;
    private static final int MAINMENU = 1;
    private static final int ADD_EXPENSE = 2;
    private static final int SETTINGS = 3;
    private static final int ADD_CATEGORY = 4;

    public CountemanBot() {
        super();
    }

    @Override
    public String getBotToken() {
        return Context.getConfig().getString(Keys.BOT_TOKEN);
    }

    @Override
    public String getBotUsername() {
        return Context.getConfig().getString(Keys.BOT_USERNAME);
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                handleUpdate(update.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleUpdate(Message message) throws TelegramApiException {

        final int position = DatabaseManager.getInstance().getPosition(message.getFrom().getId(), message.getChatId());

        if (message.hasText() && message.getText().equals(getBackCommand())) {
            execute(sendMessageDefault(message));
            return;
        }

        SendMessage sendMessageRequest;
        switch (position) {
            case MAINMENU:
                sendMessageRequest = messageOnMainMenu(message);
                break;
            case ADD_EXPENSE:
                sendMessageRequest = messageOnAddExpense(message);
                break;
            case SETTINGS:
            case ADD_CATEGORY:
            default:
                sendMessageRequest = sendMessageDefault(message);
                break;
        }

        execute(sendMessageRequest);

        /*try {

            String msg[] = update.getMessage().getText().split(" ");
            SendMessage sendMessage = new SendMessage()
                    .setChatId(update.getMessage().getChatId());

            if(msg.length < 2) {
                sendMessage.setText("Use next format '/command param'");
            } else {

                if ("/add" .equals(msg[0])) {

                    double amount = Double.parseDouble(msg[1]);
                    if (amount > 0) {
                        DatabaseManager.getInstance().saveExpense(new Expense(update.getMessage().getFrom().getId(),
                                update.getMessage().getChatId(), amount));
                        sendMessage.setText("Success!");
                    } else {
                        sendMessage.setText("Expense must be more zero!");
                    }

                } else if ("/sum" .equals(msg[0])) {
                    sendMessage.setText(String.valueOf(DatabaseManager.getInstance().sum(update.getMessage().getFrom().getId())));
                } else {
                    sendMessage.setText("Unknown command");
                }
            }


//                    .setReplyMarkup(getKeyboard())
//                    .setReplyToMessageId(update.getMessage().getMessageId())
//                    .setText("Hello!");

            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
    }

    /*private boolean isCommandForOther(String text) {
        boolean isSimpleCommand = text.equals("/start") || text.equals("/help") || text.equals("/stop");
        return text.startsWith("/") && !isSimpleCommand;
    }

    private void sendHideKeyboard(Integer userId, Long chatId, Integer messageId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setText(Emoji.WAVING_HAND_SIGN.toString());

        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setSelective(true);
        sendMessage.setReplyMarkup(replyKeyboardRemove);

        execute(sendMessage);
        DatabaseManager.getInstance().insertPosition(userId, chatId, STARTSTATE);
    }*/

    private SendMessage messageOnMainMenu(Message message) {
        if (message.hasText()) {
            if (message.getText().equals(getAddCommand())) {
                return sendMessage(message, ADD_EXPENSE, getBackKeyboard(), "Enter expense");
            } else if (message.getText().equals(getSettingCommand())) {
                return sendMessage(message, SETTINGS, getBackKeyboard(), "Settings Counterman Bot");
            }
        }

        return sendMessageDefault(message);
    }

    private SendMessage messageOnAddExpense(Message message) {
        if (message.hasText()) {
            String result = "Incorrect value. Please enter correct value";
            try {
                double amount = Double.parseDouble(message.getText());

                if (amount > 0) {
                    DatabaseManager.getInstance().saveExpense(message.getFrom().getId(), message.getChatId(), amount);
                    result = "Expense saved. Please enter other value or back in main menu";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return sendSomeMessage(message.getChatId(), message.getMessageId(), getBackKeyboard(), result);
        }

        return sendMessageDefault(message);
    }

    private SendMessage sendMessageDefault(Message message) {
        return sendMessage(message, MAINMENU, getMainMenuKeyboard(), "help message");
    }

    private SendMessage sendMessage(Message message, int position, ReplyKeyboardMarkup keyboardMarkup, String text) {
        DatabaseManager.getInstance().insertPosition(message.getFrom().getId(), message.getChatId(), position);
        return sendSomeMessage(message.getChatId(), message.getMessageId(), keyboardMarkup, text);
    }

    private SendMessage sendSomeMessage(Long chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText(text);
        return sendMessage;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(getAddCommand());
        keyboardFirstRow.add(getSettingCommand());
        keyboard.add(keyboardFirstRow);

        return getKeyboard(keyboard);
    }

    private ReplyKeyboardMarkup getSettingKeyboard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(getCategoryCommand());
        keyboardFirstRow.add(getBackCommand());
        keyboard.add(keyboardFirstRow);

        return getKeyboard(keyboard);
    }

    private ReplyKeyboardMarkup getBackKeyboard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(getBackCommand());
        keyboard.add(keyboardFirstRow);

        return getKeyboard(keyboard);
    }

    private ReplyKeyboardMarkup getKeyboard(List<KeyboardRow> keyboard) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private String getAddCommand() {
        return String.format("%sAdd", Emoji.HEAVY_PLUS_SIGN.toString());
    }

    private String getCategoryCommand() {
        return String.format("%sCategory", Emoji.PENCIL.toString());
    }

    private String getBackCommand() {
        return String.format("%sBack", Emoji.BACK_WITH_LEFTWARDS_ARROW_ABOVE.toString());
    }

    private String getSettingCommand() {
        return String.format("%sSettings", Emoji.WRENCH.toString());
    }

}
