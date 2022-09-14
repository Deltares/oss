create index IX_287EDDFF on Downloads_Download (groupId, downloadId);
create index IX_C74FEA82 on Downloads_Download (groupId, shareId);
create unique index IX_61C2A739 on Downloads_Download (groupId, userId, downloadId);
create index IX_A1413B88 on Downloads_Download (groupId, userId, shareId);
create index IX_4E718C9C on Downloads_Download (userId);

create unique index IX_E94513D0 on Downloads_DownloadCount (downloadId);