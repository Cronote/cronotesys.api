package com.cronotesys.APIrest;

public class MessageUtil {

	public String generateMessage(String type, String member, String team) {
		if(type.equalsIgnoreCase("team")) {
			System.out.println("Olá usuário, você foi convidado a se juntar ao time(time)\nCaso queira participar é só clicar no link abaixo:\n"
					+"http://localhost:8080/myresource/teamAccepted?member="+member+"&team="+team+ "\nCaso não queira é só ignorar o email e assim o link irá se expirar");
			return "Olá usuário, você foi convidado a se juntar ao time(time)\nCaso queira participar é só clicar no link abaixo:\n"
					+"http://localhost:8080/myresource/teamAccepted?member="+member+"&team="+team+ "\nCaso não queira é só ignorar o email e assim o link irá se expirar";
		}
		return "Error";
	}
	
}
