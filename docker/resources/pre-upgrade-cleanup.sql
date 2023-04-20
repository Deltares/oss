-- MySQL Clean up script for upgrade Liferay 7.1 to 7.4
--
--
-- Clear out useless data
--
DELETE FROM AMImageEntry;
DELETE FROM Address;
DELETE FROM AnnouncementsDelivery;
DELETE FROM AnnouncementsEntry;
DELETE FROM AnnouncementsFlag;
DELETE FROM Audit_AuditEvent;
DELETE FROM BackgroundTask;
DELETE FROM BlogsEntry;
DELETE FROM BlogsStatsUser;
DELETE FROM BookmarksEntry;
DELETE FROM BrowserTracker;
DELETE FROM CalEvent;
DELETE FROM Calendar;
DELETE FROM CalendarBooking;
DELETE FROM CalendarResource;
DELETE FROM ChangesetCollection;
DELETE FROM EmailAddress;
DELETE FROM SystemEvent;
DELETE FROM OAuth2Application;
DELETE FROM OAuth2Authorization;
DELETE FROM SamlSpAuthRequest;
DELETE FROM SamlSpSession;
DELETE FROM SystemEvent;
DELETE FROM SVN_SVNLog;
DELETE FROM Subversion_RepositoryLog;
DELETE FROM TrashEntry;
DELETE FROM TrashVersion;
DELETE FROM Twitter_Feed;
DELETE FROM Website;
DELETE FROM WikiPage;
DELETE FROM WikiPageResource;
DELETE FROM WorkflowDefinitionLink ;

--
-- Remove orphaned structures, templates, layouts
--
-- Below SQL shows which structures are not linked to a group.
--
-- SELECT d.structureId, d.companyId, d.structureKey  , d.groupId , g.groupId ,  g.friendlyURL , g.site  FROM DDMStructure d LEFT JOIN Group_ g ON d.groupId = g.groupId ;
--
DELETE FROM AssetDisplayPageEntry  WHERE companyId NOT IN (SELECT c.companyId FROM Company c) AND companyId != 0;
DELETE FROM DDMStructure  WHERE companyId NOT IN (SELECT c.companyId FROM Company c) AND companyId != 0;
DELETE FROM DDMStructureLink  WHERE companyId NOT IN (SELECT c.companyId FROM Company c) AND companyId != 0;
DELETE FROM DDMStructureLayout  WHERE companyId NOT IN (SELECT c.companyId FROM Company c) AND companyId != 0;

DELETE FROM DDMStructure WHERE groupId NOT IN (SELECT g.groupId FROM Group_ g);
DELETE FROM DDMTemplate WHERE groupId NOT IN (SELECT g.groupId FROM Group_ g );
DELETE FROM DDMStructureLayout WHERE groupId NOT IN (SELECT g.groupId FROM Group_ g);

-- Clean orphaned records in LayoutFriendlyURL, Layout and LayoutSet
DELETE FROM LayoutFriendlyURL WHERE groupId NOT IN (SELECT g.groupId FROM Group_ g );
DELETE FROM Layout WHERE groupId NOT IN (SELECT g.groupId FROM Group_ g );
DELETE FROM LayoutSet WHERE groupId NOT IN (SELECT g.groupId FROM Group_ g );

-- Clean configurations
DELETE FROM Configuration_;

--
-- Following steps are POST-Upgrade SQL statements.
-- These can be executed after upgrade to clean up old records.
--

--
-- Cleanup orphaned user records
--
DELETE FROM UserNotificationEvent WHERE userId NOT IN (SELECT u.userId FROM User_ u)
DELETE FROM MBMessage  WHERE userId NOT IN (SELECT u.userId FROM User_ u) AND createDate < '2018-01-01'
-- We can only remove users from the User_ table when we are sure no other tables link to these users
-- We can only remove user group records from Group_ when we are sure the corresponding users no longer exist.


