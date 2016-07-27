package dsaver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.joda.time.DateTime;

import com.eptd.dsaver.core.Client;
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
		Task task1 = new Task()
			.setClientID(1)
			.setTotal(20)
			.setParaLanguage("java")
			.setParaSize("4000..40000");
		Task task2 = new Task()
			.setClientID(1)
			.setTotal(10)
			.setParaLanguage("java")
			.setParaForks("200..2000");
		Client client1 = new Client()
			.setClientID(1)
			.setFingerPrint("test-1")
			.setLastUpdate(new DateTime())
			.addTask(task1)
			.addTask(task2);
		
		Task task3 = new Task()
			.setClientID(2)
			.setTotal(40)
			.setParaLanguage("java")
			.setParaSize("2000..20000");
		Task task4 = new Task()
			.setClientID(2)
			.setTotal(20)
			.setParaLanguage("java")
			.setParaForks("400..4000");
		Client client2 = new Client()
			.setClientID(2)
			.setFingerPrint("test-2")
			.setLastUpdate(new DateTime())
			.addTask(task3)
			.addTask(task4);
		String json1 = gson.toJson(client1);
		System.out.println(json1);
		String json2 = gson.toJson(client2);
		System.out.println(json2);*/
		
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
