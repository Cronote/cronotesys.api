package com.cronotesys.APIrest;

import java.util.HashMap;

import com.cronoteSys.util.RestUtil;

public class MessageUtil {

	public String generateMessage(String type, HashMap<String, String> values) {
		if (type.equalsIgnoreCase("team")) {

			return "Olá usuário, você foi convidado a se juntar ao time  " + values.get("teamName")
					+ "\nCaso queira participar é só clicar no link abaixo:\n" + RestUtil.HOST + "teamAccepted?member="
					+ values.get("member") + "&team=" + values.get("team")
					+ "\nCaso não queira é só ignorar o email e assim o link irá se expirar";
		}
		return "Error";
	}

}
