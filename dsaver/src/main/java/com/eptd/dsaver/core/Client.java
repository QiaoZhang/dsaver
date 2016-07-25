package com.eptd.dsaver.core;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class Client {
	private int clientID;
	private String fingerPrint;
	private String username;
	private String password;
	private String appClientID;
	private String appClientSecret;
	private DateTime lastUpdate;
	private ArrayList<Task> tasks;
	
	public int getClientID() {
		return clientID;
	}
	
	public Client setClientID(int clientID) {
		this.clientID = clientID;
		return this;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

	public Client setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public Client setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Client setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getAppClientID() {
		return appClientID;
	}

	public Client setAppClientID(String appClientID) {
		this.appClientID = appClientID;
		return this;
	}

	public String getAppClientSecret() {
		return appClientSecret;
	}

	public Client setAppClientSecret(String appClientSecret) {
		this.appClientSecret = appClientSecret;
		return this;
	}

	public DateTime getLastUpdate() {
		return lastUpdate;
	}

	public Client setLastUpdate(DateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
		return this;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public Client setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
		return this;
	}
	
	public void addTask(Task task){
		this.tasks.add(task);
	}
}
