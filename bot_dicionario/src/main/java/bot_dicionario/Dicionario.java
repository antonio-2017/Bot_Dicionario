package bot_dicionario;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import bot_dicionario.JsonReader;

public class Dicionario extends TelegramLongPollingBot {

	public void onUpdateReceived (Update update) {
		Message msg = update.getMessage();
		JSONObject json = null;
		SendMessage sm1 = new SendMessage();
		try {
			json = JsonReader.readJsonFromUrl("http://dicionario-aberto.net/search-json/" + msg.getText());
		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//exibe a entrada no console
		System.out.println("mensagem de entrada -> " + msg.getText());
		//armazena o fluxo de dados em uma string para poder ser feita uma comparação 
		String string= (String) json.toString();
		
		if(string.contains("superEntry")) {
			//retorna a palavra solicitada
			String palavra = (String) json.getJSONArray("superEntry").getJSONObject(0).getJSONObject("entry").getJSONObject("form").get("orth");
			String pala=palavra.toUpperCase();
			//retorna a definição da palavra
			String definicao=(String) json.getJSONArray("superEntry").getJSONObject(0).getJSONObject("entry").getJSONArray("sense").getJSONObject(0).get("def");
			String def= definicao.replaceAll("<br/>","\n");
			sm1.setChatId(update.getMessage().getChatId());
			
			sm1.setText(pala+"\n"+def);
		}else {
			String palavra = (String) json.getJSONObject("entry").getJSONObject("form").get("orth");
			String pala=palavra.toUpperCase();
			
			String definicao=(String) json.getJSONObject("entry").getJSONArray("sense").getJSONObject(0).get("def");
			String def= definicao.replaceAll("<br/>","\n");
			sm1.setChatId(update.getMessage().getChatId());
			
			sm1.setText(pala+"\n"+def);
		}
		
		try {
			execute(sm1);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
	}

	public String getBotUsername() {
		
		return "Dicionario_Popular_bot";
	}

	@Override
	public String getBotToken() {
		
		return "791079780:AAHQEZLVdGCj0VnP-pqVn-bGZyXxoUPrKDk";
	}

	

}
