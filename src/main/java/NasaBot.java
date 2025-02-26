import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class NasaBot extends TelegramLongPollingBot {

    String url = "https://api.nasa.gov/planetary/apod?api_key=pdveHUdddhcn9UrCJjcomIef4rczSB2QoPwhIdVs";
    String botName;
    String botToken;

    public NasaBot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }



    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String action = update.getMessage().getText();
            String[] splittedAction = action.split(" ");
            String option = splittedAction[0];


            try {
                switch (option) {
                    case "/start":
                        sendMessage(chatId, "I am NasaBot, and I send the picture of the day.\uD83D\uDEF0\uFE0F ");
                        break;
                    case "/help":
                        sendMessage(chatId, "Enter /image to get the picture of the day or /date yyyy-mm-dd to get the picture for specific day \uD83D\uDEF0\uFE0F");
                        break;
                    case "/image":
                        String image = Utils.getImage(url);
                        sendMessage(chatId, image);
                        break;
                    case "/date":
                        String date = splittedAction[1];
                        image = Utils.getImage(url + "&date=" + date);
                        sendMessage(chatId, image);
                        break;
                    default:
                        sendMessage(chatId, "\uD83D\uDC68\u200D\uD83D\uDE80 Houston, we have a problem! I don’t recognize that command. Type /help to navigate through space. \uD83C\uDF0C");
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void sendMessage(long chatId, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
