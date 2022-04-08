create table Downloads_Download (
	id_ LONG not null primary key,
	companyId LONG,
	groupId LONG,
	downloadId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	filePath STRING null,
	expiryDate DATE null,
	organization VARCHAR(75) null,
	countryCode VARCHAR(75) null,
	city VARCHAR(75) null,
	shareId LONG,
	directDownloadUrl STRING null
);

create table Downloads_DownloadCount (
	id_ LONG not null primary key,
	companyId LONG,
	groupId LONG,
	downloadId LONG,
	count INTEGER
);