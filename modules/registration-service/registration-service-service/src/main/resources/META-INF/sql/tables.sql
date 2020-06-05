create table Registrations_Registration (
	registrationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	articleId LONG,
	userPreferences STRING null,
	startTime DATE null,
	endTime DATE null,
	parentArticleId LONG
);