package com.eptd.dsaver.dbo;

import java.sql.SQLException;

import com.eptd.dsaver.core.FilteredUser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FilteredUserProcessor {
	private final FilteredUser user;
	private final DBOperation dbo;
	
	public FilteredUserProcessor(FilteredUser user,DBOperation dbo){
		this.user = user;
		this.dbo = dbo;
	}

	public FilteredUser getUser() {
		return user;
	}
	
	public JsonObject process(long repo_id){
		JsonObject resp = new JsonObject();//return value
		JsonArray errors = new JsonArray();//collector of next-level errors
		resp.addProperty("type", "user");
		resp.addProperty("success", true);
		try {
			int id = dbo.insert(user);
			if(id > 0)
				resp.addProperty("generated_id", id);
			else if(id == 0){
				//error object for major repo
				JsonObject error = new JsonObject();
				error.addProperty("id", user.getUserId());
				error.addProperty("message", "Tuple of user "+user.getUserId()+" already exists");
				errors.add(error);
			}
			//connect user with major repo
			int connID = dbo.connect(repo_id, user);
			if(connID > 0)
				resp.addProperty("connect_id", connID);
			else
				throw new SQLException("Connection failed, no generated id captured during connect operation of user "+user.getUserId()+" and major repo "+repo_id);
		} catch (Exception e) {
			resp.remove("success");
			resp.addProperty("success", false);
			//error object for major repo
			JsonObject error = new JsonObject();
			error.addProperty("id", user.getUserId());
			error.addProperty("message", e.toString().replace(':', '-'));
			errors.add(error);
		}
		//add error objects into response object
		if(errors.size() > 0)
			resp.add("error_messages", errors);
		return resp;
	}
}
