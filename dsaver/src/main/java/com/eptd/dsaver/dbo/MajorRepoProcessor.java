package com.eptd.dsaver.dbo;

import java.sql.SQLException;

import com.eptd.dsaver.core.MajorRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MajorRepoProcessor {
	private final MajorRepository repo;
	private DBOperation dbo;
	private int failed;
	
	public MajorRepoProcessor(MajorRepository repo){
		this.repo = repo;
		this.failed = 0;
	}
	
	public MajorRepoProcessor(MajorRepository repo,int failed){
		this.repo = repo;
		this.failed = failed;
	}
	
	public JsonObject process(){
		JsonObject resp = new JsonObject();//return value
		JsonArray errors = new JsonArray();//collector of next-level errors
		resp.addProperty("type", "major");
		resp.addProperty("success", true);
		try {
			dbo = new DBOperation();
			int id = dbo.insert(repo);
			if(id > 0)
				resp.addProperty("generated_id", id);
			else if(id == 0)
				throw new SQLException("Tuple of major repo "+repo.getProjectID()+" already exists");
			//process all contributors
			for(int i=0;i<repo.getContributors().size();i++){
				UserProcessor processor = new UserProcessor(repo.getContributors().get(i),dbo);
				JsonObject response = processor.process(repo.getProjectID());//insert tuple of user into database
				if(!response.get("success").getAsBoolean()){
					resp.remove("success");
					resp.addProperty("success", false);
					errors.add(response);
				}
			}
			//update task and client
			dbo.updateTask(repo.getTaskID());//success + 1
			dbo.updateTask(repo.getTaskID(), failed);//update failed
			dbo.updateClient(repo);
		} catch (Exception e) {
			resp.remove("success");
			resp.addProperty("success", false);
			//error object for major repo
			JsonObject error = new JsonObject();
			error.addProperty("id", repo.getProjectID());
			error.addProperty("message", e.toString().replace(':', '-'));
			errors.add(error);
		}
		//add error objects into response object
		if(errors.size() > 0)
			resp.add("error_messages", errors);
		return resp;
		
		/***********************************
		 * Json response example:
		 * 	{
		 * 		"type":"major",
		 * 		"success":false,
		 * 		"generated_id":"1000",
		 * 		"error_messages":[
		 * 			{
		 * 				"id":"123456789",
		 * 				"message":"Creation failed ......"
		 * 			},
		 * 			{
		 * 				"type":"user",
		 * 				"success":false,
		 *				"generated_id":"10000",
		 *				"connect_id":"20000",
		 *				"error_messages":[
		 *					{
		 *						"id":"456789123"，
		 *						"message":"Connection failed ......"
		 *					},
		 *					{
		 *						"type":"repo",
		 *						"success":false,
		 *						"generated_id":"100000",
		 *						"connect_id":"200000",
		 *						"error_messages":[
		 *							{
		 *								"id":"789123456",
		 *								"message":"Creation failed ......"
		 *							}
		 *						]
		 *					}
		 *				]	 					
		 * 			},
		 * 			{
		 * 				"type":"user",
		 * 				......
		 * 			}
		 * 		]
		 * 	}
		 ***********************************/
	}
	
	public MajorRepository getRepo() {
		return repo;
	}
}
