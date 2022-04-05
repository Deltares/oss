create table Downloads_Download (
	downloadId LONG not null,
	userId LONG not null,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	path_ VARCHAR(75) null,
	expiryDate DATE null,
	organization VARCHAR(75) null,
	countryCode VARCHAR(75) null,
	city VARCHAR(75) null,
	shareId LONG,
	directDownloadUrl VARCHAR(75) null,
	primary key (downloadId, userId)
);

create table Downloads_DownloadCount (
	downloadId LONG not null primary key,
	count INTEGER
);