create index IX_BF3FE342 on Registrations_Registration (groupId, eventResourcePrimaryKey, registeredByUserId);
create index IX_EF4176CF on Registrations_Registration (groupId, parentResourcePrimaryKey);
create index IX_F02BB679 on Registrations_Registration (groupId, registeredByUserId);
create index IX_CAA20105 on Registrations_Registration (groupId, resourcePrimaryKey);
create index IX_38E884F5 on Registrations_Registration (groupId, userId, eventResourcePrimaryKey);
create index IX_96E41489 on Registrations_Registration (groupId, userId, parentResourcePrimaryKey);
create index IX_1113F23F on Registrations_Registration (groupId, userId, resourcePrimaryKey);