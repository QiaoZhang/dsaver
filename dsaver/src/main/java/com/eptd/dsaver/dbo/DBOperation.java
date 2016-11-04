package com.eptd.dsaver.dbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.eptd.dsaver.core.Client;
import com.eptd.dsaver.core.FilteredUser;
import com.eptd.dsaver.core.MajorRepository;
import com.eptd.dsaver.core.Repository;
import com.eptd.dsaver.core.Task;
import com.eptd.dsaver.core.User;

public class DBOperation {
	private Connection conn;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	static final String DB_URL = "jdbc:mariadb://localhost:3306/eptd";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "loveon1225";

	public DBOperation() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		this.conn = this.getConnection();
	}

	private Connection getConnection()
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class.forName(JDBC_DRIVER);
		// create database connection
		Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
		return con;
	}
	
	public ResultSet getMajorRepos() throws SQLException{
		String sql = "SELECT major_repo.id,repo.repo_name,major_repo.contributors,repo.debt_ratio FROM major_repo,repo WHERE major_repo.repo_id=repo.repo_id AND repo.debt_ratio IS NOT NULL";
		PreparedStatement ps = conn.prepareStatement(sql);
		return ps.executeQuery();
	}
	
	public ResultSet getRepoUsers(int repoID) throws SQLException {
		String sql = "SELECT * FROM repo_users,user WHERE repo_users.repo_id=? AND repo_users.user_id=user.user_id ORDER BY repo_users.contribution DESC";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, repoID);
		return ps.executeQuery();
	}
	
	public int insert(MajorRepository repo) throws Exception{
		String sql = "INSERT IGNORE INTO major_repo (id,repo_id,task_id,contributors) VALUES (?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);//id
		ps.setLong(2, repo.getProjectID());//repo_id
		ps.setInt(3, repo.getTaskID());//task_id
		ps.setInt(4, repo.getContributors().size());//contributors
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				Repository repoData = repo;
				if(insert(repoData)>0)
					return val;
				else
					return 0;
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Creating major repo failed, no rows affected during insert operation of major repo "+repo.getProjectID());
		}
	}

	public int insert(Repository repo) throws Exception {
		final String sql = "INSERT IGNORE INTO repo (id,repo_id,repo_url,repo_html,repo_name,owner_login,owner_type,major_language,version,size,stargazers,forks,issues,handled_issues,avg_days,created_date,bugs,vulnerabilities,code_smells,sqale_index,debt_ratio,dp_lines_density,dp_blocks,dp_lines,dp_files,loc,total_lines,statements,functions,classes,files,directories,complexity,file_complexity,function_complexity,class_complexity,comment_density,comment_lines,public_api,documented_api_density,undocumented_api) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);// id
		ps.setLong(2, repo.getProjectID());// repo_id
		ps.setString(3, repo.getRepositoryURL());// repo_url
		ps.setString(4, repo.getRepositoryHTML());// repo_html
		ps.setString(5, repo.getProjectName());// repo_name
		ps.setString(6, repo.getOwnerLogin());// owner_login
		ps.setString(7, repo.getUserType());// owner_type
		ps.setString(8, repo.getLanguage());// major_language
		ps.setString(9, repo.getVersion());// version
		ps.setLong(10, repo.getSize());// size
		ps.setLong(11, repo.getStargazersCount());// stargazers
		ps.setLong(12, repo.getForksCount());// forks
		ps.setLong(13, repo.getIssuesCount());// issues
		ps.setLong(14, repo.getHandledIssuesCount());// handled_issues
		ps.setDouble(15, repo.getAvgIssueHandledDays());// avg_days,
		ps.setTimestamp(16, new Timestamp(repo.getCreatedAt().getMillis()));// created_date
		if(repo.getSonarMetrics().size()>0){
			ps.setLong(17, repo.getSonarMetrics("bugs").longValue());//bugs
			ps.setLong(18, repo.getSonarMetrics("vulnerabilities").longValue());//vulnerabilities
			ps.setLong(19, repo.getSonarMetrics("code_smells").longValue());//code_smells
			ps.setLong(20, repo.getSonarMetrics("sqale_index").longValue());//sqale_index
			ps.setDouble(21, repo.getSonarMetrics("sqale_debt_ratio"));//debt_ratio
			ps.setDouble(22, repo.getSonarMetrics("duplicated_lines_density"));//dp_lines_density
			ps.setLong(23, repo.getSonarMetrics("duplicated_blocks").longValue());//dp_blocks
			ps.setLong(24, repo.getSonarMetrics("duplicated_lines").longValue());//dp_lines
			ps.setLong(25, repo.getSonarMetrics("duplicated_files").longValue());//dp_files
			ps.setLong(26, repo.getSonarMetrics("ncloc").longValue());//loc
			ps.setLong(27, repo.getSonarMetrics("lines").longValue());//total_lines
			ps.setLong(28, repo.getSonarMetrics("statements").longValue());//statements
			ps.setLong(29, repo.getSonarMetrics("functions").longValue());//functions
			ps.setLong(30, repo.getSonarMetrics("classes").longValue());//classes
			ps.setLong(31, repo.getSonarMetrics("files").longValue());//files
			ps.setLong(32, repo.getSonarMetrics("directories").longValue());//directories
			ps.setLong(33, repo.getSonarMetrics("complexity").longValue());//complexity
			ps.setDouble(34, repo.getSonarMetrics("file_complexity"));//file_complexity
			ps.setDouble(35, repo.getSonarMetrics("function_complexity"));//function_complexity
			ps.setDouble(36, repo.getSonarMetrics("class_complexity"));//class_complexity
			ps.setDouble(37, repo.getSonarMetrics("comment_lines_density"));//comment_density
			ps.setLong(38, repo.getSonarMetrics("comment_lines").longValue());//comment_lines
			ps.setLong(39, repo.getSonarMetrics("public_api").longValue());//public_api
			ps.setDouble(40, repo.getSonarMetrics("public_documented_api_density"));//documented_api_density
			ps.setLong(41, repo.getSonarMetrics("public_undocumented_api").longValue());//undocumented_api
		}else{
			ps.setNull(17, Types.INTEGER);//bugs
			ps.setNull(18, Types.INTEGER);//vulnerabilities
			ps.setNull(19, Types.INTEGER);//code_smells
			ps.setNull(20, Types.INTEGER);//sqale_index
			ps.setNull(21, Types.DOUBLE);//debt_ratio
			ps.setNull(22, Types.DOUBLE);//dp_lines_density
			ps.setNull(23, Types.INTEGER);//dp_blocks
			ps.setNull(24, Types.INTEGER);//dp_lines
			ps.setNull(25, Types.INTEGER);//dp_files
			ps.setNull(26, Types.INTEGER);//loc
			ps.setNull(27, Types.INTEGER);//total_lines
			ps.setNull(28, Types.INTEGER);//statements
			ps.setNull(29, Types.INTEGER);//functions
			ps.setNull(30, Types.INTEGER);//classes
			ps.setNull(31, Types.INTEGER);//files
			ps.setNull(32, Types.INTEGER);//directories
			ps.setNull(33, Types.INTEGER);//complexity
			ps.setNull(34, Types.DOUBLE);//file_complexity
			ps.setNull(35, Types.DOUBLE);//function_complexity
			ps.setNull(36, Types.DOUBLE);//class_complexity
			ps.setNull(37, Types.DOUBLE);//comment_density
			ps.setNull(38, Types.INTEGER);//comment_lines
			ps.setNull(39, Types.INTEGER);//public_api
			ps.setNull(40, Types.DOUBLE);//documented_api_density
			ps.setNull(41, Types.INTEGER);//undocumented_api
		}
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Creating repo failed, no rows affected during insert operation of repo "+repo.getProjectID());
		}
	}
	
	public int insert(FilteredUser user) throws Exception {
		String sql = "INSERT IGNORE INTO filtereduser (id,user_id,user_login,user_url,user_html) VALUES (?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);
		ps.setLong(2, user.getUserId());
		ps.setString(3, user.getLogin());
		ps.setString(4, user.getUserURL());
		ps.setString(5, user.getUserHTML());
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Creating filtered user failed, no rows affected during insert operation of user "+user.getUserId());
		}
	}

	public int insert(User user) throws Exception {
		final String sql = "INSERT IGNORE INTO user (id,user_id,user_url,user_html,user_login,user_name,public_repos,followers,assignees,analyzed_repos,avg_size,avg_stargazers,avg_subscribers,avg_forks,avg_issues,avg_issue_ratio,avg_issue_days,pull_requests,accepted_pr,contrib_repos,avg_commits,avg_addition,avg_deletion,avg_changed_files,avg_days_interval,avg_bugs,avg_vulnerabilities,avg_code_smells,avg_sqale_index,avg_debt_ratio,avg_dp_lines_density,avg_dp_blocks,avg_dp_lines,avg_dp_files,avg_loc,avg_lines,avg_statements,avg_functions,avg_classes,avg_files,avg_directories,avg_complexity,avg_file_cp,avg_function_cp,avg_class_cp,avg_comment_density,avg_comment_lines,avg_public_api,avg_d_api_density,avg_ud_api) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);// id
		ps.setLong(2, user.getUserId());// user_id
		ps.setString(3, user.getUserURL());// user_url
		ps.setString(4, user.getUserHTML());//user_html
		ps.setString(5, user.getLogin());// user_login
		ps.setString(6, user.getName());// user_name
		ps.setInt(7, user.getNumOfPublicRepos());// public_repos
		ps.setLong(8, user.getFollowers());// followers
		ps.setLong(9, user.getNumOfAssignees());// assignees
		ps.setInt(10, user.getNumOfAnalyzedRepos());// analyzed_repos
		ps.setDouble(11, user.getAvgSize());// avg_size
		ps.setDouble(12, user.getAvgStargazersCount());// avg_stargazers
		ps.setDouble(13, user.getAvgSubscribersCount());// avg_subscribers
		ps.setDouble(14, user.getAvgForksCount());// avg_forks
		ps.setDouble(15, user.getAvgIssuesCount());// avg_issues
		ps.setDouble(16, user.getAvgHandledIssuesRatio());// avg_issue_ratio
		ps.setDouble(17, user.getAvgIssueHandledDays());// avg_issue_days
		ps.setDouble(18, user.getNumOfPullRequest());// pull_requests
		ps.setDouble(19, user.getNumOfAcceptedPR());// accepted_pr
		ps.setDouble(20, user.getNumOfContributedRepos());// contrib_repos
		ps.setDouble(21, user.getAvgCommits());// avg_commits
		ps.setDouble(22, user.getAvgAdditions());// avg_addition
		ps.setDouble(23, user.getAvgDeletions());// avg_deletion
		ps.setDouble(24, user.getAvgChangedFiles());// avg_changed_files
		ps.setDouble(25, user.getAvgDaysIntervalOfPR());// avg_days_interval
		ps.setDouble(26, user.getAvgBugs());// avg_bugs
		ps.setDouble(27, user.getAvgVulnerabilities());// avg_vulnerabilities
		ps.setDouble(28, user.getAvgCodeSmells());// avg_code_smells
		ps.setDouble(29, user.getAvgSqaleIndex());// avg_sqale_index
		ps.setDouble(30, user.getAvgDebtRatio());// avg_debt_ratio
		ps.setDouble(31, user.getAvgDuplicatedLineDensity());// avg_dp_lines_density
		ps.setDouble(32, user.getAvgDuplicatedBlocks());// avg_dp_blocks
		ps.setDouble(33, user.getAvgDuplicatedLines());// avg_dp_lines
		ps.setDouble(34, user.getAvgDuplicatedFiles());// avg_dp_files
		ps.setDouble(35, user.getAvgMajorLanguageLOC());// avg_loc
		ps.setDouble(36, user.getAvgLines());// avg_lines
		ps.setDouble(37, user.getAvgStatements());// avg_statements
		ps.setDouble(38, user.getAvgFunctions());// avg_functions
		ps.setDouble(39, user.getAvgClasses());// avg_classes
		ps.setDouble(40, user.getAvgFiles());// avg_files
		ps.setDouble(41, user.getAvgDirectories());// avg_directories
		ps.setDouble(42, user.getAvgComplexity());// avg_complexity
		ps.setDouble(43, user.getAvgFileComplexity());// avg_file_cp
		ps.setDouble(44, user.getAvgFunctionComplexity());// avg_function_cp
		ps.setDouble(45, user.getAvgClassComplexity());// avg_class_cp
		ps.setDouble(46, user.getAvgCommemtLineDensity());// avg_comment_density
		ps.setDouble(47, user.getAvgCommentLines());// avg_comment_lines
		ps.setDouble(48, user.getAvgPublicAPI());// avg_public_api
		ps.setDouble(49, user.getAvgDocumentedAPIDensity());// avg_d_api_density
		ps.setDouble(50, user.getAvgUndocumentedAPI());// avg_ud_api		
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Creating user failed, no rows affected during insert operation of user "+user.getUserId());
		}
	}

	public int insert(Client client) throws Exception{
		final String sql = "INSERT INTO clients (client_id,finger_print,username,password,app_id,app_secret,last_update) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, client.getClientID());//client_id
		ps.setString(2, client.getFingerPrint());//finger_print
		ps.setString(3, client.getUsername());//username
		ps.setString(4, client.getPassword());//password
		ps.setString(5, client.getAppClientID());//app_id
		ps.setString(6, client.getAppClientSecret());//app_secret
		ps.setTimestamp(7, new Timestamp(client.getLastUpdate().getMillis()));//last_update
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Creating client failed, no rows affected during insert operation of client "+client.getAppClientID());
		}
	}
	
	public int insert(Task task) throws Exception{
		final String sql = "INSERT INTO tasks (task_id,client_id,state,total,success,failed,language,user,size,stars,forks,created) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);//task_id - auto-increment
		//client_id - default as 0
		if(task.getClientID()!=0)
			ps.setInt(2, task.getClientID());
		else
			throw new SQLException("No Client ID provided.");
		ps.setString(3, "assigned");//state - default as assigned
		//total - default as 0
		if(task.getTotal()!=0)
			ps.setInt(4, task.getTotal());
		else
			throw new SQLException("No total task count provided.");
		ps.setInt(5, 0);//success - default as 0
		ps.setInt(6, 0);//failed - default as 0
		//language - default as NULL
		if(task.getParaLanguage()!=null)
			ps.setString(7, task.getParaLanguage());
		else
			ps.setNull(7, Types.VARCHAR);
		//user
		if(task.getParaUser()!=null)
			ps.setString(8, task.getParaUser());
		else
			ps.setNull(8, Types.VARCHAR);		
		//size
		if(task.getParaSize()!=null)
			ps.setString(9, task.getParaSize());
		else
			ps.setNull(9, Types.VARCHAR);
		//stars
		if(task.getParaStars()!=null)
			ps.setString(10, task.getParaStars());
		else
			ps.setNull(10, Types.VARCHAR);
		//forks - default as NULL
		if(task.getParaForks()!=null)
			ps.setString(11, task.getParaForks());
		else
			ps.setNull(11, Types.VARCHAR);
		//created - default as Task Object created
		ps.setTimestamp(12, new Timestamp(task.getCreated().getMillis()));
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Creating task failed, no rows affected during insert operation of task "+task.getTaskID());
		}
	}
	
	public int connect(long repo_id,FilteredUser user) throws Exception{
		final String sql = "INSERT INTO repo_filterdusers (id,repo_id,user_id,contribution) VALUES (?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);//id
		ps.setLong(2, repo_id);//repo_id
		ps.setLong(3, user.getUserId());//user_id
		ps.setLong(4, user.getContribution());//contribution
		if (ps.executeUpdate() == 0){
			ps.close();//close statement to release resource
			throw new SQLException("Creating connection between major repo "+repo_id+" and filtered user "+user.getUserId()+" failed, no rows affected.");
		}else {
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}
	}
	
	public int connect(long repo_id,long user_id,long contribution) throws Exception{
		final String sql = "INSERT INTO repo_users (id,repo_id,user_id,contribution) VALUES (?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);//id
		ps.setLong(2, repo_id);//repo_id
		ps.setLong(3, user_id);//user_id
		ps.setLong(4, contribution);//contribution
		if (ps.executeUpdate() == 0){
			ps.close();//close statement to release resource
			throw new SQLException("Creating connection between major repo "+repo_id+" and user "+user_id+" failed, no rows affected.");
		}else {
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}
	}
	
	public int connect(long user_id,long repo_id) throws Exception{
		final String sql = "INSERT INTO user_repos (id,user_id,repo_id) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);//id
		ps.setLong(2, user_id);//repo_id
		ps.setLong(3, repo_id);//user_id
		if (ps.executeUpdate() == 0){
			ps.close();//close statement to release resource
			throw new SQLException("Creating connection between user "+user_id+" and repo "+repo_id+" failed, no rows affected.");
		}else {
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}
	}
	
	public ArrayList<Client> getClientInfo() throws Exception{
		ArrayList<Client> clients = new ArrayList<Client>();
		final String clientSQL = "SELECT * FROM clients";
		PreparedStatement clientPS = conn.prepareStatement(clientSQL);
		ResultSet clientRS = clientPS.executeQuery();
		while(clientRS.next()){
			clients.add(extractClientInfo(clientRS));
		}
		return clients;
	}

	public Client getClientInfo(String fingerPrint) throws Exception{
		final String clientSQL = "SELECT * FROM clients WHERE finger_print = ?";
		PreparedStatement clientPS = conn.prepareStatement(clientSQL);
		clientPS.setString(1, fingerPrint);
		ResultSet clientRS = clientPS.executeQuery();
		if(clientRS.next())
			return extractClientInfo(clientRS);
		return null;
	}
	
	public List<Long> getFinishedRepos(int taskID) throws Exception{
		final String sql = "SELECT repo_id FROM major_repo WHERE task_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, taskID);//task_id
		ResultSet rs = ps.executeQuery();
		//save to List<Long>
		List<Long> finishedRepos = new ArrayList<Long>();
		while(rs.next()){
			finishedRepos.add(rs.getLong("repo_id"));
		}
		return finishedRepos;
	}
	
	public int updateClient(int clientID) throws Exception{
		try {
			final String sql = "UPDATE clients SET last_update = ? WHERE client_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, new Timestamp(new DateTime().getMillis()));
			ps.setInt(2, clientID);//task_id
			if (ps.executeUpdate() >= 0){
				ResultSet rs = ps.getGeneratedKeys();
				ps.close();//close statement to release resource
				if(rs.next()){
					int val = rs.getInt(1);
					rs.close();
					return val;
				}
				return 0;
			}else{
				ps.close();//close statement to release resource
				throw new SQLException("Updating client failed, no rows affected during update operation of client "+clientID);
			}
		} catch (Exception e){
			return 0;
		}		
	}
	
	public int updateClient(MajorRepository repo) throws Exception{
		try {
			final String sql = "UPDATE clients SET last_update = ? WHERE client_id = (SELECT client_id FROM tasks WHERE task_id = ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, new Timestamp(new DateTime().getMillis()));
			ps.setInt(2, repo.getTaskID());//task_id
			if (ps.executeUpdate() >= 0){
				ResultSet rs = ps.getGeneratedKeys();
				ps.close();//close statement to release resource
				if(rs.next()){
					int val = rs.getInt(1);
					rs.close();
					return val;
				}
				return 0;
			}else{
				ps.close();//close statement to release resource
				throw new SQLException("Updating client failed, no rows affected during update operation of client who has task "+repo.getTaskID());
			}
		} catch (Exception e){
			return 0;
		}	
	}
	
	/**
	 * Increase the success of a task by 1 if a major repo data set has been successfully recorded
	 * @param taskID to identify which task
	 * @return 0 if the operation failed; others if success
	 * @throws Exception including NullPointerException and SQLException
	 */
	public int updateTask(int taskID) throws Exception{
		try {
			final String sql = "UPDATE tasks SET success = success + 1 WHERE task_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, taskID);//task_id
			if (ps.executeUpdate() >= 0){
				ResultSet rs = ps.getGeneratedKeys();
				ps.close();//close statement to release resource
				if(rs.next()){
					int val = rs.getInt(1);
					rs.close();
					return val;
				}
				return 0;
			}else{
				ps.close();//close statement to release resource
				throw new SQLException("Updating task failed, no rows affected during update operation of task "+taskID);
			}
		} catch (Exception e){
			return 0;
		}		
	}
	
	/**
	 * Update task failed based on provided taskID
	 * @param taskID to identify which task
	 * @param failed the number of failed or skipped major repos
	 * @return 0 if the operation failed; others if success
	 * @throws Exception including NullPointerException and SQLException
	 */
	public int updateTask(int taskID, int failed) throws Exception{
		final String sql = "UPDATE tasks SET failed = ? WHERE task_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, failed);//failed
		ps.setInt(2, taskID);//task_id
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Updating task failed, no rows affected during update operation of task "+taskID);
		}
	}
	
	/**
	 * Update task state based on provided taskID
	 * @param taskID to identify which task
	 * @param state to determine the new state
	 * @return 0 if the operation failed; others if success
	 * @throws Exception including NullPointerException and SQLException
	 */
	public int updateTask(int taskID, String state) throws Exception{
		final String sql = "UPDATE tasks SET state = ? WHERE task_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, state);//failed
		ps.setInt(2, taskID);//task_id
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Updating task failed, no rows affected during update operation of task "+taskID);
		}
	}
	
	/**
	 * Update relevant task during a data saving operation from client
	 * @param client to provide client's fingerPrint
	 * @param state to determine the new state of task which is 'open'
	 * @param failed the number of failed or skipped major repos
	 * @return 0 if the operation failed; others if success
	 * @throws Exception including NullPointerException and SQLException
	 */
	public int updateTask(Client client, String state, int failed) throws Exception{
		final String sql = "UPDATE tasks SET state = ?,failed=? WHERE state = 'open' AND client_id = (SELECT client_id FROM clients WHERE finger_print = ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, state);//failed
		ps.setInt(2, failed);
		ps.setString(3, client.getFingerPrint());//task_id
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				int val = rs.getInt(1);
				rs.close();
				return val;
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Updating task failed, no rows affected during update operation of the 'open' state task of client "+client.getFingerPrint());
		}
	}
	
	/**
	 * Extract client and its tasks information based on passed client info result set
	 * @param clientRS result set where the current pointer point to a client tuple
	 * @return Client Class instance which contains all client and its tasks information
	 * @throws Exception including NullPointerException and SQLException
	 */
	private Client extractClientInfo(ResultSet clientRS) throws Exception{
		Client client = new Client()
			.setClientID(clientRS.getInt("client_id"))
			.setFingerPrint(clientRS.getString("finger_print"))
			.setUsername(clientRS.getString("username"))
			.setPassword(clientRS.getString("password"))
			.setAppClientID(clientRS.getString("app_id"))
			.setAppClientSecret(clientRS.getString("app_secret"))		
			.setLastUpdate(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSSSS").parseDateTime(clientRS.getString("last_update")));	
		final String taskSQL = "SELECT * FROM tasks WHERE tasks.client_id = ?";
		PreparedStatement taskPS = conn.prepareStatement(taskSQL);
		taskPS.setInt(1, client.getClientID());
		ResultSet taskRS = taskPS.executeQuery();
		while(taskRS.next()){
			client.addTask(new Task()
				.setTaskID(taskRS.getInt("task_id"))
				.setClientID(taskRS.getInt("client_id"))
				.setState(isStateError(taskRS.getString("state"),client.getLastUpdate()))
				.setTotal(taskRS.getInt("total"))
				.setSuccess(taskRS.getInt("success"))
				.setFailed(taskRS.getInt("failed"))
				.setParaLanguage(taskRS.getString("language"))
				.setParaUser(taskRS.getString("user"))
				.setParaSize(taskRS.getString("size"))
				.setParaStars(taskRS.getString("stars"))
				.setParaForks(taskRS.getString("forks"))
				.setCreated(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSSSS").parseDateTime(taskRS.getString("created"))));	
		}
		return client;
	}
	
	private String isStateError(String state,DateTime lastUpdate){
		//calculate date difference between lastUpdate and new DateTime()
		long difference = ((new DateTime().getMillis() - lastUpdate.getMillis())/1000)/60;//unit in minutes
		//if state is open, return error
		if(difference > 30 && state.equals("open"))
			return "error";
		return state;
	}
	
	protected void finalize() throws Exception{
		this.conn.close();
	}
}
