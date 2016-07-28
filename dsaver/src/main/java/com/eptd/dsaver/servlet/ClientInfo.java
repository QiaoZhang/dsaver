package com.eptd.dsaver.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eptd.dsaver.dbo.ClientInfoProcessor;
import com.google.gson.JsonObject;

public class ClientInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ClientInfo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject respStr = new JsonObject();			
		//get all Client info from database
		ClientInfoProcessor processor = new ClientInfoProcessor();
		JsonObject resp = processor.process();
		if(resp.get("success").getAsBoolean()){
			respStr.addProperty("success", true);
			respStr.add("data", resp.get("clients").getAsJsonArray());
		}else{
			respStr.addProperty("success", false);
			respStr.add("error_msg", resp.get("error_messages").getAsJsonArray());
		}
		//response with json data
		response.setContentType("application/json");
		response.getWriter().append(respStr.toString());		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
