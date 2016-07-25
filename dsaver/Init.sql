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
	-- client id whom the task is assigned to
	client_id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'client id whom the task is assigned to',
	-- finger print to differentiate dminer clients
	finger_print varchar(50) DEFAULT 'Unknown' COMMENT 'finger print to differentiate dminer clients',
	-- username of github account
	username varchar(100) DEFAULT 'Unknown' COMMENT 'username of github account',
	-- password of github account
	password varchar(100) DEFAULT 'Unknown' COMMENT 'password of github account',
	-- github developer app client id
	app_id varchar(100) DEFAULT 'Unknown' COMMENT 'github developer app client id',
	-- github developer app client secret
	app_secret varchar(200) DEFAULT 'Unknown' COMMENT 'github developer app client secret',
	last_update timestamp DEFAULT '1970-01-01 00:00:01.000000',
	PRIMARY KEY (client_id),
	UNIQUE (finger_print)
);


CREATE TABLE major_repo
(
	-- client id whom the task is assigned to
	client_id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'client id whom the task is assigned to',
	-- github repository id
	repo_id int unsigned DEFAULT 0 NOT NULL COMMENT 'github repository id',
	-- task id whom the major repo belongs to
	task_id int unsigned zerofill COMMENT 'task id whom the major repo belongs to',
	-- number of contributors of the major repository
	contributors int unsigned DEFAULT 0 COMMENT 'number of contributors of the major repository',
	PRIMARY KEY (client_id),
	UNIQUE (repo_id)
);


CREATE TABLE repo
(
	-- record id as primary key
	id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'record id as primary key',
	-- github repository id
	repo_id int unsigned DEFAULT 0 COMMENT 'github repository id',
	-- github repository api url
	repo_url varchar(500) DEFAULT 'Unkown' COMMENT 'github repository api url',
	-- github repository html url
	repo_html varchar(500) DEFAULT 'Unknown' COMMENT 'github repository html url',
	-- repository name
	repo_name varchar(500) DEFAULT 'Unknown' COMMENT 'repository name',
	-- repository owner's github login 
	owner_login varchar(100) DEFAULT 'Unknown' COMMENT 'repository owner''s github login ',
	-- github repository owner type: user/orgs
	owner_type char(20) DEFAULT 'User' COMMENT 'github repository owner type: user/orgs',
	-- repository's major programming language
	major_language varchar(50) DEFAULT 'Unknown' COMMENT 'repository''s major programming language',
	-- repository's current version/tag
	version varchar(200) DEFAULT '1.0.0' COMMENT 'repository''s current version/tag',
	-- repository's size in bytes
	size int unsigned DEFAULT 0 COMMENT 'repository''s size in bytes',
	-- number of stargazers
	stargazers int unsigned DEFAULT 0 COMMENT 'number of stargazers',
	-- number of forks
	forks int unsigned DEFAULT 0 COMMENT 'number of forks',
	-- number of issues
	issues int unsigned DEFAULT 0 COMMENT 'number of issues',
	-- number of handled issues
	handled_issues int unsigned DEFAULT 0 COMMENT 'number of handled issues',
	-- average days between issue created and first qualified event
	avg_days double unsigned DEFAULT 0.0 COMMENT 'average days between issue created and first qualified event',
	-- creation date of repository
	created_date timestamp DEFAULT '1970-01-01 00:00:01.000000' COMMENT 'creation date of repository',
	PRIMARY KEY (id),
	UNIQUE (repo_id)
);


CREATE TABLE repo_users
(
	-- record id
	id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'record id',
	-- github repository id
	repo_id int unsigned COMMENT 'github repository id',
	-- github user id
	user_id int unsigned COMMENT 'github user id',
	PRIMARY KEY (id)
);


CREATE TABLE tasks
(
	-- task id whom the major repo belongs to
	task_id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'task id whom the major repo belongs to',
	-- client id whom the task is assigned to
	client_id int unsigned zerofill DEFAULT 0 COMMENT 'client id whom the task is assigned to',
	-- either unassigned, assigned, open, close
	state varchar(50) COMMENT 'either unassigned, assigned, open, close',
	-- number of total repositories of this task
	-- 
	total int unsigned DEFAULT 0 COMMENT 'number of total repositories of this task
',
	-- number of successly recorded major repository info
	success int unsigned DEFAULT 0 COMMENT 'number of successly recorded major repository info',
	-- number of unsucessfully recorded major repository info
	failed int unsigned DEFAULT 0 COMMENT 'number of unsucessfully recorded major repository info',
	-- search query parameter - language
	language varchar(50) COMMENT 'search query parameter - language',
	-- search query parameter - user name
	user varchar(200) COMMENT 'search query parameter - user name',
	-- search query parameter - size
	size varchar(50) COMMENT 'search query parameter - size',
	-- search query parameter - stars
	stars varchar(50) BINARY COMMENT 'search query parameter - stars',
	-- search query parameter - forks
	forks varchar(50) COMMENT 'search query parameter - forks',
	-- search query parameter - stars
	created varchar(50) COMMENT 'search query parameter - stars',
	PRIMARY KEY (task_id)
);


CREATE TABLE user
(
	-- task id whom the major repo belongs to
	task_id int unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'task id whom the major repo belongs to',
	-- github user id
	user_id int unsigned DEFAULT 0 COMMENT 'github user id',
	-- github user api url
	user_url varchar(500) DEFAULT 'Unknown' COMMENT 'github user api url',
	-- github user html url
	user_html varchar(500) DEFAULT 'Unknown' COMMENT 'github user html url',
	-- github user's login
	user_login varchar(200) DEFAULT 'Unknown' COMMENT 'github user''s login',
	-- github user name
	user_name varchar(500) DEFAULT 'Unknown' COMMENT 'github user name',
	-- number of user's public repositories
	public_repos int unsigned DEFAULT 0 COMMENT 'number of user''s public repositories',
	-- number of user's followers
	followers int unsigned DEFAULT 0 COMMENT 'number of user''s followers',
	-- number of times user being assignee 
	assignees int unsigned DEFAULT 0 COMMENT 'number of times user being assignee ',
	-- number of analyzed repositories
	analyzed_repos int unsigned DEFAULT 0 COMMENT 'number of analyzed repositories',
	-- average size in byte of analyzed repos
	avg_size double unsigned DEFAULT 0.0 COMMENT 'average size in byte of analyzed repos',
	-- average number of stargazers of analyzed repos
	avg_stargazers double unsigned DEFAULT 0.0 COMMENT 'average number of stargazers of analyzed repos',
	-- average number of subscribers of analyzed repos
	avg_subscribers double unsigned DEFAULT 0.0 COMMENT 'average number of subscribers of analyzed repos',
	-- average number of forks of analyzed repos
	avg_forks double unsigned DEFAULT 0.0 COMMENT 'average number of forks of analyzed repos',
	-- average number of issues of analyzed repos
	avg_issues double unsigned DEFAULT 0.0 COMMENT 'average number of issues of analyzed repos',
	-- average ratio of handled issues of analyzed repos
	avg_issue_ratio double unsigned DEFAULT 0.0 COMMENT 'average ratio of handled issues of analyzed repos',
	-- average issue handled days of analzyed repos
	avg_issue_days double unsigned DEFAULT 0.0 COMMENT 'average issue handled days of analzyed repos',
	-- average loc of major language of analyzed repos
	avg_loc double unsigned DEFAULT 0.0 COMMENT 'average loc of major language of analyzed repos',
	-- average SQALE index of analyzed repos
	avg_sqale_index double unsigned DEFAULT 0.0 COMMENT 'average SQALE index of analyzed repos',
	-- average technical debt ratio of analyzed repos
	avg_debt_ratio double unsigned DEFAULT 0.0 COMMENT 'average technical debt ratio of analyzed repos',
	-- number of pull requests the user submitted
	pull_requests int unsigned DEFAULT 0 COMMENT 'number of pull requests the user submitted',
	-- number of accepted pull requested among user's submission
	accepted_pr int unsigned DEFAULT 0 COMMENT 'number of accepted pull requested among user''s submission',
	-- number of contributed repos among accepted pull requests of the user
	contrib_repos int unsigned DEFAULT 0 COMMENT 'number of contributed repos among accepted pull requests of the user',
	-- average commits count of accepted pull requests of the user
	avg_commits double unsigned DEFAULT 0.0 COMMENT 'average commits count of accepted pull requests of the user',
	-- average addition in loc of pull requests of the user
	avg_addition double unsigned DEFAULT 0.0 COMMENT 'average addition in loc of pull requests of the user',
	-- average deletion of pull requets of the user
	avg_deletion double unsigned DEFAULT 0.0 COMMENT 'average deletion of pull requets of the user',
	-- average number of files changed among pull requests of the user
	avg_changed_files double unsigned DEFAULT 0.0 COMMENT 'average number of files changed among pull requests of the user',
	-- average days interval between two accepted pull requests of the user
	avg_days_interval double unsigned DEFAULT 0.0 COMMENT 'average days interval between two accepted pull requests of the user',
	PRIMARY KEY (task_id),
	UNIQUE (user_id)
);


CREATE TABLE user_repos
(
	id int unsigned zerofill NOT NULL AUTO_INCREMENT,
	-- github user id
	user_id int unsigned COMMENT 'github user id',
	-- github repository id
	repo_id int unsigned COMMENT 'github repository id',
	PRIMARY KEY (id)
);



