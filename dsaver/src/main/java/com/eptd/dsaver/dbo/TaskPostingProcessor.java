package com.eptd.dsaver.dbo;

import java.sql.SQLException;

import com.eptd.dsaver.core.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TaskPostingProcessor {
	private Task task;
	private DBOperation dbo;
	
	public TaskPostingProcessor(Task task){
		this.task = task;
	}
	
	public JsonObject process(){
		JsonObject resp = new JsonObject();//return value
		JsonArray errors = new JsonArray();//collector of next-level errors
		resp.addProperty("type", "major");
		resp.addProperty("success", true);
		try {
			dbo = new DBOperation();
			int id = dbo.insert(task);
			if(id > 0)
				resp.addProperty("generated_id", id);
			else if(id == 0)
				throw new SQLException("Tuple of task assigned to "+task.getClientID()+" already exists");
		} catch (Exception e) {
			resp.remove("success");
			resp.addProperty("success", false);
			//error object for major repo
			JsonObject error = new JsonObject();
			error.addProperty("client_id", task.getClientID());
			error.addProperty("message", e.toString().replace(':', '-'));
			errors.add(error);
		}
		//add error objects into response object
		if(errors.size() > 0)
			resp.add("error_messages", errors);
		return new JsonObject();
	}
}
