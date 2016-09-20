package com.eptd.dsaver.core;
import java.util.ArrayList;
import java.util.List;

public class User {
	private String userURL = "";
	private String userHTML = "";
	private long userId = 0;
	private String login = "";
	private String name = "";	
	private int numOfPublicRepos = 0;
	private int numOfAssignees = 0;
	private long followers = 0;
	private String folderPath = "";
	private long contribution = 0;
	
	//attributes from owned repositories
	private ArrayList<Repository> ownRepos = null;						
	private int numOfAnalyzedRepos = 0;
	private double avgSize = 0.0;
	private double avgStargazersCount = 0.0;
	private double avgSubscribersCount = 0.0;
	private double avgForksCount = 0.0;
	private double avgIssuesCount = 0.0;
	private double avgHandledIssuesRatio = 0.0;
	private double avgIssueHandledDays = 0.0;
	
	//SonarQube metrics
	//Reliability
	private double avgBugs = 0.0;
	//Vulnerabilities
	private double avgVulnerabilities = 0.0;
	//Maintainability
	private double avgCodeSmells = 0.0;
	private double avgSqaleIndex = 0.0;
	private double avgDebtRatio = 0.0;
	//Duplications
	private double avgDuplicatedLineDensity = 0.0;
	private double avgDuplicatedBlocks = 0.0;
	private double avgDuplicatedLines = 0.0;
	private double avgDuplicatedFiles = 0.0;
	//Size
	private double avgMajorLanguageLOC = 0.0;
	private double avgLines = 0.0;
	private double avgStatements = 0.0;
	private double avgFunctions = 0.0;
	private double avgClasses = 0.0;
	private double avgFiles = 0.0;
	private double avgDirectories = 0.0;
	//Complexity
	private double avgComplexity = 0.0;
	private double avgFileComplexity = 0.0;
	private double avgFunctionComplexity = 0.0;
	private double avgClassComplexity = 0.0;
	//Documentation
	private double avgCommemtLineDensity = 0.0;
	private double avgCommentLines = 0.0;
	private double avgPublicAPI = 0.0;
	private double avgDocumentedAPIDensity = 0.0;
	private double avgUndocumentedAPI = 0.0;		
	
	//attributes from investigated pull request
	private int numOfPullRequest = 0;
	private int numOfAcceptedPR = 0;
	private int numOfContributedRepos = 0;	
	private double avgCommits = 0.0;
	private double avgAdditions = 0.0;
	private double avgDeletions = 0.0;
	private double avgChangedFiles = 0.0;
	private double avgDaysIntervalOfPR = 0.0;
	
	public User(){
		ownRepos = new ArrayList<Repository>();
	}

	public String getUserURL() {
		return userURL;
	}

	public void setUserURL(String userURL) {
		this.userURL = userURL;
	}

	public String getUserHTML() {
		return userHTML;
	}

	public void setUserHTML(String userHTML) {
		this.userHTML = userHTML;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumOfAssignees() {
		return numOfAssignees;
	}

	public void setNumOfAssignees(int numOfAssignees) {
		this.numOfAssignees = numOfAssignees;
	}

	public int getNumOfPublicRepos() {
		return numOfPublicRepos;
	}

	public void setNumOfPublicRepos(int numOfPublicRepos) {
		this.numOfPublicRepos = numOfPublicRepos;
	}

	public long getFollowers() {
		return followers;
	}

	public void setFollowers(long followers) {
		this.followers = followers;
	}

	public ArrayList<Repository> getOwnRepos() {
		return ownRepos;
	}

	public void setOwnRepos(ArrayList<Repository> ownRepos) {
		this.ownRepos = ownRepos;
	}
	
	public void addOwnRepo(Repository repo){
		this.ownRepos.add(repo);
	}
	
	public void addAllOwnRepo(List<Repository> ownRepos){
		this.ownRepos.addAll(ownRepos);
	}
	
	public void clearOwnRepos(){
		this.ownRepos.clear();
	}

	public int getNumOfAnalyzedRepos(){
		return numOfAnalyzedRepos;
	}

	public double getAvgSize() {
		return avgSize;
	}

	public void setAvgSize(double avgSize) {
		this.avgSize = avgSize;
	}

	public double getAvgStargazersCount() {
		return avgStargazersCount;
	}

	public void setAvgStargazersCount(double avgStargazersCount) {
		this.avgStargazersCount = avgStargazersCount;
	}

	public double getAvgSubscribersCount() {
		return avgSubscribersCount;
	}

	public void setAvgSubscribersCount(double avgSubscribersCount) {
		this.avgSubscribersCount = avgSubscribersCount;
	}

	public double getAvgForksCount() {
		return avgForksCount;
	}

	public void setAvgForksCount(double avgForksCount) {
		this.avgForksCount = avgForksCount;
	}

	public double getAvgIssuesCount() {
		return avgIssuesCount;
	}

	public void setAvgIssuesCount(double avgIssuesCount) {
		this.avgIssuesCount = avgIssuesCount;
	}

	public double getAvgHandledIssuesRatio() {
		return avgHandledIssuesRatio;
	}

	public void setAvgHandledIssuesRatio(double avgHandledIssuesRatio) {
		this.avgHandledIssuesRatio = avgHandledIssuesRatio;
	}

	public double getAvgIssueHandledDays() {
		return avgIssueHandledDays;
	}

	public void setAvgIssueHandledDays(double avgIssueHandledDays) {
		this.avgIssueHandledDays = avgIssueHandledDays;
	}

	public double getAvgMajorLanguageLOC() {
		return avgMajorLanguageLOC;
	}

	public void setAvgMajorLanguageLOC(double avgMajorLanguageLOC) {
		this.avgMajorLanguageLOC = avgMajorLanguageLOC;
	}

	public double getAvgDebtRatio() {
		return avgDebtRatio;
	}

	public void setAvgDebtRatio(double avgDebtRatio) {
		this.avgDebtRatio = avgDebtRatio;
	}

	public double getAvgSqaleIndex() {
		return avgSqaleIndex;
	}

	public void setAvgSqaleIndex(double avgSqaleIndex) {
		this.avgSqaleIndex = avgSqaleIndex;
	}

	public int getNumOfPullRequest() {
		return numOfPullRequest;
	}

	public void setNumOfPullRequest(int numOfPullRequest) {
		this.numOfPullRequest = numOfPullRequest;
	}

	public int getNumOfAcceptedPR() {
		return numOfAcceptedPR;
	}

	public void setNumOfAcceptedPR(int numOfAcceptedPR) {
		this.numOfAcceptedPR = numOfAcceptedPR;
	}

	public int getNumOfContributedRepos() {
		return numOfContributedRepos;
	}

	public void setNumOfContributedRepos(int numOfContributedRepos) {
		this.numOfContributedRepos = numOfContributedRepos;
	}

	public double getAvgCommits() {
		return avgCommits;
	}

	public void setAvgCommits(double avgCommits) {
		this.avgCommits = avgCommits;
	}

	public double getAvgAdditions() {
		return avgAdditions;
	}

	public void setAvgAdditions(double avgAdditions) {
		this.avgAdditions = avgAdditions;
	}

	public double getAvgDeletions() {
		return avgDeletions;
	}

	public void setAvgDeletions(double avgDeletions) {
		this.avgDeletions = avgDeletions;
	}

	public double getAvgChangedFiles() {
		return avgChangedFiles;
	}

	public void setAvgChangedFiles(double avgChangedFiles) {
		this.avgChangedFiles = avgChangedFiles;
	}

	public double getAvgDaysIntervalOfPR() {
		return avgDaysIntervalOfPR;
	}

	public void setAvgDaysIntervalOfPR(double avgDaysIntervalOfPR) {
		this.avgDaysIntervalOfPR = avgDaysIntervalOfPR;
	}

	public double getAvgBugs() {
		return avgBugs;
	}

	public void setAvgBugs(double avgBugs) {
		this.avgBugs = avgBugs;
	}

	public double getAvgVulnerabilities() {
		return avgVulnerabilities;
	}

	public void setAvgVulnerabilities(double avgVulnerabilities) {
		this.avgVulnerabilities = avgVulnerabilities;
	}

	public double getAvgCodeSmells() {
		return avgCodeSmells;
	}

	public void setAvgCodeSmells(double avgCodeSmells) {
		this.avgCodeSmells = avgCodeSmells;
	}

	public double getAvgDuplicatedLineDensity() {
		return avgDuplicatedLineDensity;
	}

	public void setAvgDuplicatedLineDensity(double avgDuplicatedLineDensity) {
		this.avgDuplicatedLineDensity = avgDuplicatedLineDensity;
	}

	public double getAvgDuplicatedBlocks() {
		return avgDuplicatedBlocks;
	}

	public void setAvgDuplicatedBlocks(double avgDuplicatedBlocks) {
		this.avgDuplicatedBlocks = avgDuplicatedBlocks;
	}

	public double getAvgDuplicatedLines() {
		return avgDuplicatedLines;
	}

	public void setAvgDuplicatedLines(double avgDuplicatedLines) {
		this.avgDuplicatedLines = avgDuplicatedLines;
	}

	public double getAvgDuplicatedFiles() {
		return avgDuplicatedFiles;
	}

	public void setAvgDuplicatedFiles(double avgDuplicatedFiles) {
		this.avgDuplicatedFiles = avgDuplicatedFiles;
	}

	public double getAvgLines() {
		return avgLines;
	}

	public void setAvgLines(double avgLines) {
		this.avgLines = avgLines;
	}

	public double getAvgStatements() {
		return avgStatements;
	}

	public void setAvgStatements(double avgStatements) {
		this.avgStatements = avgStatements;
	}

	public double getAvgFunctions() {
		return avgFunctions;
	}

	public void setAvgFunctions(double avgFunctions) {
		this.avgFunctions = avgFunctions;
	}

	public double getAvgClasses() {
		return avgClasses;
	}

	public void setAvgClasses(double avgClasses) {
		this.avgClasses = avgClasses;
	}

	public double getAvgFiles() {
		return avgFiles;
	}

	public void setAvgFiles(double avgFiles) {
		this.avgFiles = avgFiles;
	}

	public double getAvgDirectories() {
		return avgDirectories;
	}

	public void setAvgDirectories(double avgDirectories) {
		this.avgDirectories = avgDirectories;
	}

	public double getAvgComplexity() {
		return avgComplexity;
	}

	public void setAvgComplexity(double avgComplexity) {
		this.avgComplexity = avgComplexity;
	}

	public double getAvgFileComplexity() {
		return avgFileComplexity;
	}

	public void setAvgFileComplexity(double avgFileComplexity) {
		this.avgFileComplexity = avgFileComplexity;
	}

	public double getAvgFunctionComplexity() {
		return avgFunctionComplexity;
	}

	public void setAvgFunctionComplexity(double avgFunctionComplexity) {
		this.avgFunctionComplexity = avgFunctionComplexity;
	}

	public double getAvgClassComplexity() {
		return avgClassComplexity;
	}

	public void setAvgClassComplexity(double avgClassComplexity) {
		this.avgClassComplexity = avgClassComplexity;
	}

	public double getAvgCommemtLineDensity() {
		return avgCommemtLineDensity;
	}

	public void setAvgCommemtLineDensity(double avgCommemtLineDensity) {
		this.avgCommemtLineDensity = avgCommemtLineDensity;
	}

	public double getAvgCommentLines() {
		return avgCommentLines;
	}

	public void setAvgCommentLines(double avgCommentLines) {
		this.avgCommentLines = avgCommentLines;
	}

	public double getAvgPublicAPI() {
		return avgPublicAPI;
	}

	public void setAvgPublicAPI(double avgPublicAPI) {
		this.avgPublicAPI = avgPublicAPI;
	}

	public double getAvgDocumentedAPIDensity() {
		return avgDocumentedAPIDensity;
	}

	public void setAvgDocumentedAPIDensity(double avgDocumentedAPIDensity) {
		this.avgDocumentedAPIDensity = avgDocumentedAPIDensity;
	}

	public double getAvgUndocumentedAPI() {
		return avgUndocumentedAPI;
	}

	public void setAvgUndocumentedAPI(double avgUndocumentedAPI) {
		this.avgUndocumentedAPI = avgUndocumentedAPI;
	}

	public long getContribution() {
		return contribution;
	}

	public void setContribution(long contribution) {
		this.contribution = contribution;
	}
}
