create index IX_8C9C52E3 on Service_builder_Registration (authorId, groupId);
create index IX_43F44AFD on Service_builder_Registration (authorId, registrationResourceId);
create index IX_D65097C3 on Service_builder_Registration (registrationResourceId);
create index IX_989238E3 on Service_builder_Registration (userId, groupId);
create index IX_3CB5A4FD on Service_builder_Registration (userId, registrationResourceId);

create index IX_7F9618A3 on Service_builder_RegistrationAttribute (registrationId);

create index IX_54F3D924 on Service_builder_RegistrationPeriod (registrationResourceId);

create index IX_1DA215DA on Service_builder_RegistrationResource (groupId, eventArticleId);
create index IX_9870B764 on Service_builder_RegistrationResource (groupId, eventResourceId);
create index IX_E7F1F576 on Service_builder_RegistrationResource (groupId, parentResourceId);
create index IX_1D837E45 on Service_builder_RegistrationResource (groupId, registrationResourceId);