package com.cronotesys.APIrest;

public class MessageUtil {

	public String generateMessage(String string) {
		if(string.equalsIgnoreCase("team")) {
			return "Olá usuário, você foi convidado a se juntar ao time(time)\nCaso queira participar é só clicar no link abaixo:\n"
					+ "(link)\nCaso não queira é só ignorar o email e assim o link irá se expirar";
		}
		return "Error";
	}
	
}
