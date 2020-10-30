create index IX_7DD2C1EF on Registrations_Registration (groupId, eventResourcePrimaryKey);
create index IX_EF4176CF on Registrations_Registration (groupId, parentResourcePrimaryKey);
create index IX_CAA20105 on Registrations_Registration (groupId, resourcePrimaryKey);
create index IX_38E884F5 on Registrations_Registration (groupId, userId, eventResourcePrimaryKey);
create index IX_96E41489 on Registrations_Registration (groupId, userId, parentResourcePrimaryKey);
create index IX_1113F23F on Registrations_Registration (groupId, userId, resourcePrimaryKey);