package com.eptd.dsaver.dbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import org.joda.time.format.DateTimeFormat;

import com.eptd.dsaver.core.Client;
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

	/**
	 * Method of connecting database
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private Connection getConnection()
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class.forName(JDBC_DRIVER);
		// create database connection
		Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
		return con;
	}

	public int insert(Repository repo)
			throws SQLException {
		final String sql = "INSERT IGNORE INTO repo (id,repo_id,repo_url,repo_html,repo_name,owner_login,owner_type,major_language,version,size,stargazers,forks,issues,handled_issues,avg_days,created_date,loc,sqale_index,debt_ratio) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			ps.setLong(17, repo.getSonarMetrics().get(0).getValue().longValue());//loc
			ps.setLong(18, repo.getSonarMetrics().get(1).getValue().longValue());//sqale_index
			ps.setDouble(19, repo.getSonarMetrics().get(2).getValue());//debt_ratio
		}else{
			ps.setNull(17, Types.INTEGER);
			ps.setNull(18, Types.INTEGER);
			ps.setNull(19, Types.DOUBLE);
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
			throw new SQLException("Creating user failed, no rows affected during insert operation of repo "+repo.getProjectID());
		}
	}

	public int insert(User user) throws SQLException {
		final String sql = "INSERT IGNORE INTO user (id,user_id,user_html,user_login,user_name,public_repos,followers,assignees,analyzed_repos,avg_size,avg_stargazers,avg_subscribers,avg_forks,avg_issues,avg_issue_ratio,avg_issue_days,avg_loc,avg_sqale_index,avg_debt_ratio,pull_requests,accepted_pr,contrib_repos,avg_commits,avg_addition,avg_deletion,avg_changed_files,avg_days_interval) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);// id
		ps.setLong(2, user.getUserId());// user_id
		ps.setString(3, user.getUserURL());// user_html
		ps.setString(4, user.getLogin());// user_login
		ps.setString(5, user.getName());// user_name
		ps.setInt(6, user.getNumOfPublicRepos());// public_repos
		ps.setLong(7, user.getFollowers());// followers
		ps.setLong(8, user.getNumOfAssignees());// assignees
		ps.setInt(9, user.getNumOfAnalyzedRepos());// analyzed_repos
		ps.setDouble(10, user.getAvgSize());// avg_size
		ps.setDouble(11, user.getAvgStargazersCount());// avg_stargazers
		ps.setDouble(12, user.getAvgSubscribersCount());// avg_subscribers
		ps.setDouble(13, user.getAvgForksCount());// avg_forks
		ps.setDouble(14, user.getAvgIssuesCount());// avg_issues
		ps.setDouble(15, user.getAvgHandledIssuesRatio());// avg_issue_ratio
		ps.setDouble(16, user.getAvgIssueHandledDays());// avg_issue_days
		ps.setDouble(17, user.getAvgMajorLanguageLOC());// avg_loc
		ps.setDouble(18, user.getAvgSqaleIndex());// avg_sqale_index
		ps.setDouble(19, user.getAvgDebtRatio());// avg_debt_ratio
		ps.setDouble(20, user.getNumOfPullRequest());// pull_requests
		ps.setDouble(21, user.getNumOfAcceptedPR());// accepted_pr
		ps.setDouble(22, user.getNumOfContributedRepos());// contrib_repos
		ps.setDouble(23, user.getAvgCommits());// avg_commits
		ps.setDouble(24, user.getAvgAdditions());// avg_addition
		ps.setDouble(25, user.getAvgDeletions());// avg_deletion
		ps.setDouble(26, user.getAvgChangedFiles());// avg_changed_files
		ps.setDouble(27, user.getAvgDaysIntervalOfPR());// avg_days_interval
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
	
	public int insert(MajorRepository repo) throws SQLException{
		String sql = "INSERT IGNORE INTO major_repo (id,repo_id,contributors) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);//id
		ps.setLong(2, repo.getProjectID());//repo_id
		ps.setInt(3, repo.getContributors().size());//contributors
		if (ps.executeUpdate() >= 0){
			ResultSet rs = ps.getGeneratedKeys();
			ps.close();//close statement to release resource
			if(rs.next()){
				rs.close();
				sql = "INSERT IGNORE INTO repo (id,repo_id,repo_url,repo_html,repo_name,owner_login,owner_type,major_language,version,size,stargazers,forks,issues,handled_issues,avg_days,created_date,loc,sqale_index,debt_ratio) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
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
					ps.setLong(17, repo.getSonarMetrics().get(0).getValue().longValue());//loc
					ps.setLong(18, repo.getSonarMetrics().get(1).getValue().longValue());//sqale_index
					ps.setDouble(19, repo.getSonarMetrics().get(2).getValue());//debt_ratio
				}else{
					ps.setNull(17, Types.INTEGER);
					ps.setNull(18, Types.INTEGER);
					ps.setNull(19, Types.DOUBLE);
				}
				if (ps.executeUpdate() >= 0){
					ResultSet rs_repo = ps.getGeneratedKeys();
					ps.close();//close statement to release resource
					if(rs_repo.next()){
						int val = rs_repo.getInt(1);
						rs_repo.close();
						return val;
					}
					return 0;
				}else{
					ps.close();//close statement to release resource
					throw new SQLException("Creating user failed, no rows affected during insert operation of repo "+repo.getProjectID());
				}
			}
			return 0;
		}else{
			ps.close();//close statement to release resource
			throw new SQLException("Creating user failed, no rows affected during insert operation of major repo "+repo.getProjectID());
		}
	}
	
	public int connect(long repo_id,long user_id,boolean isMajorRepo) throws SQLException{
		if(!isMajorRepo)
			return connect(user_id,repo_id);
		final String sql = "INSERT INTO repo_users (id,repo_id,user_id) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setNull(1, Types.INTEGER);//id
		ps.setLong(2, repo_id);//repo_id
		ps.setLong(3, user_id);//user_id
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
	
	public int connect(long user_id,long repo_id) throws SQLException{
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
	
	public int insert(Client client) throws SQLException{
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
	
	public int insert(Task task) throws SQLException{
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
	
	public ArrayList<Client> getClientInfo() throws SQLException{
		ArrayList<Client> clients = new ArrayList<Client>();
		final String clientSQL = "SELECT * FROM clients";
		PreparedStatement clientPS = conn.prepareStatement(clientSQL);
		ResultSet clientRS = clientPS.executeQuery();
		while(clientRS.next()){
			clients.add(extractClientInfo(clientRS));
		}
		return clients;
	}

	public Client getClientInfo(String fingerPrint) throws SQLException{
		final String clientSQL = "SELECT * FROM clients WHERE finger_print = ?";
		PreparedStatement clientPS = conn.prepareStatement(clientSQL);
		clientPS.setString(1, fingerPrint);
		ResultSet clientRS = clientPS.executeQuery();
		if(clientRS.next())
			return extractClientInfo(clientRS);
		return null;
	}
	
	public int updateTask(int taskID, boolean success) throws SQLException{
		final String sql;
		if(success)
			sql = "UPDATE tasks SET success = success + 1 WHERE task_id = ?";
		else
			sql = "UPDATE tasks SET failed = failed + 1 WHERE task_id = ?";
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
	}
	
	private Client extractClientInfo(ResultSet clientRS) throws SQLException{
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
				.setState(taskRS.getString("state"))
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
	
	protected void finalize() throws SQLException{
		this.conn.close();
	}
}
