package dsaver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.joda.time.DateTime;

import com.eptd.dsaver.core.Task;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TaskTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
		/*
		Task task = new Task()
			.setClientID(1)
			.setTotal(20)
			.setParaLanguage("java")
			.setParaSize("4000..40000");		
		String json = gson.toJson(task);
		System.out.println(json);
		*/
		String request = "{\"clientID\":1,\"total\":20,\"paraLanguage\":\"java\",\"paraSize\":\"4000..40000\"}";
		JsonObject obj = new JsonParser().parse(request).getAsJsonObject();
		Task task = gson.fromJson(obj, Task.class);
		System.out.println(task.getClientID());
		System.out.println(task.getTotal());
		System.out.println(task.getParaLanguage());
		System.out.println(task.getParaSize());
		System.out.println(task.getCreated());
		System.out.println(task.getParaForks());
	}

}
