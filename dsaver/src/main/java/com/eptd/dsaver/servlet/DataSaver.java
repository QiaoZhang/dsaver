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

/**
 * Servlet implementation class DataSaver
 */
public class DataSaver extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JsonObject respStr;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataSaver() {
        super();
        respStr = new JsonObject();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//convert posted data into MajorRepository data class
		Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		MajorRepository majorRepo = gson.fromJson(reader, MajorRepository.class);
		//insert MajorRepository data into database
		if(majorRepo.getProjectID() != 0){
			MajorRepoProcessor processor = new MajorRepoProcessor(majorRepo);
			JsonObject resp = processor.process();
			//response with json data
			response.setContentType("application/json");
			if(resp.get("success").getAsBoolean()){
				respStr.addProperty("success", true);
				//respStr.add("completed_repos", resp.get("completed_repos").getAsJsonArray());
			}else{
				respStr.addProperty("success", false);
				respStr.add("error_msg", resp.get("error_messages").getAsJsonArray());
			}
			response.getWriter().append(respStr.toString());
		}else{
			respStr.addProperty("success", false);
			respStr.addProperty("error_msg", "Invalid repo data attached in request entity.");
		}
	}

}
