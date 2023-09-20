create index IX_287EDDFF on Downloads_Download (groupId, downloadId);
create unique index IX_61C2A739 on Downloads_Download (groupId, userId, downloadId);

create unique index IX_E94513D0 on Downloads_DownloadCount (downloadId);