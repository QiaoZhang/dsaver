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
	-- github id
	repo_id int unsigned DEFAULT 0 NOT NULL COMMENT 'github id',
	-- task id
	task_id int unsigned zerofill COMMENT 'task id',
	-- number of contributors
	contributors int unsigned DEFAULT 0 COMMENT 'number of contributors',
	PRIMARY KEY (id),
	UNIQUE (repo_id)
);


CREATE TABLE repo
(
	-- record id as primary key
	id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'record id as primary key',
	-- github id
	repo_id int unsigned DEFAULT 0 COMMENT 'github id',
	-- github api url
	repo_url varchar(500) DEFAULT 'Unkown' COMMENT 'github api url',
	-- github html url
	repo_html varchar(500) DEFAULT 'Unknown' COMMENT 'github html url',
	-- github name
	repo_name varchar(500) DEFAULT 'Unknown' COMMENT 'github name',
	-- github owner login 
	owner_login varchar(100) DEFAULT 'Unknown' COMMENT 'github owner login ',
	-- github owner type: user/orgs
	owner_type char(20) DEFAULT 'User' COMMENT 'github owner type: user/orgs',
	-- major programming language
	major_language varchar(50) DEFAULT 'Unknown' COMMENT 'major programming language',
	-- current version/tag
	version varchar(200) DEFAULT '1.0.0' COMMENT 'current version/tag',
	-- repo's size in bytes
	size int unsigned DEFAULT 0 COMMENT 'repo''s size in bytes',
	-- #of stars
	stargazers int unsigned DEFAULT 0 COMMENT '#of stars',
	-- # of forks
	forks int unsigned DEFAULT 0 COMMENT '# of forks',
	-- # of issues
	issues int unsigned DEFAULT 0 COMMENT '# of issues',
	-- # of handled issues
	handled_issues int unsigned DEFAULT 0 COMMENT '# of handled issues',
	-- avg days til first qualified issue event
	avg_days double unsigned DEFAULT 0.0 COMMENT 'avg days til first qualified issue event',
	-- repo creation date
	created_date timestamp DEFAULT '1970-01-01 00:00:01.000000' COMMENT 'repo creation date',
	-- loc of repo
	loc int unsigned COMMENT 'loc of repo',
	-- td amount
	sqale_index int unsigned COMMENT 'td amount',
	-- index/(loc*30)
	debt_ratio double unsigned COMMENT 'index/(loc*30)',
	PRIMARY KEY (id),
	UNIQUE (repo_id)
);


CREATE TABLE repo_users
(
	-- record id
	id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'record id',
	-- github id
	repo_id int unsigned COMMENT 'github id',
	-- github id
	user_id int unsigned COMMENT 'github id',
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
	-- github id
	user_id int unsigned DEFAULT 0 COMMENT 'github id',
	-- github api url
	user_url varchar(500) DEFAULT 'Unknown' COMMENT 'github api url',
	-- github html url
	user_html varchar(500) DEFAULT 'Unknown' COMMENT 'github html url',
	-- github login
	user_login varchar(200) DEFAULT 'Unknown' COMMENT 'github login',
	-- github name
	user_name varchar(500) DEFAULT 'Unknown' COMMENT 'github name',
	-- # of public repo
	public_repos int unsigned DEFAULT 0 COMMENT '# of public repo',
	-- # of follower
	followers int unsigned DEFAULT 0 COMMENT '# of follower',
	-- # of being assignee
	assignees int unsigned DEFAULT 0 COMMENT '# of being assignee',
	-- # of AR
	analyzed_repos int unsigned DEFAULT 0 COMMENT '# of AR',
	-- avg size of AR
	avg_size double unsigned DEFAULT 0.0 COMMENT 'avg size of AR',
	-- avg stars of AR
	avg_stargazers double unsigned DEFAULT 0.0 COMMENT 'avg stars of AR',
	-- avg watchers of AR
	avg_subscribers double unsigned DEFAULT 0.0 COMMENT 'avg watchers of AR',
	-- avg forks of AR
	avg_forks double unsigned DEFAULT 0.0 COMMENT 'avg forks of AR',
	-- avg issues of AR
	avg_issues double unsigned DEFAULT 0.0 COMMENT 'avg issues of AR',
	-- avg issue handled ratio of AR
	avg_issue_ratio double unsigned DEFAULT 0.0 COMMENT 'avg issue handled ratio of AR',
	-- avg issue handled days of AR
	avg_issue_days double unsigned DEFAULT 0.0 COMMENT 'avg issue handled days of AR',
	-- avg loc of AR
	avg_loc double unsigned DEFAULT 0.0 COMMENT 'avg loc of AR',
	-- avg sqale index of AR
	avg_sqale_index double unsigned DEFAULT 0.0 COMMENT 'avg sqale index of AR',
	-- avg td ratio of AR
	avg_debt_ratio double unsigned DEFAULT 0.0 COMMENT 'avg td ratio of AR',
	-- # of PR
	pull_requests int unsigned DEFAULT 0 COMMENT '# of PR',
	-- accepted PR
	accepted_pr int unsigned DEFAULT 0 COMMENT 'accepted PR',
	-- # of repos among accepted PR
	contrib_repos int unsigned DEFAULT 0 COMMENT '# of repos among accepted PR',
	-- avg commits among PR
	avg_commits double unsigned DEFAULT 0.0 COMMENT 'avg commits among PR',
	-- avg addition among PR
	avg_addition double unsigned DEFAULT 0.0 COMMENT 'avg addition among PR',
	-- avg deletion among PR
	avg_deletion double unsigned DEFAULT 0.0 COMMENT 'avg deletion among PR',
	-- avg changed files among PR
	avg_changed_files double unsigned DEFAULT 0.0 COMMENT 'avg changed files among PR',
	-- avg days interval among PR
	avg_days_interval double unsigned DEFAULT 0.0 COMMENT 'avg days interval among PR',
	PRIMARY KEY (id),
	UNIQUE (user_id)
);


CREATE TABLE user_repos
(
	id int unsigned zerofill NOT NULL AUTO_INCREMENT,
	-- github id
	user_id int unsigned COMMENT 'github id',
	-- github id
	repo_id int unsigned COMMENT 'github id',
	PRIMARY KEY (id)
);



