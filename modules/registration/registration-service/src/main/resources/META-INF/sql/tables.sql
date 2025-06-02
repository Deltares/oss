create table Service_builder_Registration (
	registrationId LONG not null primary key,
	groupId LONG,
	registrationResourceId LONG,
	userId LONG,
	authorId LONG,
	registrationTime DATE null
);

create table Service_builder_RegistrationAttribute (
	registrationAttributeId LONG not null primary key,
	registrationId LONG,
	name VARCHAR(75) null,
	value STRING null
);

create table Service_builder_RegistrationPeriod (
	registrationPeriodId LONG not null primary key,
	registrationResourceId LONG,
	startTime DATE null,
	endTime DATE null
);

create table Service_builder_RegistrationResource (
	registrationResourceId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	eventResourceId LONG,
	parentResourceId LONG,
	resourceName VARCHAR(255) null,
	eventResourceName VARCHAR(255) null,
	eventArticleId LONG
);