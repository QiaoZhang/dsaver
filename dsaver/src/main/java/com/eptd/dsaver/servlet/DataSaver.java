package com.eptd.dsaver.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eptd.dsaver.core.MajorRepository;
import com.eptd.dsaver.dbo.MajorRepoProcessor;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class DataSaver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DataSaver() {
        super();        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject respStr = new JsonObject();			
		//convert posted data into MajorRepository data class
		Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		MajorRepository majorRepo = gson.fromJson(reader, MajorRepository.class);
		//insert MajorRepository data into database
		response.setContentType("application/json");
		if(majorRepo.getProjectID() != 0){
			MajorRepoProcessor processor;
			if(request.getHeader("Failed")!=null&&Integer.valueOf(request.getHeader("Failed"))>0)
				processor = new MajorRepoProcessor(majorRepo,Integer.valueOf(request.getHeader("Failed")));
			else
				processor = new MajorRepoProcessor(majorRepo);
			JsonObject resp = processor.process();
			//response with json data			
			if(resp.get("success").getAsBoolean())
				respStr.addProperty("success", true);
			else{
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
