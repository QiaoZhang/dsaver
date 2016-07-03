package com.eptd.dsaver.dbo;

import java.sql.SQLException;

import com.eptd.dsaver.core.Repository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RepoProcessor {
	private final Repository repo;
	private final DBOperation dbo;
	
	public RepoProcessor(Repository repo,DBOperation dbo){
		this.repo = repo;
		this.dbo = dbo;
	}
	
	public JsonObject process(long user_id){
		JsonObject resp = new JsonObject();//return value
		JsonArray errors = new JsonArray();//collector of next-level errors
		resp.addProperty("type", "repo");
		resp.addProperty("success", true);
		try {
			//insert user into database
			int id = dbo.insert(repo);
			if(id > 0)
				resp.addProperty("generated_id", id);
			else if(id == 0)
				throw new SQLException("Tuple of repo "+repo.getProjectID()+" already exists");
			//connect user with major repo
			int connID = dbo.connect(user_id, repo.getProjectID());
			if(connID > 0)
				resp.addProperty("connect_id", connID);
			else
				throw new SQLException("Connection failed, no generated id captured during connect operation of repo "+repo.getProjectID()+" and user "+user_id);
		} catch (Exception e) {
			resp.remove("success");
			resp.addProperty("success", false);
			//error object for major repo
			JsonObject error = new JsonObject();
			error.addProperty("id", repo.getProjectID());
			error.addProperty("messages", e.toString().replace(':', '-'));
			errors.add(error);
		}
		//add error objects into response object
		if(errors.size() > 0)
			resp.add("error_messages", errors);
		return resp;
	}
}
