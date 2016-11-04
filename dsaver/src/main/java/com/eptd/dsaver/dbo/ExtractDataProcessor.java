package com.eptd.dsaver.dbo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonObject;

public class ExtractDataProcessor {
	private static final String SEPERATOR = ",";
	
	
	public static String writeLine(List<String> cells){
		StringBuilder line = new StringBuilder();
		for(String s : cells){
			line.append(s);
			line.append(SEPERATOR);
		}
		line.replace(line.length()-1, line.length(),"\n");
		return line.toString();
	}
	
	public JsonObject process(){
		try {
			StringBuffer sb = new StringBuffer();
			DBOperation dbo = new DBOperation();
			ResultSet rs = dbo.getMajorRepos();
			sb.append("id,repo_id,repo_name,contributors,debt_ratio");
			for(int i=0;i<30;i++)
				sb.append(",contribution,public_repos,followers,assignees,analyzed_repos,avg_size,avg_stargazers,avg_subscribers,avg_forks,avg_issues,avg_issue_ratio,avg_issue_days,pull_requests,accepted_pr,contrib_repos,avg_commits,avg_addition,avg_deletion,avg_changed_files,avg_days_interval,avg_bugs,avg_vulnerabilities,avg_code_smells,avg_sqale_index,avg_debt_ratio,avg_dp_lines_density,avg_dp_blocks,avg_dp_lines,avg_dp_files,avg_loc,avg_lines,avg_statements,avg_functions,avg_classes,avg_files,avg_directories,avg_complexity,avg_file_cp,avg_function_cp,avg_class_cp,avg_comment_density,avg_comment_lines,avg_public_api,avg_d_api_density,avg_ud_api");
			sb.append("\n");
			while(rs.next()){
				List<String> values = new ArrayList<String>();
				values.addAll(Arrays.asList(
					rs.getString("id"),
					rs.getString("repo_id"),
					rs.getString("repo_name"),
					rs.getString("contributors"),
					rs.getString("debt_ratio")
				));
				//users
				int usersCount = 0;
				ResultSet usersRS = dbo.getRepoUsers(rs.getInt("repo_id"));
				while(usersRS.next()){
					usersCount++;
					values.addAll(Arrays.asList(
						rs.getString("contribution"),
						rs.getString("public_repos"),
						rs.getString("followers"),
						rs.getString("assignees"),
						rs.getString("analyzed_repos"),
						rs.getString("avg_size"),
						rs.getString("avg_stargazers"),
						rs.getString("avg_subscribers"),
						rs.getString("avg_forks"),
						rs.getString("avg_issues"),
						rs.getString("avg_issue_ratio"),
						rs.getString("avg_issue_days"),
						rs.getString("pull_requests"),
						rs.getString("accepted_pr"),
						rs.getString("contrib_repos"),
						rs.getString("avg_commits"),
						rs.getString("avg_addition"),
						rs.getString("avg_deletion"),
						rs.getString("avg_changed_files"),
						rs.getString("avg_days_interval"),
						rs.getString("avg_bugs"),
						rs.getString("avg_vulnerabilities"),
						rs.getString("avg_code_smells"),
						rs.getString("avg_sqale_index"),
						rs.getString("avg_debt_ratio"),
						rs.getString("avg_dp_lines_density"),
						rs.getString("avg_dp_blocks"),
						rs.getString("avg_dp_lines"),
						rs.getString("avg_dp_files"),
						rs.getString("avg_loc"),
						rs.getString("avg_lines"),
						rs.getString("avg_statements"),
						rs.getString("avg_functions"),
						rs.getString("avg_classes"),
						rs.getString("avg_files"),
						rs.getString("avg_directories"),
						rs.getString("avg_complexity"),
						rs.getString("avg_file_cp"),
						rs.getString("avg_function_cp"),
						rs.getString("avg_class_cp"),
						rs.getString("avg_comment_density"),
						rs.getString("avg_comment_lines"),
						rs.getString("avg_public_api"),
						rs.getString("avg_d_api_density"),
						rs.getString("avg_ud_api")
					));
				}
				//fill the blank
				for(int i=0;i<(30-usersCount)*45;i++)
					values.add("0");
				sb.append(writeLine(values));
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonObject();
	}
}
