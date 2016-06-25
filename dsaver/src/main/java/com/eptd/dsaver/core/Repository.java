package com.eptd.dsaver.core;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class Repository {
	//basic repository information
	protected String repositoryURL = null;
	protected String repositoryHTML = null;
	protected String filePath = null;
	protected long projectID = 0;
	protected String projectName = null;
	protected String ownerLogin = null;
	protected String userType = null;
	protected String language = null;
	protected String version = null;
	
	//TODO Instead of using major language, use language set to make comparison
	//protected Languages languages;
	
	protected long size = 0;
	protected long stargazersCount = 0;
	protected long subscribersCount = 0;
	protected long forksCount = 0;
	protected DateTime createdAt = null;
	protected long issuesCount = 0;
	protected long handledIssuesCount = 0;
	protected double avgIssueHandledDays = 0.0;
	
	//sonar evaluation data
	protected ArrayList<SonarMetrics> sonarMetrics = new ArrayList<SonarMetrics>();
	
	public Repository(){
		createdAt = new DateTime();
	}

	public String getRepositoryURL() {
		return repositoryURL;
	}

	public void setRepositoryURL(String repositoryURL) {
		this.repositoryURL = repositoryURL;
	}

	public String getRepositoryHTML() {
		return repositoryHTML;
	}

	public void setRepositoryHTML(String repositoryHTML) {
		this.repositoryHTML = repositoryHTML;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getProjectID() {
		return projectID;
	}

	public void setProjectID(long projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOwnerLogin() {
		return ownerLogin;
	}

	public void setOwnerLogin(String ownerLogin) {
		this.ownerLogin = ownerLogin;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ArrayList<SonarMetrics> getSonarMetrics() {
		return sonarMetrics;
	}

	public void setSonarMetrics(ArrayList<SonarMetrics> sonarMetrics) {
		if(sonarMetrics!=null)
			this.sonarMetrics = sonarMetrics;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getStargazersCount() {
		return stargazersCount;
	}

	public void setStargazersCount(long stargazersCount) {
		this.stargazersCount = stargazersCount;
	}

	public long getSubscribersCount() {
		return subscribersCount;
	}

	public void setSubscribersCount(long subscribersCount) {
		this.subscribersCount = subscribersCount;
	}

	public long getForksCount() {
		return forksCount;
	}

	public void setForksCount(long forksCount) {
		this.forksCount = forksCount;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public long getIssuesCount() {
		return issuesCount;
	}

	public void setIssuesCount(long issuesCount) {
		this.issuesCount = issuesCount;
	}

	public long getHandledIssuesCount() {
		return handledIssuesCount;
	}

	public void setHandledIssuesCount(long handledIssuesCount) {
		this.handledIssuesCount = handledIssuesCount;
	}

	public double getAvgIssueHandledDays() {
		return avgIssueHandledDays;
	}

	public void setAvgIssueHandledDays(double avgIssueHandledDays) {
		this.avgIssueHandledDays = avgIssueHandledDays;
	}
}
