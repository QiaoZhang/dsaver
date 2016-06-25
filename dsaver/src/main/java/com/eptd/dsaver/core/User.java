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
	private double avgMajorLanguageLOC = 0.0;
	private double avgSqaleRating = 0.0;
	private double avgDebtRatio = 0.0;
	private double avgSqaleIndex = 0.0;
	
	
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

	public double getAvgSqaleRating() {
		return avgSqaleRating;
	}

	public void setAvgSqaleRating(double avgSqaleRating) {
		this.avgSqaleRating = avgSqaleRating;
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
}
