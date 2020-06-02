create table Registrations_Registration (
	uuid_ VARCHAR(75) null,
	registrationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	articleId LONG,
	userPreferences VARCHAR(500) null,
	startTime DATE null,
	endTime DATE null
);