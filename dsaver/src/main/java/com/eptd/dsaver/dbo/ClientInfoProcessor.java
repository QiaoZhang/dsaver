package com.eptd.dsaver.dbo;

import java.sql.SQLException;
import java.util.ArrayList;

import com.eptd.dsaver.core.Client;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ClientInfoProcessor {
	private DBOperation dbo;
	
	public JsonObject process(){
		JsonObject resp = new JsonObject();//return value
		JsonArray errors = new JsonArray();//collector of next-level errors
		resp.addProperty("type", "clients");
		resp.addProperty("success", true);
		try {
			dbo = new DBOperation();
			ArrayList<Client> clients = dbo.getClientInfo();
			if(clients.size() > 0){
				JsonArray data = new JsonArray();
				Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
				for(int i=0;i<clients.size();i++)
					data.add(new JsonParser().parse(gson.toJson(clients.get(i), Client.class)));
				resp.add("clients", data);
			} else
				throw new SQLException("No client tuple exists in database.");
		} catch (Exception e) {
			resp.remove("success");
			resp.addProperty("success", false);
			//error object for major repo
			JsonObject error = new JsonObject();
			error.addProperty("message", e.toString().replace(':', '-'));
			errors.add(error);
		}
		//add error objects into response object
		if(errors.size() > 0)
			resp.add("error_messages", errors);
		return resp;
	}
}
