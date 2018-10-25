package bot_dicionario;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class App {

	public static void main(String[] args)   {
		
		
		ApiContextInitializer.init();
		
		TelegramBotsApi bot= new TelegramBotsApi();
		
		try {
			bot.registerBot(new Dicionario());
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}

	}

}
