create table Downloads_Download (
	id_ LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	downloadId LONG,
	filePath STRING null,
	expiryDate DATE null,
	organization VARCHAR(75) null,
	geoLocationId LONG,
	shareId INTEGER,
	directDownloadUrl STRING null,
	licenseDownloadUrl STRING null
);

create table Downloads_DownloadCount (
	id_ LONG not null primary key,
	companyId LONG,
	groupId LONG,
	downloadId LONG,
	count INTEGER
);