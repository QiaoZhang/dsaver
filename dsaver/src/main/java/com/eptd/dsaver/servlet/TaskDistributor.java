package com.eptd.dsaver.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eptd.dsaver.core.Client;
import com.eptd.dsaver.dbo.TaskDistributingProcessor;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class TaskDistributor extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TaskDistributor() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject respStr = new JsonObject();
		//convert posted data into Task data class
		Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		Client client = gson.fromJson(reader, Client.class);
		//insert Task data into database
		response.setContentType("application/json");
		if(client.getFingerPrint() != null){
			TaskDistributingProcessor processor;
			if(request.getHeader("Failed-repo")!=null&&Integer.valueOf(request.getHeader("Failed-repo"))>0)
				processor = new TaskDistributingProcessor(client,Integer.valueOf(request.getHeader("Failed-repo")));
			else
				processor = new TaskDistributingProcessor(client);
			JsonObject resp = processor.process();
			//response with json data			
			if(resp.get("success").getAsBoolean()){
				respStr.addProperty("success", true);
				if(resp.get("task")!=null)
					respStr.add("data", resp.get("task").getAsJsonObject());
				if(resp.get("generated_id")!=null)
					respStr.addProperty("generated_id", resp.get("generated_id").getAsInt());
			}else{
				respStr.addProperty("success", false);
				respStr.add("error_msg", resp.get("error_messages").getAsJsonArray());
			}
		}else{
			respStr.addProperty("success", false);
			respStr.addProperty("error_msg", "Invalid client info attached in request entity.");
		}
		response.getWriter().append(respStr.toString());
	}

}
