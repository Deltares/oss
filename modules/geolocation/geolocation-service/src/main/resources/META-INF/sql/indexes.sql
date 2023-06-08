create unique index IX_CE619311 on GeoLocations_GeoLocation (countryId, cityName[$COLUMN_LENGTH:75$]);
create index IX_E9EF222E on GeoLocations_GeoLocation (uuid_[$COLUMN_LENGTH:75$], companyId);