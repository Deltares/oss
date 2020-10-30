create table Registrations_Registration (
	registrationId LONG not null primary key,
	groupId LONG,
	eventResourcePrimaryKey LONG,
	companyId LONG,
	userId LONG,
	resourcePrimaryKey LONG,
	userPreferences STRING null,
	startTime DATE null,
	endTime DATE null,
	parentResourcePrimaryKey LONG
);