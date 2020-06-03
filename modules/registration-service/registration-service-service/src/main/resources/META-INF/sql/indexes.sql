create index IX_7B15BB on Registrations_Registration (groupId, articleId);
create index IX_28068E0 on Registrations_Registration (groupId, userId);
create index IX_5CF75B78 on Registrations_Registration (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_99105FFA on Registrations_Registration (uuid_[$COLUMN_LENGTH:75$], groupId);