package com.eptd.dsaver.dbo;

import java.sql.SQLException;

import org.joda.time.DateTime;

import com.eptd.dsaver.core.Client;
import com.eptd.dsaver.core.Task;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TaskDistributingProcessor {
	private DBOperation dbo;
	private Client client;
	private int failed;
	
	public TaskDistributingProcessor(Client client){
		this.client = client;
		this.failed = 0;
	}
	
	public TaskDistributingProcessor(Client client, int failed){
		this.client = client;
		this.failed = failed;
	}
	
	public JsonObject process(){
		JsonObject resp = new JsonObject();//return value
		JsonArray errors = new JsonArray();//collector of next-level errors
		resp.addProperty("type", "distribution");
		resp.addProperty("success", true);
		try {
			dbo = new DBOperation();
			//TODO if a failed has been passed, close the opening task and make the next assigned task open
			if(failed != 0){
				
			}
			Client clientTasks = dbo.getClientInfo(client.getFingerPrint());
			if(clientTasks != null){
				//if specific client exists
				dbo.updateClient(clientTasks.getClientID());
				if(clientTasks.getTasks().size() > 0){
					Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
					resp.add("task", new JsonParser().parse(gson.toJson(clientTasks.getTasks().get(0), Task.class)));
					boolean nonOpenTask = true;
					for(int i=1;i<clientTasks.getTasks().size();i++){
						if(clientTasks.getTasks().get(i).getState().equals("open")){
							nonOpenTask = false;
							resp.remove("task");
							resp.add("task", new JsonParser().parse(gson.toJson(clientTasks.getTasks().get(i), Task.class)));
						}
					}
					//if there is no open state task
					if(nonOpenTask){
						//TODO make the clientTasks.getTasks().get(0) as open state
					}
				} else
					throw new SQLException("No task assigned to client "+client.getFingerPrint()+" exists in database.");
			}else{
				//no client in database
				int id = dbo.insert(client.setLastUpdate(new DateTime()));
				if(id > 0)
					resp.addProperty("generated_id", id);
				else if(id == 0)
					throw new SQLException("Tuple of client with finger print "+client.getFingerPrint()+" already exists");
			}			
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
