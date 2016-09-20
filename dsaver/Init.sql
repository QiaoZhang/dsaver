SET SESSION FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS major_repo;
DROP TABLE IF EXISTS repo;
DROP TABLE IF EXISTS repo_users;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_repos;

/* Create Tables */

CREATE TABLE clients
(
	-- auto increment
	client_id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
	-- client identification
	finger_print varchar(50) DEFAULT 'Unknown' COMMENT 'client identification',
	-- github login
	username varchar(100) DEFAULT 'Unknown' COMMENT 'github login',
	-- github password
	password varchar(100) DEFAULT 'Unknown' COMMENT 'github password',
	-- app client id
	app_id varchar(100) DEFAULT 'Unknown' COMMENT 'app client id',
	-- app client secret
	app_secret varchar(200) DEFAULT 'Unknown' COMMENT 'app client secret',
	-- latest communication
	last_update timestamp DEFAULT '1970-01-01 00:00:01.000000' COMMENT 'latest communication',
	PRIMARY KEY (client_id),
	UNIQUE (finger_print)
);


CREATE TABLE major_repo
(
	id int unsigned zerofill NOT NULL AUTO_INCREMENT,
	repo_id int unsigned DEFAULT 0 NOT NULL,
	-- task id
	task_id int unsigned zerofill COMMENT 'task id',
	-- number of contributors
	contributors int unsigned DEFAULT 0 COMMENT 'number of contributors',
	PRIMARY KEY (id),
	UNIQUE (repo_id)
);


CREATE TABLE repo
(
	id int unsigned zerofill NOT NULL AUTO_INCREMENT,
	repo_id int unsigned DEFAULT 0,
	repo_url varchar(500) DEFAULT 'Unkown',
	repo_html varchar(500) DEFAULT 'Unknown',
	repo_name varchar(500) DEFAULT 'Unknown',
	owner_login varchar(100) DEFAULT 'Unknown',
	owner_type char(20) DEFAULT 'User',
	major_language varchar(50) DEFAULT 'Unknown',
	version varchar(200) DEFAULT '1.0.0',
	size int unsigned DEFAULT 0,
	stargazers int unsigned DEFAULT 0,
	forks int unsigned DEFAULT 0,
	issues int unsigned DEFAULT 0,
	handled_issues int unsigned DEFAULT 0,
	avg_days double unsigned DEFAULT 0.0,
	created_date timestamp DEFAULT '1970-01-01 00:00:01.000000',
	bugs int unsigned,
	vulnerabilities int unsigned,
	code_smells int unsigned,
	-- td amount
	sqale_index int unsigned COMMENT 'td amount',
	-- index/(loc*30)
	debt_ratio double unsigned COMMENT 'index/(loc*30)',
	dp_lines_density double unsigned,
	dp_blocks int unsigned,
	dp_lines int unsigned,
	dp_files int unsigned,
	loc int unsigned,
	total_lines int unsigned,
	statements int unsigned,
	functions int unsigned,
	classes int unsigned,
	files int unsigned,
	directories int unsigned,
	complexity int unsigned,
	file_complexity double unsigned,
	function_complexity double unsigned,
	class_complexity double unsigned,
	comment_density double unsigned,
	comment_lines int unsigned,
	public_api int unsigned,
	documented_api_density double unsigned,
	undocumented_api int unsigned,
	PRIMARY KEY (id),
	UNIQUE (repo_id)
);


CREATE TABLE repo_users
(
	-- record id
	id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'record id',
	repo_id int unsigned,
	user_id int unsigned,
	contribution int unsigned,
	PRIMARY KEY (id)
);


CREATE TABLE tasks
(
	-- task id
	task_id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'task id',
	-- auto increment
	client_id int unsigned zerofill DEFAULT 0 COMMENT 'auto increment',
	-- assigned, open, close, error
	state varchar(50) DEFAULT 'assigned' COMMENT 'assigned, open, close, error',
	-- total major repos counts
	total int unsigned DEFAULT 0 COMMENT 'total major repos counts',
	-- success major repo recorded
	success int unsigned DEFAULT 0 COMMENT 'success major repo recorded',
	-- failed major repo process
	failed int unsigned DEFAULT 0 COMMENT 'failed major repo process',
	-- parameter - language
	language varchar(50) COMMENT 'parameter - language',
	-- parameter - login
	user varchar(200) COMMENT 'parameter - login',
	-- parameter - size
	size varchar(50) COMMENT 'parameter - size',
	-- parameter - stars
	stars varchar(50) BINARY COMMENT 'parameter - stars',
	-- parameter - forks
	forks varchar(50) COMMENT 'parameter - forks',
	-- parameter - stars
	created varchar(50) COMMENT 'parameter - stars',
	PRIMARY KEY (task_id)
);


CREATE TABLE user
(
	id int unsigned zerofill NOT NULL AUTO_INCREMENT,
	user_id int unsigned DEFAULT 0,
	user_url varchar(500) DEFAULT 'Unknown',
	user_html varchar(500) DEFAULT 'Unknown',
	user_login varchar(200) DEFAULT 'Unknown',
	user_name varchar(500) DEFAULT 'Unknown',
	public_repos int unsigned DEFAULT 0,
	followers int unsigned DEFAULT 0,
	assignees int unsigned DEFAULT 0,
	analyzed_repos int unsigned DEFAULT 0,
	avg_size double unsigned DEFAULT 0.0,
	avg_stargazers double unsigned DEFAULT 0.0,
	avg_subscribers double unsigned DEFAULT 0.0,
	avg_forks double unsigned DEFAULT 0.0,
	avg_issues double unsigned DEFAULT 0.0,
	avg_issue_ratio double unsigned DEFAULT 0.0,
	avg_issue_days double unsigned DEFAULT 0.0,
	pull_requests int unsigned DEFAULT 0,
	accepted_pr int unsigned DEFAULT 0,
	contrib_repos int unsigned DEFAULT 0,
	avg_commits double unsigned DEFAULT 0.0,
	avg_addition double unsigned DEFAULT 0.0,
	avg_deletion double unsigned DEFAULT 0.0,
	avg_changed_files double unsigned DEFAULT 0.0,
	avg_days_interval double unsigned DEFAULT 0.0,
	avg_bugs double unsigned,
	avg_vulnerabilities double unsigned,
	avg_code_smells double unsigned,
	-- avg sqale index of AR
	avg_sqale_index double unsigned DEFAULT 0.0 COMMENT 'avg sqale index of AR',
	-- avg td ratio of AR
	avg_debt_ratio double unsigned DEFAULT 0.0 COMMENT 'avg td ratio of AR',
	avg_dp_lines_density double unsigned,
	avg_dp_blocks double unsigned,
	avg_dp_lines double unsigned,
	avg_dp_files double unsigned,
	-- avg loc of AR
	avg_loc double unsigned DEFAULT 0.0 COMMENT 'avg loc of AR',
	avg_lines double unsigned,
	avg_statements double unsigned,
	avg_functions double unsigned,
	avg_classes double unsigned,
	avg_files double unsigned,
	avg_directories double unsigned,
	avg_complexity double unsigned,
	avg_file_cp double unsigned,
	avg_function_cp double unsigned,
	avg_class_cp double unsigned,
	avg_comment_density double unsigned,
	avg_comment_lines double unsigned,
	avg_public_api double unsigned,
	avg_d_api_density double unsigned,
	avg_ud_api double unsigned,
	PRIMARY KEY (id),
	UNIQUE (user_id)
);


CREATE TABLE user_repos
(
	id int unsigned zerofill NOT NULL AUTO_INCREMENT,
	user_id int unsigned,
	repo_id int unsigned,
	PRIMARY KEY (id)
);



