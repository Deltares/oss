create index IX_287EDDFF on Downloads_Download (groupId, downloadId);
create unique index IX_61C2A739 on Downloads_Download (groupId, userId, downloadId);
create index IX_4E718C9C on Downloads_Download (userId);

create unique index IX_C6FD00E8 on Downloads_DownloadCount (groupId, downloadId);