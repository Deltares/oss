create table GeoLocations_GeoLocation (
	uuid_ VARCHAR(75) null,
	locationId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	countryId LONG,
	cityName VARCHAR(75) null,
	latitude DOUBLE,
	longitude DOUBLE
);