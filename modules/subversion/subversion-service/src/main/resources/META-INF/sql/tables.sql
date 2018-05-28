create table deltares_Repository (
	repositoryId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	classNameId LONG,
	classPK LONG,
	repositoryName VARCHAR(75) null,
	createdDate DATE null,
	modifiedDate DATE null
);

create table deltares_RepositoryFolder (
	folderId LONG not null primary key,
	repositoryId LONG,
	name VARCHAR(75) null,
	worldRead BOOLEAN,
	worldWrite BOOLEAN,
	createDate DATE null,
	modifiedDate DATE null
);

create table deltares_RepositoryFolderPermission (
	permissionId LONG not null primary key,
	folderId LONG,
	permission VARCHAR(75) null,
	recurse BOOLEAN,
	entityType VARCHAR(75) null,
	entityId LONG,
	createDate DATE null,
	modifiedDate DATE null
);

create table deltares_RepositoryLog (
	logId LONG not null primary key,
	ipAddress VARCHAR(75) null,
	screenName VARCHAR(75) null,
	action VARCHAR(75) null,
	createDate LONG,
	repository VARCHAR(75) null
);