package com.eptd.dsaver.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eptd.dsaver.core.Task;
import com.eptd.dsaver.dbo.TaskPostingProcessor;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class TaskPoster extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TaskPoster() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	JsonObject respStr = new JsonObject();
		Task task = new Task()
				.setClientID(2)
				.setTotal(40)
				.setParaLanguage("java")
				.setParaSize("2000..20000");
		TaskPostingProcessor processor = new TaskPostingProcessor(task);
		JsonObject resp = processor.process();
		//response with json data
		response.setContentType("application/json");
		if(resp.get("success").getAsBoolean()){
			respStr.addProperty("success", true);
			respStr.addProperty("generated_id", resp.get("generated_id").getAsInt());
		}else{
			respStr.addProperty("success", false);
			respStr.add("error_msg", resp.get("error_messages").getAsJsonArray());
		}
		response.getWriter().append(respStr.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject respStr = new JsonObject();
		//convert posted data into Task data class
		Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		Task task = gson.fromJson(reader, Task.class);
		//insert MajorRepository data into database
		if(task.getClientID() != 0){
			TaskPostingProcessor processor = new TaskPostingProcessor(task);
			JsonObject resp = processor.process();
			//response with json data
			response.setContentType("application/json");
			if(resp.get("success").getAsBoolean()){
				respStr.addProperty("success", true);
				respStr.addProperty("generated_id", resp.get("generated_id").getAsInt());
			}else{
				respStr.addProperty("success", false);
				respStr.add("error_msg", resp.get("error_messages").getAsJsonArray());
			}
		}else{
			respStr.addProperty("success", false);
			respStr.addProperty("error_msg", "Invalid repo data attached in request entity.");
		}
		response.getWriter().append(respStr.toString());
	}

}
