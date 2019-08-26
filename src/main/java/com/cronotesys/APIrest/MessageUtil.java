package com.cronotesys.APIrest;

public class MessageUtil {

	public String generateMessage(String type, String member, String team, String teamName) {
		if(type.equalsIgnoreCase("team")) {
			return "Olá usuário, você foi convidado a se juntar ao time  "+teamName+"\nCaso queira participar é só clicar no link abaixo:\n"
					+"http://localhost:8080/myresource/teamAccepted?member="+member+"&team="+team+ "\nCaso não queira é só ignorar o email e assim o link irá se expirar";
		}
		return "Error";
	}
	
}
