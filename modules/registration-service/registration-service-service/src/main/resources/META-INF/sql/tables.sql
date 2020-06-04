create table Registrations_Registration (
	registrationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	articleId LONG,
	userPreferences VARCHAR(75) null,
	startTime DATE null,
	endTime DATE null
);