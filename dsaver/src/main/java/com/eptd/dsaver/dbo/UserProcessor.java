package com.eptd.dsaver.dbo;

import java.sql.SQLException;

import com.eptd.dsaver.core.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class UserProcessor {
	private final User user;
	private final DBOperation dbo;
	
	public UserProcessor(User user,DBOperation dbo){
		this.user = user;
		this.dbo = dbo;
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
			//process all repos
			for(int i=0;i<user.getOwnRepos().size();i++){
				RepoProcessor processor = new RepoProcessor(user.getOwnRepos().get(i),dbo);
				JsonObject response = processor.process(user.getUserId());//insert tuple of user into database
				if(!response.get("success").getAsBoolean()){
					resp.remove("success");
					resp.addProperty("success", false);
					errors.add(response);
				}
			}
			//connect user with major repo
			int connID = dbo.connect(repo_id, user.getUserId(), user.getContribution());
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

	public User getUser() {
		return user;
	}
}
