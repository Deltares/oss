-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: keycloak
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.19-MariaDB-1~jessie

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ADMIN_EVENT_ENTITY`
--

DROP TABLE IF EXISTS `ADMIN_EVENT_ENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ADMIN_EVENT_ENTITY` (
  `ID` varchar(36) NOT NULL,
  `ADMIN_EVENT_TIME` bigint(20) DEFAULT NULL,
  `REALM_ID` varchar(255) DEFAULT NULL,
  `OPERATION_TYPE` varchar(255) DEFAULT NULL,
  `AUTH_REALM_ID` varchar(255) DEFAULT NULL,
  `AUTH_CLIENT_ID` varchar(255) DEFAULT NULL,
  `AUTH_USER_ID` varchar(255) DEFAULT NULL,
  `IP_ADDRESS` varchar(255) DEFAULT NULL,
  `RESOURCE_PATH` varchar(2550) DEFAULT NULL,
  `REPRESENTATION` text,
  `ERROR` varchar(255) DEFAULT NULL,
  `RESOURCE_TYPE` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ADMIN_EVENT_ENTITY`
--

LOCK TABLES `ADMIN_EVENT_ENTITY` WRITE;
/*!40000 ALTER TABLE `ADMIN_EVENT_ENTITY` DISABLE KEYS */;
INSERT INTO `ADMIN_EVENT_ENTITY` VALUES ('0a53bff4-f06f-4fb7-8438-36b4406fe5ea',1621415922000,'liferay-portal','UPDATE','liferay-portal','d377a198-34a9-46a2-9564-f468fb84e9b8','04a65cd9-859e-492e-a312-564d9d1505a7','172.19.0.6','users/fc7b51a0-662e-44ca-8621-5921972d159c','{\"id\":\"fc7b51a0-662e-44ca-8621-5921972d159c\",\"createdTimestamp\":1617882322772,\"username\":\"test\",\"enabled\":true,\"totp\":false,\"emailVerified\":true,\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"test@liferay.com\",\"attributes\":{\"login.login-count.your.docker.com\":[\"17\"],\"login.first-login-date.your.docker.com.admin-console\":[\"2021-05-18T13:40:11.944\"],\"login.recent-login-date.localhost\":[\"2021-05-14T08:58:27.156\"],\"login.recent-login-date.your.docker.com.my-program\":[\"2021-05-19T08:15:03.083\"],\"login.recent-login-date.account\":[\"2021-05-18T13:38:20.858\"],\"login.recent-login-date.localhost.program\":[\"2021-04-23T09:54:27.726\"],\"login.recent-login-date.your.docker.com.registration-form\":[\"2021-05-18T09:20:04.578\"],\"login.recent-login-date.my.docker.com\":[\"2021-05-14T13:56:08.208\"],\"login.first-login-date.my.docker.com.home\":[\"2021-05-14T08:50:24.964\"],\"login.login-count.your.docker.com.program\":[\"2\"],\"login.first-login-date.your.docker.com.registration-form\":[\"2021-05-17T13:20:26.959\"],\"login.login-count.localhost.program\":[\"1\"],\"login.recent-login-date.localhost.admin-console\":[\"2021-05-10T15:22:47.817\"],\"login.first-login-date.localhost.admin-console\":[\"2021-05-06T07:29:33.312\"],\"login.first-login-date.your.docker.com.my-program\":[\"2021-05-19T08:15:03.083\"],\"org_address\":[\"cz\"],\"login.login-count.localhost\":[\"11\"],\"login.recent-login-date.guest\":[\"2021-05-12T09:25:44.201\"],\"login.login-count.my.docker.com.home\":[\"18\"],\"login.first-login-date.your.docker.com.oss-admin-console\":[\"2021-05-14T13:13:28.924\"],\"login.first-login-date.your.docker.com\":[\"2021-05-14T09:02:02.269\"],\"pay_reference\":[\"a\"],\"login.login-count.your.docker.com.registration-form\":[\"2\"],\"login.login-count.localhost.admin-console\":[\"6\"],\"login.first-login-date.liferay-docker2-saml\":[\"2021-05-14T11:18:34.040\"],\"login.recent-login-date\":[\"2021-05-18T13:40:11.433\"],\"login.login-count.your.docker.com.admin-console\":[\"1\"],\"login.first-login-date.localhost\":[\"2021-04-23T07:58:02.346\"],\"login.recent-login-date.your.docker.com\":[\"2021-05-18T12:15:04.321\"],\"login.recent-login-date.liferay-docker-saml\":[\"2021-05-19T08:38:12.481\"],\"login.login-count.account\":[\"6\"],\"login.recent-login-date.liferay.program\":[\"2021-05-19T09:18:42.728\"],\"login.recent-login-date.your.docker.com.home\":[\"2021-05-18T11:15:08.346\"],\"login.recent-login-date.your.docker.com.admin-console\":[\"2021-05-18T13:40:11.944\"],\"org_city\":[\"zc\"],\"org_country\":[\"afghanistan\"],\"login.first-login-date.liferay.program\":[\"2021-05-19T09:15:40.910\"],\"login.first-login-date.my.docker.com\":[\"2021-05-14T08:47:29.812\"],\"login.recent-login-date.your.docker.com.oss-admin-console\":[\"2021-05-14T13:13:28.924\"],\"login.login-count.my.docker.com\":[\"27\"],\"login.login-count.your.docker.com.my-program\":[\"1\"],\"login.login-count.liferay-docker-saml\":[\"53\"],\"login.first-login-date.account\":[\"2021-05-11T14:35:50.443\"],\"login.login-count.localhost.guest\":[\"36\"],\"locale\":[\"en\"],\"org_postal\":[\"zc\"],\"login.login-count\":[\"25\"],\"login.recent-login-date.my.docker.com.home\":[\"2021-05-18T09:29:13.277\"],\"login.first-login-date.guest\":[\"2021-05-12T09:25:44.199\"],\"login.recent-login-date.liferay-docker2-saml\":[\"2021-05-19T09:18:42.522\"],\"login.first-login-date.liferay-docker-saml\":[\"2021-04-21T09:59:48.905\"],\"login.first-login-date\":[\"2021-05-14T08:51:01.945\"],\"login.login-count.your.docker.com.home\":[\"31\"],\"org_name\":[\"cz\"],\"login.recent-login-date.localhost.guest\":[\"2021-05-14T08:53:04.831\"],\"org_preferred_payment\":[\"bankTransfer\"],\"login.login-count.guest\":[\"1\"],\"login.first-login-date.localhost.guest\":[\"2021-04-21T09:59:49.067\"],\"login.first-login-date.your.docker.com.program\":[\"2021-05-18T13:00:20.238\"],\"login.login-count.your.docker.com.oss-admin-console\":[\"1\"],\"login.login-count.liferay-docker2-saml\":[\"33\"],\"login.recent-login-date.your.docker.com.program\":[\"2021-05-19T08:11:39.800\"],\"login.login-count.liferay.program\":[\"3\"],\"login.first-login-date.your.docker.com.home\":[\"2021-05-14T09:08:02.593\"],\"org_vat\":[\"b\"],\"login.first-login-date.localhost.program\":[\"2021-04-23T09:54:27.726\"]},\"disableableCredentialTypes\":[\"password\"],\"requiredActions\":[],\"notBefore\":1620743754,\"access\":{\"manageGroupMembership\":true,\"view\":true,\"mapRoles\":true,\"impersonate\":false,\"manage\":true}}',NULL,'USER'),('20499e66-5083-46dc-a234-c1c397f643f7',1621415808000,'liferay-portal','UPDATE','liferay-portal','d377a198-34a9-46a2-9564-f468fb84e9b8','04a65cd9-859e-492e-a312-564d9d1505a7','172.19.0.6','users/fc7b51a0-662e-44ca-8621-5921972d159c','{\"id\":\"fc7b51a0-662e-44ca-8621-5921972d159c\",\"createdTimestamp\":1617882322772,\"username\":\"test\",\"enabled\":true,\"totp\":false,\"emailVerified\":true,\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"test@liferay.com\",\"attributes\":{\"login.login-count.your.docker.com\":[\"17\"],\"login.recent-login-date.localhost\":[\"2021-05-14T08:58:27.156\"],\"login.first-login-date.your.docker.com.admin-console\":[\"2021-05-18T13:40:11.944\"],\"login.recent-login-date.your.docker.com.my-program\":[\"2021-05-19T08:15:03.083\"],\"login.recent-login-date.account\":[\"2021-05-18T13:38:20.858\"],\"login.recent-login-date.localhost.program\":[\"2021-04-23T09:54:27.726\"],\"login.recent-login-date.your.docker.com.registration-form\":[\"2021-05-18T09:20:04.578\"],\"login.recent-login-date.my.docker.com\":[\"2021-05-14T13:56:08.208\"],\"login.first-login-date.my.docker.com.home\":[\"2021-05-14T08:50:24.964\"],\"login.login-count.your.docker.com.program\":[\"2\"],\"login.first-login-date.your.docker.com.registration-form\":[\"2021-05-17T13:20:26.959\"],\"login.login-count.localhost.program\":[\"1\"],\"login.recent-login-date.localhost.admin-console\":[\"2021-05-10T15:22:47.817\"],\"login.first-login-date.localhost.admin-console\":[\"2021-05-06T07:29:33.312\"],\"login.first-login-date.your.docker.com.my-program\":[\"2021-05-19T08:15:03.083\"],\"org_address\":[\"cz\"],\"login.login-count.localhost\":[\"11\"],\"login.login-count.my.docker.com.home\":[\"18\"],\"login.recent-login-date.guest\":[\"2021-05-12T09:25:44.201\"],\"login.first-login-date.your.docker.com.oss-admin-console\":[\"2021-05-14T13:13:28.924\"],\"pay_reference\":[\"a\"],\"login.first-login-date.your.docker.com\":[\"2021-05-14T09:02:02.269\"],\"login.login-count.your.docker.com.registration-form\":[\"2\"],\"login.login-count.localhost.admin-console\":[\"6\"],\"login.first-login-date.liferay-docker2-saml\":[\"2021-05-14T11:18:34.040\"],\"login.recent-login-date\":[\"2021-05-18T13:40:11.433\"],\"login.login-count.your.docker.com.admin-console\":[\"1\"],\"login.recent-login-date.your.docker.com\":[\"2021-05-18T12:15:04.321\"],\"login.first-login-date.localhost\":[\"2021-04-23T07:58:02.346\"],\"login.recent-login-date.liferay-docker-saml\":[\"2021-05-19T08:38:12.481\"],\"login.login-count.account\":[\"6\"],\"login.recent-login-date.liferay.program\":[\"2021-05-19T09:16:48.225\"],\"login.recent-login-date.your.docker.com.home\":[\"2021-05-18T11:15:08.346\"],\"org_city\":[\"zc\"],\"login.recent-login-date.your.docker.com.admin-console\":[\"2021-05-18T13:40:11.944\"],\"login.first-login-date.liferay.program\":[\"2021-05-19T09:15:40.910\"],\"org_country\":[\"afghanistan\"],\"login.first-login-date.my.docker.com\":[\"2021-05-14T08:47:29.812\"],\"login.recent-login-date.your.docker.com.oss-admin-console\":[\"2021-05-14T13:13:28.924\"],\"login.login-count.my.docker.com\":[\"27\"],\"login.login-count.your.docker.com.my-program\":[\"1\"],\"login.login-count.liferay-docker-saml\":[\"53\"],\"login.first-login-date.account\":[\"2021-05-11T14:35:50.443\"],\"login.login-count.localhost.guest\":[\"36\"],\"locale\":[\"en\"],\"org_postal\":[\"zc\"],\"login.login-count\":[\"25\"],\"login.recent-login-date.my.docker.com.home\":[\"2021-05-18T09:29:13.277\"],\"login.first-login-date.guest\":[\"2021-05-12T09:25:44.199\"],\"login.recent-login-date.liferay-docker2-saml\":[\"2021-05-19T09:16:47.790\"],\"login.first-login-date.liferay-docker-saml\":[\"2021-04-21T09:59:48.905\"],\"login.first-login-date\":[\"2021-05-14T08:51:01.945\"],\"login.login-count.your.docker.com.home\":[\"31\"],\"org_name\":[\"cz\"],\"login.recent-login-date.localhost.guest\":[\"2021-05-14T08:53:04.831\"],\"org_preferred_payment\":[\"bankTransfer\"],\"login.login-count.guest\":[\"1\"],\"login.first-login-date.localhost.guest\":[\"2021-04-21T09:59:49.067\"],\"login.login-count.liferay-docker2-saml\":[\"32\"],\"login.login-count.your.docker.com.oss-admin-console\":[\"1\"],\"login.first-login-date.your.docker.com.program\":[\"2021-05-18T13:00:20.238\"],\"login.recent-login-date.your.docker.com.program\":[\"2021-05-19T08:11:39.800\"],\"login.login-count.liferay.program\":[\"2\"],\"org_vat\":[\"b\"],\"login.first-login-date.your.docker.com.home\":[\"2021-05-14T09:08:02.593\"],\"login.first-login-date.localhost.program\":[\"2021-04-23T09:54:27.726\"]},\"disableableCredentialTypes\":[\"password\"],\"requiredActions\":[],\"notBefore\":1620743754,\"access\":{\"manageGroupMembership\":true,\"view\":true,\"mapRoles\":true,\"impersonate\":false,\"manage\":true}}',NULL,'USER'),('24659b96-cadd-4213-8660-89df8a1888e8',1621412251000,'liferay-portal','UPDATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.18.0.1','authentication/required-actions/terms_and_privacy','{\"alias\":\"terms_and_privacy\",\"name\":\"Deltares Terms and Privacy\",\"enabled\":true,\"defaultAction\":true,\"priority\":30,\"config\":{}}',NULL,'REQUIRED_ACTION'),('25d8baec-c202-4117-b2cb-9a5c400dac63',1621412241000,'liferay-portal','UPDATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.18.0.1','authentication/required-actions/terms_and_privacy/raise-priority',NULL,NULL,'REQUIRED_ACTION'),('28baba25-0128-4c4e-b81a-c121cc042570',1621415719000,'liferay-portal','UPDATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.19.0.1','clients/b8a62fae-771b-402a-8515-1b13b025da91','{\"id\":\"b8a62fae-771b-402a-8515-1b13b025da91\",\"clientId\":\"liferay-docker2-saml\",\"adminUrl\":\"http://liferay:8081/c/portal/saml\",\"surrogateAuthRequired\":false,\"enabled\":true,\"clientAuthenticatorType\":\"client-secret\",\"redirectUris\":[\"http://liferay:8081/*\"],\"webOrigins\":[],\"notBefore\":0,\"bearerOnly\":false,\"consentRequired\":false,\"standardFlowEnabled\":true,\"implicitFlowEnabled\":false,\"directAccessGrantsEnabled\":false,\"serviceAccountsEnabled\":false,\"publicClient\":false,\"frontchannelLogout\":true,\"protocol\":\"saml\",\"attributes\":{\"saml.assertion.signature\":\"false\",\"saml.force.post.binding\":\"true\",\"saml.multivalued.roles\":\"false\",\"saml_single_logout_service_url_post\":\"http://liferay:8081/c/portal/saml/slo\",\"saml.encrypt\":\"false\",\"saml.server.signature\":\"true\",\"saml.server.signature.keyinfo.ext\":\"false\",\"exclude.session.state.from.auth.response\":\"false\",\"saml.signing.certificate\":\"MIIClzCCAX8CBgF5fskFATANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDDARUZXN0MB4XDTIxMDUxODA5MjI1M1oXDTIyMDUwOTA5MjI1M1owDzENMAsGA1UEAwwEVGVzdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAIGrhrQe3GqB2NAr6FnTlvbRI4E1GI6kFiDmwgF/tty7HJBVz4+CBxdKjf42f7zdMDoStdophoZ/5u3P92V7I6oTfwNAWHNjSRiTx/Ft+xw3kzIKgClauvXjyupsVDZB0ON+C0rOw9StDYjSEJS68K4nQ6J/aQl4rP+copgSPCv89Jy/FZTl243mODzbYe23a577HQU1+BtwzyqTuFMYhrIsRP+1xxtkk4WmYH/t24bfGKnUUxMuRkzqwZxbRinD6tLOwfLNhEWcw1draluFtN2iCf8IpnKBIazPgTwrk1brsE59i/yoiOptRy47RL6XwhM4G7mT3arUiY5U6zacyCkCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAUdFjoAZbwrEJNKqsB4B2o9JYtebkg0eqlTIHzmw82XxIUwp//LVcvxn4NnO/ixrGONpj/vVVnew3x8intsGMko+wNglC1SeQRLjl3CfUcQxQd4yNC3kxqUbNvqevxGgbbFKFTD8QDZRdlGzzq55JDeQxtvgRUrvj9hv2vWvv1fmHYH4IvVdk7Mfeqo9AEZq9s4d7RfRGjk953JFyV2ELTI6x8LjVRoowczs9CjQ5cWzSMz0vyBNJzxtUmfI7lkfyRdHme2Az6bbUWwy78yapzBNqeIjQstFMnVs1cyRhV2D9v/ANvlHCTsGmV/v4jcfuQw94Oj2pAKdyHvkG5g+r8A==\",\"saml_single_logout_service_url_redirect\":\"http://liferay:8081/c/portal/saml/slo\",\"saml.signature.algorithm\":\"RSA_SHA1\",\"saml_force_name_id_format\":\"false\",\"saml.client.signature\":\"true\",\"tls.client.certificate.bound.access.tokens\":\"false\",\"saml.authnstatement\":\"true\",\"display.on.consent.screen\":\"false\",\"saml_name_id_format\":\"email\",\"saml.onetimeuse.condition\":\"false\",\"saml.server.signature.keyinfo.xmlSigKeyInfoKeyNameTransformer\":\"NONE\",\"saml_signature_canonicalization_method\":\"http://www.w3.org/2001/10/xml-exc-c14n#\"},\"authenticationFlowBindingOverrides\":{},\"fullScopeAllowed\":true,\"nodeReRegistrationTimeout\":-1,\"protocolMappers\":[{\"id\":\"2424c783-fd04-43e6-b064-4534f889f8f8\",\"name\":\"lastName\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"lastName\",\"attribute.name\":\"lastName\"}},{\"id\":\"dc3e541e-5793-4d6e-b279-f679321c69a0\",\"name\":\"firstName\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"firstName\",\"attribute.name\":\"firstName\"}},{\"id\":\"1e4e9953-5d7e-4f07-af67-94b748d5ce01\",\"name\":\"email\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"email\",\"attribute.name\":\"emailAddress\"}},{\"id\":\"1965c356-0e3d-40b6-9840-44978f3ba9e0\",\"name\":\"username\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"username\",\"attribute.name\":\"screenName\"}}],\"defaultClientScopes\":[\"web-origins\",\"role_list\",\"profile\",\"roles\",\"email\"],\"optionalClientScopes\":[\"address\",\"phone\",\"offline_access\"],\"access\":{\"view\":true,\"configure\":true,\"manage\":true}}',NULL,'CLIENT'),('44aeb462-3a12-4d35-8e60-9646670ccb4f',1621412235000,'liferay-portal','CREATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.18.0.1','authentication/register-required-action','{\"providerId\":\"terms_and_privacy\",\"name\":\"Deltares Terms and Privacy\",\"id\":\"241da5b0-bd62-4a70-90ce-49f45607a107\"}',NULL,'REQUIRED_ACTION'),('587b23b0-036e-4920-a67e-8c652ae742d8',1621412243000,'liferay-portal','UPDATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.18.0.1','authentication/required-actions/terms_and_privacy/raise-priority',NULL,NULL,'REQUIRED_ACTION'),('73bc8bda-ce9a-4efb-a6ee-0513aad4b304',1621415679000,'liferay-portal','UPDATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.19.0.1','clients/dbe87c24-ee61-418a-9058-85660d151e0e','{\"id\":\"dbe87c24-ee61-418a-9058-85660d151e0e\",\"clientId\":\"liferay-docker-saml\",\"adminUrl\":\"http://localhost:8081/c/portal/saml\",\"surrogateAuthRequired\":false,\"enabled\":true,\"clientAuthenticatorType\":\"client-secret\",\"redirectUris\":[\"http://localhost:8081/*\"],\"webOrigins\":[],\"notBefore\":0,\"bearerOnly\":false,\"consentRequired\":false,\"standardFlowEnabled\":true,\"implicitFlowEnabled\":false,\"directAccessGrantsEnabled\":false,\"serviceAccountsEnabled\":false,\"publicClient\":false,\"frontchannelLogout\":true,\"protocol\":\"saml\",\"attributes\":{\"saml.assertion.signature\":\"false\",\"saml.force.post.binding\":\"true\",\"saml.multivalued.roles\":\"false\",\"saml_single_logout_service_url_post\":\"http://localhost:8081/c/portal/saml/slo\",\"saml.encrypt\":\"false\",\"saml.server.signature\":\"true\",\"saml.server.signature.keyinfo.ext\":\"false\",\"exclude.session.state.from.auth.response\":\"false\",\"saml.signing.certificate\":\"MIIClzCCAX8CBgF5fs82VDANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDDARUZXN0MB4XDTIxMDUxODA5MjkzOVoXDTIyMDUwOTA5MjkzOVowDzENMAsGA1UEAwwEVGVzdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKCdTn9hNHyERjK7DmNNhpMjM4CVS7Rx/SZDhyid79fMU3XZVf6YNIpjTVoYyf03Ff+dGejMvz7KA3UXR9AOBpX0MJaqK5fIf5svVm2gl1ommkDS7UO3PFnUUS33ZjqM7dYa0eAfm5OgsZKytxsBnSv7UBTk9zU7JKpcD2jidcH76kSzeDMKjqusexps0PvFaAxU5fxD4rarrrO9VzyHj2jlun/EC9ymgvPxN96et5qYTMRttmYdxSTLasN6NtL6tgxysBoTMglcz9qd+QgHahA17e2J8hqugf4hrHmeM/uJEjx6iPX+KqmlPdzFbRk6ExzCW+K1Hi7oGHRFiKDv6GUCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAKQ9J7ojmt1hxvrf67VmRKk7MT7l2OBbLfvkZfy+QZBYYlaj3Gn/moFaUWcas24j1Z1SQ/OILIIs25r3VtDSL9eChvJJyoPsqdph2q9d7+i1B9GQm5cRaR8PL5S/pREpdA9mxoh5vR0PdXQrwsH0I6QLY3Z99Mzexb4XaGhPsFLIouayYmGirAL7wujF03eAqcxZJKnyahC46hcKtOgZZYps1x1lEJEbybKB1mlrn0nV5LgyLBBXY7JhnegnA9yEVUSGJYCrwR0Wk9nbf89yTqdIzM+cMBATiH80uzPBP290nsaYjvSP+1agIVzGQTxWjFAFIY222jCAtQmtqhJWozg==\",\"saml_single_logout_service_url_redirect\":\"http://localhost:8081/c/portal/saml/slo\",\"saml.signature.algorithm\":\"RSA_SHA1\",\"saml_force_name_id_format\":\"false\",\"saml.client.signature\":\"true\",\"tls.client.certificate.bound.access.tokens\":\"false\",\"saml.authnstatement\":\"true\",\"display.on.consent.screen\":\"false\",\"saml_name_id_format\":\"email\",\"saml.onetimeuse.condition\":\"false\",\"saml.server.signature.keyinfo.xmlSigKeyInfoKeyNameTransformer\":\"NONE\",\"saml_signature_canonicalization_method\":\"http://www.w3.org/2001/10/xml-exc-c14n#\"},\"authenticationFlowBindingOverrides\":{},\"fullScopeAllowed\":true,\"nodeReRegistrationTimeout\":-1,\"protocolMappers\":[{\"id\":\"96738fc1-152c-415e-9c71-03959adab303\",\"name\":\"lastName\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"lastName\",\"attribute.name\":\"lastName\"}},{\"id\":\"7a774a42-c62b-4a18-b0a9-8331fab8f323\",\"name\":\"username\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"username\",\"attribute.name\":\"screenName\"}},{\"id\":\"f4a18126-e246-4ed1-9438-5b341c01470b\",\"name\":\"email\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"email\",\"attribute.name\":\"emailAddress\"}},{\"id\":\"af711ee2-37f5-4080-aae7-d42dbe6c7f4e\",\"name\":\"firstName\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"firstName\",\"attribute.name\":\"firstName\"}}],\"defaultClientScopes\":[\"web-origins\",\"role_list\",\"profile\",\"roles\",\"email\"],\"optionalClientScopes\":[\"address\",\"phone\",\"offline_access\"],\"access\":{\"view\":true,\"configure\":true,\"manage\":true}}',NULL,'CLIENT'),('77411d4d-f15b-4ec3-868e-76786aef5915',1621415741000,'liferay-portal','UPDATE','liferay-portal','d377a198-34a9-46a2-9564-f468fb84e9b8','04a65cd9-859e-492e-a312-564d9d1505a7','172.19.0.6','users/fc7b51a0-662e-44ca-8621-5921972d159c','{\"id\":\"fc7b51a0-662e-44ca-8621-5921972d159c\",\"createdTimestamp\":1617882322772,\"username\":\"test\",\"enabled\":true,\"totp\":false,\"emailVerified\":true,\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"test@liferay.com\",\"attributes\":{\"login.login-count.your.docker.com\":[\"17\"],\"login.recent-login-date.localhost\":[\"2021-05-14T08:58:27.156\"],\"login.first-login-date.your.docker.com.admin-console\":[\"2021-05-18T13:40:11.944\"],\"login.recent-login-date.your.docker.com.my-program\":[\"2021-05-19T08:15:03.083\"],\"login.recent-login-date.account\":[\"2021-05-18T13:38:20.858\"],\"login.recent-login-date.localhost.program\":[\"2021-04-23T09:54:27.726\"],\"login.recent-login-date.your.docker.com.registration-form\":[\"2021-05-18T09:20:04.578\"],\"login.first-login-date.my.docker.com.home\":[\"2021-05-14T08:50:24.964\"],\"login.login-count.your.docker.com.program\":[\"2\"],\"login.recent-login-date.my.docker.com\":[\"2021-05-14T13:56:08.208\"],\"login.login-count.localhost.program\":[\"1\"],\"login.first-login-date.your.docker.com.registration-form\":[\"2021-05-17T13:20:26.959\"],\"login.recent-login-date.localhost.admin-console\":[\"2021-05-10T15:22:47.817\"],\"login.first-login-date.localhost.admin-console\":[\"2021-05-06T07:29:33.312\"],\"login.first-login-date.your.docker.com.my-program\":[\"2021-05-19T08:15:03.083\"],\"org_address\":[\"cz\"],\"login.login-count.localhost\":[\"11\"],\"login.recent-login-date.guest\":[\"2021-05-12T09:25:44.201\"],\"login.login-count.my.docker.com.home\":[\"18\"],\"login.first-login-date.your.docker.com.oss-admin-console\":[\"2021-05-14T13:13:28.924\"],\"login.login-count.your.docker.com.registration-form\":[\"2\"],\"pay_reference\":[\"a\"],\"login.first-login-date.your.docker.com\":[\"2021-05-14T09:02:02.269\"],\"login.login-count.localhost.admin-console\":[\"6\"],\"login.first-login-date.liferay-docker2-saml\":[\"2021-05-14T11:18:34.040\"],\"login.recent-login-date\":[\"2021-05-18T13:40:11.433\"],\"login.login-count.your.docker.com.admin-console\":[\"1\"],\"login.recent-login-date.your.docker.com\":[\"2021-05-18T12:15:04.321\"],\"login.first-login-date.localhost\":[\"2021-04-23T07:58:02.346\"],\"login.recent-login-date.liferay-docker-saml\":[\"2021-05-19T08:38:12.481\"],\"login.login-count.account\":[\"6\"],\"login.recent-login-date.your.docker.com.home\":[\"2021-05-18T11:15:08.346\"],\"login.recent-login-date.liferay.program\":[\"2021-05-19T09:15:40.912\"],\"org_city\":[\"zc\"],\"login.recent-login-date.your.docker.com.admin-console\":[\"2021-05-18T13:40:11.944\"],\"org_country\":[\"afghanistan\"],\"login.first-login-date.liferay.program\":[\"2021-05-19T09:15:40.910\"],\"login.first-login-date.my.docker.com\":[\"2021-05-14T08:47:29.812\"],\"login.recent-login-date.your.docker.com.oss-admin-console\":[\"2021-05-14T13:13:28.924\"],\"login.login-count.my.docker.com\":[\"27\"],\"login.login-count.your.docker.com.my-program\":[\"1\"],\"login.login-count.liferay-docker-saml\":[\"53\"],\"login.first-login-date.account\":[\"2021-05-11T14:35:50.443\"],\"login.login-count.localhost.guest\":[\"36\"],\"locale\":[\"en\"],\"org_postal\":[\"zc\"],\"login.login-count\":[\"25\"],\"login.recent-login-date.my.docker.com.home\":[\"2021-05-18T09:29:13.277\"],\"login.first-login-date.guest\":[\"2021-05-12T09:25:44.199\"],\"login.recent-login-date.liferay-docker2-saml\":[\"2021-05-19T09:15:40.462\"],\"login.first-login-date.liferay-docker-saml\":[\"2021-04-21T09:59:48.905\"],\"login.first-login-date\":[\"2021-05-14T08:51:01.945\"],\"login.login-count.your.docker.com.home\":[\"31\"],\"org_name\":[\"cz\"],\"login.recent-login-date.localhost.guest\":[\"2021-05-14T08:53:04.831\"],\"org_preferred_payment\":[\"bankTransfer\"],\"login.login-count.guest\":[\"1\"],\"login.first-login-date.localhost.guest\":[\"2021-04-21T09:59:49.067\"],\"login.login-count.your.docker.com.oss-admin-console\":[\"1\"],\"login.first-login-date.your.docker.com.program\":[\"2021-05-18T13:00:20.238\"],\"login.login-count.liferay-docker2-saml\":[\"31\"],\"login.recent-login-date.your.docker.com.program\":[\"2021-05-19T08:11:39.800\"],\"login.login-count.liferay.program\":[\"1\"],\"org_vat\":[\"b\"],\"login.first-login-date.your.docker.com.home\":[\"2021-05-14T09:08:02.593\"],\"login.first-login-date.localhost.program\":[\"2021-04-23T09:54:27.726\"]},\"disableableCredentialTypes\":[\"password\"],\"requiredActions\":[],\"notBefore\":1620743754,\"access\":{\"manageGroupMembership\":true,\"view\":true,\"mapRoles\":true,\"impersonate\":false,\"manage\":true}}',NULL,'USER'),('883f67ea-10d2-49da-8136-b530caf8348f',1621412239000,'liferay-portal','UPDATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.18.0.1','authentication/required-actions/terms_and_privacy/raise-priority',NULL,NULL,'REQUIRED_ACTION'),('a0079587-8f3f-4196-b664-de133f45c31b',1621416954000,'liferay-portal','UPDATE','liferay-portal','d377a198-34a9-46a2-9564-f468fb84e9b8','04a65cd9-859e-492e-a312-564d9d1505a7','172.19.0.6','users/fc7b51a0-662e-44ca-8621-5921972d159c','{\"id\":\"fc7b51a0-662e-44ca-8621-5921972d159c\",\"createdTimestamp\":1617882322772,\"username\":\"test\",\"enabled\":true,\"totp\":false,\"emailVerified\":true,\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"test@liferay.com\",\"attributes\":{\"login.login-count.your.docker.com\":[\"17\"],\"login.first-login-date.your.docker.com.admin-console\":[\"2021-05-18T13:40:11.944\"],\"login.recent-login-date.localhost\":[\"2021-05-14T08:58:27.156\"],\"login.recent-login-date.your.docker.com.my-program\":[\"2021-05-19T08:15:03.083\"],\"login.recent-login-date.account\":[\"2021-05-18T13:38:20.858\"],\"login.recent-login-date.localhost.program\":[\"2021-04-23T09:54:27.726\"],\"login.recent-login-date.your.docker.com.registration-form\":[\"2021-05-18T09:20:04.578\"],\"login.login-count.your.docker.com.program\":[\"2\"],\"login.recent-login-date.my.docker.com\":[\"2021-05-14T13:56:08.208\"],\"login.first-login-date.my.docker.com.home\":[\"2021-05-14T08:50:24.964\"],\"login.login-count.localhost.program\":[\"1\"],\"login.first-login-date.your.docker.com.registration-form\":[\"2021-05-17T13:20:26.959\"],\"login.recent-login-date.localhost.admin-console\":[\"2021-05-10T15:22:47.817\"],\"login.first-login-date.localhost.admin-console\":[\"2021-05-06T07:29:33.312\"],\"login.first-login-date.your.docker.com.my-program\":[\"2021-05-19T08:15:03.083\"],\"org_address\":[\"cz\"],\"login.login-count.localhost\":[\"11\"],\"login.login-count.my.docker.com.home\":[\"18\"],\"login.recent-login-date.guest\":[\"2021-05-12T09:25:44.201\"],\"login.first-login-date.your.docker.com.oss-admin-console\":[\"2021-05-14T13:13:28.924\"],\"login.first-login-date.your.docker.com\":[\"2021-05-14T09:02:02.269\"],\"pay_reference\":[\"a\"],\"login.login-count.your.docker.com.registration-form\":[\"2\"],\"login.first-login-date.liferay-docker2-saml\":[\"2021-05-14T11:18:34.040\"],\"login.login-count.localhost.admin-console\":[\"6\"],\"login.recent-login-date\":[\"2021-05-18T13:40:11.433\"],\"login.login-count.your.docker.com.admin-console\":[\"1\"],\"login.recent-login-date.your.docker.com\":[\"2021-05-18T12:15:04.321\"],\"login.first-login-date.localhost\":[\"2021-04-23T07:58:02.346\"],\"login.recent-login-date.liferay-docker-saml\":[\"2021-05-19T09:35:54.196\"],\"login.login-count.account\":[\"6\"],\"login.recent-login-date.your.docker.com.home\":[\"2021-05-18T11:15:08.346\"],\"login.recent-login-date.liferay.program\":[\"2021-05-19T09:18:42.728\"],\"org_city\":[\"zc\"],\"login.recent-login-date.your.docker.com.admin-console\":[\"2021-05-18T13:40:11.944\"],\"login.first-login-date.liferay.program\":[\"2021-05-19T09:15:40.910\"],\"org_country\":[\"afghanistan\"],\"login.first-login-date.my.docker.com\":[\"2021-05-14T08:47:29.812\"],\"login.recent-login-date.your.docker.com.oss-admin-console\":[\"2021-05-14T13:13:28.924\"],\"login.login-count.my.docker.com\":[\"27\"],\"login.login-count.your.docker.com.my-program\":[\"1\"],\"login.login-count.liferay-docker-saml\":[\"54\"],\"login.first-login-date.account\":[\"2021-05-11T14:35:50.443\"],\"login.login-count.localhost.guest\":[\"37\"],\"locale\":[\"en\"],\"org_postal\":[\"zc\"],\"login.login-count\":[\"25\"],\"login.recent-login-date.my.docker.com.home\":[\"2021-05-18T09:29:13.277\"],\"login.first-login-date.guest\":[\"2021-05-12T09:25:44.199\"],\"login.recent-login-date.liferay-docker2-saml\":[\"2021-05-19T09:18:42.522\"],\"login.first-login-date.liferay-docker-saml\":[\"2021-04-21T09:59:48.905\"],\"login.first-login-date\":[\"2021-05-14T08:51:01.945\"],\"login.login-count.your.docker.com.home\":[\"31\"],\"org_name\":[\"cz\"],\"login.recent-login-date.localhost.guest\":[\"2021-05-19T09:35:54.600\"],\"org_preferred_payment\":[\"bankTransfer\"],\"login.login-count.guest\":[\"1\"],\"login.first-login-date.localhost.guest\":[\"2021-04-21T09:59:49.067\"],\"login.first-login-date.your.docker.com.program\":[\"2021-05-18T13:00:20.238\"],\"login.login-count.liferay-docker2-saml\":[\"33\"],\"login.login-count.your.docker.com.oss-admin-console\":[\"1\"],\"login.recent-login-date.your.docker.com.program\":[\"2021-05-19T08:11:39.800\"],\"login.login-count.liferay.program\":[\"3\"],\"login.first-login-date.your.docker.com.home\":[\"2021-05-14T09:08:02.593\"],\"org_vat\":[\"b\"],\"login.first-login-date.localhost.program\":[\"2021-04-23T09:54:27.726\"]},\"disableableCredentialTypes\":[\"password\"],\"requiredActions\":[],\"notBefore\":1620743754,\"access\":{\"manageGroupMembership\":true,\"view\":true,\"mapRoles\":true,\"impersonate\":false,\"manage\":true}}',NULL,'USER'),('b400ca6a-eba7-482f-87c0-f5e7775a796c',1621412247000,'liferay-portal','UPDATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.18.0.1','authentication/required-actions/terms_and_conditions','{\"alias\":\"terms_and_conditions\",\"name\":\"Terms and Conditions\",\"enabled\":false,\"defaultAction\":true,\"priority\":20,\"config\":{}}',NULL,'REQUIRED_ACTION'),('bd9fba5e-a370-48c6-93f4-6c7f6fc10832',1621412244000,'liferay-portal','UPDATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.18.0.1','authentication/required-actions/terms_and_privacy/raise-priority',NULL,NULL,'REQUIRED_ACTION'),('c112d527-9691-48c4-92b3-6ceee92d55d7',1621415672000,'liferay-portal','UPDATE','master','56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6360111f-0e87-4f47-873a-72c07714a985','172.19.0.1','clients/dbe87c24-ee61-418a-9058-85660d151e0e','{\"id\":\"dbe87c24-ee61-418a-9058-85660d151e0e\",\"clientId\":\"liferay-docker-saml\",\"adminUrl\":\"http://my.docker.com:8081/c/portal/saml\",\"surrogateAuthRequired\":false,\"enabled\":true,\"clientAuthenticatorType\":\"client-secret\",\"redirectUris\":[\"http://localhost:8081/*\"],\"webOrigins\":[],\"notBefore\":0,\"bearerOnly\":false,\"consentRequired\":false,\"standardFlowEnabled\":true,\"implicitFlowEnabled\":false,\"directAccessGrantsEnabled\":false,\"serviceAccountsEnabled\":false,\"publicClient\":false,\"frontchannelLogout\":true,\"protocol\":\"saml\",\"attributes\":{\"saml.assertion.signature\":\"false\",\"saml.force.post.binding\":\"true\",\"saml.multivalued.roles\":\"false\",\"saml_single_logout_service_url_post\":\"http://localhost:8081/c/portal/saml/slo\",\"saml.encrypt\":\"false\",\"saml.server.signature\":\"true\",\"saml.server.signature.keyinfo.ext\":\"false\",\"exclude.session.state.from.auth.response\":\"false\",\"saml.signing.certificate\":\"MIIClzCCAX8CBgF5fs82VDANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDDARUZXN0MB4XDTIxMDUxODA5MjkzOVoXDTIyMDUwOTA5MjkzOVowDzENMAsGA1UEAwwEVGVzdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKCdTn9hNHyERjK7DmNNhpMjM4CVS7Rx/SZDhyid79fMU3XZVf6YNIpjTVoYyf03Ff+dGejMvz7KA3UXR9AOBpX0MJaqK5fIf5svVm2gl1ommkDS7UO3PFnUUS33ZjqM7dYa0eAfm5OgsZKytxsBnSv7UBTk9zU7JKpcD2jidcH76kSzeDMKjqusexps0PvFaAxU5fxD4rarrrO9VzyHj2jlun/EC9ymgvPxN96et5qYTMRttmYdxSTLasN6NtL6tgxysBoTMglcz9qd+QgHahA17e2J8hqugf4hrHmeM/uJEjx6iPX+KqmlPdzFbRk6ExzCW+K1Hi7oGHRFiKDv6GUCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAKQ9J7ojmt1hxvrf67VmRKk7MT7l2OBbLfvkZfy+QZBYYlaj3Gn/moFaUWcas24j1Z1SQ/OILIIs25r3VtDSL9eChvJJyoPsqdph2q9d7+i1B9GQm5cRaR8PL5S/pREpdA9mxoh5vR0PdXQrwsH0I6QLY3Z99Mzexb4XaGhPsFLIouayYmGirAL7wujF03eAqcxZJKnyahC46hcKtOgZZYps1x1lEJEbybKB1mlrn0nV5LgyLBBXY7JhnegnA9yEVUSGJYCrwR0Wk9nbf89yTqdIzM+cMBATiH80uzPBP290nsaYjvSP+1agIVzGQTxWjFAFIY222jCAtQmtqhJWozg==\",\"saml_single_logout_service_url_redirect\":\"http://localhost:8081/c/portal/saml/slo\",\"saml.signature.algorithm\":\"RSA_SHA1\",\"saml_force_name_id_format\":\"false\",\"saml.client.signature\":\"true\",\"tls.client.certificate.bound.access.tokens\":\"false\",\"saml.authnstatement\":\"true\",\"display.on.consent.screen\":\"false\",\"saml_name_id_format\":\"email\",\"saml.onetimeuse.condition\":\"false\",\"saml.server.signature.keyinfo.xmlSigKeyInfoKeyNameTransformer\":\"NONE\",\"saml_signature_canonicalization_method\":\"http://www.w3.org/2001/10/xml-exc-c14n#\"},\"authenticationFlowBindingOverrides\":{},\"fullScopeAllowed\":true,\"nodeReRegistrationTimeout\":-1,\"protocolMappers\":[{\"id\":\"96738fc1-152c-415e-9c71-03959adab303\",\"name\":\"lastName\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"lastName\",\"attribute.name\":\"lastName\"}},{\"id\":\"7a774a42-c62b-4a18-b0a9-8331fab8f323\",\"name\":\"username\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"username\",\"attribute.name\":\"screenName\"}},{\"id\":\"f4a18126-e246-4ed1-9438-5b341c01470b\",\"name\":\"email\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"email\",\"attribute.name\":\"emailAddress\"}},{\"id\":\"af711ee2-37f5-4080-aae7-d42dbe6c7f4e\",\"name\":\"firstName\",\"protocol\":\"saml\",\"protocolMapper\":\"saml-user-property-mapper\",\"consentRequired\":false,\"config\":{\"user.attribute\":\"firstName\",\"attribute.name\":\"firstName\"}}],\"defaultClientScopes\":[\"web-origins\",\"role_list\",\"profile\",\"roles\",\"email\"],\"optionalClientScopes\":[\"address\",\"phone\",\"offline_access\"],\"access\":{\"view\":true,\"configure\":true,\"manage\":true}}',NULL,'CLIENT');
/*!40000 ALTER TABLE `ADMIN_EVENT_ENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ASSOCIATED_POLICY`
--

DROP TABLE IF EXISTS `ASSOCIATED_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ASSOCIATED_POLICY` (
  `POLICY_ID` varchar(36) NOT NULL,
  `ASSOCIATED_POLICY_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`POLICY_ID`,`ASSOCIATED_POLICY_ID`),
  KEY `IDX_ASSOC_POL_ASSOC_POL_ID` (`ASSOCIATED_POLICY_ID`),
  CONSTRAINT `FK_FRSR5S213XCX4WNKOG82SSRFY` FOREIGN KEY (`ASSOCIATED_POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`),
  CONSTRAINT `FK_FRSRPAS14XCX4WNKOG82SSRFY` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ASSOCIATED_POLICY`
--

LOCK TABLES `ASSOCIATED_POLICY` WRITE;
/*!40000 ALTER TABLE `ASSOCIATED_POLICY` DISABLE KEYS */;
INSERT INTO `ASSOCIATED_POLICY` VALUES ('1d1be5b2-368b-4f73-91ef-f5fff682aa73','eca0e811-43db-41f8-a3b9-5d37f235e1b3'),('464a4f94-3ebe-4b25-a773-43004e2e73d6','8119b5a3-d21e-4c4b-bac8-134c495e0a77'),('c57f7c57-d56d-44d5-bbb2-ebf557f24688','ccef7efc-fb1f-4a55-8429-bac1e0261314'),('d66a85ba-62e8-49ed-b0cc-29ab865a86e5','7e75f663-76ca-42cc-92ce-732e3caf409a');
/*!40000 ALTER TABLE `ASSOCIATED_POLICY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AUTHENTICATION_EXECUTION`
--

DROP TABLE IF EXISTS `AUTHENTICATION_EXECUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHENTICATION_EXECUTION` (
  `ID` varchar(36) NOT NULL,
  `ALIAS` varchar(255) DEFAULT NULL,
  `AUTHENTICATOR` varchar(36) DEFAULT NULL,
  `REALM_ID` varchar(36) DEFAULT NULL,
  `FLOW_ID` varchar(36) DEFAULT NULL,
  `REQUIREMENT` int(11) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `AUTHENTICATOR_FLOW` bit(1) NOT NULL DEFAULT b'0',
  `AUTH_FLOW_ID` varchar(36) DEFAULT NULL,
  `AUTH_CONFIG` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_AUTH_EXEC_REALM_FLOW` (`REALM_ID`,`FLOW_ID`),
  KEY `IDX_AUTH_EXEC_FLOW` (`FLOW_ID`),
  CONSTRAINT `FK_AUTH_EXEC_FLOW` FOREIGN KEY (`FLOW_ID`) REFERENCES `AUTHENTICATION_FLOW` (`ID`),
  CONSTRAINT `FK_AUTH_EXEC_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHENTICATION_EXECUTION`
--

LOCK TABLES `AUTHENTICATION_EXECUTION` WRITE;
/*!40000 ALTER TABLE `AUTHENTICATION_EXECUTION` DISABLE KEYS */;
INSERT INTO `AUTHENTICATION_EXECUTION` VALUES ('01a8b830-b215-436b-aef0-2b9a02146f0f',NULL,'registration-profile-action','liferay-portal','b4160b75-0dea-4e50-bcdf-2a20febadb92',0,40,'\0',NULL,NULL),('0231ab6f-8306-44d1-bd0c-304fab5006b8',NULL,NULL,'liferay-portal','07b51b53-ee3a-4223-aa83-a6bdb9f8928e',2,30,'','f1bee9b6-8824-4563-8f1d-ed9597add0ba',NULL),('0266e5de-fe8c-4643-8789-4f06575b6741',NULL,NULL,'master','7614a21f-c34b-4b95-8de0-59a6d4254acb',2,30,'','c1307f95-be2f-4b27-ae4f-4f6f230404a0',NULL),('04af8e2d-5c04-43b3-baf8-91105b229bda',NULL,'auth-cookie','liferay-portal','6ee5e6cc-eb53-4947-90e2-af4216ec5de0',2,10,'\0',NULL,NULL),('0527cc5a-da65-4b95-aae7-f0ea2a281250',NULL,'auth-spnego','master','e75011ee-5899-46c9-8f16-bafa3003fedc',3,20,'\0',NULL,NULL),('076bdb47-06a6-46c3-944d-2b84e54746c7',NULL,NULL,'liferay-portal','bb998170-2e53-4226-9854-49a8697e2167',2,30,'','370a3614-81d4-4907-a87e-31e5fc764294',NULL),('08f120dd-1757-43a9-b53e-c2fe7ead9e23',NULL,'direct-grant-validate-username','master','e1b2d379-14e4-4585-8060-548924a4e1a4',0,10,'\0',NULL,NULL),('0a53fddd-58b2-4fce-80e6-f5c2b3f754a8',NULL,'registration-profile-action','liferay-portal','c8d9730a-0b82-4254-b128-65327a9820c0',0,60,'\0',NULL,NULL),('124b83c0-78fe-4a61-8e2b-6d2f4eb96abc',NULL,'auth-otp-form','liferay-portal','370a3614-81d4-4907-a87e-31e5fc764294',1,20,'\0',NULL,NULL),('18b6716c-7677-45ad-addf-be989f4c30c4',NULL,'reset-credential-email','liferay-portal','338d3c95-8e22-463f-af1a-73fa3dedae37',0,20,'\0',NULL,NULL),('193c5432-b081-48f3-b27d-9640b27e9832',NULL,'reset-credential-email','master','4b647958-8075-4fb0-b549-5487a63f9e4d',0,20,'\0',NULL,NULL),('1dd753b2-e922-42b1-a2bc-4c599f74e547',NULL,'registration-page-form','liferay-portal','590ca870-9a16-42b4-8874-02a16b754e91',0,10,'','c8d9730a-0b82-4254-b128-65327a9820c0',NULL),('21773569-7bdb-4cad-96c5-c26ec6da45c3',NULL,'registration-user-creation','master','442a5ee1-4771-4344-8419-6746407eae97',0,20,'\0',NULL,NULL),('236f2613-5c02-41f8-b36e-dbdb4eea30f7',NULL,'idp-create-user-if-unique','liferay-portal','ec540d8a-a156-427a-a4b9-b8347b40fc2d',2,20,'\0',NULL,'8cf58761-33cf-4799-adfe-24b738b61bfb'),('24afb150-791d-41b3-8d12-88f9407144b3',NULL,'registration-user-creation','liferay-portal','b4160b75-0dea-4e50-bcdf-2a20febadb92',0,20,'\0',NULL,NULL),('30dc7ad9-c5c5-49db-9c4b-d03cf19e6606',NULL,'identity-provider-redirector','master','e75011ee-5899-46c9-8f16-bafa3003fedc',2,25,'\0',NULL,NULL),('329fe434-89eb-48f3-a212-c6802b46d2ac',NULL,'auth-username-password-form','master','4a13e413-5fc6-4bc2-9981-02eb6da349fe',0,10,'\0',NULL,NULL),('34794323-ccb8-491f-8361-6306bfc5e018',NULL,'deltares-auth-username-password-form','liferay-portal','dcc6ee87-d0dd-4895-aca2-eeb06e74458e',0,20,'\0',NULL,NULL),('37576e31-1467-4ee2-bd2d-b22426bfedd6',NULL,'client-jwt','master','b37eaf99-a47d-4e9d-8bbd-e909ba16a0cf',2,20,'\0',NULL,NULL),('3f4a9598-55b1-4744-a7d6-ebc68ce659ce',NULL,'reset-credentials-choose-user','liferay-portal','338d3c95-8e22-463f-af1a-73fa3dedae37',0,10,'\0',NULL,NULL),('41864ad3-0fd7-47f0-a273-a89b994a2d8b',NULL,'idp-confirm-link','liferay-portal','bb998170-2e53-4226-9854-49a8697e2167',0,10,'\0',NULL,NULL),('419b1f59-b5b1-42a7-9c2d-fdf926c53919',NULL,NULL,'master','e75011ee-5899-46c9-8f16-bafa3003fedc',2,30,'','4a13e413-5fc6-4bc2-9981-02eb6da349fe',NULL),('558c3c8e-a6c9-4251-a694-7847a46d4ce1',NULL,'reset-otp','liferay-portal','338d3c95-8e22-463f-af1a-73fa3dedae37',1,40,'\0',NULL,NULL),('5594a26c-1eb0-41c4-a192-7144ecd129f0',NULL,'reset-password','master','4b647958-8075-4fb0-b549-5487a63f9e4d',0,30,'\0',NULL,NULL),('57d35851-5409-421a-8c08-bcb3a0fa4330',NULL,'registration-page-form','master','2128ac0c-dbda-4e7d-81a0-db457aa4b2ef',0,10,'','442a5ee1-4771-4344-8419-6746407eae97',NULL),('58a66725-2a46-47cc-9dab-e0d34961a076',NULL,'identity-provider-redirector','liferay-portal','6ee5e6cc-eb53-4947-90e2-af4216ec5de0',2,25,'\0',NULL,NULL),('5b41730f-0f68-4831-a0e8-eb7ce5849bf7',NULL,'auth-spnego','master','68dad491-d1a9-4311-8231-01af5d75a746',3,40,'\0',NULL,NULL),('5bb513e5-d653-4771-b2d8-f29986c4096c',NULL,'http-basic-authenticator','master','641e66e0-bde8-4306-8e38-77bf8c3aeb81',0,10,'\0',NULL,NULL),('5d6abae3-49a9-4795-8846-d7a760c5716e',NULL,'direct-grant-validate-username','liferay-portal','ee89edb5-990d-479c-9204-33c657204877',0,10,'\0',NULL,NULL),('5f15bac9-c456-4627-8c56-0ba059da19cd',NULL,'direct-grant-validate-password','master','e1b2d379-14e4-4585-8060-548924a4e1a4',0,20,'\0',NULL,NULL),('644f27e3-c94c-4619-8dd2-44b95d7bfda2',NULL,'auth-otp-form','master','c1307f95-be2f-4b27-ae4f-4f6f230404a0',1,20,'\0',NULL,NULL),('6748bd31-19c7-4e14-a739-aef762bbc5c9',NULL,'auth-spnego','liferay-portal','219a8707-abde-42a2-9539-d02c01a46b5b',3,40,'\0',NULL,NULL),('6a519d1f-598c-42eb-a9af-ab7511b3daf8',NULL,'auth-spnego','liferay-portal','6ee5e6cc-eb53-4947-90e2-af4216ec5de0',3,20,'\0',NULL,NULL),('6d32aa70-6981-4359-9a75-b06d1e39d1fc',NULL,'reset-credentials-choose-user','master','4b647958-8075-4fb0-b549-5487a63f9e4d',0,10,'\0',NULL,NULL),('6f7a08ee-4804-458e-ae29-aba161146dc4',NULL,'idp-email-verification','liferay-portal','bb998170-2e53-4226-9854-49a8697e2167',2,20,'\0',NULL,NULL),('700886d8-2f17-4c34-954c-31a13cd8d89f',NULL,'auth-username-password-form','liferay-portal','f1bee9b6-8824-4563-8f1d-ed9597add0ba',0,10,'\0',NULL,NULL),('742882cb-df62-4904-af1c-ee1d84330876',NULL,'idp-create-user-if-unique','master','fdc24500-5ddf-4f3a-b766-38822e438db0',2,20,'\0',NULL,'5343141e-6cea-475a-a374-edc1d692369d'),('76f51bfa-0b78-47f3-8b33-680a48755513',NULL,'client-secret-jwt','liferay-portal','19d7a544-e0b6-41ac-872f-e8b957353cce',2,30,'\0',NULL,NULL),('77c0c8a5-4138-4260-acad-342938e768b9',NULL,'docker-http-basic-authenticator','master','553687d3-9524-495a-88d3-98bc0ccb616e',0,10,'\0',NULL,NULL),('783052e6-a2ac-4c98-8c17-0b03e71fe651',NULL,'client-secret','master','b37eaf99-a47d-4e9d-8bbd-e909ba16a0cf',2,10,'\0',NULL,NULL),('78a2f6b9-d1d0-4c70-bd84-790ae51d911e',NULL,'registration-profile-action','master','442a5ee1-4771-4344-8419-6746407eae97',0,40,'\0',NULL,NULL),('78fa1449-f05b-4273-afd3-e21cae108bbe',NULL,'direct-grant-validate-otp','liferay-portal','ee89edb5-990d-479c-9204-33c657204877',1,30,'\0',NULL,NULL),('7c2d2295-9dc9-4240-a7c4-c22d87a39cfc',NULL,'client-secret','liferay-portal','19d7a544-e0b6-41ac-872f-e8b957353cce',2,10,'\0',NULL,NULL),('800d9021-97bf-443d-8d70-90e22332f65f',NULL,'direct-grant-validate-otp','master','e1b2d379-14e4-4585-8060-548924a4e1a4',1,30,'\0',NULL,NULL),('82b32e18-4288-4090-b934-18b2fee41b9a',NULL,'auth-otp-form','liferay-portal','dcc6ee87-d0dd-4895-aca2-eeb06e74458e',1,21,'\0',NULL,NULL),('82b5a5bf-126a-4aca-a239-d10bc8a80044',NULL,'reset-password','liferay-portal','338d3c95-8e22-463f-af1a-73fa3dedae37',0,30,'\0',NULL,NULL),('839dc34a-ad9a-40b1-abad-fc76316f3815',NULL,'auth-cookie','master','e75011ee-5899-46c9-8f16-bafa3003fedc',2,10,'\0',NULL,NULL),('8403ef04-9fa3-4895-a1d8-e68d959179d8',NULL,'idp-confirm-link','master','7614a21f-c34b-4b95-8de0-59a6d4254acb',0,10,'\0',NULL,NULL),('84f53231-35dc-46dd-8e2d-b01f7e3411e1',NULL,'basic-auth-otp','liferay-portal','219a8707-abde-42a2-9539-d02c01a46b5b',3,30,'\0',NULL,NULL),('8967a4c5-d40c-4ece-8756-1d93367bf36a',NULL,NULL,'liferay-portal','6ee5e6cc-eb53-4947-90e2-af4216ec5de0',2,30,'','dcc6ee87-d0dd-4895-aca2-eeb06e74458e',NULL),('8e217c46-0448-4f81-9a28-b5b28418c50b',NULL,'idp-username-password-form','liferay-portal','370a3614-81d4-4907-a87e-31e5fc764294',0,10,'\0',NULL,NULL),('8e76a8cf-c11d-4efd-b97e-1b7818c92a9e',NULL,'registration-password-action','liferay-portal','b4160b75-0dea-4e50-bcdf-2a20febadb92',0,50,'\0',NULL,NULL),('9f2180d0-0fac-4450-84c3-b071fb69f47b',NULL,'idp-review-profile','master','fdc24500-5ddf-4f3a-b766-38822e438db0',0,10,'\0',NULL,'4b898e3c-3f10-4cc7-96af-b0d26baca582'),('a59ab065-e0bc-4b58-8f1f-af800f614e50',NULL,'auth-otp-form','liferay-portal','f1bee9b6-8824-4563-8f1d-ed9597add0ba',1,20,'\0',NULL,NULL),('a5b07032-9aa4-4ded-8c94-fc9ca5cc3be8',NULL,'reset-otp','master','4b647958-8075-4fb0-b549-5487a63f9e4d',1,40,'\0',NULL,NULL),('a62212fb-bc21-4550-8273-e919fee5be2a',NULL,'identity-provider-redirector','liferay-portal','07b51b53-ee3a-4223-aa83-a6bdb9f8928e',2,25,'\0',NULL,NULL),('aa1fc612-b254-4ba6-a9dd-19e38a3a6a6a',NULL,'registration-password-action','liferay-portal','c8d9730a-0b82-4254-b128-65327a9820c0',0,61,'\0',NULL,NULL),('aaaf486d-4695-4f3c-b63b-62ac3d333626',NULL,'registration-recaptcha-action','master','442a5ee1-4771-4344-8419-6746407eae97',3,60,'\0',NULL,NULL),('b078dc5c-c65b-4f08-bb19-3f86b70d4ace',NULL,NULL,'liferay-portal','ec540d8a-a156-427a-a4b9-b8347b40fc2d',2,30,'','bb998170-2e53-4226-9854-49a8697e2167',NULL),('b1bac1ec-e8e2-4065-8ebc-339ed01185cb',NULL,'auth-otp-form','master','4a13e413-5fc6-4bc2-9981-02eb6da349fe',1,20,'\0',NULL,NULL),('b588f6e0-bf27-40ef-ab6f-ac11170889e7',NULL,'no-cookie-redirect','liferay-portal','219a8707-abde-42a2-9539-d02c01a46b5b',0,10,'\0',NULL,NULL),('b7447121-b16e-4989-8791-e6f0c91de5ff',NULL,'registration-deltares-user-action','liferay-portal','c8d9730a-0b82-4254-b128-65327a9820c0',0,40,'\0',NULL,NULL),('bb711c66-3d09-48b0-af42-d95d7ce27259',NULL,'direct-grant-validate-password','liferay-portal','ee89edb5-990d-479c-9204-33c657204877',0,20,'\0',NULL,NULL),('bb931e59-e1d5-45ba-838f-c68a7ed89677',NULL,'no-cookie-redirect','master','68dad491-d1a9-4311-8231-01af5d75a746',0,10,'\0',NULL,NULL),('be401b19-d26d-4379-a765-facd3c75e63a',NULL,'client-x509','master','b37eaf99-a47d-4e9d-8bbd-e909ba16a0cf',2,40,'\0',NULL,NULL),('bf189df9-f533-4c99-9757-4bcc5c01c6f8',NULL,'idp-username-password-form','master','c1307f95-be2f-4b27-ae4f-4f6f230404a0',0,10,'\0',NULL,NULL),('c00cba38-3eed-4597-a7ed-9554f28f4542',NULL,NULL,'master','fdc24500-5ddf-4f3a-b766-38822e438db0',2,30,'','7614a21f-c34b-4b95-8de0-59a6d4254acb',NULL),('c0cfa377-bfd6-403e-b5c7-bd92679fc4bd',NULL,'registration-user-creation','liferay-portal','c8d9730a-0b82-4254-b128-65327a9820c0',0,50,'\0',NULL,NULL),('c1471d05-a347-43bc-af26-d1dadc3977db',NULL,'registration-deltares-email-action','liferay-portal','c8d9730a-0b82-4254-b128-65327a9820c0',0,20,'\0',NULL,NULL),('c192a3d2-1193-49cc-861b-bf3404a28acf',NULL,'http-basic-authenticator','liferay-portal','38654633-b0d8-4d3b-86d6-b61ab9c0b451',0,10,'\0',NULL,NULL),('c5c08c0b-8cbd-459b-998e-1f4c565f2424',NULL,'client-jwt','liferay-portal','19d7a544-e0b6-41ac-872f-e8b957353cce',2,20,'\0',NULL,NULL),('ca2def84-32cf-4fe7-83cc-d274f86135ac',NULL,'client-secret-jwt','master','b37eaf99-a47d-4e9d-8bbd-e909ba16a0cf',2,30,'\0',NULL,NULL),('d3f04476-3fbe-41f2-af6d-154949d08be7',NULL,'idp-email-verification','master','7614a21f-c34b-4b95-8de0-59a6d4254acb',2,20,'\0',NULL,NULL),('d691fdb3-7989-4e25-9194-b76cc1487b63',NULL,'basic-auth','liferay-portal','219a8707-abde-42a2-9539-d02c01a46b5b',0,20,'\0',NULL,NULL),('d7c0a156-f6bd-4e13-b0ac-79fd8ce85ea3',NULL,'basic-auth','master','68dad491-d1a9-4311-8231-01af5d75a746',0,20,'\0',NULL,NULL),('db3bf73a-0a68-4e24-84a2-013b88ff00e8',NULL,'registration-password-action','master','442a5ee1-4771-4344-8419-6746407eae97',0,50,'\0',NULL,NULL),('e1b9a3f9-9875-4d04-8735-9f324d6ca2a5',NULL,'auth-spnego','liferay-portal','07b51b53-ee3a-4223-aa83-a6bdb9f8928e',3,20,'\0',NULL,NULL),('e48d312d-47fa-4130-aa94-b51ce09e60ce',NULL,'registration-recaptcha-action','liferay-portal','c8d9730a-0b82-4254-b128-65327a9820c0',3,62,'\0',NULL,NULL),('e4e94fab-68b5-438c-836b-45073a8f1039',NULL,'registration-page-form','liferay-portal','8a915fdf-8e7f-4a1a-8db4-c78a8850b168',0,10,'','b4160b75-0dea-4e50-bcdf-2a20febadb92',NULL),('e83e1621-c1fc-408f-91bb-4751ad818604',NULL,'client-x509','liferay-portal','19d7a544-e0b6-41ac-872f-e8b957353cce',2,40,'\0',NULL,NULL),('ec11217d-b955-4356-8812-019d06512745',NULL,'docker-http-basic-authenticator','liferay-portal','438193d9-170f-4439-b56c-c0d7cb1183a7',0,10,'\0',NULL,NULL),('f11195bf-ea1e-4810-b3e2-01fcb9004f5e',NULL,'idp-review-profile','liferay-portal','ec540d8a-a156-427a-a4b9-b8347b40fc2d',0,10,'\0',NULL,'75288a6b-025b-402f-8400-8fa6345b4b7e'),('f2d25d10-6c1c-4e0b-85e0-a230a3a4c2c0',NULL,'basic-auth-otp','master','68dad491-d1a9-4311-8231-01af5d75a746',3,30,'\0',NULL,NULL),('fb732093-d5d1-4a3e-b67e-cbece37929b5',NULL,'registration-recaptcha-action','liferay-portal','b4160b75-0dea-4e50-bcdf-2a20febadb92',3,60,'\0',NULL,NULL),('ffb6c8c7-b0e2-4c97-849c-cbdac6871a09',NULL,'auth-cookie','liferay-portal','07b51b53-ee3a-4223-aa83-a6bdb9f8928e',2,10,'\0',NULL,NULL);
/*!40000 ALTER TABLE `AUTHENTICATION_EXECUTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AUTHENTICATION_FLOW`
--

DROP TABLE IF EXISTS `AUTHENTICATION_FLOW`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHENTICATION_FLOW` (
  `ID` varchar(36) NOT NULL,
  `ALIAS` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) DEFAULT NULL,
  `PROVIDER_ID` varchar(36) NOT NULL DEFAULT 'basic-flow',
  `TOP_LEVEL` bit(1) NOT NULL DEFAULT b'0',
  `BUILT_IN` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`ID`),
  KEY `IDX_AUTH_FLOW_REALM` (`REALM_ID`),
  CONSTRAINT `FK_AUTH_FLOW_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHENTICATION_FLOW`
--

LOCK TABLES `AUTHENTICATION_FLOW` WRITE;
/*!40000 ALTER TABLE `AUTHENTICATION_FLOW` DISABLE KEYS */;
INSERT INTO `AUTHENTICATION_FLOW` VALUES ('07b51b53-ee3a-4223-aa83-a6bdb9f8928e','browser','browser based authentication','liferay-portal','basic-flow','',''),('19d7a544-e0b6-41ac-872f-e8b957353cce','clients','Base authentication for clients','liferay-portal','client-flow','',''),('2128ac0c-dbda-4e7d-81a0-db457aa4b2ef','registration','registration flow','master','basic-flow','',''),('219a8707-abde-42a2-9539-d02c01a46b5b','http challenge','An authentication flow based on challenge-response HTTP Authentication Schemes','liferay-portal','basic-flow','',''),('338d3c95-8e22-463f-af1a-73fa3dedae37','reset credentials','Reset credentials for a user if they forgot their password or something','liferay-portal','basic-flow','',''),('370a3614-81d4-4907-a87e-31e5fc764294','Verify Existing Account by Re-authentication','Reauthentication of existing account','liferay-portal','basic-flow','\0',''),('38654633-b0d8-4d3b-86d6-b61ab9c0b451','saml ecp','SAML ECP Profile Authentication Flow','liferay-portal','basic-flow','',''),('438193d9-170f-4439-b56c-c0d7cb1183a7','docker auth','Used by Docker clients to authenticate against the IDP','liferay-portal','basic-flow','',''),('442a5ee1-4771-4344-8419-6746407eae97','registration form','registration form','master','form-flow','\0',''),('4a13e413-5fc6-4bc2-9981-02eb6da349fe','forms','Username, password, otp and other auth forms.','master','basic-flow','\0',''),('4b647958-8075-4fb0-b549-5487a63f9e4d','reset credentials','Reset credentials for a user if they forgot their password or something','master','basic-flow','',''),('553687d3-9524-495a-88d3-98bc0ccb616e','docker auth','Used by Docker clients to authenticate against the IDP','master','basic-flow','',''),('590ca870-9a16-42b4-8874-02a16b754e91','deltares registration','registration flow','liferay-portal','basic-flow','','\0'),('641e66e0-bde8-4306-8e38-77bf8c3aeb81','saml ecp','SAML ECP Profile Authentication Flow','master','basic-flow','',''),('68dad491-d1a9-4311-8231-01af5d75a746','http challenge','An authentication flow based on challenge-response HTTP Authentication Schemes','master','basic-flow','',''),('6ee5e6cc-eb53-4947-90e2-af4216ec5de0','deltares browser','browser based authentication','liferay-portal','basic-flow','','\0'),('7614a21f-c34b-4b95-8de0-59a6d4254acb','Handle Existing Account','Handle what to do if there is existing account with same email/username like authenticated identity provider','master','basic-flow','\0',''),('8a915fdf-8e7f-4a1a-8db4-c78a8850b168','registration','registration flow','liferay-portal','basic-flow','',''),('b37eaf99-a47d-4e9d-8bbd-e909ba16a0cf','clients','Base authentication for clients','master','client-flow','',''),('b4160b75-0dea-4e50-bcdf-2a20febadb92','registration form','registration form','liferay-portal','form-flow','\0',''),('bb998170-2e53-4226-9854-49a8697e2167','Handle Existing Account','Handle what to do if there is existing account with same email/username like authenticated identity provider','liferay-portal','basic-flow','\0',''),('c1307f95-be2f-4b27-ae4f-4f6f230404a0','Verify Existing Account by Re-authentication','Reauthentication of existing account','master','basic-flow','\0',''),('c8d9730a-0b82-4254-b128-65327a9820c0','deltares registration registration form','registration form','liferay-portal','form-flow','\0','\0'),('dcc6ee87-d0dd-4895-aca2-eeb06e74458e','deltares browser forms','Username, password, otp and other auth forms.','liferay-portal','basic-flow','\0','\0'),('e1b2d379-14e4-4585-8060-548924a4e1a4','direct grant','OpenID Connect Resource Owner Grant','master','basic-flow','',''),('e75011ee-5899-46c9-8f16-bafa3003fedc','browser','browser based authentication','master','basic-flow','',''),('ec540d8a-a156-427a-a4b9-b8347b40fc2d','first broker login','Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account','liferay-portal','basic-flow','',''),('ee89edb5-990d-479c-9204-33c657204877','direct grant','OpenID Connect Resource Owner Grant','liferay-portal','basic-flow','',''),('f1bee9b6-8824-4563-8f1d-ed9597add0ba','forms','Username, password, otp and other auth forms.','liferay-portal','basic-flow','\0',''),('fdc24500-5ddf-4f3a-b766-38822e438db0','first broker login','Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account','master','basic-flow','','');
/*!40000 ALTER TABLE `AUTHENTICATION_FLOW` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AUTHENTICATOR_CONFIG`
--

DROP TABLE IF EXISTS `AUTHENTICATOR_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHENTICATOR_CONFIG` (
  `ID` varchar(36) NOT NULL,
  `ALIAS` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_AUTH_CONFIG_REALM` (`REALM_ID`),
  CONSTRAINT `FK_AUTH_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHENTICATOR_CONFIG`
--

LOCK TABLES `AUTHENTICATOR_CONFIG` WRITE;
/*!40000 ALTER TABLE `AUTHENTICATOR_CONFIG` DISABLE KEYS */;
INSERT INTO `AUTHENTICATOR_CONFIG` VALUES ('4b898e3c-3f10-4cc7-96af-b0d26baca582','review profile config','master'),('5343141e-6cea-475a-a374-edc1d692369d','create unique user config','master'),('75288a6b-025b-402f-8400-8fa6345b4b7e','review profile config','liferay-portal'),('8cf58761-33cf-4799-adfe-24b738b61bfb','create unique user config','liferay-portal');
/*!40000 ALTER TABLE `AUTHENTICATOR_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AUTHENTICATOR_CONFIG_ENTRY`
--

DROP TABLE IF EXISTS `AUTHENTICATOR_CONFIG_ENTRY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHENTICATOR_CONFIG_ENTRY` (
  `AUTHENTICATOR_ID` varchar(36) NOT NULL,
  `VALUE` longtext,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`AUTHENTICATOR_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHENTICATOR_CONFIG_ENTRY`
--

LOCK TABLES `AUTHENTICATOR_CONFIG_ENTRY` WRITE;
/*!40000 ALTER TABLE `AUTHENTICATOR_CONFIG_ENTRY` DISABLE KEYS */;
INSERT INTO `AUTHENTICATOR_CONFIG_ENTRY` VALUES ('4b898e3c-3f10-4cc7-96af-b0d26baca582','missing','update.profile.on.first.login'),('5343141e-6cea-475a-a374-edc1d692369d','false','require.password.update.after.registration'),('75288a6b-025b-402f-8400-8fa6345b4b7e','missing','update.profile.on.first.login'),('8cf58761-33cf-4799-adfe-24b738b61bfb','false','require.password.update.after.registration');
/*!40000 ALTER TABLE `AUTHENTICATOR_CONFIG_ENTRY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BROKER_LINK`
--

DROP TABLE IF EXISTS `BROKER_LINK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BROKER_LINK` (
  `IDENTITY_PROVIDER` varchar(255) NOT NULL,
  `STORAGE_PROVIDER_ID` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `BROKER_USER_ID` varchar(255) DEFAULT NULL,
  `BROKER_USERNAME` varchar(255) DEFAULT NULL,
  `TOKEN` text,
  `USER_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`IDENTITY_PROVIDER`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BROKER_LINK`
--

LOCK TABLES `BROKER_LINK` WRITE;
/*!40000 ALTER TABLE `BROKER_LINK` DISABLE KEYS */;
/*!40000 ALTER TABLE `BROKER_LINK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT`
--

DROP TABLE IF EXISTS `CLIENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT` (
  `ID` varchar(36) NOT NULL,
  `ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `FULL_SCOPE_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
  `CLIENT_ID` varchar(255) DEFAULT NULL,
  `NOT_BEFORE` int(11) DEFAULT NULL,
  `PUBLIC_CLIENT` bit(1) NOT NULL DEFAULT b'0',
  `SECRET` varchar(255) DEFAULT NULL,
  `BASE_URL` varchar(255) DEFAULT NULL,
  `BEARER_ONLY` bit(1) NOT NULL DEFAULT b'0',
  `MANAGEMENT_URL` varchar(255) DEFAULT NULL,
  `SURROGATE_AUTH_REQUIRED` bit(1) NOT NULL DEFAULT b'0',
  `REALM_ID` varchar(36) DEFAULT NULL,
  `PROTOCOL` varchar(255) DEFAULT NULL,
  `NODE_REREG_TIMEOUT` int(11) DEFAULT '0',
  `FRONTCHANNEL_LOGOUT` bit(1) NOT NULL DEFAULT b'0',
  `CONSENT_REQUIRED` bit(1) NOT NULL DEFAULT b'0',
  `NAME` varchar(255) DEFAULT NULL,
  `SERVICE_ACCOUNTS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `CLIENT_AUTHENTICATOR_TYPE` varchar(255) DEFAULT NULL,
  `ROOT_URL` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `REGISTRATION_TOKEN` varchar(255) DEFAULT NULL,
  `STANDARD_FLOW_ENABLED` bit(1) NOT NULL DEFAULT b'1',
  `IMPLICIT_FLOW_ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `DIRECT_ACCESS_GRANTS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_B71CJLBENV945RB6GCON438AT` (`REALM_ID`,`CLIENT_ID`),
  CONSTRAINT `FK_P56CTINXXB9GSK57FO49F9TAC` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT`
--

LOCK TABLES `CLIENT` WRITE;
/*!40000 ALTER TABLE `CLIENT` DISABLE KEYS */;
INSERT INTO `CLIENT` VALUES ('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','','','admin-api-admin',0,'\0','**********',NULL,'\0',NULL,'\0','liferay-portal','openid-connect',-1,'\0','\0',NULL,'','client-secret',NULL,NULL,NULL,'\0','\0',''),('1e563445-4114-4608-b5dc-0e6626b2732c','','\0','admin-cli',0,'','039a27b5-689e-4819-a29f-34f53af07abc',NULL,'\0',NULL,'\0','master','openid-connect',0,'\0','\0','${client_admin-cli}','\0','client-secret',NULL,NULL,NULL,'\0','\0',''),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','','','admin-api-viewer',0,'\0','**********',NULL,'\0',NULL,'\0','liferay-portal','openid-connect',-1,'\0','\0',NULL,'','client-secret',NULL,NULL,NULL,'\0','\0',''),('364c3e71-9195-4162-9d08-e048421fdd0f','','\0','security-admin-console',0,'','**********','/auth/admin/liferay-portal/console/index.html','\0',NULL,'\0','liferay-portal','openid-connect',0,'\0','\0','${client_security-admin-console}','\0','client-secret',NULL,NULL,NULL,'','\0','\0'),('434f2a4d-52d7-4823-badc-54b9835ec942','','\0','admin-cli',0,'','**********',NULL,'\0',NULL,'\0','liferay-portal','openid-connect',0,'\0','\0','${client_admin-cli}','\0','client-secret',NULL,NULL,NULL,'\0','\0',''),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','','\0','security-admin-console',0,'','4f0bf00c-5bca-42c3-ac89-f4f279549ba6','/auth/admin/master/console/index.html','\0',NULL,'\0','master','openid-connect',0,'\0','\0','${client_security-admin-console}','\0','client-secret',NULL,NULL,NULL,'','\0','\0'),('67d6d3e3-38a7-4562-aac7-03832d713532','','','user-api',0,'\0','**********',NULL,'\0',NULL,'\0','liferay-portal','openid-connect',-1,'\0','\0',NULL,'','client-secret',NULL,NULL,NULL,'','\0',''),('7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','\0','realm-management',0,'\0','**********',NULL,'\0',NULL,'\0','liferay-portal','openid-connect',0,'\0','\0','${client_realm-management}','','client-secret',NULL,NULL,NULL,'','\0','\0'),('9b3c4d13-0a92-46a4-a07c-e412e33024d7','','\0','broker',0,'\0','**********',NULL,'\0',NULL,'\0','liferay-portal','openid-connect',0,'\0','\0','${client_broker}','\0','client-secret',NULL,NULL,NULL,'','\0','\0'),('b39c0c40-7753-42c6-9356-694c4e7182dc','','','master-realm',0,'\0','56319af0-c285-45b1-a781-1515998f1fb5',NULL,'',NULL,'\0','master',NULL,0,'\0','\0','master Realm','\0','client-secret',NULL,NULL,NULL,'','\0','\0'),('b8a62fae-771b-402a-8515-1b13b025da91','','','liferay-docker2-saml',0,'\0','4be94952-8075-444b-aab2-e960fc3d5b76',NULL,'\0','http://liferay:8081/c/portal/saml','\0','liferay-portal','saml',-1,'','\0',NULL,'\0','client-secret',NULL,NULL,NULL,'','\0','\0'),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','','\0','broker',0,'\0','17b98d1c-b7d0-4ef2-a794-47e98f0ee9da',NULL,'\0',NULL,'\0','master','openid-connect',0,'\0','\0','${client_broker}','\0','client-secret',NULL,NULL,NULL,'','\0','\0'),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','','liferay-portal-realm',0,'\0','f7b0b9d8-c7ff-4dad-867c-188b171d9671',NULL,'',NULL,'\0','master',NULL,0,'\0','\0','liferay-portal Realm','\0','client-secret',NULL,NULL,NULL,'','\0','\0'),('d377a198-34a9-46a2-9564-f468fb84e9b8','','','oss-accounts',0,'\0','eee6a051-728d-47c6-b9c2-7ac7972a53f8',NULL,'\0',NULL,'\0','liferay-portal','openid-connect',-1,'\0','\0',NULL,'','client-secret',NULL,NULL,NULL,'\0','\0',''),('dbe87c24-ee61-418a-9058-85660d151e0e','','','liferay-docker-saml',0,'\0','**********',NULL,'\0','http://localhost:8081/c/portal/saml','\0','liferay-portal','saml',-1,'','\0',NULL,'\0','client-secret',NULL,NULL,NULL,'','\0','\0'),('f1b13f7c-a916-4b24-b314-0200496926ce','','\0','account',0,'\0','e09f5761-0e3f-465e-8636-0188fc1b78c7','/auth/realms/liferay-portal/account','\0',NULL,'\0','liferay-portal','openid-connect',0,'\0','\0','${client_account}','\0','client-secret',NULL,NULL,NULL,'','\0','\0'),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','','\0','account',0,'\0','579ef92b-307d-4cb9-ad95-bf68bfd227d7','/auth/realms/master/account','\0',NULL,'\0','master','openid-connect',0,'\0','\0','${client_account}','\0','client-secret',NULL,NULL,NULL,'','\0','\0');
/*!40000 ALTER TABLE `CLIENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_ATTRIBUTES`
--

DROP TABLE IF EXISTS `CLIENT_ATTRIBUTES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_ATTRIBUTES` (
  `CLIENT_ID` varchar(36) NOT NULL,
  `VALUE` varchar(4000) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`CLIENT_ID`,`NAME`),
  CONSTRAINT `FK3C47C64BEACCA966` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_ATTRIBUTES`
--

LOCK TABLES `CLIENT_ATTRIBUTES` WRITE;
/*!40000 ALTER TABLE `CLIENT_ATTRIBUTES` DISABLE KEYS */;
INSERT INTO `CLIENT_ATTRIBUTES` VALUES ('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','display.on.consent.screen'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','exclude.session.state.from.auth.response'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml.assertion.signature'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml.authnstatement'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml.client.signature'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml.encrypt'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml.force.post.binding'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml.multivalued.roles'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml.onetimeuse.condition'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml.server.signature'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml.server.signature.keyinfo.ext'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','saml_force_name_id_format'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','false','tls.client.certificate.bound.access.tokens'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','display.on.consent.screen'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','exclude.session.state.from.auth.response'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml.assertion.signature'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml.authnstatement'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml.client.signature'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml.encrypt'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml.force.post.binding'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml.multivalued.roles'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml.onetimeuse.condition'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml.server.signature'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml.server.signature.keyinfo.ext'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','saml_force_name_id_format'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','false','tls.client.certificate.bound.access.tokens'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','display.on.consent.screen'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','exclude.session.state.from.auth.response'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml.assertion.signature'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml.authnstatement'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml.client.signature'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml.encrypt'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml.force.post.binding'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml.multivalued.roles'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml.onetimeuse.condition'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml.server.signature'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml.server.signature.keyinfo.ext'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','saml_force_name_id_format'),('67d6d3e3-38a7-4562-aac7-03832d713532','false','tls.client.certificate.bound.access.tokens'),('b8a62fae-771b-402a-8515-1b13b025da91','false','display.on.consent.screen'),('b8a62fae-771b-402a-8515-1b13b025da91','false','exclude.session.state.from.auth.response'),('b8a62fae-771b-402a-8515-1b13b025da91','false','saml.assertion.signature'),('b8a62fae-771b-402a-8515-1b13b025da91','true','saml.authnstatement'),('b8a62fae-771b-402a-8515-1b13b025da91','true','saml.client.signature'),('b8a62fae-771b-402a-8515-1b13b025da91','false','saml.encrypt'),('b8a62fae-771b-402a-8515-1b13b025da91','true','saml.force.post.binding'),('b8a62fae-771b-402a-8515-1b13b025da91','false','saml.multivalued.roles'),('b8a62fae-771b-402a-8515-1b13b025da91','false','saml.onetimeuse.condition'),('b8a62fae-771b-402a-8515-1b13b025da91','true','saml.server.signature'),('b8a62fae-771b-402a-8515-1b13b025da91','false','saml.server.signature.keyinfo.ext'),('b8a62fae-771b-402a-8515-1b13b025da91','NONE','saml.server.signature.keyinfo.xmlSigKeyInfoKeyNameTransformer'),('b8a62fae-771b-402a-8515-1b13b025da91','RSA_SHA1','saml.signature.algorithm'),('b8a62fae-771b-402a-8515-1b13b025da91','MIIClzCCAX8CBgF5fskFATANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDDARUZXN0MB4XDTIxMDUxODA5MjI1M1oXDTIyMDUwOTA5MjI1M1owDzENMAsGA1UEAwwEVGVzdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAIGrhrQe3GqB2NAr6FnTlvbRI4E1GI6kFiDmwgF/tty7HJBVz4+CBxdKjf42f7zdMDoStdophoZ/5u3P92V7I6oTfwNAWHNjSRiTx/Ft+xw3kzIKgClauvXjyupsVDZB0ON+C0rOw9StDYjSEJS68K4nQ6J/aQl4rP+copgSPCv89Jy/FZTl243mODzbYe23a577HQU1+BtwzyqTuFMYhrIsRP+1xxtkk4WmYH/t24bfGKnUUxMuRkzqwZxbRinD6tLOwfLNhEWcw1draluFtN2iCf8IpnKBIazPgTwrk1brsE59i/yoiOptRy47RL6XwhM4G7mT3arUiY5U6zacyCkCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAUdFjoAZbwrEJNKqsB4B2o9JYtebkg0eqlTIHzmw82XxIUwp//LVcvxn4NnO/ixrGONpj/vVVnew3x8intsGMko+wNglC1SeQRLjl3CfUcQxQd4yNC3kxqUbNvqevxGgbbFKFTD8QDZRdlGzzq55JDeQxtvgRUrvj9hv2vWvv1fmHYH4IvVdk7Mfeqo9AEZq9s4d7RfRGjk953JFyV2ELTI6x8LjVRoowczs9CjQ5cWzSMz0vyBNJzxtUmfI7lkfyRdHme2Az6bbUWwy78yapzBNqeIjQstFMnVs1cyRhV2D9v/ANvlHCTsGmV/v4jcfuQw94Oj2pAKdyHvkG5g+r8A==','saml.signing.certificate'),('b8a62fae-771b-402a-8515-1b13b025da91','false','saml_force_name_id_format'),('b8a62fae-771b-402a-8515-1b13b025da91','email','saml_name_id_format'),('b8a62fae-771b-402a-8515-1b13b025da91','http://www.w3.org/2001/10/xml-exc-c14n#','saml_signature_canonicalization_method'),('b8a62fae-771b-402a-8515-1b13b025da91','http://liferay:8081/c/portal/saml/slo','saml_single_logout_service_url_post'),('b8a62fae-771b-402a-8515-1b13b025da91','http://liferay:8081/c/portal/saml/slo','saml_single_logout_service_url_redirect'),('b8a62fae-771b-402a-8515-1b13b025da91','false','tls.client.certificate.bound.access.tokens'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','display.on.consent.screen'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','exclude.session.state.from.auth.response'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml.assertion.signature'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml.authnstatement'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml.client.signature'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml.encrypt'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml.force.post.binding'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml.multivalued.roles'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml.onetimeuse.condition'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml.server.signature'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml.server.signature.keyinfo.ext'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','saml_force_name_id_format'),('d377a198-34a9-46a2-9564-f468fb84e9b8','false','tls.client.certificate.bound.access.tokens'),('dbe87c24-ee61-418a-9058-85660d151e0e','false','display.on.consent.screen'),('dbe87c24-ee61-418a-9058-85660d151e0e','false','exclude.session.state.from.auth.response'),('dbe87c24-ee61-418a-9058-85660d151e0e','false','saml.assertion.signature'),('dbe87c24-ee61-418a-9058-85660d151e0e','true','saml.authnstatement'),('dbe87c24-ee61-418a-9058-85660d151e0e','true','saml.client.signature'),('dbe87c24-ee61-418a-9058-85660d151e0e','false','saml.encrypt'),('dbe87c24-ee61-418a-9058-85660d151e0e','true','saml.force.post.binding'),('dbe87c24-ee61-418a-9058-85660d151e0e','false','saml.multivalued.roles'),('dbe87c24-ee61-418a-9058-85660d151e0e','false','saml.onetimeuse.condition'),('dbe87c24-ee61-418a-9058-85660d151e0e','true','saml.server.signature'),('dbe87c24-ee61-418a-9058-85660d151e0e','false','saml.server.signature.keyinfo.ext'),('dbe87c24-ee61-418a-9058-85660d151e0e','NONE','saml.server.signature.keyinfo.xmlSigKeyInfoKeyNameTransformer'),('dbe87c24-ee61-418a-9058-85660d151e0e','RSA_SHA1','saml.signature.algorithm'),('dbe87c24-ee61-418a-9058-85660d151e0e','MIIClzCCAX8CBgF5fs82VDANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDDARUZXN0MB4XDTIxMDUxODA5MjkzOVoXDTIyMDUwOTA5MjkzOVowDzENMAsGA1UEAwwEVGVzdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKCdTn9hNHyERjK7DmNNhpMjM4CVS7Rx/SZDhyid79fMU3XZVf6YNIpjTVoYyf03Ff+dGejMvz7KA3UXR9AOBpX0MJaqK5fIf5svVm2gl1ommkDS7UO3PFnUUS33ZjqM7dYa0eAfm5OgsZKytxsBnSv7UBTk9zU7JKpcD2jidcH76kSzeDMKjqusexps0PvFaAxU5fxD4rarrrO9VzyHj2jlun/EC9ymgvPxN96et5qYTMRttmYdxSTLasN6NtL6tgxysBoTMglcz9qd+QgHahA17e2J8hqugf4hrHmeM/uJEjx6iPX+KqmlPdzFbRk6ExzCW+K1Hi7oGHRFiKDv6GUCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAKQ9J7ojmt1hxvrf67VmRKk7MT7l2OBbLfvkZfy+QZBYYlaj3Gn/moFaUWcas24j1Z1SQ/OILIIs25r3VtDSL9eChvJJyoPsqdph2q9d7+i1B9GQm5cRaR8PL5S/pREpdA9mxoh5vR0PdXQrwsH0I6QLY3Z99Mzexb4XaGhPsFLIouayYmGirAL7wujF03eAqcxZJKnyahC46hcKtOgZZYps1x1lEJEbybKB1mlrn0nV5LgyLBBXY7JhnegnA9yEVUSGJYCrwR0Wk9nbf89yTqdIzM+cMBATiH80uzPBP290nsaYjvSP+1agIVzGQTxWjFAFIY222jCAtQmtqhJWozg==','saml.signing.certificate'),('dbe87c24-ee61-418a-9058-85660d151e0e','false','saml_force_name_id_format'),('dbe87c24-ee61-418a-9058-85660d151e0e','email','saml_name_id_format'),('dbe87c24-ee61-418a-9058-85660d151e0e','http://www.w3.org/2001/10/xml-exc-c14n#','saml_signature_canonicalization_method'),('dbe87c24-ee61-418a-9058-85660d151e0e','http://localhost:8081/c/portal/saml/slo','saml_single_logout_service_url_post'),('dbe87c24-ee61-418a-9058-85660d151e0e','http://localhost:8081/c/portal/saml/slo','saml_single_logout_service_url_redirect'),('dbe87c24-ee61-418a-9058-85660d151e0e','false','tls.client.certificate.bound.access.tokens'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','display.on.consent.screen'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','exclude.session.state.from.auth.response'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml.assertion.signature'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml.authnstatement'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml.client.signature'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml.encrypt'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml.force.post.binding'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml.multivalued.roles'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml.onetimeuse.condition'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml.server.signature'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml.server.signature.keyinfo.ext'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','saml_force_name_id_format'),('f1b13f7c-a916-4b24-b314-0200496926ce','false','tls.client.certificate.bound.access.tokens');
/*!40000 ALTER TABLE `CLIENT_ATTRIBUTES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_AUTH_FLOW_BINDINGS`
--

DROP TABLE IF EXISTS `CLIENT_AUTH_FLOW_BINDINGS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_AUTH_FLOW_BINDINGS` (
  `CLIENT_ID` varchar(36) NOT NULL,
  `FLOW_ID` varchar(36) DEFAULT NULL,
  `BINDING_NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`CLIENT_ID`,`BINDING_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_AUTH_FLOW_BINDINGS`
--

LOCK TABLES `CLIENT_AUTH_FLOW_BINDINGS` WRITE;
/*!40000 ALTER TABLE `CLIENT_AUTH_FLOW_BINDINGS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_AUTH_FLOW_BINDINGS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_DEFAULT_ROLES`
--

DROP TABLE IF EXISTS `CLIENT_DEFAULT_ROLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_DEFAULT_ROLES` (
  `CLIENT_ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`CLIENT_ID`,`ROLE_ID`),
  UNIQUE KEY `UK_8AELWNIBJI49AVXSRTUF6XJOW` (`ROLE_ID`),
  KEY `IDX_CLIENT_DEF_ROLES_CLIENT` (`CLIENT_ID`),
  CONSTRAINT `FK_8AELWNIBJI49AVXSRTUF6XJOW` FOREIGN KEY (`ROLE_ID`) REFERENCES `KEYCLOAK_ROLE` (`ID`),
  CONSTRAINT `FK_NUILTS7KLWQW2H8M2B5JOYTKY` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_DEFAULT_ROLES`
--

LOCK TABLES `CLIENT_DEFAULT_ROLES` WRITE;
/*!40000 ALTER TABLE `CLIENT_DEFAULT_ROLES` DISABLE KEYS */;
INSERT INTO `CLIENT_DEFAULT_ROLES` VALUES ('f1b13f7c-a916-4b24-b314-0200496926ce','0ab906cb-cc4f-4af4-9dd4-e676e6f0f9f3'),('f1b13f7c-a916-4b24-b314-0200496926ce','8c430ca8-55e7-43c6-8625-527ab3f3ec3f'),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','b3a357a8-691e-4855-bf2c-a2c5757f8a54'),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','ea1d0b81-cdda-4b96-85fe-2c81438537a2');
/*!40000 ALTER TABLE `CLIENT_DEFAULT_ROLES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_INITIAL_ACCESS`
--

DROP TABLE IF EXISTS `CLIENT_INITIAL_ACCESS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_INITIAL_ACCESS` (
  `ID` varchar(36) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `TIMESTAMP` int(11) DEFAULT NULL,
  `EXPIRATION` int(11) DEFAULT NULL,
  `COUNT` int(11) DEFAULT NULL,
  `REMAINING_COUNT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_CLIENT_INIT_ACC_REALM` (`REALM_ID`),
  CONSTRAINT `FK_CLIENT_INIT_ACC_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_INITIAL_ACCESS`
--

LOCK TABLES `CLIENT_INITIAL_ACCESS` WRITE;
/*!40000 ALTER TABLE `CLIENT_INITIAL_ACCESS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_INITIAL_ACCESS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_NODE_REGISTRATIONS`
--

DROP TABLE IF EXISTS `CLIENT_NODE_REGISTRATIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_NODE_REGISTRATIONS` (
  `CLIENT_ID` varchar(36) NOT NULL,
  `VALUE` int(11) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`CLIENT_ID`,`NAME`),
  CONSTRAINT `FK4129723BA992F594` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_NODE_REGISTRATIONS`
--

LOCK TABLES `CLIENT_NODE_REGISTRATIONS` WRITE;
/*!40000 ALTER TABLE `CLIENT_NODE_REGISTRATIONS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_NODE_REGISTRATIONS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SCOPE`
--

DROP TABLE IF EXISTS `CLIENT_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SCOPE` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `PROTOCOL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_CLI_SCOPE` (`REALM_ID`,`NAME`),
  KEY `IDX_REALM_CLSCOPE` (`REALM_ID`),
  CONSTRAINT `FK_REALM_CLI_SCOPE` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SCOPE`
--

LOCK TABLES `CLIENT_SCOPE` WRITE;
/*!40000 ALTER TABLE `CLIENT_SCOPE` DISABLE KEYS */;
INSERT INTO `CLIENT_SCOPE` VALUES ('097f22bc-e851-40d0-a305-abc3958b9bc1','phone','master','OpenID Connect built-in scope: phone','openid-connect'),('2872c7d0-5ebf-41e7-a73a-febd30bac99c','role_list','master','SAML role list','saml'),('3522f3d8-9fad-4c62-bfc8-000b9091361a','profile','liferay-portal','OpenID Connect built-in scope: profile','openid-connect'),('55164a6a-d9a0-4d36-a289-abafa7fc1551','role_list','liferay-portal','SAML role list','saml'),('5f3033d7-efbf-4529-bb82-39f017fa0a0d','web-origins','master','OpenID Connect scope for add allowed web origins to the access token','openid-connect'),('6789e146-cf4b-4c40-a182-d3aecf1f5227','profile','master','OpenID Connect built-in scope: profile','openid-connect'),('7f259547-fc8a-4689-a51f-5991b1a93562','email','liferay-portal','OpenID Connect built-in scope: email','openid-connect'),('8464704c-e331-40e0-be56-66582add8286','email','master','OpenID Connect built-in scope: email','openid-connect'),('93b2f0f7-c201-4196-b6bc-2829369f70e2','phone','liferay-portal','OpenID Connect built-in scope: phone','openid-connect'),('a13d2169-a592-4de0-8dd7-6a0c100ccf4a','offline_access','master','OpenID Connect built-in scope: offline_access','openid-connect'),('aa7d808e-1c63-4fa3-80ec-e222079435c0','address','master','OpenID Connect built-in scope: address','openid-connect'),('c6dc943c-f19c-4f65-9116-38d8773fb28d','web-origins','liferay-portal','OpenID Connect scope for add allowed web origins to the access token','openid-connect'),('c8b1ab10-6b0d-477a-8f5a-0bbc952a0053','roles','master','OpenID Connect scope for add user roles to the access token','openid-connect'),('d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6','roles','liferay-portal','OpenID Connect scope for add user roles to the access token','openid-connect'),('d97d8e90-e832-40ff-b928-4fc675a50743','microprofile-jwt','master','Microprofile - JWT built-in scope','openid-connect'),('eb4606ff-4ec0-4ffd-980a-a735c2465aeb','address','liferay-portal','OpenID Connect built-in scope: address','openid-connect'),('fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','offline_access','liferay-portal','OpenID Connect built-in scope: offline_access','openid-connect');
/*!40000 ALTER TABLE `CLIENT_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SCOPE_ATTRIBUTES`
--

DROP TABLE IF EXISTS `CLIENT_SCOPE_ATTRIBUTES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SCOPE_ATTRIBUTES` (
  `SCOPE_ID` varchar(36) NOT NULL,
  `VALUE` varchar(2048) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`SCOPE_ID`,`NAME`),
  KEY `IDX_CLSCOPE_ATTRS` (`SCOPE_ID`),
  CONSTRAINT `FK_CL_SCOPE_ATTR_SCOPE` FOREIGN KEY (`SCOPE_ID`) REFERENCES `CLIENT_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SCOPE_ATTRIBUTES`
--

LOCK TABLES `CLIENT_SCOPE_ATTRIBUTES` WRITE;
/*!40000 ALTER TABLE `CLIENT_SCOPE_ATTRIBUTES` DISABLE KEYS */;
INSERT INTO `CLIENT_SCOPE_ATTRIBUTES` VALUES ('097f22bc-e851-40d0-a305-abc3958b9bc1','${phoneScopeConsentText}','consent.screen.text'),('097f22bc-e851-40d0-a305-abc3958b9bc1','true','display.on.consent.screen'),('097f22bc-e851-40d0-a305-abc3958b9bc1','true','include.in.token.scope'),('2872c7d0-5ebf-41e7-a73a-febd30bac99c','${samlRoleListScopeConsentText}','consent.screen.text'),('2872c7d0-5ebf-41e7-a73a-febd30bac99c','true','display.on.consent.screen'),('3522f3d8-9fad-4c62-bfc8-000b9091361a','${profileScopeConsentText}','consent.screen.text'),('3522f3d8-9fad-4c62-bfc8-000b9091361a','true','display.on.consent.screen'),('55164a6a-d9a0-4d36-a289-abafa7fc1551','${samlRoleListScopeConsentText}','consent.screen.text'),('55164a6a-d9a0-4d36-a289-abafa7fc1551','true','display.on.consent.screen'),('5f3033d7-efbf-4529-bb82-39f017fa0a0d','','consent.screen.text'),('5f3033d7-efbf-4529-bb82-39f017fa0a0d','false','display.on.consent.screen'),('5f3033d7-efbf-4529-bb82-39f017fa0a0d','false','include.in.token.scope'),('6789e146-cf4b-4c40-a182-d3aecf1f5227','${profileScopeConsentText}','consent.screen.text'),('6789e146-cf4b-4c40-a182-d3aecf1f5227','true','display.on.consent.screen'),('6789e146-cf4b-4c40-a182-d3aecf1f5227','true','include.in.token.scope'),('7f259547-fc8a-4689-a51f-5991b1a93562','${emailScopeConsentText}','consent.screen.text'),('7f259547-fc8a-4689-a51f-5991b1a93562','true','display.on.consent.screen'),('8464704c-e331-40e0-be56-66582add8286','${emailScopeConsentText}','consent.screen.text'),('8464704c-e331-40e0-be56-66582add8286','true','display.on.consent.screen'),('8464704c-e331-40e0-be56-66582add8286','true','include.in.token.scope'),('93b2f0f7-c201-4196-b6bc-2829369f70e2','${phoneScopeConsentText}','consent.screen.text'),('93b2f0f7-c201-4196-b6bc-2829369f70e2','true','display.on.consent.screen'),('a13d2169-a592-4de0-8dd7-6a0c100ccf4a','${offlineAccessScopeConsentText}','consent.screen.text'),('a13d2169-a592-4de0-8dd7-6a0c100ccf4a','true','display.on.consent.screen'),('aa7d808e-1c63-4fa3-80ec-e222079435c0','${addressScopeConsentText}','consent.screen.text'),('aa7d808e-1c63-4fa3-80ec-e222079435c0','true','display.on.consent.screen'),('aa7d808e-1c63-4fa3-80ec-e222079435c0','true','include.in.token.scope'),('c6dc943c-f19c-4f65-9116-38d8773fb28d','','consent.screen.text'),('c6dc943c-f19c-4f65-9116-38d8773fb28d','false','display.on.consent.screen'),('c6dc943c-f19c-4f65-9116-38d8773fb28d','false','include.in.token.scope'),('c8b1ab10-6b0d-477a-8f5a-0bbc952a0053','${rolesScopeConsentText}','consent.screen.text'),('c8b1ab10-6b0d-477a-8f5a-0bbc952a0053','true','display.on.consent.screen'),('c8b1ab10-6b0d-477a-8f5a-0bbc952a0053','false','include.in.token.scope'),('d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6','${rolesScopeConsentText}','consent.screen.text'),('d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6','true','display.on.consent.screen'),('d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6','false','include.in.token.scope'),('d97d8e90-e832-40ff-b928-4fc675a50743','false','display.on.consent.screen'),('d97d8e90-e832-40ff-b928-4fc675a50743','true','include.in.token.scope'),('eb4606ff-4ec0-4ffd-980a-a735c2465aeb','${addressScopeConsentText}','consent.screen.text'),('eb4606ff-4ec0-4ffd-980a-a735c2465aeb','true','display.on.consent.screen'),('fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','${offlineAccessScopeConsentText}','consent.screen.text'),('fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','true','display.on.consent.screen');
/*!40000 ALTER TABLE `CLIENT_SCOPE_ATTRIBUTES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SCOPE_CLIENT`
--

DROP TABLE IF EXISTS `CLIENT_SCOPE_CLIENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SCOPE_CLIENT` (
  `CLIENT_ID` varchar(36) NOT NULL,
  `SCOPE_ID` varchar(36) NOT NULL,
  `DEFAULT_SCOPE` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`CLIENT_ID`,`SCOPE_ID`),
  KEY `IDX_CLSCOPE_CL` (`CLIENT_ID`),
  KEY `IDX_CL_CLSCOPE` (`SCOPE_ID`),
  CONSTRAINT `FK_C_CLI_SCOPE_CLIENT` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`),
  CONSTRAINT `FK_C_CLI_SCOPE_SCOPE` FOREIGN KEY (`SCOPE_ID`) REFERENCES `CLIENT_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SCOPE_CLIENT`
--

LOCK TABLES `CLIENT_SCOPE_CLIENT` WRITE;
/*!40000 ALTER TABLE `CLIENT_SCOPE_CLIENT` DISABLE KEYS */;
INSERT INTO `CLIENT_SCOPE_CLIENT` VALUES ('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','7f259547-fc8a-4689-a51f-5991b1a93562',''),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('1e563445-4114-4608-b5dc-0e6626b2732c','097f22bc-e851-40d0-a305-abc3958b9bc1','\0'),('1e563445-4114-4608-b5dc-0e6626b2732c','2872c7d0-5ebf-41e7-a73a-febd30bac99c',''),('1e563445-4114-4608-b5dc-0e6626b2732c','5f3033d7-efbf-4529-bb82-39f017fa0a0d',''),('1e563445-4114-4608-b5dc-0e6626b2732c','6789e146-cf4b-4c40-a182-d3aecf1f5227',''),('1e563445-4114-4608-b5dc-0e6626b2732c','8464704c-e331-40e0-be56-66582add8286',''),('1e563445-4114-4608-b5dc-0e6626b2732c','a13d2169-a592-4de0-8dd7-6a0c100ccf4a','\0'),('1e563445-4114-4608-b5dc-0e6626b2732c','aa7d808e-1c63-4fa3-80ec-e222079435c0','\0'),('1e563445-4114-4608-b5dc-0e6626b2732c','c8b1ab10-6b0d-477a-8f5a-0bbc952a0053',''),('1e563445-4114-4608-b5dc-0e6626b2732c','d97d8e90-e832-40ff-b928-4fc675a50743','\0'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','7f259547-fc8a-4689-a51f-5991b1a93562',''),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('364c3e71-9195-4162-9d08-e048421fdd0f','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('364c3e71-9195-4162-9d08-e048421fdd0f','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('364c3e71-9195-4162-9d08-e048421fdd0f','7f259547-fc8a-4689-a51f-5991b1a93562',''),('364c3e71-9195-4162-9d08-e048421fdd0f','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('364c3e71-9195-4162-9d08-e048421fdd0f','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('364c3e71-9195-4162-9d08-e048421fdd0f','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('364c3e71-9195-4162-9d08-e048421fdd0f','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('364c3e71-9195-4162-9d08-e048421fdd0f','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('434f2a4d-52d7-4823-badc-54b9835ec942','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('434f2a4d-52d7-4823-badc-54b9835ec942','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('434f2a4d-52d7-4823-badc-54b9835ec942','7f259547-fc8a-4689-a51f-5991b1a93562',''),('434f2a4d-52d7-4823-badc-54b9835ec942','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('434f2a4d-52d7-4823-badc-54b9835ec942','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('434f2a4d-52d7-4823-badc-54b9835ec942','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('434f2a4d-52d7-4823-badc-54b9835ec942','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('434f2a4d-52d7-4823-badc-54b9835ec942','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','097f22bc-e851-40d0-a305-abc3958b9bc1','\0'),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','2872c7d0-5ebf-41e7-a73a-febd30bac99c',''),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','5f3033d7-efbf-4529-bb82-39f017fa0a0d',''),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','6789e146-cf4b-4c40-a182-d3aecf1f5227',''),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','8464704c-e331-40e0-be56-66582add8286',''),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','a13d2169-a592-4de0-8dd7-6a0c100ccf4a','\0'),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','aa7d808e-1c63-4fa3-80ec-e222079435c0','\0'),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','c8b1ab10-6b0d-477a-8f5a-0bbc952a0053',''),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','d97d8e90-e832-40ff-b928-4fc675a50743','\0'),('67d6d3e3-38a7-4562-aac7-03832d713532','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('67d6d3e3-38a7-4562-aac7-03832d713532','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('67d6d3e3-38a7-4562-aac7-03832d713532','7f259547-fc8a-4689-a51f-5991b1a93562',''),('67d6d3e3-38a7-4562-aac7-03832d713532','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('67d6d3e3-38a7-4562-aac7-03832d713532','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('67d6d3e3-38a7-4562-aac7-03832d713532','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('67d6d3e3-38a7-4562-aac7-03832d713532','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('67d6d3e3-38a7-4562-aac7-03832d713532','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','7f259547-fc8a-4689-a51f-5991b1a93562',''),('7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('9b3c4d13-0a92-46a4-a07c-e412e33024d7','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('9b3c4d13-0a92-46a4-a07c-e412e33024d7','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('9b3c4d13-0a92-46a4-a07c-e412e33024d7','7f259547-fc8a-4689-a51f-5991b1a93562',''),('9b3c4d13-0a92-46a4-a07c-e412e33024d7','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('9b3c4d13-0a92-46a4-a07c-e412e33024d7','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('9b3c4d13-0a92-46a4-a07c-e412e33024d7','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('9b3c4d13-0a92-46a4-a07c-e412e33024d7','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('9b3c4d13-0a92-46a4-a07c-e412e33024d7','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('b39c0c40-7753-42c6-9356-694c4e7182dc','097f22bc-e851-40d0-a305-abc3958b9bc1','\0'),('b39c0c40-7753-42c6-9356-694c4e7182dc','2872c7d0-5ebf-41e7-a73a-febd30bac99c',''),('b39c0c40-7753-42c6-9356-694c4e7182dc','5f3033d7-efbf-4529-bb82-39f017fa0a0d',''),('b39c0c40-7753-42c6-9356-694c4e7182dc','6789e146-cf4b-4c40-a182-d3aecf1f5227',''),('b39c0c40-7753-42c6-9356-694c4e7182dc','8464704c-e331-40e0-be56-66582add8286',''),('b39c0c40-7753-42c6-9356-694c4e7182dc','a13d2169-a592-4de0-8dd7-6a0c100ccf4a','\0'),('b39c0c40-7753-42c6-9356-694c4e7182dc','aa7d808e-1c63-4fa3-80ec-e222079435c0','\0'),('b39c0c40-7753-42c6-9356-694c4e7182dc','c8b1ab10-6b0d-477a-8f5a-0bbc952a0053',''),('b39c0c40-7753-42c6-9356-694c4e7182dc','d97d8e90-e832-40ff-b928-4fc675a50743','\0'),('b8a62fae-771b-402a-8515-1b13b025da91','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('b8a62fae-771b-402a-8515-1b13b025da91','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('b8a62fae-771b-402a-8515-1b13b025da91','7f259547-fc8a-4689-a51f-5991b1a93562',''),('b8a62fae-771b-402a-8515-1b13b025da91','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('b8a62fae-771b-402a-8515-1b13b025da91','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('b8a62fae-771b-402a-8515-1b13b025da91','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('b8a62fae-771b-402a-8515-1b13b025da91','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('b8a62fae-771b-402a-8515-1b13b025da91','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','097f22bc-e851-40d0-a305-abc3958b9bc1','\0'),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','2872c7d0-5ebf-41e7-a73a-febd30bac99c',''),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','5f3033d7-efbf-4529-bb82-39f017fa0a0d',''),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','6789e146-cf4b-4c40-a182-d3aecf1f5227',''),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','8464704c-e331-40e0-be56-66582add8286',''),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','a13d2169-a592-4de0-8dd7-6a0c100ccf4a','\0'),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','aa7d808e-1c63-4fa3-80ec-e222079435c0','\0'),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','c8b1ab10-6b0d-477a-8f5a-0bbc952a0053',''),('cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','d97d8e90-e832-40ff-b928-4fc675a50743','\0'),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','097f22bc-e851-40d0-a305-abc3958b9bc1','\0'),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','2872c7d0-5ebf-41e7-a73a-febd30bac99c',''),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','5f3033d7-efbf-4529-bb82-39f017fa0a0d',''),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','6789e146-cf4b-4c40-a182-d3aecf1f5227',''),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','8464704c-e331-40e0-be56-66582add8286',''),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','a13d2169-a592-4de0-8dd7-6a0c100ccf4a','\0'),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','aa7d808e-1c63-4fa3-80ec-e222079435c0','\0'),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','c8b1ab10-6b0d-477a-8f5a-0bbc952a0053',''),('d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','d97d8e90-e832-40ff-b928-4fc675a50743','\0'),('d377a198-34a9-46a2-9564-f468fb84e9b8','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('d377a198-34a9-46a2-9564-f468fb84e9b8','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('d377a198-34a9-46a2-9564-f468fb84e9b8','7f259547-fc8a-4689-a51f-5991b1a93562',''),('d377a198-34a9-46a2-9564-f468fb84e9b8','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('d377a198-34a9-46a2-9564-f468fb84e9b8','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('d377a198-34a9-46a2-9564-f468fb84e9b8','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('d377a198-34a9-46a2-9564-f468fb84e9b8','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('d377a198-34a9-46a2-9564-f468fb84e9b8','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('dbe87c24-ee61-418a-9058-85660d151e0e','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('dbe87c24-ee61-418a-9058-85660d151e0e','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('dbe87c24-ee61-418a-9058-85660d151e0e','7f259547-fc8a-4689-a51f-5991b1a93562',''),('dbe87c24-ee61-418a-9058-85660d151e0e','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('dbe87c24-ee61-418a-9058-85660d151e0e','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('dbe87c24-ee61-418a-9058-85660d151e0e','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('dbe87c24-ee61-418a-9058-85660d151e0e','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('dbe87c24-ee61-418a-9058-85660d151e0e','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('f1b13f7c-a916-4b24-b314-0200496926ce','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('f1b13f7c-a916-4b24-b314-0200496926ce','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('f1b13f7c-a916-4b24-b314-0200496926ce','7f259547-fc8a-4689-a51f-5991b1a93562',''),('f1b13f7c-a916-4b24-b314-0200496926ce','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('f1b13f7c-a916-4b24-b314-0200496926ce','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('f1b13f7c-a916-4b24-b314-0200496926ce','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('f1b13f7c-a916-4b24-b314-0200496926ce','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('f1b13f7c-a916-4b24-b314-0200496926ce','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','097f22bc-e851-40d0-a305-abc3958b9bc1','\0'),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','2872c7d0-5ebf-41e7-a73a-febd30bac99c',''),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','5f3033d7-efbf-4529-bb82-39f017fa0a0d',''),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','6789e146-cf4b-4c40-a182-d3aecf1f5227',''),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','8464704c-e331-40e0-be56-66582add8286',''),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','a13d2169-a592-4de0-8dd7-6a0c100ccf4a','\0'),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','aa7d808e-1c63-4fa3-80ec-e222079435c0','\0'),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','c8b1ab10-6b0d-477a-8f5a-0bbc952a0053',''),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','d97d8e90-e832-40ff-b928-4fc675a50743','\0');
/*!40000 ALTER TABLE `CLIENT_SCOPE_CLIENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SCOPE_ROLE_MAPPING`
--

DROP TABLE IF EXISTS `CLIENT_SCOPE_ROLE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SCOPE_ROLE_MAPPING` (
  `SCOPE_ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`SCOPE_ID`,`ROLE_ID`),
  KEY `IDX_CLSCOPE_ROLE` (`SCOPE_ID`),
  KEY `IDX_ROLE_CLSCOPE` (`ROLE_ID`),
  CONSTRAINT `FK_CL_SCOPE_RM_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `KEYCLOAK_ROLE` (`ID`),
  CONSTRAINT `FK_CL_SCOPE_RM_SCOPE` FOREIGN KEY (`SCOPE_ID`) REFERENCES `CLIENT_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SCOPE_ROLE_MAPPING`
--

LOCK TABLES `CLIENT_SCOPE_ROLE_MAPPING` WRITE;
/*!40000 ALTER TABLE `CLIENT_SCOPE_ROLE_MAPPING` DISABLE KEYS */;
INSERT INTO `CLIENT_SCOPE_ROLE_MAPPING` VALUES ('3522f3d8-9fad-4c62-bfc8-000b9091361a','3d9c5e71-233a-4e29-9e60-f5d9129f9c57'),('a13d2169-a592-4de0-8dd7-6a0c100ccf4a','c9a15285-475b-4369-94f6-a6de3bcce560'),('fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','f4e93900-0b7d-4fa3-b2ca-523715fff9d1');
/*!40000 ALTER TABLE `CLIENT_SCOPE_ROLE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION`
--

DROP TABLE IF EXISTS `CLIENT_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION` (
  `ID` varchar(36) NOT NULL,
  `CLIENT_ID` varchar(36) DEFAULT NULL,
  `REDIRECT_URI` varchar(255) DEFAULT NULL,
  `STATE` varchar(255) DEFAULT NULL,
  `TIMESTAMP` int(11) DEFAULT NULL,
  `SESSION_ID` varchar(36) DEFAULT NULL,
  `AUTH_METHOD` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(255) DEFAULT NULL,
  `AUTH_USER_ID` varchar(36) DEFAULT NULL,
  `CURRENT_ACTION` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_CLIENT_SESSION_SESSION` (`SESSION_ID`),
  CONSTRAINT `FK_B4AO2VCVAT6UKAU74WBWTFQO1` FOREIGN KEY (`SESSION_ID`) REFERENCES `USER_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION`
--

LOCK TABLES `CLIENT_SESSION` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION_AUTH_STATUS`
--

DROP TABLE IF EXISTS `CLIENT_SESSION_AUTH_STATUS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION_AUTH_STATUS` (
  `AUTHENTICATOR` varchar(36) NOT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `CLIENT_SESSION` varchar(36) NOT NULL,
  PRIMARY KEY (`CLIENT_SESSION`,`AUTHENTICATOR`),
  CONSTRAINT `AUTH_STATUS_CONSTRAINT` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION_AUTH_STATUS`
--

LOCK TABLES `CLIENT_SESSION_AUTH_STATUS` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION_AUTH_STATUS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION_AUTH_STATUS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION_NOTE`
--

DROP TABLE IF EXISTS `CLIENT_SESSION_NOTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION_NOTE` (
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `CLIENT_SESSION` varchar(36) NOT NULL,
  PRIMARY KEY (`CLIENT_SESSION`,`NAME`),
  CONSTRAINT `FK5EDFB00FF51C2736` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION_NOTE`
--

LOCK TABLES `CLIENT_SESSION_NOTE` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION_NOTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION_NOTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION_PROT_MAPPER`
--

DROP TABLE IF EXISTS `CLIENT_SESSION_PROT_MAPPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION_PROT_MAPPER` (
  `PROTOCOL_MAPPER_ID` varchar(36) NOT NULL,
  `CLIENT_SESSION` varchar(36) NOT NULL,
  PRIMARY KEY (`CLIENT_SESSION`,`PROTOCOL_MAPPER_ID`),
  CONSTRAINT `FK_33A8SGQW18I532811V7O2DK89` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION_PROT_MAPPER`
--

LOCK TABLES `CLIENT_SESSION_PROT_MAPPER` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION_PROT_MAPPER` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION_PROT_MAPPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION_ROLE`
--

DROP TABLE IF EXISTS `CLIENT_SESSION_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION_ROLE` (
  `ROLE_ID` varchar(255) NOT NULL,
  `CLIENT_SESSION` varchar(36) NOT NULL,
  PRIMARY KEY (`CLIENT_SESSION`,`ROLE_ID`),
  CONSTRAINT `FK_11B7SGQW18I532811V7O2DV76` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION_ROLE`
--

LOCK TABLES `CLIENT_SESSION_ROLE` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION_ROLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_USER_SESSION_NOTE`
--

DROP TABLE IF EXISTS `CLIENT_USER_SESSION_NOTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_USER_SESSION_NOTE` (
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(2048) DEFAULT NULL,
  `CLIENT_SESSION` varchar(36) NOT NULL,
  PRIMARY KEY (`CLIENT_SESSION`,`NAME`),
  CONSTRAINT `FK_CL_USR_SES_NOTE` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_USER_SESSION_NOTE`
--

LOCK TABLES `CLIENT_USER_SESSION_NOTE` WRITE;
/*!40000 ALTER TABLE `CLIENT_USER_SESSION_NOTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_USER_SESSION_NOTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COMPONENT`
--

DROP TABLE IF EXISTS `COMPONENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMPONENT` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(36) DEFAULT NULL,
  `PROVIDER_ID` varchar(36) DEFAULT NULL,
  `PROVIDER_TYPE` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) DEFAULT NULL,
  `SUB_TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_COMPONENT_REALM` (`REALM_ID`),
  KEY `IDX_COMPONENT_PROVIDER_TYPE` (`PROVIDER_TYPE`),
  CONSTRAINT `FK_COMPONENT_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMPONENT`
--

LOCK TABLES `COMPONENT` WRITE;
/*!40000 ALTER TABLE `COMPONENT` DISABLE KEYS */;
INSERT INTO `COMPONENT` VALUES ('03837f71-9da5-4ba1-8558-c3dae79d6ea8','ldap','liferay-portal','ldap','org.keycloak.storage.UserStorageProvider','liferay-portal',NULL),('0a1b32c6-c823-4e63-b414-6522f672ccc3','Allowed Client Scopes','liferay-portal','allowed-client-templates','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','liferay-portal','anonymous'),('17be5f48-db31-4813-b5f8-32f7315c551c','username','03837f71-9da5-4ba1-8558-c3dae79d6ea8','user-attribute-ldap-mapper','org.keycloak.storage.ldap.mappers.LDAPStorageMapper','liferay-portal',NULL),('18584c2c-2af1-4deb-8204-f33a82b87010','Allowed Client Scopes','master','allowed-client-templates','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','master','anonymous'),('19bbc5cd-0096-4bba-9595-703a4e7a5f1c','Trusted Hosts','master','trusted-hosts','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','master','anonymous'),('20140e70-1963-45fa-9902-36fd803b9f1b','aes-generated','master','aes-generated','org.keycloak.keys.KeyProvider','master',NULL),('25e1439a-c539-4596-8cbb-23a5d8e07dde','rsa-generated','master','rsa-generated','org.keycloak.keys.KeyProvider','master',NULL),('28e62e97-a445-4efa-86d4-d46b17820bb0','rsa-generated','liferay-portal','rsa-generated','org.keycloak.keys.KeyProvider','liferay-portal',NULL),('32614a51-31d6-423e-bbd9-dfa7251a7c15','Consent Required','master','consent-required','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','master','anonymous'),('375f68c1-aeaf-43a5-b181-c5a814d7410c','Allowed Protocol Mapper Types','liferay-portal','allowed-protocol-mappers','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','liferay-portal','anonymous'),('37e3d953-995c-4bc7-aea6-21f24fd56520','Allowed Client Scopes','master','allowed-client-templates','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','master','authenticated'),('3ebb390d-f317-43a6-9e52-69566f0d1a4b','hmac-generated','master','hmac-generated','org.keycloak.keys.KeyProvider','master',NULL),('441b18d6-0e4b-43ed-bc85-f953fb681526','Max Clients Limit','master','max-clients','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','master','anonymous'),('58f1a27f-d961-4a0c-b49e-d8423d694bae','Allowed Protocol Mapper Types','master','allowed-protocol-mappers','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','master','anonymous'),('67414869-c280-406e-83be-385c145f3c26','creation date','03837f71-9da5-4ba1-8558-c3dae79d6ea8','user-attribute-ldap-mapper','org.keycloak.storage.ldap.mappers.LDAPStorageMapper','liferay-portal',NULL),('676fbf1b-df0d-4b7f-b784-1b59acff57b5','aes-generated','liferay-portal','aes-generated','org.keycloak.keys.KeyProvider','liferay-portal',NULL),('68b48bd4-d709-49af-ba37-7398b2f8c5b4','Max Clients Limit','liferay-portal','max-clients','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','liferay-portal','anonymous'),('696bce11-aba3-4404-a73c-394418461f8f','Full Scope Disabled','master','scope','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','master','anonymous'),('7599466f-ef4e-4482-88e7-537e2ff9ea6a','Allowed Client Scopes','liferay-portal','allowed-client-templates','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','liferay-portal','authenticated'),('7dfe4eaa-9a70-435d-bbd0-3edd33ca837b','hmac-generated','liferay-portal','hmac-generated','org.keycloak.keys.KeyProvider','liferay-portal',NULL),('8cdbbda9-5c6d-4a37-84c6-7003f93e26ee','first name','03837f71-9da5-4ba1-8558-c3dae79d6ea8','user-attribute-ldap-mapper','org.keycloak.storage.ldap.mappers.LDAPStorageMapper','liferay-portal',NULL),('ba7a1f32-fff1-4380-9941-1f437d928164','Full Scope Disabled','liferay-portal','scope','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','liferay-portal','anonymous'),('be72acd7-2087-495e-a0d1-301d321cac4b','modify date','03837f71-9da5-4ba1-8558-c3dae79d6ea8','user-attribute-ldap-mapper','org.keycloak.storage.ldap.mappers.LDAPStorageMapper','liferay-portal',NULL),('c15058f7-a6bf-4423-a149-1f8ceb99696b','Allowed Protocol Mapper Types','master','allowed-protocol-mappers','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','master','authenticated'),('c86fc1b2-504a-4417-bd42-779d46dcceab','Trusted Hosts','liferay-portal','trusted-hosts','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','liferay-portal','anonymous'),('e20572d1-5584-4f2c-82d9-f7064fccfe4c','last name','03837f71-9da5-4ba1-8558-c3dae79d6ea8','user-attribute-ldap-mapper','org.keycloak.storage.ldap.mappers.LDAPStorageMapper','liferay-portal',NULL),('f011ef48-981f-4805-8801-cd643f6c6406','email','03837f71-9da5-4ba1-8558-c3dae79d6ea8','user-attribute-ldap-mapper','org.keycloak.storage.ldap.mappers.LDAPStorageMapper','liferay-portal',NULL),('f0b85e83-55b5-4129-a0d7-9e0f930434c4','Allowed Protocol Mapper Types','liferay-portal','allowed-protocol-mappers','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','liferay-portal','authenticated'),('fa24c2ee-61a9-48ac-baaa-0dd6a806b77d','Consent Required','liferay-portal','consent-required','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','liferay-portal','anonymous');
/*!40000 ALTER TABLE `COMPONENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COMPONENT_CONFIG`
--

DROP TABLE IF EXISTS `COMPONENT_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMPONENT_CONFIG` (
  `ID` varchar(36) NOT NULL,
  `COMPONENT_ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_COMPO_CONFIG_COMPO` (`COMPONENT_ID`),
  CONSTRAINT `FK_COMPONENT_CONFIG` FOREIGN KEY (`COMPONENT_ID`) REFERENCES `COMPONENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMPONENT_CONFIG`
--

LOCK TABLES `COMPONENT_CONFIG` WRITE;
/*!40000 ALTER TABLE `COMPONENT_CONFIG` DISABLE KEYS */;
INSERT INTO `COMPONENT_CONFIG` VALUES ('00814514-127c-4666-817b-1f6c338c428f','f0b85e83-55b5-4129-a0d7-9e0f930434c4','allowed-protocol-mapper-types','saml-role-list-mapper'),('00970a0c-5851-451a-9e75-731439914c8b','f011ef48-981f-4805-8801-cd643f6c6406','always.read.value.from.ldap','false'),('0151aa8e-a947-49b8-a793-c192d4b38a00','03837f71-9da5-4ba1-8558-c3dae79d6ea8','usernameLDAPAttribute','cn'),('019fc67d-b6e8-43a2-a196-998575c17b3d','c15058f7-a6bf-4423-a149-1f8ceb99696b','allowed-protocol-mapper-types','oidc-usermodel-attribute-mapper'),('047ccf30-cfc0-4038-bfb7-c5823fb285f9','03837f71-9da5-4ba1-8558-c3dae79d6ea8','useTruststoreSpi','ldapsOnly'),('0b125e89-571b-4ba5-b865-e78bea3ee7ef','03837f71-9da5-4ba1-8558-c3dae79d6ea8','importEnabled','true'),('0f9817a8-2305-4ea1-a29f-0b092df64f57','03837f71-9da5-4ba1-8558-c3dae79d6ea8','searchScope','1'),('1474f872-ed5f-4af9-a5de-dfc0009dc322','03837f71-9da5-4ba1-8558-c3dae79d6ea8','editMode','WRITABLE'),('1787bd2b-89ea-4130-9cf8-8180649ee568','8cdbbda9-5c6d-4a37-84c6-7003f93e26ee','always.read.value.from.ldap','true'),('1c1782de-60f9-47ee-8c52-a33778f999f0','f0b85e83-55b5-4129-a0d7-9e0f930434c4','allowed-protocol-mapper-types','oidc-sha256-pairwise-sub-mapper'),('1cc93aae-96a4-49e3-b3cd-af2e82169dc5','03837f71-9da5-4ba1-8558-c3dae79d6ea8','pagination','true'),('1d197443-1b09-430d-8426-24ae7f74f757','03837f71-9da5-4ba1-8558-c3dae79d6ea8','validatePasswordPolicy','false'),('1fa6bf99-021d-4426-acef-e0c65340c50a','03837f71-9da5-4ba1-8558-c3dae79d6ea8','fullSyncPeriod','-1'),('204888d1-4cda-4d30-8158-9884b30e2841','18584c2c-2af1-4deb-8204-f33a82b87010','allow-default-scopes','true'),('2171b4f0-deaa-4ab8-8ddd-e36d6c74e984','f0b85e83-55b5-4129-a0d7-9e0f930434c4','allowed-protocol-mapper-types','oidc-address-mapper'),('22cd4189-3417-4a12-9ad4-579716af7c7b','be72acd7-2087-495e-a0d1-301d321cac4b','is.mandatory.in.ldap','false'),('26c6f616-b2c7-4d45-8276-5a06ec51f473','58f1a27f-d961-4a0c-b49e-d8423d694bae','allowed-protocol-mapper-types','oidc-usermodel-attribute-mapper'),('26d57081-ffd7-43ac-a16f-28c789f00191','e20572d1-5584-4f2c-82d9-f7064fccfe4c','user.model.attribute','lastName'),('297d9da0-ae2f-4ac3-b442-b197652d40e7','17be5f48-db31-4813-b5f8-32f7315c551c','ldap.attribute','cn'),('2c0deaaf-7c73-4190-8ceb-a37c646ab6f0','3ebb390d-f317-43a6-9e52-69566f0d1a4b','secret','nSCxjzRX_HNy2KltblmYtDhzsLZh_us6jr43O-Un2vNnI1xKn3umAC3IFv20WaB7SPnrnFptDzzALeKIZpCNpw'),('30548e00-0e48-459e-b5e0-2d02d7f138fd','03837f71-9da5-4ba1-8558-c3dae79d6ea8','cachePolicy','DEFAULT'),('30bf4229-5304-414c-8058-a994dcc77958','676fbf1b-df0d-4b7f-b784-1b59acff57b5','kid','0aa6e665-aa04-4aec-84d6-541d78c6239c'),('30c4fe5f-2728-415c-ba66-203c26acc2ad','3ebb390d-f317-43a6-9e52-69566f0d1a4b','algorithm','HS256'),('347cf22c-590f-4704-8050-73e91be05402','20140e70-1963-45fa-9902-36fd803b9f1b','priority','100'),('34c5ed9d-d8f4-4bac-aa5c-cc3f208d1ee7','c15058f7-a6bf-4423-a149-1f8ceb99696b','allowed-protocol-mapper-types','oidc-sha256-pairwise-sub-mapper'),('3559d506-5af2-40d3-9386-bca9f67d8b1c','03837f71-9da5-4ba1-8558-c3dae79d6ea8','userObjectClasses','person,inetOrgPerson, organizationalPerson'),('36b8e7bb-8d2c-4507-aeeb-975fc995a816','be72acd7-2087-495e-a0d1-301d321cac4b','ldap.attribute','modifyTimestamp'),('3843e50e-8991-42af-8fef-4d10c99517c2','8cdbbda9-5c6d-4a37-84c6-7003f93e26ee','ldap.attribute','givenName'),('3bc551cf-e977-467e-af2b-2e1b2d8ef9dd','8cdbbda9-5c6d-4a37-84c6-7003f93e26ee','read.only','false'),('3dc07f52-7875-46f7-9e51-2360f4225c1e','67414869-c280-406e-83be-385c145f3c26','user.model.attribute','createTimestamp'),('425b6dba-2743-413e-8d9e-dcd3ee3d1d34','f0b85e83-55b5-4129-a0d7-9e0f930434c4','allowed-protocol-mapper-types','saml-user-property-mapper'),('43680c3e-b073-47fd-8dbe-591bf727af28','e20572d1-5584-4f2c-82d9-f7064fccfe4c','always.read.value.from.ldap','true'),('4a882ae2-dcf4-4cc9-8bc7-31b411b007fa','3ebb390d-f317-43a6-9e52-69566f0d1a4b','kid','f0a20930-ed8a-44e7-8208-2c2946acec2b'),('4a980406-a1fd-4eb1-af13-5c7b12617f53','c15058f7-a6bf-4423-a149-1f8ceb99696b','allowed-protocol-mapper-types','saml-role-list-mapper'),('4da3e456-5c8c-4dbd-a280-2bd91d945843','58f1a27f-d961-4a0c-b49e-d8423d694bae','allowed-protocol-mapper-types','saml-user-attribute-mapper'),('4db893c6-d925-476c-83f9-ffedd7d5ef3b','676fbf1b-df0d-4b7f-b784-1b59acff57b5','secret','1-rWjKvWe2lH_pN1H_LTyA'),('4e90046b-e56d-44bc-add8-99b9c83ec00c','f011ef48-981f-4805-8801-cd643f6c6406','read.only','false'),('527d957c-1105-457a-98d5-5c1ac7148bd8','375f68c1-aeaf-43a5-b181-c5a814d7410c','allowed-protocol-mapper-types','oidc-full-name-mapper'),('554be7ba-105f-45b2-8813-664d49d12855','375f68c1-aeaf-43a5-b181-c5a814d7410c','allowed-protocol-mapper-types','saml-role-list-mapper'),('56329ebe-1f1d-47f3-b3b0-3cd9db8fa266','7599466f-ef4e-4482-88e7-537e2ff9ea6a','allow-default-scopes','true'),('5633388f-a90f-46d1-b7fe-ca89b4daed15','c86fc1b2-504a-4417-bd42-779d46dcceab','client-uris-must-match','true'),('57645da1-f1d7-4b91-97d6-c1b1afed2ce3','58f1a27f-d961-4a0c-b49e-d8423d694bae','allowed-protocol-mapper-types','oidc-address-mapper'),('578ea77e-c84b-4480-87c9-4a5a81428557','67414869-c280-406e-83be-385c145f3c26','is.mandatory.in.ldap','false'),('584296d7-ba3a-4950-9125-892ef0e7be3c','441b18d6-0e4b-43ed-bc85-f953fb681526','max-clients','200'),('58cc3b3b-16f8-4272-a829-525f9f3107f1','375f68c1-aeaf-43a5-b181-c5a814d7410c','allowed-protocol-mapper-types','saml-user-attribute-mapper'),('5edc2cc8-5a68-4614-bb34-1cee8cdcfce3','8cdbbda9-5c6d-4a37-84c6-7003f93e26ee','user.model.attribute','firstName'),('60278d80-196d-463b-aeec-672ce429892a','03837f71-9da5-4ba1-8558-c3dae79d6ea8','bindDn','cn=admin,dc=oss,dc=deltares,dc=nl'),('60f14dc2-54bc-4cb0-bab7-84652beaf8f3','17be5f48-db31-4813-b5f8-32f7315c551c','user.model.attribute','username'),('66f15e61-4806-4e43-b948-ba761940b066','375f68c1-aeaf-43a5-b181-c5a814d7410c','allowed-protocol-mapper-types','oidc-usermodel-attribute-mapper'),('69c620f0-8b40-4355-af17-ab1dcd1c235e','25e1439a-c539-4596-8cbb-23a5d8e07dde','certificate','MIICmzCCAYMCBgF4sUfQ5zANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjEwNDA4MTEzNzU3WhcNMzEwNDA4MTEzOTM3WjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCAyDe6S1DL3mj9m8OuGgHEC/ijFP4p/xBAFfkmTEKxeV79qUv7iCpQAR6qlqYUi+2MR9FZcpjRWDS3oLSrFifvIDX1a0hXNNDWjlPqemgMCzfA/QS2TsHOKbmGPr8EyqBKzS/A8vsCMRsFs/Bt9AZwr4AtNxGUFUbimZlNfVsFem/LnzaacLxqkC/gMj4IsH4DfZL7FwHqZ/YRcsAwUn9gaxTF35yMWRfapumPh8DyF07tx5xMflwzDCR1gHI/keQ2xBzm2F3RdIefHxp4UJ3bYQCoU7fGhrs1j2v5wB9V2ET8O7FLA292CyZ+kvkDiJ1gj/IknHrqkDXcyA6/Sh5JAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAAYY2L+YJLf+uNjbEcUipyRz4eKRZUALVIm7/kpHGyqoaJgvCGo36hN2XBpmNF7xW+l5TFQ9HvIgeeWhW03ZnIYwzG6W36KaHDS78AxMh+iUD6PN4fvtlE6+vKqhJI/PV9bJy3bfkCVSdWq+8a7+Z8+xqLjtbj10X+WfyLwerTHhznhcmdid2RkErg8KXQ6mKVTP/8m09L91Z7Qy2k5PKf9IQx1s29FkcCSyVm/yQCFxcQGKql8Din0isIGpjZ2XOlwFzCE2Eu4JNE30f+b/wgRITvjUXChGU5UmXZmGQiMAiQOAmQXL0qlW6kxum5TXjKj2Pz3BhtY71kCX+B3zB1I='),('6f533ead-9af8-4127-90b1-fa9fa3da9aff','67414869-c280-406e-83be-385c145f3c26','always.read.value.from.ldap','true'),('7055d3b7-7cc6-4f96-b136-f021f159598f','28e62e97-a445-4efa-86d4-d46b17820bb0','privateKey','MIIEogIBAAKCAQEAhO892iwCxTBTZRBjxw7LKih7msvw2C+hc8yidLwKqD+hVsH1iVonNPS9aa1KTNDoF8vVzpLxqI0pUCnVtAm8VSmxibQ9lRO4CKsAxVt0dvalGDjsO+wfrcMnkGIaiBeN1Qh+VOeCbHBN+xfQs5nl82fkZxWDcQprbtaHA4SJ+ar16PyRcKznGkJoXiXRfrZ0+XxNJ+WA+4Meh/Ur6u8JZV+QGJyoiaqwDocJDt6qH3b+NpPYWZ++1YK0hq0xk5vl18maCcU7i50lPvLVKbBd4BftYh20YZcNa25mDWYK3/Z8xrBk1daEyIx2XSQrUl4PFSB1qin8ddoLqLweo7J8lwIDAQABAoIBADeJBysFJechSEW5ciWv7XpgTTsfJi/6vhV1kQz5ne4qHwnyn9RQ4H5EHHlwltC+maePVXQmeWS0DGVcBNZhFtfxQiVIMk38Xe7pGbpZea9VXSVganiiLZ3XIMk14uWmCW/NS5HG4frRdBKJIXxWt/VXGtlh6HaqJrW4U9FJZqbxhgU4cKTSGTFz/M+vdH2cg909tkPVPcIE37PrCB0U/noJcDjzt7uAp4AmtFNzWeaxljaiGM/jlQsGiMBmvl97JSZSsdhxgXMk3Kc3o1roqF8NnnbGM9dKk+MsWcyZU0SdqasCE6Gwnb9pzFW42isJ3qiiIlRL5rni6pn3luBjjNECgYEAuOlopWvUYiU2j4LUfDSE2z4nOQOC+GjX1NoLlUrFTOpWwln8wdhS2RPGPMmvpM1bM8+y77PoOGKpmSlO8kMANFmS2ZicD0g1LooK65DRBEerNzQJOrV9KBcHMZHXTeGtQM1E78rxdGd593N9v8T7JtVRM1+hpCX+Uy7r5RXuBokCgYEAuApatYrDIDs6OMZCLL8gsNtvhpfdaKFOPwptFvmnMUPEFh8B4prJaFvVrCthSy9sikQ+DTdaOEjLcF5Ho/yZ0UBarHpeFjzIgbmkzO7/UNimx/Xg/LYOJBJ0J8RNHDd0O7v48F1tTVepKvFX9DnEUh4CrR0PbXRuLZas/p6Voh8CgYB/vifgF8UqZGDz9cMLZ1DdEWwRVB63s95fGp9j7Lqu7zkunPIjjGl3s9SAjCi+Zmv6OCN6u0PTKD3YJJR4gtbyU2tCzc4KNK0g0JbItEvhdA2RXm5904F6H6aFlSYzlJOXL/fO3Nm/u/ijfXLC97c11ap6HjfR6blwB/tJUMQWWQKBgDen0fSyqP2XS60dr6duiUlSovB1/pwEo/AGjCT1jK0mTCdQazaHDr+DfiOZ3sto5Q8PQiCnC56XzQYHTOz3l+Ci8IjWXJum+A/a/06GPCnLyuwPnGFwAOjBt+TKxXLyBjGGT+sIpeRMzTjSUYHm8krPDoohjqfQHP2mYlyBwsiLAoGAWOgrn8Ub5G1uZnWu/klM0LfmP9C0Kt+G/6pGmV932fFV/TndLaOxviymcs+B81Bb6kE1xHM3Xnfkht63roLfLB7OSJ1XJN9OdrMy3b1Rlak1mXRQcjp2e/VUCLEoDSQWojLIINttcJElyR1+abD9Otxfs1fKA3Tr0JGTwGO0G0E='),('735906e8-3a4a-4f46-a89d-b6403c020110','03837f71-9da5-4ba1-8558-c3dae79d6ea8','usersDn','ou=people,dc=oss,dc=deltares,dc=nl'),('747cad2a-7469-4bda-8dd1-3d391d2b6b07','58f1a27f-d961-4a0c-b49e-d8423d694bae','allowed-protocol-mapper-types','oidc-sha256-pairwise-sub-mapper'),('76ae3567-f71a-4c93-8421-7eed073b1af7','03837f71-9da5-4ba1-8558-c3dae79d6ea8','allowKerberosAuthentication','false'),('7795f1c2-9eac-4f7f-9a32-c74370988f83','17be5f48-db31-4813-b5f8-32f7315c551c','is.mandatory.in.ldap','true'),('79c66225-1cc4-40b6-bb16-619916b095c9','375f68c1-aeaf-43a5-b181-c5a814d7410c','allowed-protocol-mapper-types','oidc-sha256-pairwise-sub-mapper'),('7c35cbb9-3911-47a5-a745-e9bce01b9e1e','37e3d953-995c-4bc7-aea6-21f24fd56520','allow-default-scopes','true'),('7cd66084-306d-4b73-8498-10c2f65bca03','e20572d1-5584-4f2c-82d9-f7064fccfe4c','read.only','false'),('7ebb003e-b1c9-405f-9e61-159fd461c2d1','03837f71-9da5-4ba1-8558-c3dae79d6ea8','useKerberosForPasswordAuthentication','false'),('7ed1723c-3676-4c6b-bdc5-394fb08942ed','c15058f7-a6bf-4423-a149-1f8ceb99696b','allowed-protocol-mapper-types','saml-user-attribute-mapper'),('81e5091b-a883-4dbe-98c0-2b9d7ce5a282','03837f71-9da5-4ba1-8558-c3dae79d6ea8','batchSizeForSync','1000'),('86eab130-98bf-4677-8bce-1c369da644eb','e20572d1-5584-4f2c-82d9-f7064fccfe4c','ldap.attribute','sn'),('8e73f47c-c38e-4948-92b3-eb3aeb79c9b7','28e62e97-a445-4efa-86d4-d46b17820bb0','certificate','MIICqzCCAZMCBgF4sUffdTANBgkqhkiG9w0BAQsFADAZMRcwFQYDVQQDDA5saWZlcmF5LXBvcnRhbDAeFw0yMTA0MDgxMTM4MDBaFw0zMTA0MDgxMTM5NDBaMBkxFzAVBgNVBAMMDmxpZmVyYXktcG9ydGFsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhO892iwCxTBTZRBjxw7LKih7msvw2C+hc8yidLwKqD+hVsH1iVonNPS9aa1KTNDoF8vVzpLxqI0pUCnVtAm8VSmxibQ9lRO4CKsAxVt0dvalGDjsO+wfrcMnkGIaiBeN1Qh+VOeCbHBN+xfQs5nl82fkZxWDcQprbtaHA4SJ+ar16PyRcKznGkJoXiXRfrZ0+XxNJ+WA+4Meh/Ur6u8JZV+QGJyoiaqwDocJDt6qH3b+NpPYWZ++1YK0hq0xk5vl18maCcU7i50lPvLVKbBd4BftYh20YZcNa25mDWYK3/Z8xrBk1daEyIx2XSQrUl4PFSB1qin8ddoLqLweo7J8lwIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQB188zpFZ+1U+tNpcFeJaYPN06NH/3IhzVkhW+WwPnc21r1bJwP3ArcrzKfVxaFmCQomJMwChzBWVIOidVMyXotW/c3WX+ZiQbG8DRkYtGU+q6/GKzZHdBU6x5wGjGjzXCzlAFeZ3SsoRdJafUR/DNjIwd1GmvjCitlDp9LKv9+kGoV/VelHSH6JnN1oZZjtvIhXBoMozX62NeLURBbsszL/GXONe+WM8SBlJs3EONc0y+MRqbJIyI75eUnNwPIHys2C1TKSEING9DrU0P5Pei8AYOboXNWMKos6UWkEwAiGP11BXq4zp3se+BX6StPpg1Ew5UpvDv5hfDG+SfICc/M'),('8eddf7e3-69f9-4785-8913-70c4b0d89486','03837f71-9da5-4ba1-8558-c3dae79d6ea8','priority','0'),('8f9c6a52-9c4f-4e26-a438-2b2f59f8cc1c','17be5f48-db31-4813-b5f8-32f7315c551c','read.only','false'),('913b1c10-b45a-4a13-84f1-130bbf18b89b','f0b85e83-55b5-4129-a0d7-9e0f930434c4','allowed-protocol-mapper-types','oidc-usermodel-attribute-mapper'),('921f807a-805b-4e46-b14c-9630258b40ac','03837f71-9da5-4ba1-8558-c3dae79d6ea8','vendor','other'),('94bfaddc-00e8-4c58-ab86-ca85f55305c7','03837f71-9da5-4ba1-8558-c3dae79d6ea8','uuidLDAPAttribute','cn'),('96a3fdec-6acd-4ef1-9d8f-19ac5dd230ee','03837f71-9da5-4ba1-8558-c3dae79d6ea8','rdnLDAPAttribute','cn'),('99615b95-a565-4e2d-8ab0-f11b9c3fbf72','e20572d1-5584-4f2c-82d9-f7064fccfe4c','is.mandatory.in.ldap','true'),('9ad527ed-d200-4ce6-b67e-9244079e95bc','be72acd7-2087-495e-a0d1-301d321cac4b','user.model.attribute','modifyTimestamp'),('9ca2d88e-e559-4f29-8e9e-c53bcf4d7c2f','f011ef48-981f-4805-8801-cd643f6c6406','is.mandatory.in.ldap','false'),('9e5c6760-4ef2-4d1b-b651-18a0115e039e','58f1a27f-d961-4a0c-b49e-d8423d694bae','allowed-protocol-mapper-types','saml-user-property-mapper'),('9e724ebe-d056-47c8-a7ff-d7f9b8605653','58f1a27f-d961-4a0c-b49e-d8423d694bae','allowed-protocol-mapper-types','saml-role-list-mapper'),('9f1eb4f0-a1c4-458d-b51d-6a005c7ff5fd','7dfe4eaa-9a70-435d-bbd0-3edd33ca837b','kid','38dc6340-acab-4769-a3d2-6c8e53cf090b'),('a2366634-19b7-4b7d-a245-6f2ff55c39b4','67414869-c280-406e-83be-385c145f3c26','ldap.attribute','createTimestamp'),('a4541079-6e0f-4e2b-a77e-b6c0f7dfc8ed','f0b85e83-55b5-4129-a0d7-9e0f930434c4','allowed-protocol-mapper-types','saml-user-attribute-mapper'),('a63ed77b-c2e6-42d0-ab86-48ddd7e6901e','f011ef48-981f-4805-8801-cd643f6c6406','ldap.attribute','mail'),('a6d383e6-2a8c-4375-9487-2c3ac380ce37','f0b85e83-55b5-4129-a0d7-9e0f930434c4','allowed-protocol-mapper-types','oidc-usermodel-property-mapper'),('a80eaf45-1ba0-4588-a615-1a3eb63089c9','58f1a27f-d961-4a0c-b49e-d8423d694bae','allowed-protocol-mapper-types','oidc-usermodel-property-mapper'),('aad76622-d591-4db7-8e3f-e630c67fcc85','c15058f7-a6bf-4423-a149-1f8ceb99696b','allowed-protocol-mapper-types','saml-user-property-mapper'),('ab0022c2-d12f-4e55-8074-8ef8c50a3ea7','25e1439a-c539-4596-8cbb-23a5d8e07dde','privateKey','MIIEpAIBAAKCAQEAgMg3uktQy95o/ZvDrhoBxAv4oxT+Kf8QQBX5JkxCsXle/alL+4gqUAEeqpamFIvtjEfRWXKY0Vg0t6C0qxYn7yA19WtIVzTQ1o5T6npoDAs3wP0Etk7Bzim5hj6/BMqgSs0vwPL7AjEbBbPwbfQGcK+ALTcRlBVG4pmZTX1bBXpvy582mnC8apAv4DI+CLB+A32S+xcB6mf2EXLAMFJ/YGsUxd+cjFkX2qbpj4fA8hdO7cecTH5cMwwkdYByP5HkNsQc5thd0XSHnx8aeFCd22EAqFO3xoa7NY9r+cAfVdhE/DuxSwNvdgsmfpL5A4idYI/yJJx66pA13MgOv0oeSQIDAQABAoIBAGaZnTRxzrQtxO3QTG0H0UJU20lqbzgcOuEj4HTvqu7kKJbla2DBUhydV8ZG8ZhLBv2NNjASScf3g0CM64OmyjgMLJETYPYBYSSfDiIwVXbK9SdvLXLleih7O5DFqr/xgxU1Ngd+Than8sRvYGzpTrw2RmM/jWSyMuBTyTxgU8UfbNljFg7NYkhzEj003Z26c0HB27iE89sj0zP24HJ5mKNrT3vLr0xs82f27yFQ5n6c/RZWzJFFsV88sxYqHAAGcbavdsfl0zTM8F5A8jd2kwRuSUZhlAsCog4kwkIVE1VtwIQCjKEIdlIuAplhbp9GE/b/sXLpm/Wwus9Fx07ZZhECgYEAyUQo9CtM51i4jbyR5Idrkt++RnoR+LBOz1uBI4Dq1PB1klbUOsuXdsw00oW853Ox4g49wn52q/5pgv3z+ALCPAJFZq2LAKCv+dWrjJdL0/gPPx92ld3pyoCYizSXtiF74m/YUdS1o996eaOOXtOlkZGXSrc6kjrQe6SaZ5xxtFUCgYEAo83TO5hSrNJkFMURFIMT/dp07Yp76sYk3iC5x5bskNV2BvnSp1w24A9wdjRBzpPAUschxoGbFXZrG46dygX2zwk/6/p97Do0kRr1C3ycf+WZBwB6WsuRvdMg5QqbbF/SEmA0/MlZsC1hLRTki+f04IyK1aZUl9Coyy3Lujms1iUCgYEAjwCr7JkbgkEqmCKEspB5oZjyFBdFz6JNzM1iWzyNHhFgKEMjdkcT7me3nGFD0FwG5uxhe2DpptkUVOtlvjEPmx2t+/+QhTim8GeeBT22tfxJsPsFAgTzyVtHh5kttk3iPQhk9fehu7bcybKpRL0ypdSBX6gCqMW7Pi69OqMQsyUCgYA/rrqLtcmWpOkB5iegyOER1UeWPju057nXnfN8NCHeoQ6U4bVTGZe//mCjo+ddr7WauQ5ZhXPMoVA803Jm43Czxw3R6X3hRDPBojBYeebCFnoq7bWj7PbJTPbB9SC9vncGa97N1F4JD2tzQmckOVbLofgr3dGPgDDPZQuKZ/fY/QKBgQCS2vUMfdp+FJM20ONxIoN0ZvhenL23FSjmRCJ0CKvKJfkduqrDSfCTJMgG7YXl7qJR9obh1qJTKprlJr/VGh6w35Z8qcy/5HRQnie9vq7NqhRftx/ztrtfC0cOJFd0lQeEnvwxDivxmbTbuyW1wLw7spVU4EZSoP6SJdEA6/519g=='),('af1c9045-49ac-441d-bc2d-f834d0917767','03837f71-9da5-4ba1-8558-c3dae79d6ea8','debug','false'),('b14ceb34-7ffd-41af-a2ef-f3a7ba544d81','375f68c1-aeaf-43a5-b181-c5a814d7410c','allowed-protocol-mapper-types','saml-user-property-mapper'),('b5dcd9d3-f5e3-4abd-b039-c8e441a420c3','03837f71-9da5-4ba1-8558-c3dae79d6ea8','connectionPooling','true'),('b8a5a1ae-7d8b-47c2-9b93-7c0eee99886a','0a1b32c6-c823-4e63-b414-6522f672ccc3','allow-default-scopes','true'),('bc225da5-dc97-4431-9269-8ef9722f3711','03837f71-9da5-4ba1-8558-c3dae79d6ea8','syncRegistrations','true'),('bea2999f-5a80-4555-b6c6-f329ecc4dcda','676fbf1b-df0d-4b7f-b784-1b59acff57b5','priority','100'),('c0dba013-ec7f-4474-aa56-17ffc08348a3','375f68c1-aeaf-43a5-b181-c5a814d7410c','allowed-protocol-mapper-types','oidc-usermodel-property-mapper'),('c33dc045-3b08-4348-9cd5-520bffd2e850','7dfe4eaa-9a70-435d-bbd0-3edd33ca837b','priority','100'),('c4462ffd-ccb6-42f0-92dc-26ad63f93b4a','03837f71-9da5-4ba1-8558-c3dae79d6ea8','authType','simple'),('c75e7c95-467f-4984-991a-33814694b304','f0b85e83-55b5-4129-a0d7-9e0f930434c4','allowed-protocol-mapper-types','oidc-full-name-mapper'),('c8e92519-7ad4-4cd9-83e7-12a9e3d48649','f011ef48-981f-4805-8801-cd643f6c6406','user.model.attribute','email'),('ca059d60-6bd0-43cf-94c0-a9cd774bfcef','68b48bd4-d709-49af-ba37-7398b2f8c5b4','max-clients','200'),('ca5f1633-82df-444a-9269-b23e65a9c544','3ebb390d-f317-43a6-9e52-69566f0d1a4b','priority','100'),('cb1df2f7-08e2-43bf-9a5f-6b65854f5ea0','28e62e97-a445-4efa-86d4-d46b17820bb0','priority','100'),('cc5a278c-2499-46e6-bd38-6bb4905a4f1f','c15058f7-a6bf-4423-a149-1f8ceb99696b','allowed-protocol-mapper-types','oidc-full-name-mapper'),('d765bc60-0816-4184-9fea-5fc77d9083e6','7dfe4eaa-9a70-435d-bbd0-3edd33ca837b','algorithm','HS256'),('db29a836-fb24-4b61-84e4-db903f9e432c','03837f71-9da5-4ba1-8558-c3dae79d6ea8','connectionUrl','ldap://localhost:1636'),('de1e5db8-96c8-4f50-b09a-68cc192d932b','c86fc1b2-504a-4417-bd42-779d46dcceab','host-sending-registration-request-must-match','true'),('dece1ab1-5257-4039-9973-10c628e8b60e','17be5f48-db31-4813-b5f8-32f7315c551c','always.read.value.from.ldap','false'),('e1ec56f8-6b58-4876-85d3-ac83c8f7a993','375f68c1-aeaf-43a5-b181-c5a814d7410c','allowed-protocol-mapper-types','oidc-address-mapper'),('e37a70ea-16b4-4d69-bd65-998edfb65572','03837f71-9da5-4ba1-8558-c3dae79d6ea8','bindCredential','**********'),('e53d70f1-625f-4ab7-a5b0-029a17d19499','19bbc5cd-0096-4bba-9595-703a4e7a5f1c','host-sending-registration-request-must-match','true'),('ea62faac-421f-4dbc-ae3d-89c437aecaff','03837f71-9da5-4ba1-8558-c3dae79d6ea8','changedSyncPeriod','-1'),('eb69613d-dead-409a-9ce9-e49d28192cd3','8cdbbda9-5c6d-4a37-84c6-7003f93e26ee','is.mandatory.in.ldap','true'),('ed73a947-373c-4aec-9f45-38da84183090','c15058f7-a6bf-4423-a149-1f8ceb99696b','allowed-protocol-mapper-types','oidc-address-mapper'),('ee0b532c-e81c-4f30-9b65-3ba70be034ed','67414869-c280-406e-83be-385c145f3c26','read.only','true'),('eefb239b-9145-40a3-a00e-4cd71c563ef2','20140e70-1963-45fa-9902-36fd803b9f1b','secret','VIgNNlCY_b8xqnAVivw7PA'),('ef9141c9-a09b-4f2b-81d0-f21625ce0b92','58f1a27f-d961-4a0c-b49e-d8423d694bae','allowed-protocol-mapper-types','oidc-full-name-mapper'),('f0d62718-1382-44b1-86cb-4d05dd9e71ef','7dfe4eaa-9a70-435d-bbd0-3edd33ca837b','secret','g6pwJEmFXACqdALW9nioVWdvcLpGXCdVa3GRrCI-7sTmtBrTvAhALdtvg64z0KuE5KS93DLeNOz8p-fMa8bdKQ'),('f22cd08b-788c-45f4-b926-5e6f41db8f4d','20140e70-1963-45fa-9902-36fd803b9f1b','kid','0011621b-7cf2-4232-b8b4-0f7e2df85c67'),('f2497d2d-3922-4b2b-99bb-6594a3fddaac','be72acd7-2087-495e-a0d1-301d321cac4b','always.read.value.from.ldap','true'),('f54515a7-271e-49a3-b36f-cf6199b65967','19bbc5cd-0096-4bba-9595-703a4e7a5f1c','client-uris-must-match','true'),('f8ae6d2c-1462-40df-94a7-f43cde913be2','c15058f7-a6bf-4423-a149-1f8ceb99696b','allowed-protocol-mapper-types','oidc-usermodel-property-mapper'),('faa0efc4-ce9b-4849-843a-9c01276488f9','25e1439a-c539-4596-8cbb-23a5d8e07dde','priority','100'),('fb368bd8-077f-46dd-946c-ce617ea4f442','03837f71-9da5-4ba1-8558-c3dae79d6ea8','enabled','false'),('fd3615bb-0ac5-4462-872f-6dba2260c67b','be72acd7-2087-495e-a0d1-301d321cac4b','read.only','true');
/*!40000 ALTER TABLE `COMPONENT_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COMPOSITE_ROLE`
--

DROP TABLE IF EXISTS `COMPOSITE_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMPOSITE_ROLE` (
  `COMPOSITE` varchar(36) NOT NULL,
  `CHILD_ROLE` varchar(36) NOT NULL,
  PRIMARY KEY (`COMPOSITE`,`CHILD_ROLE`),
  KEY `IDX_COMPOSITE` (`COMPOSITE`),
  KEY `IDX_COMPOSITE_CHILD` (`CHILD_ROLE`),
  CONSTRAINT `FK_A63WVEKFTU8JO1PNJ81E7MCE2` FOREIGN KEY (`COMPOSITE`) REFERENCES `KEYCLOAK_ROLE` (`ID`),
  CONSTRAINT `FK_GR7THLLB9LU8Q4VQA4524JJY8` FOREIGN KEY (`CHILD_ROLE`) REFERENCES `KEYCLOAK_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMPOSITE_ROLE`
--

LOCK TABLES `COMPOSITE_ROLE` WRITE;
/*!40000 ALTER TABLE `COMPOSITE_ROLE` DISABLE KEYS */;
INSERT INTO `COMPOSITE_ROLE` VALUES ('0ab906cb-cc4f-4af4-9dd4-e676e6f0f9f3','5e57a33b-de71-4df9-b941-05bd9d599478'),('3d9c5e71-233a-4e29-9e60-f5d9129f9c57','c524cfb5-69f9-4423-8eab-e057118149ef'),('3d9c5e71-233a-4e29-9e60-f5d9129f9c57','df21d69e-011e-41c6-b8f4-983b4b7b1c01'),('6fc6d874-b718-468f-9d78-b1ab20ef0391','b0766bbf-93ae-48ce-8e7f-d8286db7811a'),('6fc6d874-b718-468f-9d78-b1ab20ef0391','d2d76201-4687-4118-8fc9-5275666a507f'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','06f1980a-cdbc-4fe3-a2aa-01ea57eae2ce'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','174374c7-a893-4867-98b4-22b624d927ee'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','2ff71c9b-391d-4e49-8871-c0429f5d3ada'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','375717d0-db43-469d-a2bc-2b514bf8251c'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','3d9c5e71-233a-4e29-9e60-f5d9129f9c57'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','4ae9f0e8-1f16-44ef-90c9-558002aee88b'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','5f6c2d9f-744b-49f1-ad3a-03c1abbbe1f5'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','789a51ae-8ae9-406b-a9bf-02fa6df70bac'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','7c636332-3fd3-4f72-bc53-551b3b91d5bf'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','907300b5-9e9a-423d-96fe-1c5604d00dc9'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','996812e8-82a2-4b44-9b38-0cad02f391ec'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','b6d58577-5e67-4e02-a166-80da656d8c57'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','bf76efcd-e2c7-4ba3-9f8a-507e787c26b2'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','c524cfb5-69f9-4423-8eab-e057118149ef'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','d76e15a1-3afe-435d-8720-69c533fd4b18'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','df21d69e-011e-41c6-b8f4-983b4b7b1c01'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','eb738200-9d7e-4235-86b8-f5351679f7df'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','fcc87b08-b814-48c0-baee-97f9b81de4e4'),('b3a357a8-691e-4855-bf2c-a2c5757f8a54','1b40735f-07e5-48a8-a1d5-877e1d266790'),('bf5081f3-e26c-4040-9c69-d99ec64a8eaa','6ab65a6b-280d-4bb5-84e0-179cb3ceb0ec'),('e5636186-d221-43bf-9267-f8a0f67095b0','059d4f19-1a45-40f1-865a-9bb8bff29baa'),('e5636186-d221-43bf-9267-f8a0f67095b0','07645452-fe83-4846-bb3a-9779265d532e'),('e5636186-d221-43bf-9267-f8a0f67095b0','0c63cc77-5e0f-4adb-a34e-6d586a454e2c'),('e5636186-d221-43bf-9267-f8a0f67095b0','0ed0a574-da50-4d78-b46e-3577fefc7074'),('e5636186-d221-43bf-9267-f8a0f67095b0','17116a85-c7f7-48de-ac78-a83f88557568'),('e5636186-d221-43bf-9267-f8a0f67095b0','3947b9dc-f2f9-4bc7-b936-c777fb54069a'),('e5636186-d221-43bf-9267-f8a0f67095b0','4b008f85-b09a-4906-9aa0-2915474ab2f7'),('e5636186-d221-43bf-9267-f8a0f67095b0','4c400968-a526-419d-869c-59a1554a6a75'),('e5636186-d221-43bf-9267-f8a0f67095b0','51563dae-4e98-46a0-9654-957a7b0a404f'),('e5636186-d221-43bf-9267-f8a0f67095b0','545a4d10-404f-4b85-a8bc-34eb54172599'),('e5636186-d221-43bf-9267-f8a0f67095b0','55bfd3d7-0e1a-400f-baf8-0e4e8d234b89'),('e5636186-d221-43bf-9267-f8a0f67095b0','588bf995-f90d-4658-a41c-3036fc8684b2'),('e5636186-d221-43bf-9267-f8a0f67095b0','6ab65a6b-280d-4bb5-84e0-179cb3ceb0ec'),('e5636186-d221-43bf-9267-f8a0f67095b0','6fc6d874-b718-468f-9d78-b1ab20ef0391'),('e5636186-d221-43bf-9267-f8a0f67095b0','7eaba2a8-49ab-4173-92ca-56b7e58afc6d'),('e5636186-d221-43bf-9267-f8a0f67095b0','87b0300c-cc13-4e0f-abfb-0eae5c0d99fc'),('e5636186-d221-43bf-9267-f8a0f67095b0','8a47e63d-596d-49c2-bd83-f1971e93fb18'),('e5636186-d221-43bf-9267-f8a0f67095b0','8dc48f0d-a02e-4360-a4a6-8f17c0b482cb'),('e5636186-d221-43bf-9267-f8a0f67095b0','927460c0-4081-49af-967f-d616d02a1337'),('e5636186-d221-43bf-9267-f8a0f67095b0','92d83d6a-97b3-4997-a52e-f15baf8132e3'),('e5636186-d221-43bf-9267-f8a0f67095b0','a4115cd3-76f2-4094-a866-acb1bbec9674'),('e5636186-d221-43bf-9267-f8a0f67095b0','a9351d2d-8ebe-439d-8ee7-dd52b6ed150f'),('e5636186-d221-43bf-9267-f8a0f67095b0','b0766bbf-93ae-48ce-8e7f-d8286db7811a'),('e5636186-d221-43bf-9267-f8a0f67095b0','b72cc114-4029-475b-928b-1d3e4e5d7c83'),('e5636186-d221-43bf-9267-f8a0f67095b0','b8893ff5-9180-4ad8-8da2-cda8cb08b91c'),('e5636186-d221-43bf-9267-f8a0f67095b0','bf5081f3-e26c-4040-9c69-d99ec64a8eaa'),('e5636186-d221-43bf-9267-f8a0f67095b0','d2d76201-4687-4118-8fc9-5275666a507f'),('e5636186-d221-43bf-9267-f8a0f67095b0','d9e8aba2-54fe-4281-aeb7-ac904109fa7a'),('e5636186-d221-43bf-9267-f8a0f67095b0','dddf08d4-1fb3-4760-8a17-db88e47f20f2'),('e5636186-d221-43bf-9267-f8a0f67095b0','e317b333-aa1c-4f2e-a3df-76a4fde61902'),('e5636186-d221-43bf-9267-f8a0f67095b0','e31d20dd-bc25-4acb-99a1-84ab43719f56'),('e5636186-d221-43bf-9267-f8a0f67095b0','e8e02874-cf61-4933-9469-5559c422a6c5'),('e5636186-d221-43bf-9267-f8a0f67095b0','ec622a84-2b17-4260-ab86-bac6a872ab8a'),('e5636186-d221-43bf-9267-f8a0f67095b0','ef054e30-c8cd-4cd8-9b00-04c1992f276e'),('e5636186-d221-43bf-9267-f8a0f67095b0','efd229c1-fa0f-43ed-83ed-457b20f4fb46'),('e5636186-d221-43bf-9267-f8a0f67095b0','fa8ba997-495f-4a68-a09d-0a5db04079a4'),('e5636186-d221-43bf-9267-f8a0f67095b0','fb9b1194-4460-4d3d-95d1-66c4dc3adbee'),('e8e02874-cf61-4933-9469-5559c422a6c5','92d83d6a-97b3-4997-a52e-f15baf8132e3'),('efd229c1-fa0f-43ed-83ed-457b20f4fb46','0ed0a574-da50-4d78-b46e-3577fefc7074'),('efd229c1-fa0f-43ed-83ed-457b20f4fb46','4b008f85-b09a-4906-9aa0-2915474ab2f7'),('fcc87b08-b814-48c0-baee-97f9b81de4e4','d76e15a1-3afe-435d-8720-69c533fd4b18');
/*!40000 ALTER TABLE `COMPOSITE_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CREDENTIAL`
--

DROP TABLE IF EXISTS `CREDENTIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CREDENTIAL` (
  `ID` varchar(36) NOT NULL,
  `DEVICE` varchar(255) DEFAULT NULL,
  `HASH_ITERATIONS` int(11) DEFAULT NULL,
  `SALT` tinyblob,
  `TYPE` varchar(255) DEFAULT NULL,
  `VALUE` varchar(4000) DEFAULT NULL,
  `USER_ID` varchar(36) DEFAULT NULL,
  `CREATED_DATE` bigint(20) DEFAULT NULL,
  `COUNTER` int(11) DEFAULT '0',
  `DIGITS` int(11) DEFAULT '6',
  `PERIOD` int(11) DEFAULT '30',
  `ALGORITHM` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_USER_CREDENTIAL` (`USER_ID`),
  CONSTRAINT `FK_PFYR0GLASQYL0DEI3KL69R6V0` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CREDENTIAL`
--

LOCK TABLES `CREDENTIAL` WRITE;
/*!40000 ALTER TABLE `CREDENTIAL` DISABLE KEYS */;
INSERT INTO `CREDENTIAL` VALUES ('598069f0-7940-4dc4-9765-dde080691a04',NULL,27500,'ûg\ZÓ~l—\\Ùå6æÓ∏úŒ','password','/MpPd9FqdXtpXb+R7Kq/4ccrqh6F8VvlzfE7RpdZWNti77SxfmQD38e4mAy6NpwiYKDFXfufrSZk0PCcu3pSwQ==','6360111f-0e87-4f47-873a-72c07714a985',NULL,0,0,0,'pbkdf2-sha256'),('b0ead23d-a4ce-4a9a-8153-0176b30b4bb2',NULL,27500,'|}Ø´≤Ó‚£&ÚÌ:ÕDÌ','password','TnZNVk3bd1vkg+s9GPmcZm6Y/4W+EkUX5ogQ6C4F3UA3+PwtjIe1X4MxJxA0yINLy3LJnEh7C6D7IgMFPORzmw==','fc7b51a0-662e-44ca-8621-5921972d159c',1620981580651,0,0,0,'pbkdf2-sha256');
/*!40000 ALTER TABLE `CREDENTIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CREDENTIAL_ATTRIBUTE`
--

DROP TABLE IF EXISTS `CREDENTIAL_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CREDENTIAL_ATTRIBUTE` (
  `ID` varchar(36) NOT NULL,
  `CREDENTIAL_ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_CREDENTIAL_ATTR_CRED` (`CREDENTIAL_ID`),
  CONSTRAINT `FK_CRED_ATTR` FOREIGN KEY (`CREDENTIAL_ID`) REFERENCES `CREDENTIAL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CREDENTIAL_ATTRIBUTE`
--

LOCK TABLES `CREDENTIAL_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `CREDENTIAL_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `CREDENTIAL_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG`
--

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG` VALUES ('1.0.0.Final-KEYCLOAK-5461','sthorger@redhat.com','META-INF/jpa-changelog-1.0.0.Final.xml','2021-04-08 11:39:10',1,'EXECUTED','7:4e70412f24a3f382c82183742ec79317','createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.0.0.Final-KEYCLOAK-5461','sthorger@redhat.com','META-INF/db2-jpa-changelog-1.0.0.Final.xml','2021-04-08 11:39:10',2,'MARK_RAN','7:cb16724583e9675711801c6875114f28','createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.1.0.Beta1','sthorger@redhat.com','META-INF/jpa-changelog-1.1.0.Beta1.xml','2021-04-08 11:39:10',3,'EXECUTED','7:0310eb8ba07cec616460794d42ade0fa','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.1.0.Final','sthorger@redhat.com','META-INF/jpa-changelog-1.1.0.Final.xml','2021-04-08 11:39:10',4,'EXECUTED','7:5d25857e708c3233ef4439df1f93f012','renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.2.0.Beta1','psilva@redhat.com','META-INF/jpa-changelog-1.2.0.Beta1.xml','2021-04-08 11:39:11',5,'EXECUTED','7:c7a54a1041d58eb3817a4a883b4d4e84','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.2.0.Beta1','psilva@redhat.com','META-INF/db2-jpa-changelog-1.2.0.Beta1.xml','2021-04-08 11:39:11',6,'MARK_RAN','7:2e01012df20974c1c2a605ef8afe25b7','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.2.0.RC1','bburke@redhat.com','META-INF/jpa-changelog-1.2.0.CR1.xml','2021-04-08 11:39:12',7,'EXECUTED','7:0f08df48468428e0f30ee59a8ec01a41','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.2.0.RC1','bburke@redhat.com','META-INF/db2-jpa-changelog-1.2.0.CR1.xml','2021-04-08 11:39:12',8,'MARK_RAN','7:a77ea2ad226b345e7d689d366f185c8c','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.2.0.Final','keycloak','META-INF/jpa-changelog-1.2.0.Final.xml','2021-04-08 11:39:12',9,'EXECUTED','7:a3377a2059aefbf3b90ebb4c4cc8e2ab','update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.3.0','bburke@redhat.com','META-INF/jpa-changelog-1.3.0.xml','2021-04-08 11:39:13',10,'EXECUTED','7:04c1dbedc2aa3e9756d1a1668e003451','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.4.0','bburke@redhat.com','META-INF/jpa-changelog-1.4.0.xml','2021-04-08 11:39:14',11,'EXECUTED','7:36ef39ed560ad07062d956db861042ba','delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.4.0','bburke@redhat.com','META-INF/db2-jpa-changelog-1.4.0.xml','2021-04-08 11:39:14',12,'MARK_RAN','7:d909180b2530479a716d3f9c9eaea3d7','delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.5.0','bburke@redhat.com','META-INF/jpa-changelog-1.5.0.xml','2021-04-08 11:39:14',13,'EXECUTED','7:cf12b04b79bea5152f165eb41f3955f6','delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.6.1_from15','mposolda@redhat.com','META-INF/jpa-changelog-1.6.1.xml','2021-04-08 11:39:14',14,'EXECUTED','7:7e32c8f05c755e8675764e7d5f514509','addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.6.1_from16-pre','mposolda@redhat.com','META-INF/jpa-changelog-1.6.1.xml','2021-04-08 11:39:14',15,'MARK_RAN','7:980ba23cc0ec39cab731ce903dd01291','delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.6.1_from16','mposolda@redhat.com','META-INF/jpa-changelog-1.6.1.xml','2021-04-08 11:39:14',16,'MARK_RAN','7:2fa220758991285312eb84f3b4ff5336','dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.6.1','mposolda@redhat.com','META-INF/jpa-changelog-1.6.1.xml','2021-04-08 11:39:14',17,'EXECUTED','7:d41d8cd98f00b204e9800998ecf8427e','empty','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.7.0','bburke@redhat.com','META-INF/jpa-changelog-1.7.0.xml','2021-04-08 11:39:15',18,'EXECUTED','7:91ace540896df890cc00a0490ee52bbc','createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.8.0','mposolda@redhat.com','META-INF/jpa-changelog-1.8.0.xml','2021-04-08 11:39:15',19,'EXECUTED','7:c31d1646dfa2618a9335c00e07f89f24','addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.8.0-2','keycloak','META-INF/jpa-changelog-1.8.0.xml','2021-04-08 11:39:15',20,'EXECUTED','7:df8bc21027a4f7cbbb01f6344e89ce07','dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.8.0','mposolda@redhat.com','META-INF/db2-jpa-changelog-1.8.0.xml','2021-04-08 11:39:15',21,'MARK_RAN','7:f987971fe6b37d963bc95fee2b27f8df','addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.8.0-2','keycloak','META-INF/db2-jpa-changelog-1.8.0.xml','2021-04-08 11:39:15',22,'MARK_RAN','7:df8bc21027a4f7cbbb01f6344e89ce07','dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.9.0','mposolda@redhat.com','META-INF/jpa-changelog-1.9.0.xml','2021-04-08 11:39:16',23,'EXECUTED','7:ed2dc7f799d19ac452cbcda56c929e47','update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.9.1','keycloak','META-INF/jpa-changelog-1.9.1.xml','2021-04-08 11:39:16',24,'EXECUTED','7:80b5db88a5dda36ece5f235be8757615','modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.9.1','keycloak','META-INF/db2-jpa-changelog-1.9.1.xml','2021-04-08 11:39:16',25,'MARK_RAN','7:1437310ed1305a9b93f8848f301726ce','modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM','',NULL,'3.5.4',NULL,NULL,'7881948490'),('1.9.2','keycloak','META-INF/jpa-changelog-1.9.2.xml','2021-04-08 11:39:16',26,'EXECUTED','7:b82ffb34850fa0836be16deefc6a87c4','createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authz-2.0.0','psilva@redhat.com','META-INF/jpa-changelog-authz-2.0.0.xml','2021-04-08 11:39:17',27,'EXECUTED','7:9cc98082921330d8d9266decdd4bd658','createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authz-2.5.1','psilva@redhat.com','META-INF/jpa-changelog-authz-2.5.1.xml','2021-04-08 11:39:17',28,'EXECUTED','7:03d64aeed9cb52b969bd30a7ac0db57e','update tableName=RESOURCE_SERVER_POLICY','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.1.0-KEYCLOAK-5461','bburke@redhat.com','META-INF/jpa-changelog-2.1.0.xml','2021-04-08 11:39:18',29,'EXECUTED','7:f1f9fd8710399d725b780f463c6b21cd','createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.2.0','bburke@redhat.com','META-INF/jpa-changelog-2.2.0.xml','2021-04-08 11:39:18',30,'EXECUTED','7:53188c3eb1107546e6f765835705b6c1','addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.3.0','bburke@redhat.com','META-INF/jpa-changelog-2.3.0.xml','2021-04-08 11:39:19',31,'EXECUTED','7:d6e6f3bc57a0c5586737d1351725d4d4','createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.4.0','bburke@redhat.com','META-INF/jpa-changelog-2.4.0.xml','2021-04-08 11:39:19',32,'EXECUTED','7:454d604fbd755d9df3fd9c6329043aa5','customChange','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.5.0','bburke@redhat.com','META-INF/jpa-changelog-2.5.0.xml','2021-04-08 11:39:19',33,'EXECUTED','7:57e98a3077e29caf562f7dbf80c72600','customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.5.0-unicode-oracle','hmlnarik@redhat.com','META-INF/jpa-changelog-2.5.0.xml','2021-04-08 11:39:19',34,'MARK_RAN','7:e4c7e8f2256210aee71ddc42f538b57a','modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.5.0-unicode-other-dbs','hmlnarik@redhat.com','META-INF/jpa-changelog-2.5.0.xml','2021-04-08 11:39:19',35,'EXECUTED','7:09a43c97e49bc626460480aa1379b522','modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.5.0-duplicate-email-support','slawomir@dabek.name','META-INF/jpa-changelog-2.5.0.xml','2021-04-08 11:39:19',36,'EXECUTED','7:26bfc7c74fefa9126f2ce702fb775553','addColumn tableName=REALM','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.5.0-unique-group-names','hmlnarik@redhat.com','META-INF/jpa-changelog-2.5.0.xml','2021-04-08 11:39:19',37,'EXECUTED','7:a161e2ae671a9020fff61e996a207377','addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP','',NULL,'3.5.4',NULL,NULL,'7881948490'),('2.5.1','bburke@redhat.com','META-INF/jpa-changelog-2.5.1.xml','2021-04-08 11:39:19',38,'EXECUTED','7:37fc1781855ac5388c494f1442b3f717','addColumn tableName=FED_USER_CONSENT','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.0.0','bburke@redhat.com','META-INF/jpa-changelog-3.0.0.xml','2021-04-08 11:39:19',39,'EXECUTED','7:13a27db0dae6049541136adad7261d27','addColumn tableName=IDENTITY_PROVIDER','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.2.0-fix','keycloak','META-INF/jpa-changelog-3.2.0.xml','2021-04-08 11:39:19',40,'MARK_RAN','7:550300617e3b59e8af3a6294df8248a3','addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.2.0-fix-with-keycloak-5416','keycloak','META-INF/jpa-changelog-3.2.0.xml','2021-04-08 11:39:19',41,'MARK_RAN','7:e3a9482b8931481dc2772a5c07c44f17','dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.2.0-fix-offline-sessions','hmlnarik','META-INF/jpa-changelog-3.2.0.xml','2021-04-08 11:39:19',42,'EXECUTED','7:72b07d85a2677cb257edb02b408f332d','customChange','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.2.0-fixed','keycloak','META-INF/jpa-changelog-3.2.0.xml','2021-04-08 11:39:20',43,'EXECUTED','7:a72a7858967bd414835d19e04d880312','addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.3.0','keycloak','META-INF/jpa-changelog-3.3.0.xml','2021-04-08 11:39:20',44,'EXECUTED','7:94edff7cf9ce179e7e85f0cd78a3cf2c','addColumn tableName=USER_ENTITY','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authz-3.4.0.CR1-resource-server-pk-change-part1','glavoie@gmail.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2021-04-08 11:39:20',45,'EXECUTED','7:6a48ce645a3525488a90fbf76adf3bb3','addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095','hmlnarik@redhat.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2021-04-08 11:39:20',46,'EXECUTED','7:e64b5dcea7db06077c6e57d3b9e5ca14','customChange','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authz-3.4.0.CR1-resource-server-pk-change-part3-fixed','glavoie@gmail.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2021-04-08 11:39:20',47,'MARK_RAN','7:fd8cf02498f8b1e72496a20afc75178c','dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex','glavoie@gmail.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2021-04-08 11:39:21',48,'EXECUTED','7:542794f25aa2b1fbabb7e577d6646319','addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authn-3.4.0.CR1-refresh-token-max-reuse','glavoie@gmail.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2021-04-08 11:39:21',49,'EXECUTED','7:edad604c882df12f74941dac3cc6d650','addColumn tableName=REALM','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.4.0','keycloak','META-INF/jpa-changelog-3.4.0.xml','2021-04-08 11:39:22',50,'EXECUTED','7:0f88b78b7b46480eb92690cbf5e44900','addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.4.0-KEYCLOAK-5230','hmlnarik@redhat.com','META-INF/jpa-changelog-3.4.0.xml','2021-04-08 11:39:22',51,'EXECUTED','7:d560e43982611d936457c327f872dd59','createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.4.1','psilva@redhat.com','META-INF/jpa-changelog-3.4.1.xml','2021-04-08 11:39:22',52,'EXECUTED','7:c155566c42b4d14ef07059ec3b3bbd8e','modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.4.2','keycloak','META-INF/jpa-changelog-3.4.2.xml','2021-04-08 11:39:22',53,'EXECUTED','7:b40376581f12d70f3c89ba8ddf5b7dea','update tableName=REALM','',NULL,'3.5.4',NULL,NULL,'7881948490'),('3.4.2-KEYCLOAK-5172','mkanis@redhat.com','META-INF/jpa-changelog-3.4.2.xml','2021-04-08 11:39:22',54,'EXECUTED','7:a1132cc395f7b95b3646146c2e38f168','update tableName=CLIENT','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.0.0-KEYCLOAK-6335','bburke@redhat.com','META-INF/jpa-changelog-4.0.0.xml','2021-04-08 11:39:22',55,'EXECUTED','7:d8dc5d89c789105cfa7ca0e82cba60af','createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.0.0-CLEANUP-UNUSED-TABLE','bburke@redhat.com','META-INF/jpa-changelog-4.0.0.xml','2021-04-08 11:39:22',56,'EXECUTED','7:7822e0165097182e8f653c35517656a3','dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.0.0-KEYCLOAK-6228','bburke@redhat.com','META-INF/jpa-changelog-4.0.0.xml','2021-04-08 11:39:23',57,'EXECUTED','7:c6538c29b9c9a08f9e9ea2de5c2b6375','dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.0.0-KEYCLOAK-5579-fixed','mposolda@redhat.com','META-INF/jpa-changelog-4.0.0.xml','2021-04-08 11:39:24',58,'EXECUTED','7:6d4893e36de22369cf73bcb051ded875','dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authz-4.0.0.CR1','psilva@redhat.com','META-INF/jpa-changelog-authz-4.0.0.CR1.xml','2021-04-08 11:39:25',59,'EXECUTED','7:57960fc0b0f0dd0563ea6f8b2e4a1707','createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authz-4.0.0.Beta3','psilva@redhat.com','META-INF/jpa-changelog-authz-4.0.0.Beta3.xml','2021-04-08 11:39:25',60,'EXECUTED','7:2b4b8bff39944c7097977cc18dbceb3b','addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY','',NULL,'3.5.4',NULL,NULL,'7881948490'),('authz-4.2.0.Final','mhajas@redhat.com','META-INF/jpa-changelog-authz-4.2.0.Final.xml','2021-04-08 11:39:25',61,'EXECUTED','7:2aa42a964c59cd5b8ca9822340ba33a8','createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.2.0-KEYCLOAK-6313','wadahiro@gmail.com','META-INF/jpa-changelog-4.2.0.xml','2021-04-08 11:39:25',62,'EXECUTED','7:14d407c35bc4fe1976867756bcea0c36','addColumn tableName=REQUIRED_ACTION_PROVIDER','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.3.0-KEYCLOAK-7984','wadahiro@gmail.com','META-INF/jpa-changelog-4.3.0.xml','2021-04-08 11:39:25',63,'EXECUTED','7:241a8030c748c8548e346adee548fa93','update tableName=REQUIRED_ACTION_PROVIDER','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.6.0-KEYCLOAK-7950','psilva@redhat.com','META-INF/jpa-changelog-4.6.0.xml','2021-04-08 11:39:25',64,'EXECUTED','7:7d3182f65a34fcc61e8d23def037dc3f','update tableName=RESOURCE_SERVER_RESOURCE','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.6.0-KEYCLOAK-8377','keycloak','META-INF/jpa-changelog-4.6.0.xml','2021-04-08 11:39:25',65,'EXECUTED','7:b30039e00a0b9715d430d1b0636728fa','createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.6.0-KEYCLOAK-8555','gideonray@gmail.com','META-INF/jpa-changelog-4.6.0.xml','2021-04-08 11:39:25',66,'EXECUTED','7:3797315ca61d531780f8e6f82f258159','createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.7.0-KEYCLOAK-1267','sguilhen@redhat.com','META-INF/jpa-changelog-4.7.0.xml','2021-04-08 11:39:25',67,'EXECUTED','7:c7aa4c8d9573500c2d347c1941ff0301','addColumn tableName=REALM','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.7.0-KEYCLOAK-7275','keycloak','META-INF/jpa-changelog-4.7.0.xml','2021-04-08 11:39:26',68,'EXECUTED','7:b207faee394fc074a442ecd42185a5dd','renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...','',NULL,'3.5.4',NULL,NULL,'7881948490'),('4.8.0-KEYCLOAK-8835','sguilhen@redhat.com','META-INF/jpa-changelog-4.8.0.xml','2021-04-08 11:39:26',69,'EXECUTED','7:ab9a9762faaba4ddfa35514b212c4922','addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM','',NULL,'3.5.4',NULL,NULL,'7881948490');
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` VALUES (1,'\0',NULL,NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOG_AVATAR_ENT`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG_AVATAR_ENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOG_AVATAR_ENT` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG_AVATAR_ENT`
--

LOCK TABLES `DATABASECHANGELOG_AVATAR_ENT` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG_AVATAR_ENT` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG_AVATAR_ENT` VALUES ('1.0','erik.derooij@deltares.nl','META-INF/avatar-changelog.xml','2021-04-08 11:39:28',1,'EXECUTED','7:6ea25f29f5520133018408ac09a19ef7','createTable tableName=USER_AVATAR; addPrimaryKey constraintName=PK_USER_AVATAR, tableName=USER_AVATAR; addForeignKeyConstraint baseTableName=USER_AVATAR, constraintName=FK_USER_AVATAR_USER_ENTITY, referencedTableName=USER_ENTITY; addForeignKeyCons...','',NULL,'3.5.4',NULL,NULL,'7881968353'),('1.1','erik.derooij@deltares.nl','META-INF/avatar-changelog.xml','2021-04-08 11:39:28',2,'EXECUTED','7:285ce67a1144c7d749322d938b0c0fdd','addColumn tableName=USER_AVATAR','',NULL,'3.5.4',NULL,NULL,'7881968353');
/*!40000 ALTER TABLE `DATABASECHANGELOG_AVATAR_ENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOG_MAILING_EN`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG_MAILING_EN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOG_MAILING_EN` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG_MAILING_EN`
--

LOCK TABLES `DATABASECHANGELOG_MAILING_EN` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG_MAILING_EN` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG_MAILING_EN` VALUES ('1.0','erik.derooij@deltares.nl','META-INF/mailing-changelog.xml','2021-04-08 11:39:28',1,'EXECUTED','7:80c8e5f1cd0e1ee66b7dd9adb2418780','createTable tableName=MAILING_ENTITY; addPrimaryKey constraintName=PK_MAILING_ENTITY, tableName=MAILING_ENTITY; addUniqueConstraint constraintName=UK_MAILING_NAME, tableName=MAILING_ENTITY; addForeignKeyConstraint baseTableName=MAILING_ENTITY, con...','',NULL,'3.5.4',NULL,NULL,'7881968006'),('1.1','erik.derooij@deltares.nl','META-INF/mailing-changelog.xml','2021-04-08 11:39:28',2,'EXECUTED','7:0727d0d04f32655429acb1b971c02ba2','addDefaultValue columnName=FREQUENCY, tableName=MAILING_ENTITY','',NULL,'3.5.4',NULL,NULL,'7881968006');
/*!40000 ALTER TABLE `DATABASECHANGELOG_MAILING_EN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOG_USER_MAILI`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG_USER_MAILI`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOG_USER_MAILI` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG_USER_MAILI`
--

LOCK TABLES `DATABASECHANGELOG_USER_MAILI` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG_USER_MAILI` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG_USER_MAILI` VALUES ('1.0','erik.derooij@deltares.nl','META-INF/usermailing-changelog.xml','2021-04-08 11:39:28',1,'EXECUTED','7:6e9818f583c408e4b23a5ddc88d4b89f','createTable tableName=USER_MAILING; addPrimaryKey constraintName=PK_USER_MAILING, tableName=USER_MAILING; addForeignKeyConstraint baseTableName=USER_MAILING, constraintName=FK_USER_MAILING_USER_ENTITY, referencedTableName=USER_ENTITY; addForeignKe...','',NULL,'3.5.4',NULL,NULL,'7881968611');
/*!40000 ALTER TABLE `DATABASECHANGELOG_USER_MAILI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DEFAULT_CLIENT_SCOPE`
--

DROP TABLE IF EXISTS `DEFAULT_CLIENT_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DEFAULT_CLIENT_SCOPE` (
  `REALM_ID` varchar(36) NOT NULL,
  `SCOPE_ID` varchar(36) NOT NULL,
  `DEFAULT_SCOPE` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`REALM_ID`,`SCOPE_ID`),
  KEY `IDX_DEFCLS_REALM` (`REALM_ID`),
  KEY `IDX_DEFCLS_SCOPE` (`SCOPE_ID`),
  CONSTRAINT `FK_R_DEF_CLI_SCOPE_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`),
  CONSTRAINT `FK_R_DEF_CLI_SCOPE_SCOPE` FOREIGN KEY (`SCOPE_ID`) REFERENCES `CLIENT_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DEFAULT_CLIENT_SCOPE`
--

LOCK TABLES `DEFAULT_CLIENT_SCOPE` WRITE;
/*!40000 ALTER TABLE `DEFAULT_CLIENT_SCOPE` DISABLE KEYS */;
INSERT INTO `DEFAULT_CLIENT_SCOPE` VALUES ('liferay-portal','3522f3d8-9fad-4c62-bfc8-000b9091361a',''),('liferay-portal','55164a6a-d9a0-4d36-a289-abafa7fc1551',''),('liferay-portal','7f259547-fc8a-4689-a51f-5991b1a93562',''),('liferay-portal','93b2f0f7-c201-4196-b6bc-2829369f70e2','\0'),('liferay-portal','c6dc943c-f19c-4f65-9116-38d8773fb28d',''),('liferay-portal','d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6',''),('liferay-portal','eb4606ff-4ec0-4ffd-980a-a735c2465aeb','\0'),('liferay-portal','fca7dd02-3fb8-45c2-b72e-0f4e9daeeca5','\0'),('master','097f22bc-e851-40d0-a305-abc3958b9bc1','\0'),('master','2872c7d0-5ebf-41e7-a73a-febd30bac99c',''),('master','5f3033d7-efbf-4529-bb82-39f017fa0a0d',''),('master','6789e146-cf4b-4c40-a182-d3aecf1f5227',''),('master','8464704c-e331-40e0-be56-66582add8286',''),('master','a13d2169-a592-4de0-8dd7-6a0c100ccf4a','\0'),('master','aa7d808e-1c63-4fa3-80ec-e222079435c0','\0'),('master','c8b1ab10-6b0d-477a-8f5a-0bbc952a0053',''),('master','d97d8e90-e832-40ff-b928-4fc675a50743','\0');
/*!40000 ALTER TABLE `DEFAULT_CLIENT_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EVENT_ENTITY`
--

DROP TABLE IF EXISTS `EVENT_ENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EVENT_ENTITY` (
  `ID` varchar(36) NOT NULL,
  `CLIENT_ID` varchar(255) DEFAULT NULL,
  `DETAILS_JSON` varchar(2550) DEFAULT NULL,
  `ERROR` varchar(255) DEFAULT NULL,
  `IP_ADDRESS` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(255) DEFAULT NULL,
  `SESSION_ID` varchar(255) DEFAULT NULL,
  `EVENT_TIME` bigint(20) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `USER_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EVENT_ENTITY`
--

LOCK TABLES `EVENT_ENTITY` WRITE;
/*!40000 ALTER TABLE `EVENT_ENTITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `EVENT_ENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FEDERATED_IDENTITY`
--

DROP TABLE IF EXISTS `FEDERATED_IDENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FEDERATED_IDENTITY` (
  `IDENTITY_PROVIDER` varchar(255) NOT NULL,
  `REALM_ID` varchar(36) DEFAULT NULL,
  `FEDERATED_USER_ID` varchar(255) DEFAULT NULL,
  `FEDERATED_USERNAME` varchar(255) DEFAULT NULL,
  `TOKEN` text,
  `USER_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`IDENTITY_PROVIDER`,`USER_ID`),
  KEY `IDX_FEDIDENTITY_USER` (`USER_ID`),
  KEY `IDX_FEDIDENTITY_FEDUSER` (`FEDERATED_USER_ID`),
  CONSTRAINT `FK404288B92EF007A6` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FEDERATED_IDENTITY`
--

LOCK TABLES `FEDERATED_IDENTITY` WRITE;
/*!40000 ALTER TABLE `FEDERATED_IDENTITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `FEDERATED_IDENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FEDERATED_USER`
--

DROP TABLE IF EXISTS `FEDERATED_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FEDERATED_USER` (
  `ID` varchar(255) NOT NULL,
  `STORAGE_PROVIDER_ID` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FEDERATED_USER`
--

LOCK TABLES `FEDERATED_USER` WRITE;
/*!40000 ALTER TABLE `FEDERATED_USER` DISABLE KEYS */;
/*!40000 ALTER TABLE `FEDERATED_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_CREDENTIAL_ATTRIBUTE`
--

DROP TABLE IF EXISTS `FED_CREDENTIAL_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_CREDENTIAL_ATTRIBUTE` (
  `ID` varchar(36) NOT NULL,
  `CREDENTIAL_ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_FED_CRED_ATTR_CRED` (`CREDENTIAL_ID`),
  CONSTRAINT `FK_FED_CRED_ATTR` FOREIGN KEY (`CREDENTIAL_ID`) REFERENCES `FED_USER_CREDENTIAL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_CREDENTIAL_ATTRIBUTE`
--

LOCK TABLES `FED_CREDENTIAL_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `FED_CREDENTIAL_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_CREDENTIAL_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_ATTRIBUTE`
--

DROP TABLE IF EXISTS `FED_USER_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_ATTRIBUTE` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `USER_ID` varchar(255) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
  `VALUE` varchar(2024) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_FU_ATTRIBUTE` (`USER_ID`,`REALM_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_ATTRIBUTE`
--

LOCK TABLES `FED_USER_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `FED_USER_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_CONSENT`
--

DROP TABLE IF EXISTS `FED_USER_CONSENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_CONSENT` (
  `ID` varchar(36) NOT NULL,
  `CLIENT_ID` varchar(36) DEFAULT NULL,
  `USER_ID` varchar(255) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
  `CREATED_DATE` bigint(20) DEFAULT NULL,
  `LAST_UPDATED_DATE` bigint(20) DEFAULT NULL,
  `CLIENT_STORAGE_PROVIDER` varchar(36) DEFAULT NULL,
  `EXTERNAL_CLIENT_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_FU_CONSENT` (`USER_ID`,`CLIENT_ID`),
  KEY `IDX_FU_CONSENT_RU` (`REALM_ID`,`USER_ID`),
  KEY `IDX_FU_CNSNT_EXT` (`USER_ID`,`CLIENT_STORAGE_PROVIDER`,`EXTERNAL_CLIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_CONSENT`
--

LOCK TABLES `FED_USER_CONSENT` WRITE;
/*!40000 ALTER TABLE `FED_USER_CONSENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_CONSENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_CONSENT_CL_SCOPE`
--

DROP TABLE IF EXISTS `FED_USER_CONSENT_CL_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_CONSENT_CL_SCOPE` (
  `USER_CONSENT_ID` varchar(36) NOT NULL,
  `SCOPE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`USER_CONSENT_ID`,`SCOPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_CONSENT_CL_SCOPE`
--

LOCK TABLES `FED_USER_CONSENT_CL_SCOPE` WRITE;
/*!40000 ALTER TABLE `FED_USER_CONSENT_CL_SCOPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_CONSENT_CL_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_CREDENTIAL`
--

DROP TABLE IF EXISTS `FED_USER_CREDENTIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_CREDENTIAL` (
  `ID` varchar(36) NOT NULL,
  `DEVICE` varchar(255) DEFAULT NULL,
  `HASH_ITERATIONS` int(11) DEFAULT NULL,
  `SALT` tinyblob,
  `TYPE` varchar(255) DEFAULT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `CREATED_DATE` bigint(20) DEFAULT NULL,
  `COUNTER` int(11) DEFAULT '0',
  `DIGITS` int(11) DEFAULT '6',
  `PERIOD` int(11) DEFAULT '30',
  `ALGORITHM` varchar(36) DEFAULT 'HmacSHA1',
  `USER_ID` varchar(255) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_FU_CREDENTIAL` (`USER_ID`,`TYPE`),
  KEY `IDX_FU_CREDENTIAL_RU` (`REALM_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_CREDENTIAL`
--

LOCK TABLES `FED_USER_CREDENTIAL` WRITE;
/*!40000 ALTER TABLE `FED_USER_CREDENTIAL` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_CREDENTIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_GROUP_MEMBERSHIP`
--

DROP TABLE IF EXISTS `FED_USER_GROUP_MEMBERSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_GROUP_MEMBERSHIP` (
  `GROUP_ID` varchar(36) NOT NULL,
  `USER_ID` varchar(255) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`GROUP_ID`,`USER_ID`),
  KEY `IDX_FU_GROUP_MEMBERSHIP` (`USER_ID`,`GROUP_ID`),
  KEY `IDX_FU_GROUP_MEMBERSHIP_RU` (`REALM_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_GROUP_MEMBERSHIP`
--

LOCK TABLES `FED_USER_GROUP_MEMBERSHIP` WRITE;
/*!40000 ALTER TABLE `FED_USER_GROUP_MEMBERSHIP` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_GROUP_MEMBERSHIP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_REQUIRED_ACTION`
--

DROP TABLE IF EXISTS `FED_USER_REQUIRED_ACTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_REQUIRED_ACTION` (
  `REQUIRED_ACTION` varchar(255) NOT NULL DEFAULT ' ',
  `USER_ID` varchar(255) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`REQUIRED_ACTION`,`USER_ID`),
  KEY `IDX_FU_REQUIRED_ACTION` (`USER_ID`,`REQUIRED_ACTION`),
  KEY `IDX_FU_REQUIRED_ACTION_RU` (`REALM_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_REQUIRED_ACTION`
--

LOCK TABLES `FED_USER_REQUIRED_ACTION` WRITE;
/*!40000 ALTER TABLE `FED_USER_REQUIRED_ACTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_REQUIRED_ACTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_ROLE_MAPPING`
--

DROP TABLE IF EXISTS `FED_USER_ROLE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_ROLE_MAPPING` (
  `ROLE_ID` varchar(36) NOT NULL,
  `USER_ID` varchar(255) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`,`USER_ID`),
  KEY `IDX_FU_ROLE_MAPPING` (`USER_ID`,`ROLE_ID`),
  KEY `IDX_FU_ROLE_MAPPING_RU` (`REALM_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_ROLE_MAPPING`
--

LOCK TABLES `FED_USER_ROLE_MAPPING` WRITE;
/*!40000 ALTER TABLE `FED_USER_ROLE_MAPPING` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_ROLE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GROUP_ATTRIBUTE`
--

DROP TABLE IF EXISTS `GROUP_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUP_ATTRIBUTE` (
  `ID` varchar(36) NOT NULL DEFAULT 'sybase-needs-something-here',
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `GROUP_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_GROUP_ATTR_GROUP` (`GROUP_ID`),
  CONSTRAINT `FK_GROUP_ATTRIBUTE_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `KEYCLOAK_GROUP` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GROUP_ATTRIBUTE`
--

LOCK TABLES `GROUP_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `GROUP_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `GROUP_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GROUP_ROLE_MAPPING`
--

DROP TABLE IF EXISTS `GROUP_ROLE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUP_ROLE_MAPPING` (
  `ROLE_ID` varchar(36) NOT NULL,
  `GROUP_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`GROUP_ID`),
  KEY `IDX_GROUP_ROLE_MAPP_GROUP` (`GROUP_ID`),
  CONSTRAINT `FK_GROUP_ROLE_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `KEYCLOAK_GROUP` (`ID`),
  CONSTRAINT `FK_GROUP_ROLE_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `KEYCLOAK_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GROUP_ROLE_MAPPING`
--

LOCK TABLES `GROUP_ROLE_MAPPING` WRITE;
/*!40000 ALTER TABLE `GROUP_ROLE_MAPPING` DISABLE KEYS */;
INSERT INTO `GROUP_ROLE_MAPPING` VALUES ('97e649f9-df77-479e-8704-2b8b14af3b99','1f8597e8-3178-426f-859a-690289e3271d'),('e7aeba42-1e79-490a-af35-89aa5769d67b','e7cb8adb-3875-4d36-bf72-c430af1941cd');
/*!40000 ALTER TABLE `GROUP_ROLE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IDENTITY_PROVIDER`
--

DROP TABLE IF EXISTS `IDENTITY_PROVIDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IDENTITY_PROVIDER` (
  `INTERNAL_ID` varchar(36) NOT NULL,
  `ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `PROVIDER_ALIAS` varchar(255) DEFAULT NULL,
  `PROVIDER_ID` varchar(255) DEFAULT NULL,
  `STORE_TOKEN` bit(1) NOT NULL DEFAULT b'0',
  `AUTHENTICATE_BY_DEFAULT` bit(1) NOT NULL DEFAULT b'0',
  `REALM_ID` varchar(36) DEFAULT NULL,
  `ADD_TOKEN_ROLE` bit(1) NOT NULL DEFAULT b'1',
  `TRUST_EMAIL` bit(1) NOT NULL DEFAULT b'0',
  `FIRST_BROKER_LOGIN_FLOW_ID` varchar(36) DEFAULT NULL,
  `POST_BROKER_LOGIN_FLOW_ID` varchar(36) DEFAULT NULL,
  `PROVIDER_DISPLAY_NAME` varchar(255) DEFAULT NULL,
  `LINK_ONLY` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`INTERNAL_ID`),
  UNIQUE KEY `UK_2DAELWNIBJI49AVXSRTUF6XJ33` (`PROVIDER_ALIAS`,`REALM_ID`),
  KEY `IDX_IDENT_PROV_REALM` (`REALM_ID`),
  CONSTRAINT `FK2B4EBC52AE5C3B34` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IDENTITY_PROVIDER`
--

LOCK TABLES `IDENTITY_PROVIDER` WRITE;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER` DISABLE KEYS */;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IDENTITY_PROVIDER_CONFIG`
--

DROP TABLE IF EXISTS `IDENTITY_PROVIDER_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IDENTITY_PROVIDER_CONFIG` (
  `IDENTITY_PROVIDER_ID` varchar(36) NOT NULL,
  `VALUE` longtext,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`IDENTITY_PROVIDER_ID`,`NAME`),
  CONSTRAINT `FKDC4897CF864C4E43` FOREIGN KEY (`IDENTITY_PROVIDER_ID`) REFERENCES `IDENTITY_PROVIDER` (`INTERNAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IDENTITY_PROVIDER_CONFIG`
--

LOCK TABLES `IDENTITY_PROVIDER_CONFIG` WRITE;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IDENTITY_PROVIDER_MAPPER`
--

DROP TABLE IF EXISTS `IDENTITY_PROVIDER_MAPPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IDENTITY_PROVIDER_MAPPER` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `IDP_ALIAS` varchar(255) NOT NULL,
  `IDP_MAPPER_NAME` varchar(255) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_ID_PROV_MAPP_REALM` (`REALM_ID`),
  CONSTRAINT `FK_IDPM_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IDENTITY_PROVIDER_MAPPER`
--

LOCK TABLES `IDENTITY_PROVIDER_MAPPER` WRITE;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER_MAPPER` DISABLE KEYS */;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER_MAPPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IDP_MAPPER_CONFIG`
--

DROP TABLE IF EXISTS `IDP_MAPPER_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IDP_MAPPER_CONFIG` (
  `IDP_MAPPER_ID` varchar(36) NOT NULL,
  `VALUE` longtext,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`IDP_MAPPER_ID`,`NAME`),
  CONSTRAINT `FK_IDPMCONFIG` FOREIGN KEY (`IDP_MAPPER_ID`) REFERENCES `IDENTITY_PROVIDER_MAPPER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IDP_MAPPER_CONFIG`
--

LOCK TABLES `IDP_MAPPER_CONFIG` WRITE;
/*!40000 ALTER TABLE `IDP_MAPPER_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `IDP_MAPPER_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KEYCLOAK_GROUP`
--

DROP TABLE IF EXISTS `KEYCLOAK_GROUP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KEYCLOAK_GROUP` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PARENT_GROUP` varchar(36) DEFAULT NULL,
  `REALM_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SIBLING_NAMES` (`REALM_ID`,`PARENT_GROUP`,`NAME`),
  CONSTRAINT `FK_GROUP_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KEYCLOAK_GROUP`
--

LOCK TABLES `KEYCLOAK_GROUP` WRITE;
/*!40000 ALTER TABLE `KEYCLOAK_GROUP` DISABLE KEYS */;
INSERT INTO `KEYCLOAK_GROUP` VALUES ('1f8597e8-3178-426f-859a-690289e3271d','svn-admin',NULL,'liferay-portal'),('e7cb8adb-3875-4d36-bf72-c430af1941cd','svn-user',NULL,'liferay-portal');
/*!40000 ALTER TABLE `KEYCLOAK_GROUP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KEYCLOAK_ROLE`
--

DROP TABLE IF EXISTS `KEYCLOAK_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KEYCLOAK_ROLE` (
  `ID` varchar(36) NOT NULL,
  `CLIENT_REALM_CONSTRAINT` varchar(36) DEFAULT NULL,
  `CLIENT_ROLE` bit(1) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(255) DEFAULT NULL,
  `CLIENT` varchar(36) DEFAULT NULL,
  `REALM` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_J3RWUVD56ONTGSUHOGM184WW2-2` (`NAME`,`CLIENT_REALM_CONSTRAINT`),
  KEY `IDX_KEYCLOAK_ROLE_CLIENT` (`CLIENT`),
  KEY `IDX_KEYCLOAK_ROLE_REALM` (`REALM`),
  CONSTRAINT `FK_6VYQFE4CN4WLQ8R6KT5VDSJ5C` FOREIGN KEY (`REALM`) REFERENCES `REALM` (`ID`),
  CONSTRAINT `FK_KJHO5LE2C0RAL09FL8CM9WFW9` FOREIGN KEY (`CLIENT`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KEYCLOAK_ROLE`
--

LOCK TABLES `KEYCLOAK_ROLE` WRITE;
/*!40000 ALTER TABLE `KEYCLOAK_ROLE` DISABLE KEYS */;
INSERT INTO `KEYCLOAK_ROLE` VALUES ('059d4f19-1a45-40f1-865a-9bb8bff29baa','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_manage-events}','manage-events','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('06f1980a-cdbc-4fe3-a2aa-01ea57eae2ce','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_impersonation}','impersonation','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('07645452-fe83-4846-bb3a-9779265d532e','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_view-realm}','view-realm','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('0ab906cb-cc4f-4af4-9dd4-e676e6f0f9f3','f1b13f7c-a916-4b24-b314-0200496926ce','','${role_manage-account}','manage-account','liferay-portal','f1b13f7c-a916-4b24-b314-0200496926ce',NULL),('0c63cc77-5e0f-4adb-a34e-6d586a454e2c','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_manage-realm}','manage-realm','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('0ed0a574-da50-4d78-b46e-3577fefc7074','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_query-users}','query-users','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('17116a85-c7f7-48de-ac78-a83f88557568','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_impersonation}','impersonation','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('174374c7-a893-4867-98b4-22b624d927ee','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_view-authorization}','view-authorization','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('1b40735f-07e5-48a8-a1d5-877e1d266790','f4a55e24-e087-4f50-9673-67e5f1c2feb7','','${role_manage-account-links}','manage-account-links','master','f4a55e24-e087-4f50-9673-67e5f1c2feb7',NULL),('297f8ae3-b7f1-4a35-b0a7-1f103a282bc9','liferay-portal','\0','Liferay administrator','liferay-admin','liferay-portal',NULL,'liferay-portal'),('2ff71c9b-391d-4e49-8871-c0429f5d3ada','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_view-identity-providers}','view-identity-providers','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('375717d0-db43-469d-a2bc-2b514bf8251c','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_view-realm}','view-realm','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('3947b9dc-f2f9-4bc7-b936-c777fb54069a','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_query-realms}','query-realms','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('3d9c5e71-233a-4e29-9e60-f5d9129f9c57','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_view-users}','view-users','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('4ae9f0e8-1f16-44ef-90c9-558002aee88b','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_manage-users}','manage-users','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('4b008f85-b09a-4906-9aa0-2915474ab2f7','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_query-groups}','query-groups','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('4c400968-a526-419d-869c-59a1554a6a75','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_view-events}','view-events','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('51563dae-4e98-46a0-9654-957a7b0a404f','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_manage-identity-providers}','manage-identity-providers','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('545a4d10-404f-4b85-a8bc-34eb54172599','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_manage-users}','manage-users','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('55bfd3d7-0e1a-400f-baf8-0e4e8d234b89','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_manage-clients}','manage-clients','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('588bf995-f90d-4658-a41c-3036fc8684b2','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_manage-events}','manage-events','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('590f1f75-4c4e-496a-8f8e-ff820a9ac72b','cf1dcbc6-cef7-4c20-8cf3-270ea376eb70','','${role_read-token}','read-token','master','cf1dcbc6-cef7-4c20-8cf3-270ea376eb70',NULL),('5e57a33b-de71-4df9-b941-05bd9d599478','f1b13f7c-a916-4b24-b314-0200496926ce','','${role_manage-account-links}','manage-account-links','liferay-portal','f1b13f7c-a916-4b24-b314-0200496926ce',NULL),('5f6c2d9f-744b-49f1-ad3a-03c1abbbe1f5','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_query-realms}','query-realms','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('6ab65a6b-280d-4bb5-84e0-179cb3ceb0ec','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_query-clients}','query-clients','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('6ec8ccad-ff0e-40e6-a429-295f985ff0c9','07a7d9af-fe25-4479-8d74-9ddc0baeb35e','',NULL,'uma_protection','liferay-portal','07a7d9af-fe25-4479-8d74-9ddc0baeb35e',NULL),('6fc6d874-b718-468f-9d78-b1ab20ef0391','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_view-users}','view-users','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('73788df5-8b96-4a88-bd3a-17f99b717b46','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','',NULL,'uma_protection','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('789a51ae-8ae9-406b-a9bf-02fa6df70bac','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_create-client}','create-client','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('7c636332-3fd3-4f72-bc53-551b3b91d5bf','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_manage-realm}','manage-realm','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('7eaba2a8-49ab-4173-92ca-56b7e58afc6d','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_manage-users}','manage-users','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('7ed9fd1b-7561-443d-941c-91b4a28a5974','liferay-portal','\0','','view','liferay-portal',NULL,'liferay-portal'),('84bfe1ab-3fa2-493f-a52a-20cd26d5d6ad','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_realm-admin}','realm-admin','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('84c3ea10-f27d-44a0-b7c6-d20938ef151f','9b3c4d13-0a92-46a4-a07c-e412e33024d7','','${role_read-token}','read-token','liferay-portal','9b3c4d13-0a92-46a4-a07c-e412e33024d7',NULL),('87b0300c-cc13-4e0f-abfb-0eae5c0d99fc','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_view-authorization}','view-authorization','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('8a47e63d-596d-49c2-bd83-f1971e93fb18','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_create-client}','create-client','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('8c430ca8-55e7-43c6-8625-527ab3f3ec3f','f1b13f7c-a916-4b24-b314-0200496926ce','','${role_view-profile}','view-profile','liferay-portal','f1b13f7c-a916-4b24-b314-0200496926ce',NULL),('8dc48f0d-a02e-4360-a4a6-8f17c0b482cb','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_view-identity-providers}','view-identity-providers','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('907300b5-9e9a-423d-96fe-1c5604d00dc9','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_manage-authorization}','manage-authorization','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('927460c0-4081-49af-967f-d616d02a1337','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_query-realms}','query-realms','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('92d83d6a-97b3-4997-a52e-f15baf8132e3','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_query-clients}','query-clients','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('977804ea-5096-41be-b20a-35d5b4749896','master','\0','${role_uma_authorization}','uma_authorization','master',NULL,'master'),('97e649f9-df77-479e-8704-2b8b14af3b99','liferay-portal','\0','SVN administrator has read and write access','svn-admin','liferay-portal',NULL,'liferay-portal'),('992f5685-d4b5-44a8-8745-78ef23eee8b4','27a25fc2-29bd-4afd-a135-a5b2b8be12b8','',NULL,'uma_protection','liferay-portal','27a25fc2-29bd-4afd-a135-a5b2b8be12b8',NULL),('996812e8-82a2-4b44-9b38-0cad02f391ec','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_view-events}','view-events','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('a4115cd3-76f2-4094-a866-acb1bbec9674','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_view-authorization}','view-authorization','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('a8772b31-9ccd-42b4-aff3-b9a354828e95','67d6d3e3-38a7-4562-aac7-03832d713532','',NULL,'uma_protection','liferay-portal','67d6d3e3-38a7-4562-aac7-03832d713532',NULL),('a9351d2d-8ebe-439d-8ee7-dd52b6ed150f','master','\0','${role_create-realm}','create-realm','master',NULL,'master'),('b0766bbf-93ae-48ce-8e7f-d8286db7811a','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_query-groups}','query-groups','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('b3a357a8-691e-4855-bf2c-a2c5757f8a54','f4a55e24-e087-4f50-9673-67e5f1c2feb7','','${role_manage-account}','manage-account','master','f4a55e24-e087-4f50-9673-67e5f1c2feb7',NULL),('b6d58577-5e67-4e02-a166-80da656d8c57','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_manage-clients}','manage-clients','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('b72cc114-4029-475b-928b-1d3e4e5d7c83','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_manage-authorization}','manage-authorization','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('b8893ff5-9180-4ad8-8da2-cda8cb08b91c','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_manage-realm}','manage-realm','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('beda296e-ad85-43a6-9d77-d37193147efa','liferay-portal','\0','${role_uma_authorization}','uma_authorization','liferay-portal',NULL,'liferay-portal'),('bf5081f3-e26c-4040-9c69-d99ec64a8eaa','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_view-clients}','view-clients','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('bf76efcd-e2c7-4ba3-9f8a-507e787c26b2','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_manage-identity-providers}','manage-identity-providers','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('c524cfb5-69f9-4423-8eab-e057118149ef','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_query-groups}','query-groups','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('c9a15285-475b-4369-94f6-a6de3bcce560','master','\0','${role_offline-access}','offline_access','master',NULL,'master'),('d2d76201-4687-4118-8fc9-5275666a507f','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_query-users}','query-users','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('d76e15a1-3afe-435d-8720-69c533fd4b18','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_query-clients}','query-clients','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('d9e8aba2-54fe-4281-aeb7-ac904109fa7a','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_view-realm}','view-realm','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('dddf08d4-1fb3-4760-8a17-db88e47f20f2','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_view-events}','view-events','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('df21d69e-011e-41c6-b8f4-983b4b7b1c01','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_query-users}','query-users','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('e19edb48-736a-4808-b9c1-daa24ca1a27f','d377a198-34a9-46a2-9564-f468fb84e9b8','',NULL,'uma_protection','liferay-portal','d377a198-34a9-46a2-9564-f468fb84e9b8',NULL),('e317b333-aa1c-4f2e-a3df-76a4fde61902','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_view-identity-providers}','view-identity-providers','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('e31d20dd-bc25-4acb-99a1-84ab43719f56','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_manage-clients}','manage-clients','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('e5636186-d221-43bf-9267-f8a0f67095b0','master','\0','${role_admin}','admin','master',NULL,'master'),('e7aeba42-1e79-490a-af35-89aa5769d67b','liferay-portal','\0','SVN User has read access','svn-user','liferay-portal',NULL,'liferay-portal'),('e8e02874-cf61-4933-9469-5559c422a6c5','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_view-clients}','view-clients','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('ea1d0b81-cdda-4b96-85fe-2c81438537a2','f4a55e24-e087-4f50-9673-67e5f1c2feb7','','${role_view-profile}','view-profile','master','f4a55e24-e087-4f50-9673-67e5f1c2feb7',NULL),('eb738200-9d7e-4235-86b8-f5351679f7df','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_manage-events}','manage-events','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('ec622a84-2b17-4260-ab86-bac6a872ab8a','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_manage-identity-providers}','manage-identity-providers','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('ef054e30-c8cd-4cd8-9b00-04c1992f276e','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26','','${role_create-client}','create-client','master','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',NULL),('efd229c1-fa0f-43ed-83ed-457b20f4fb46','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_view-users}','view-users','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('f4e93900-0b7d-4fa3-b2ca-523715fff9d1','liferay-portal','\0','${role_offline-access}','offline_access','liferay-portal',NULL,'liferay-portal'),('fa8ba997-495f-4a68-a09d-0a5db04079a4','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_manage-authorization}','manage-authorization','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('fb9b1194-4460-4d3d-95d1-66c4dc3adbee','b39c0c40-7753-42c6-9356-694c4e7182dc','','${role_impersonation}','impersonation','master','b39c0c40-7753-42c6-9356-694c4e7182dc',NULL),('fcc87b08-b814-48c0-baee-97f9b81de4e4','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','${role_view-clients}','view-clients','liferay-portal','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL);
/*!40000 ALTER TABLE `KEYCLOAK_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MAILING_ENTITY`
--

DROP TABLE IF EXISTS `MAILING_ENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MAILING_ENTITY` (
  `ID` varchar(36) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `LANGUAGES` varchar(36) NOT NULL DEFAULT 'en' COMMENT 'array of available publications ‚Äúen‚Äù;‚Äùnl‚Äù;‚Ä¶',
  `FREQUENCY` tinyint(4) NOT NULL DEFAULT '4' COMMENT '0,1,2,3 (weekly, monthly, annual, other)',
  `DELIVERY` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0,1 or 2 (email/post/both)',
  `CREATED_TIMESTAMP` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_MAILING_NAME` (`REALM_ID`,`NAME`),
  CONSTRAINT `FK_MAILING_REALM_ENTITY` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MAILING_ENTITY`
--

LOCK TABLES `MAILING_ENTITY` WRITE;
/*!40000 ALTER TABLE `MAILING_ENTITY` DISABLE KEYS */;
INSERT INTO `MAILING_ENTITY` VALUES ('c9970d56-1417-4c8f-9f28-68870579bf90','liferay-portal','DSD NL','Nederlandse Software dagen nieuwsbrief','nl',2,0,1621321267271),('dc7abad2-deba-4394-a823-567f8482dd81','liferay-portal','Deltares Academy','Newsletter with all upcoming course information','en',2,0,1621345191975);
/*!40000 ALTER TABLE `MAILING_ENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MIGRATION_MODEL`
--

DROP TABLE IF EXISTS `MIGRATION_MODEL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MIGRATION_MODEL` (
  `ID` varchar(36) NOT NULL,
  `VERSION` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MIGRATION_MODEL`
--

LOCK TABLES `MIGRATION_MODEL` WRITE;
/*!40000 ALTER TABLE `MIGRATION_MODEL` DISABLE KEYS */;
INSERT INTO `MIGRATION_MODEL` VALUES ('SINGLETON','4.6.0');
/*!40000 ALTER TABLE `MIGRATION_MODEL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OFFLINE_CLIENT_SESSION`
--

DROP TABLE IF EXISTS `OFFLINE_CLIENT_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OFFLINE_CLIENT_SESSION` (
  `USER_SESSION_ID` varchar(36) NOT NULL,
  `CLIENT_ID` varchar(36) NOT NULL,
  `OFFLINE_FLAG` varchar(4) NOT NULL,
  `TIMESTAMP` int(11) DEFAULT NULL,
  `DATA` longtext,
  `CLIENT_STORAGE_PROVIDER` varchar(36) NOT NULL DEFAULT 'local',
  `EXTERNAL_CLIENT_ID` varchar(255) NOT NULL DEFAULT 'local',
  PRIMARY KEY (`USER_SESSION_ID`,`CLIENT_ID`,`CLIENT_STORAGE_PROVIDER`,`EXTERNAL_CLIENT_ID`,`OFFLINE_FLAG`),
  KEY `IDX_US_SESS_ID_ON_CL_SESS` (`USER_SESSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OFFLINE_CLIENT_SESSION`
--

LOCK TABLES `OFFLINE_CLIENT_SESSION` WRITE;
/*!40000 ALTER TABLE `OFFLINE_CLIENT_SESSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `OFFLINE_CLIENT_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OFFLINE_USER_SESSION`
--

DROP TABLE IF EXISTS `OFFLINE_USER_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OFFLINE_USER_SESSION` (
  `USER_SESSION_ID` varchar(36) NOT NULL,
  `USER_ID` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `CREATED_ON` int(11) NOT NULL,
  `OFFLINE_FLAG` varchar(4) NOT NULL,
  `DATA` longtext,
  `LAST_SESSION_REFRESH` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`USER_SESSION_ID`,`OFFLINE_FLAG`),
  KEY `IDX_OFFLINE_USS_CREATEDON` (`CREATED_ON`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OFFLINE_USER_SESSION`
--

LOCK TABLES `OFFLINE_USER_SESSION` WRITE;
/*!40000 ALTER TABLE `OFFLINE_USER_SESSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `OFFLINE_USER_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POLICY_CONFIG`
--

DROP TABLE IF EXISTS `POLICY_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POLICY_CONFIG` (
  `POLICY_ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `VALUE` longtext,
  PRIMARY KEY (`POLICY_ID`,`NAME`),
  CONSTRAINT `FKDC34197CF864C4E43` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POLICY_CONFIG`
--

LOCK TABLES `POLICY_CONFIG` WRITE;
/*!40000 ALTER TABLE `POLICY_CONFIG` DISABLE KEYS */;
INSERT INTO `POLICY_CONFIG` VALUES ('1d1be5b2-368b-4f73-91ef-f5fff682aa73','defaultResourceType','urn:api-user:resources:default'),('464a4f94-3ebe-4b25-a773-43004e2e73d6','defaultResourceType','urn:api-admin:resources:default'),('7e75f663-76ca-42cc-92ce-732e3caf409a','code','// by default, grants any permission associated with this policy\n$evaluation.grant();\n'),('8119b5a3-d21e-4c4b-bac8-134c495e0a77','code','// by default, grants any permission associated with this policy\n$evaluation.grant();\n'),('c57f7c57-d56d-44d5-bbb2-ebf557f24688','defaultResourceType','urn:oss-account:resources:default'),('ccef7efc-fb1f-4a55-8429-bac1e0261314','code','// by default, grants any permission associated with this policy\n$evaluation.grant();\n'),('d66a85ba-62e8-49ed-b0cc-29ab865a86e5','defaultResourceType','urn:api-viewer:resources:default'),('eca0e811-43db-41f8-a3b9-5d37f235e1b3','code','// by default, grants any permission associated with this policy\n$evaluation.grant();\n');
/*!40000 ALTER TABLE `POLICY_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROTOCOL_MAPPER`
--

DROP TABLE IF EXISTS `PROTOCOL_MAPPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROTOCOL_MAPPER` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `PROTOCOL` varchar(255) NOT NULL,
  `PROTOCOL_MAPPER_NAME` varchar(255) NOT NULL,
  `CLIENT_ID` varchar(36) DEFAULT NULL,
  `CLIENT_SCOPE_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_PROTOCOL_MAPPER_CLIENT` (`CLIENT_ID`),
  KEY `IDX_CLSCOPE_PROTMAP` (`CLIENT_SCOPE_ID`),
  CONSTRAINT `FK_CLI_SCOPE_MAPPER` FOREIGN KEY (`CLIENT_SCOPE_ID`) REFERENCES `CLIENT_SCOPE` (`ID`),
  CONSTRAINT `FK_PCM_REALM` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROTOCOL_MAPPER`
--

LOCK TABLES `PROTOCOL_MAPPER` WRITE;
/*!40000 ALTER TABLE `PROTOCOL_MAPPER` DISABLE KEYS */;
INSERT INTO `PROTOCOL_MAPPER` VALUES ('01a7b21a-9e19-4450-8aff-1ecc2ba6cc0a','phone number','openid-connect','oidc-usermodel-attribute-mapper',NULL,'93b2f0f7-c201-4196-b6bc-2829369f70e2'),('045d423c-8990-4044-bae1-1f3661c6d668','Client ID','openid-connect','oidc-usersessionmodel-note-mapper','27a25fc2-29bd-4afd-a135-a5b2b8be12b8',NULL),('06f791b2-84e5-4fac-95ca-d7c534cce961','Client Host','openid-connect','oidc-usersessionmodel-note-mapper','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('07d8e212-3f95-4184-8bdf-de7bd9bc03bf','locale','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('0c022d5c-b3f7-43e4-b2cf-d83604dcd0e8','profile','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('0f5034f0-64f5-4cc5-80f8-e4e240a78865','family name','openid-connect','oidc-usermodel-property-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('13ca7cdb-a2c2-4509-bb65-7bea1cf9dde0','Client ID','openid-connect','oidc-usersessionmodel-note-mapper','d377a198-34a9-46a2-9564-f468fb84e9b8',NULL),('1965c356-0e3d-40b6-9840-44978f3ba9e0','username','saml','saml-user-property-mapper','b8a62fae-771b-402a-8515-1b13b025da91',NULL),('1bb13b5e-f0da-438c-8d41-6da71fcfd8d9','allowed web origins','openid-connect','oidc-allowed-origins-mapper',NULL,'5f3033d7-efbf-4529-bb82-39f017fa0a0d'),('1c80b47d-8ed3-4563-a449-c5da5995d1e2','phone number verified','openid-connect','oidc-usermodel-attribute-mapper',NULL,'93b2f0f7-c201-4196-b6bc-2829369f70e2'),('1e4e9953-5d7e-4f07-af67-94b748d5ce01','email','saml','saml-user-property-mapper','b8a62fae-771b-402a-8515-1b13b025da91',NULL),('21c661e2-280d-4c76-a2f3-96db23b298eb','email verified','openid-connect','oidc-usermodel-property-mapper',NULL,'8464704c-e331-40e0-be56-66582add8286'),('2424c783-fd04-43e6-b064-4534f889f8f8','lastName','saml','saml-user-property-mapper','b8a62fae-771b-402a-8515-1b13b025da91',NULL),('2458f314-f212-4462-abe1-5eaf2ac3d8b0','picture','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('2a75bf7d-c172-49e3-9f3d-2df3eea7ccd1','Organization name','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('2b14df9c-8490-43b9-8f96-7dbdeba820bd','email verified','openid-connect','oidc-usermodel-property-mapper',NULL,'7f259547-fc8a-4689-a51f-5991b1a93562'),('2b6f2c57-8a29-433e-95e2-425c150c9bc4','website','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('2fda9947-86b0-40af-98c2-32de93d2ae58','phone number','openid-connect','oidc-usermodel-attribute-mapper',NULL,'097f22bc-e851-40d0-a305-abc3958b9bc1'),('32032ddd-2c2d-4eae-b2d8-fbec8ec27287','birthdate','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('350fd88c-6b9e-4dd7-ae34-bc0a15e2a43c','locale','openid-connect','oidc-usermodel-attribute-mapper','56c3ecf9-d922-4563-b0b7-05c1b20f8f31',NULL),('3e79c98a-dce6-4703-8e3e-e29b67eed953','gender','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('44e8d22d-0a71-410f-84b4-eb6e0cffb977','Client ID','openid-connect','oidc-usersessionmodel-note-mapper','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('4d1c448a-91b0-4aa7-9113-9e3da59cdc2e','realm roles','openid-connect','oidc-usermodel-realm-role-mapper',NULL,'c8b1ab10-6b0d-477a-8f5a-0bbc952a0053'),('4e35419a-9168-4aa2-8b5e-5d86c9f90717','role list','saml','saml-role-list-mapper',NULL,'2872c7d0-5ebf-41e7-a73a-febd30bac99c'),('53abc792-a449-4a0d-9387-82aa17ccd3c9','Client Host','openid-connect','oidc-usersessionmodel-note-mapper','07a7d9af-fe25-4479-8d74-9ddc0baeb35e',NULL),('561782a9-c7f8-4662-85d8-724c11fc7da2','zoneinfo','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('5b90f797-ad7c-4106-9e38-3a688dc515b0','username','openid-connect','oidc-usermodel-property-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('64ca42db-acd8-48d6-865a-1add54bc49bf','full name','openid-connect','oidc-full-name-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('67ca5089-9320-4388-b3c0-0c7c709ff966','full name','openid-connect','oidc-full-name-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('6826e8b9-8f2c-4ff0-b26f-98f4706e4c2f','groups','openid-connect','oidc-usermodel-realm-role-mapper',NULL,'d97d8e90-e832-40ff-b928-4fc675a50743'),('694d7915-d713-4fba-96ec-62df97755f23','Client Host','openid-connect','oidc-usersessionmodel-note-mapper','d377a198-34a9-46a2-9564-f468fb84e9b8',NULL),('6c547391-05e3-4819-91f3-24ecffd26488','given name','openid-connect','oidc-usermodel-property-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('6cd91773-7ee3-4e9d-be6f-c840f0127bb1','Client Host','openid-connect','oidc-usersessionmodel-note-mapper','67d6d3e3-38a7-4562-aac7-03832d713532',NULL),('6daacd93-b901-4d9a-8839-9e5b6a011295','website','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('6f1d227b-54df-4888-a3ba-81884c41224d','role list','saml','saml-role-list-mapper',NULL,'55164a6a-d9a0-4d36-a289-abafa7fc1551'),('6f9e8935-da3a-45bf-ba2e-242beff49bc3','username','openid-connect','oidc-usermodel-property-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('76b2d4d8-9ca2-4645-84ae-92446f2394a0','Client IP Address','openid-connect','oidc-usersessionmodel-note-mapper','07a7d9af-fe25-4479-8d74-9ddc0baeb35e',NULL),('76f41b6d-6704-4f6b-87af-fa2cdada20ad','profile','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('7960d8e7-8a2f-4e22-9a21-751ffa26e094','updated at','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('7a774a42-c62b-4a18-b0a9-8331fab8f323','username','saml','saml-user-property-mapper','dbe87c24-ee61-418a-9058-85660d151e0e',NULL),('82fb9f97-ec06-4ba9-bf9a-1ed1200e1782','client roles','openid-connect','oidc-usermodel-client-role-mapper',NULL,'c8b1ab10-6b0d-477a-8f5a-0bbc952a0053'),('8521f557-223a-43f7-9472-b7654534d3e7','client roles','openid-connect','oidc-usermodel-client-role-mapper',NULL,'d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6'),('86cbacaa-05c6-40ad-86c4-1077c77e9f93','upn','openid-connect','oidc-usermodel-property-mapper',NULL,'d97d8e90-e832-40ff-b928-4fc675a50743'),('86e7260c-3995-4da4-aaba-355465da2a1e','allowed web origins','openid-connect','oidc-allowed-origins-mapper',NULL,'c6dc943c-f19c-4f65-9116-38d8773fb28d'),('88e952a7-c3fe-42ff-86e8-5b684cdd7a96','picture','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('8b884a22-fe4f-46b5-b733-6e04bb696f80','gender','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('8ce14cac-aca7-4ee2-9ff2-37610bc58828','Client Host','openid-connect','oidc-usersessionmodel-note-mapper','27a25fc2-29bd-4afd-a135-a5b2b8be12b8',NULL),('8f8e23e7-f017-4238-a281-b2858573b542','audience resolve','openid-connect','oidc-audience-resolve-mapper',NULL,'c8b1ab10-6b0d-477a-8f5a-0bbc952a0053'),('96738fc1-152c-415e-9c71-03959adab303','lastName','saml','saml-user-property-mapper','dbe87c24-ee61-418a-9058-85660d151e0e',NULL),('9dc000da-80d7-418b-a5ea-48ffbbf119d7','Client IP Address','openid-connect','oidc-usersessionmodel-note-mapper','27a25fc2-29bd-4afd-a135-a5b2b8be12b8',NULL),('9e85545f-f6ca-4923-9322-2dcbe13c323f','updated at','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('a8032276-8a51-4223-8843-e90b49dbb604','Client IP Address','openid-connect','oidc-usersessionmodel-note-mapper','7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('af711ee2-37f5-4080-aae7-d42dbe6c7f4e','firstName','saml','saml-user-property-mapper','dbe87c24-ee61-418a-9058-85660d151e0e',NULL),('b181f52a-406f-43a0-a77f-16a34b2b6fc8','realm roles','openid-connect','oidc-usermodel-realm-role-mapper',NULL,'d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6'),('b5b04c93-ca4b-4b95-b0a0-280edf5cdc99','family name','openid-connect','oidc-usermodel-property-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('c1e8460d-84d3-4736-a851-cdbf2f83acc6','email','openid-connect','oidc-usermodel-property-mapper',NULL,'7f259547-fc8a-4689-a51f-5991b1a93562'),('c569ab91-32b5-457b-a559-e86738517797','address','openid-connect','oidc-address-mapper',NULL,'eb4606ff-4ec0-4ffd-980a-a735c2465aeb'),('c577a27d-ea2b-4799-b984-ef0fa73339fb','nickname','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('c730e294-8c33-465b-a1a9-a42a0a1a79db','Client IP Address','openid-connect','oidc-usersessionmodel-note-mapper','f1b13f7c-a916-4b24-b314-0200496926ce',NULL),('c77be71a-b281-4532-8d89-f663caa47d8a','Client ID','openid-connect','oidc-usersessionmodel-note-mapper','07a7d9af-fe25-4479-8d74-9ddc0baeb35e',NULL),('cc24a115-a033-4a43-8669-efc955721694','audience resolve','openid-connect','oidc-audience-resolve-mapper',NULL,'d3813bc9-fcb8-4d3e-bd5a-aefd3d4b4ca6'),('cc947cdb-8e1e-4b67-a17f-079f7277f21c','Client ID','openid-connect','oidc-usersessionmodel-note-mapper','f1b13f7c-a916-4b24-b314-0200496926ce',NULL),('cd68315c-0d20-4cfe-9a52-a7f019501b98','Client ID','openid-connect','oidc-usersessionmodel-note-mapper','67d6d3e3-38a7-4562-aac7-03832d713532',NULL),('d5a891d5-4a0c-4150-89a2-ac5f78e2b0d4','phone number verified','openid-connect','oidc-usermodel-attribute-mapper',NULL,'097f22bc-e851-40d0-a305-abc3958b9bc1'),('dad203c1-eacb-4d73-be8a-907eafca608b','nickname','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('dc3e541e-5793-4d6e-b279-f679321c69a0','firstName','saml','saml-user-property-mapper','b8a62fae-771b-402a-8515-1b13b025da91',NULL),('dc4c57c6-5469-4750-bcbf-25e1b8fe62dc','Client IP Address','openid-connect','oidc-usersessionmodel-note-mapper','d377a198-34a9-46a2-9564-f468fb84e9b8',NULL),('ddd59dfb-c4e3-4a87-967d-c0528889e35e','middle name','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('e1916664-741a-45bd-ad97-5cbe0422ce0d','Client Host','openid-connect','oidc-usersessionmodel-note-mapper','f1b13f7c-a916-4b24-b314-0200496926ce',NULL),('e29251ff-161d-4de7-9e88-50862b1093db','locale','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('e38548f4-d688-4266-bf51-a38d58adbab6','middle name','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('e39f0f4d-ff37-417e-8ab6-aaa4bfd1df11','locale','openid-connect','oidc-usermodel-attribute-mapper','364c3e71-9195-4162-9d08-e048421fdd0f',NULL),('e4448a31-6363-4930-86ed-8e65b68213cc','given name','openid-connect','oidc-usermodel-property-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('e6f2582e-fe36-405f-9843-a3a9e818beee','email','openid-connect','oidc-usermodel-property-mapper',NULL,'8464704c-e331-40e0-be56-66582add8286'),('eaf0c207-03e4-4f06-8814-2a4e0f054bcf','Client IP Address','openid-connect','oidc-usersessionmodel-note-mapper','67d6d3e3-38a7-4562-aac7-03832d713532',NULL),('eb79ff4c-d4d3-4327-b15c-a26f01eb7dfc','zoneinfo','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3522f3d8-9fad-4c62-bfc8-000b9091361a'),('ee48d12b-8742-4494-aef0-9166c07f1cd7','birthdate','openid-connect','oidc-usermodel-attribute-mapper',NULL,'6789e146-cf4b-4c40-a182-d3aecf1f5227'),('f2eac318-5c5d-4730-aef1-0545946cd421','address','openid-connect','oidc-address-mapper',NULL,'aa7d808e-1c63-4fa3-80ec-e222079435c0'),('f4a18126-e246-4ed1-9438-5b341c01470b','email','saml','saml-user-property-mapper','dbe87c24-ee61-418a-9058-85660d151e0e',NULL);
/*!40000 ALTER TABLE `PROTOCOL_MAPPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROTOCOL_MAPPER_CONFIG`
--

DROP TABLE IF EXISTS `PROTOCOL_MAPPER_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROTOCOL_MAPPER_CONFIG` (
  `PROTOCOL_MAPPER_ID` varchar(36) NOT NULL,
  `VALUE` longtext,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`PROTOCOL_MAPPER_ID`,`NAME`),
  CONSTRAINT `FK_PMCONFIG` FOREIGN KEY (`PROTOCOL_MAPPER_ID`) REFERENCES `PROTOCOL_MAPPER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROTOCOL_MAPPER_CONFIG`
--

LOCK TABLES `PROTOCOL_MAPPER_CONFIG` WRITE;
/*!40000 ALTER TABLE `PROTOCOL_MAPPER_CONFIG` DISABLE KEYS */;
INSERT INTO `PROTOCOL_MAPPER_CONFIG` VALUES ('01a7b21a-9e19-4450-8aff-1ecc2ba6cc0a','true','access.token.claim'),('01a7b21a-9e19-4450-8aff-1ecc2ba6cc0a','phone_number','claim.name'),('01a7b21a-9e19-4450-8aff-1ecc2ba6cc0a','true','id.token.claim'),('01a7b21a-9e19-4450-8aff-1ecc2ba6cc0a','String','jsonType.label'),('01a7b21a-9e19-4450-8aff-1ecc2ba6cc0a','phoneNumber','user.attribute'),('01a7b21a-9e19-4450-8aff-1ecc2ba6cc0a','true','userinfo.token.claim'),('045d423c-8990-4044-bae1-1f3661c6d668','true','access.token.claim'),('045d423c-8990-4044-bae1-1f3661c6d668','clientId','claim.name'),('045d423c-8990-4044-bae1-1f3661c6d668','true','id.token.claim'),('045d423c-8990-4044-bae1-1f3661c6d668','String','jsonType.label'),('045d423c-8990-4044-bae1-1f3661c6d668','clientId','user.session.note'),('045d423c-8990-4044-bae1-1f3661c6d668','true','userinfo.token.claim'),('06f791b2-84e5-4fac-95ca-d7c534cce961','true','access.token.claim'),('06f791b2-84e5-4fac-95ca-d7c534cce961','clientHost','claim.name'),('06f791b2-84e5-4fac-95ca-d7c534cce961','true','id.token.claim'),('06f791b2-84e5-4fac-95ca-d7c534cce961','String','jsonType.label'),('06f791b2-84e5-4fac-95ca-d7c534cce961','clientHost','user.session.note'),('06f791b2-84e5-4fac-95ca-d7c534cce961','true','userinfo.token.claim'),('07d8e212-3f95-4184-8bdf-de7bd9bc03bf','true','access.token.claim'),('07d8e212-3f95-4184-8bdf-de7bd9bc03bf','locale','claim.name'),('07d8e212-3f95-4184-8bdf-de7bd9bc03bf','true','id.token.claim'),('07d8e212-3f95-4184-8bdf-de7bd9bc03bf','String','jsonType.label'),('07d8e212-3f95-4184-8bdf-de7bd9bc03bf','locale','user.attribute'),('07d8e212-3f95-4184-8bdf-de7bd9bc03bf','true','userinfo.token.claim'),('0c022d5c-b3f7-43e4-b2cf-d83604dcd0e8','true','access.token.claim'),('0c022d5c-b3f7-43e4-b2cf-d83604dcd0e8','profile','claim.name'),('0c022d5c-b3f7-43e4-b2cf-d83604dcd0e8','true','id.token.claim'),('0c022d5c-b3f7-43e4-b2cf-d83604dcd0e8','String','jsonType.label'),('0c022d5c-b3f7-43e4-b2cf-d83604dcd0e8','profile','user.attribute'),('0c022d5c-b3f7-43e4-b2cf-d83604dcd0e8','true','userinfo.token.claim'),('0f5034f0-64f5-4cc5-80f8-e4e240a78865','true','access.token.claim'),('0f5034f0-64f5-4cc5-80f8-e4e240a78865','family_name','claim.name'),('0f5034f0-64f5-4cc5-80f8-e4e240a78865','true','id.token.claim'),('0f5034f0-64f5-4cc5-80f8-e4e240a78865','String','jsonType.label'),('0f5034f0-64f5-4cc5-80f8-e4e240a78865','lastName','user.attribute'),('0f5034f0-64f5-4cc5-80f8-e4e240a78865','true','userinfo.token.claim'),('13ca7cdb-a2c2-4509-bb65-7bea1cf9dde0','true','access.token.claim'),('13ca7cdb-a2c2-4509-bb65-7bea1cf9dde0','clientId','claim.name'),('13ca7cdb-a2c2-4509-bb65-7bea1cf9dde0','true','id.token.claim'),('13ca7cdb-a2c2-4509-bb65-7bea1cf9dde0','String','jsonType.label'),('13ca7cdb-a2c2-4509-bb65-7bea1cf9dde0','clientId','user.session.note'),('13ca7cdb-a2c2-4509-bb65-7bea1cf9dde0','true','userinfo.token.claim'),('1965c356-0e3d-40b6-9840-44978f3ba9e0','screenName','attribute.name'),('1965c356-0e3d-40b6-9840-44978f3ba9e0','username','user.attribute'),('1c80b47d-8ed3-4563-a449-c5da5995d1e2','true','access.token.claim'),('1c80b47d-8ed3-4563-a449-c5da5995d1e2','phone_number_verified','claim.name'),('1c80b47d-8ed3-4563-a449-c5da5995d1e2','true','id.token.claim'),('1c80b47d-8ed3-4563-a449-c5da5995d1e2','boolean','jsonType.label'),('1c80b47d-8ed3-4563-a449-c5da5995d1e2','phoneNumberVerified','user.attribute'),('1c80b47d-8ed3-4563-a449-c5da5995d1e2','true','userinfo.token.claim'),('1e4e9953-5d7e-4f07-af67-94b748d5ce01','emailAddress','attribute.name'),('1e4e9953-5d7e-4f07-af67-94b748d5ce01','email','user.attribute'),('21c661e2-280d-4c76-a2f3-96db23b298eb','true','access.token.claim'),('21c661e2-280d-4c76-a2f3-96db23b298eb','email_verified','claim.name'),('21c661e2-280d-4c76-a2f3-96db23b298eb','true','id.token.claim'),('21c661e2-280d-4c76-a2f3-96db23b298eb','boolean','jsonType.label'),('21c661e2-280d-4c76-a2f3-96db23b298eb','emailVerified','user.attribute'),('21c661e2-280d-4c76-a2f3-96db23b298eb','true','userinfo.token.claim'),('2424c783-fd04-43e6-b064-4534f889f8f8','lastName','attribute.name'),('2424c783-fd04-43e6-b064-4534f889f8f8','lastName','user.attribute'),('2458f314-f212-4462-abe1-5eaf2ac3d8b0','true','access.token.claim'),('2458f314-f212-4462-abe1-5eaf2ac3d8b0','picture','claim.name'),('2458f314-f212-4462-abe1-5eaf2ac3d8b0','true','id.token.claim'),('2458f314-f212-4462-abe1-5eaf2ac3d8b0','String','jsonType.label'),('2458f314-f212-4462-abe1-5eaf2ac3d8b0','picture','user.attribute'),('2458f314-f212-4462-abe1-5eaf2ac3d8b0','true','userinfo.token.claim'),('2a75bf7d-c172-49e3-9f3d-2df3eea7ccd1','true','access.token.claim'),('2a75bf7d-c172-49e3-9f3d-2df3eea7ccd1','org_name','claim.name'),('2a75bf7d-c172-49e3-9f3d-2df3eea7ccd1','true','id.token.claim'),('2a75bf7d-c172-49e3-9f3d-2df3eea7ccd1','String','jsonType.label'),('2a75bf7d-c172-49e3-9f3d-2df3eea7ccd1','org_name','user.attribute'),('2a75bf7d-c172-49e3-9f3d-2df3eea7ccd1','true','userinfo.token.claim'),('2b14df9c-8490-43b9-8f96-7dbdeba820bd','true','access.token.claim'),('2b14df9c-8490-43b9-8f96-7dbdeba820bd','email_verified','claim.name'),('2b14df9c-8490-43b9-8f96-7dbdeba820bd','true','id.token.claim'),('2b14df9c-8490-43b9-8f96-7dbdeba820bd','boolean','jsonType.label'),('2b14df9c-8490-43b9-8f96-7dbdeba820bd','emailVerified','user.attribute'),('2b14df9c-8490-43b9-8f96-7dbdeba820bd','true','userinfo.token.claim'),('2b6f2c57-8a29-433e-95e2-425c150c9bc4','true','access.token.claim'),('2b6f2c57-8a29-433e-95e2-425c150c9bc4','website','claim.name'),('2b6f2c57-8a29-433e-95e2-425c150c9bc4','true','id.token.claim'),('2b6f2c57-8a29-433e-95e2-425c150c9bc4','String','jsonType.label'),('2b6f2c57-8a29-433e-95e2-425c150c9bc4','website','user.attribute'),('2b6f2c57-8a29-433e-95e2-425c150c9bc4','true','userinfo.token.claim'),('2fda9947-86b0-40af-98c2-32de93d2ae58','true','access.token.claim'),('2fda9947-86b0-40af-98c2-32de93d2ae58','phone_number','claim.name'),('2fda9947-86b0-40af-98c2-32de93d2ae58','true','id.token.claim'),('2fda9947-86b0-40af-98c2-32de93d2ae58','String','jsonType.label'),('2fda9947-86b0-40af-98c2-32de93d2ae58','phoneNumber','user.attribute'),('2fda9947-86b0-40af-98c2-32de93d2ae58','true','userinfo.token.claim'),('32032ddd-2c2d-4eae-b2d8-fbec8ec27287','true','access.token.claim'),('32032ddd-2c2d-4eae-b2d8-fbec8ec27287','birthdate','claim.name'),('32032ddd-2c2d-4eae-b2d8-fbec8ec27287','true','id.token.claim'),('32032ddd-2c2d-4eae-b2d8-fbec8ec27287','String','jsonType.label'),('32032ddd-2c2d-4eae-b2d8-fbec8ec27287','birthdate','user.attribute'),('32032ddd-2c2d-4eae-b2d8-fbec8ec27287','true','userinfo.token.claim'),('350fd88c-6b9e-4dd7-ae34-bc0a15e2a43c','true','access.token.claim'),('350fd88c-6b9e-4dd7-ae34-bc0a15e2a43c','locale','claim.name'),('350fd88c-6b9e-4dd7-ae34-bc0a15e2a43c','true','id.token.claim'),('350fd88c-6b9e-4dd7-ae34-bc0a15e2a43c','String','jsonType.label'),('350fd88c-6b9e-4dd7-ae34-bc0a15e2a43c','locale','user.attribute'),('350fd88c-6b9e-4dd7-ae34-bc0a15e2a43c','true','userinfo.token.claim'),('3e79c98a-dce6-4703-8e3e-e29b67eed953','true','access.token.claim'),('3e79c98a-dce6-4703-8e3e-e29b67eed953','gender','claim.name'),('3e79c98a-dce6-4703-8e3e-e29b67eed953','true','id.token.claim'),('3e79c98a-dce6-4703-8e3e-e29b67eed953','String','jsonType.label'),('3e79c98a-dce6-4703-8e3e-e29b67eed953','gender','user.attribute'),('3e79c98a-dce6-4703-8e3e-e29b67eed953','true','userinfo.token.claim'),('44e8d22d-0a71-410f-84b4-eb6e0cffb977','true','access.token.claim'),('44e8d22d-0a71-410f-84b4-eb6e0cffb977','clientId','claim.name'),('44e8d22d-0a71-410f-84b4-eb6e0cffb977','true','id.token.claim'),('44e8d22d-0a71-410f-84b4-eb6e0cffb977','String','jsonType.label'),('44e8d22d-0a71-410f-84b4-eb6e0cffb977','clientId','user.session.note'),('44e8d22d-0a71-410f-84b4-eb6e0cffb977','true','userinfo.token.claim'),('4d1c448a-91b0-4aa7-9113-9e3da59cdc2e','true','access.token.claim'),('4d1c448a-91b0-4aa7-9113-9e3da59cdc2e','realm_access.roles','claim.name'),('4d1c448a-91b0-4aa7-9113-9e3da59cdc2e','String','jsonType.label'),('4d1c448a-91b0-4aa7-9113-9e3da59cdc2e','true','multivalued'),('4d1c448a-91b0-4aa7-9113-9e3da59cdc2e','foo','user.attribute'),('4e35419a-9168-4aa2-8b5e-5d86c9f90717','Role','attribute.name'),('4e35419a-9168-4aa2-8b5e-5d86c9f90717','Basic','attribute.nameformat'),('4e35419a-9168-4aa2-8b5e-5d86c9f90717','false','single'),('53abc792-a449-4a0d-9387-82aa17ccd3c9','true','access.token.claim'),('53abc792-a449-4a0d-9387-82aa17ccd3c9','clientHost','claim.name'),('53abc792-a449-4a0d-9387-82aa17ccd3c9','true','id.token.claim'),('53abc792-a449-4a0d-9387-82aa17ccd3c9','String','jsonType.label'),('53abc792-a449-4a0d-9387-82aa17ccd3c9','clientHost','user.session.note'),('53abc792-a449-4a0d-9387-82aa17ccd3c9','true','userinfo.token.claim'),('561782a9-c7f8-4662-85d8-724c11fc7da2','true','access.token.claim'),('561782a9-c7f8-4662-85d8-724c11fc7da2','zoneinfo','claim.name'),('561782a9-c7f8-4662-85d8-724c11fc7da2','true','id.token.claim'),('561782a9-c7f8-4662-85d8-724c11fc7da2','String','jsonType.label'),('561782a9-c7f8-4662-85d8-724c11fc7da2','zoneinfo','user.attribute'),('561782a9-c7f8-4662-85d8-724c11fc7da2','true','userinfo.token.claim'),('5b90f797-ad7c-4106-9e38-3a688dc515b0','true','access.token.claim'),('5b90f797-ad7c-4106-9e38-3a688dc515b0','preferred_username','claim.name'),('5b90f797-ad7c-4106-9e38-3a688dc515b0','true','id.token.claim'),('5b90f797-ad7c-4106-9e38-3a688dc515b0','String','jsonType.label'),('5b90f797-ad7c-4106-9e38-3a688dc515b0','username','user.attribute'),('5b90f797-ad7c-4106-9e38-3a688dc515b0','true','userinfo.token.claim'),('64ca42db-acd8-48d6-865a-1add54bc49bf','true','access.token.claim'),('64ca42db-acd8-48d6-865a-1add54bc49bf','true','id.token.claim'),('64ca42db-acd8-48d6-865a-1add54bc49bf','true','userinfo.token.claim'),('67ca5089-9320-4388-b3c0-0c7c709ff966','true','access.token.claim'),('67ca5089-9320-4388-b3c0-0c7c709ff966','true','id.token.claim'),('67ca5089-9320-4388-b3c0-0c7c709ff966','true','userinfo.token.claim'),('6826e8b9-8f2c-4ff0-b26f-98f4706e4c2f','true','access.token.claim'),('6826e8b9-8f2c-4ff0-b26f-98f4706e4c2f','groups','claim.name'),('6826e8b9-8f2c-4ff0-b26f-98f4706e4c2f','true','id.token.claim'),('6826e8b9-8f2c-4ff0-b26f-98f4706e4c2f','String','jsonType.label'),('6826e8b9-8f2c-4ff0-b26f-98f4706e4c2f','true','multivalued'),('6826e8b9-8f2c-4ff0-b26f-98f4706e4c2f','foo','user.attribute'),('694d7915-d713-4fba-96ec-62df97755f23','true','access.token.claim'),('694d7915-d713-4fba-96ec-62df97755f23','clientHost','claim.name'),('694d7915-d713-4fba-96ec-62df97755f23','true','id.token.claim'),('694d7915-d713-4fba-96ec-62df97755f23','String','jsonType.label'),('694d7915-d713-4fba-96ec-62df97755f23','clientHost','user.session.note'),('694d7915-d713-4fba-96ec-62df97755f23','true','userinfo.token.claim'),('6c547391-05e3-4819-91f3-24ecffd26488','true','access.token.claim'),('6c547391-05e3-4819-91f3-24ecffd26488','given_name','claim.name'),('6c547391-05e3-4819-91f3-24ecffd26488','true','id.token.claim'),('6c547391-05e3-4819-91f3-24ecffd26488','String','jsonType.label'),('6c547391-05e3-4819-91f3-24ecffd26488','firstName','user.attribute'),('6c547391-05e3-4819-91f3-24ecffd26488','true','userinfo.token.claim'),('6cd91773-7ee3-4e9d-be6f-c840f0127bb1','true','access.token.claim'),('6cd91773-7ee3-4e9d-be6f-c840f0127bb1','clientHost','claim.name'),('6cd91773-7ee3-4e9d-be6f-c840f0127bb1','true','id.token.claim'),('6cd91773-7ee3-4e9d-be6f-c840f0127bb1','String','jsonType.label'),('6cd91773-7ee3-4e9d-be6f-c840f0127bb1','clientHost','user.session.note'),('6cd91773-7ee3-4e9d-be6f-c840f0127bb1','true','userinfo.token.claim'),('6daacd93-b901-4d9a-8839-9e5b6a011295','true','access.token.claim'),('6daacd93-b901-4d9a-8839-9e5b6a011295','website','claim.name'),('6daacd93-b901-4d9a-8839-9e5b6a011295','true','id.token.claim'),('6daacd93-b901-4d9a-8839-9e5b6a011295','String','jsonType.label'),('6daacd93-b901-4d9a-8839-9e5b6a011295','website','user.attribute'),('6daacd93-b901-4d9a-8839-9e5b6a011295','true','userinfo.token.claim'),('6f1d227b-54df-4888-a3ba-81884c41224d','Role','attribute.name'),('6f1d227b-54df-4888-a3ba-81884c41224d','Basic','attribute.nameformat'),('6f1d227b-54df-4888-a3ba-81884c41224d','false','single'),('6f9e8935-da3a-45bf-ba2e-242beff49bc3','true','access.token.claim'),('6f9e8935-da3a-45bf-ba2e-242beff49bc3','preferred_username','claim.name'),('6f9e8935-da3a-45bf-ba2e-242beff49bc3','true','id.token.claim'),('6f9e8935-da3a-45bf-ba2e-242beff49bc3','String','jsonType.label'),('6f9e8935-da3a-45bf-ba2e-242beff49bc3','username','user.attribute'),('6f9e8935-da3a-45bf-ba2e-242beff49bc3','true','userinfo.token.claim'),('76b2d4d8-9ca2-4645-84ae-92446f2394a0','true','access.token.claim'),('76b2d4d8-9ca2-4645-84ae-92446f2394a0','clientAddress','claim.name'),('76b2d4d8-9ca2-4645-84ae-92446f2394a0','true','id.token.claim'),('76b2d4d8-9ca2-4645-84ae-92446f2394a0','String','jsonType.label'),('76b2d4d8-9ca2-4645-84ae-92446f2394a0','clientAddress','user.session.note'),('76b2d4d8-9ca2-4645-84ae-92446f2394a0','true','userinfo.token.claim'),('76f41b6d-6704-4f6b-87af-fa2cdada20ad','true','access.token.claim'),('76f41b6d-6704-4f6b-87af-fa2cdada20ad','profile','claim.name'),('76f41b6d-6704-4f6b-87af-fa2cdada20ad','true','id.token.claim'),('76f41b6d-6704-4f6b-87af-fa2cdada20ad','String','jsonType.label'),('76f41b6d-6704-4f6b-87af-fa2cdada20ad','profile','user.attribute'),('76f41b6d-6704-4f6b-87af-fa2cdada20ad','true','userinfo.token.claim'),('7960d8e7-8a2f-4e22-9a21-751ffa26e094','true','access.token.claim'),('7960d8e7-8a2f-4e22-9a21-751ffa26e094','updated_at','claim.name'),('7960d8e7-8a2f-4e22-9a21-751ffa26e094','true','id.token.claim'),('7960d8e7-8a2f-4e22-9a21-751ffa26e094','String','jsonType.label'),('7960d8e7-8a2f-4e22-9a21-751ffa26e094','updatedAt','user.attribute'),('7960d8e7-8a2f-4e22-9a21-751ffa26e094','true','userinfo.token.claim'),('7a774a42-c62b-4a18-b0a9-8331fab8f323','screenName','attribute.name'),('7a774a42-c62b-4a18-b0a9-8331fab8f323','username','user.attribute'),('82fb9f97-ec06-4ba9-bf9a-1ed1200e1782','true','access.token.claim'),('82fb9f97-ec06-4ba9-bf9a-1ed1200e1782','resource_access.${client_id}.roles','claim.name'),('82fb9f97-ec06-4ba9-bf9a-1ed1200e1782','String','jsonType.label'),('82fb9f97-ec06-4ba9-bf9a-1ed1200e1782','true','multivalued'),('82fb9f97-ec06-4ba9-bf9a-1ed1200e1782','foo','user.attribute'),('8521f557-223a-43f7-9472-b7654534d3e7','true','access.token.claim'),('8521f557-223a-43f7-9472-b7654534d3e7','resource_access.${client_id}.roles','claim.name'),('8521f557-223a-43f7-9472-b7654534d3e7','String','jsonType.label'),('8521f557-223a-43f7-9472-b7654534d3e7','true','multivalued'),('8521f557-223a-43f7-9472-b7654534d3e7','foo','user.attribute'),('86cbacaa-05c6-40ad-86c4-1077c77e9f93','true','access.token.claim'),('86cbacaa-05c6-40ad-86c4-1077c77e9f93','upn','claim.name'),('86cbacaa-05c6-40ad-86c4-1077c77e9f93','true','id.token.claim'),('86cbacaa-05c6-40ad-86c4-1077c77e9f93','String','jsonType.label'),('86cbacaa-05c6-40ad-86c4-1077c77e9f93','username','user.attribute'),('86cbacaa-05c6-40ad-86c4-1077c77e9f93','true','userinfo.token.claim'),('88e952a7-c3fe-42ff-86e8-5b684cdd7a96','true','access.token.claim'),('88e952a7-c3fe-42ff-86e8-5b684cdd7a96','picture','claim.name'),('88e952a7-c3fe-42ff-86e8-5b684cdd7a96','true','id.token.claim'),('88e952a7-c3fe-42ff-86e8-5b684cdd7a96','String','jsonType.label'),('88e952a7-c3fe-42ff-86e8-5b684cdd7a96','picture','user.attribute'),('88e952a7-c3fe-42ff-86e8-5b684cdd7a96','true','userinfo.token.claim'),('8b884a22-fe4f-46b5-b733-6e04bb696f80','true','access.token.claim'),('8b884a22-fe4f-46b5-b733-6e04bb696f80','gender','claim.name'),('8b884a22-fe4f-46b5-b733-6e04bb696f80','true','id.token.claim'),('8b884a22-fe4f-46b5-b733-6e04bb696f80','String','jsonType.label'),('8b884a22-fe4f-46b5-b733-6e04bb696f80','gender','user.attribute'),('8b884a22-fe4f-46b5-b733-6e04bb696f80','true','userinfo.token.claim'),('8ce14cac-aca7-4ee2-9ff2-37610bc58828','true','access.token.claim'),('8ce14cac-aca7-4ee2-9ff2-37610bc58828','clientHost','claim.name'),('8ce14cac-aca7-4ee2-9ff2-37610bc58828','true','id.token.claim'),('8ce14cac-aca7-4ee2-9ff2-37610bc58828','String','jsonType.label'),('8ce14cac-aca7-4ee2-9ff2-37610bc58828','clientHost','user.session.note'),('8ce14cac-aca7-4ee2-9ff2-37610bc58828','true','userinfo.token.claim'),('96738fc1-152c-415e-9c71-03959adab303','lastName','attribute.name'),('96738fc1-152c-415e-9c71-03959adab303','lastName','user.attribute'),('9dc000da-80d7-418b-a5ea-48ffbbf119d7','true','access.token.claim'),('9dc000da-80d7-418b-a5ea-48ffbbf119d7','clientAddress','claim.name'),('9dc000da-80d7-418b-a5ea-48ffbbf119d7','true','id.token.claim'),('9dc000da-80d7-418b-a5ea-48ffbbf119d7','String','jsonType.label'),('9dc000da-80d7-418b-a5ea-48ffbbf119d7','clientAddress','user.session.note'),('9dc000da-80d7-418b-a5ea-48ffbbf119d7','true','userinfo.token.claim'),('9e85545f-f6ca-4923-9322-2dcbe13c323f','true','access.token.claim'),('9e85545f-f6ca-4923-9322-2dcbe13c323f','updated_at','claim.name'),('9e85545f-f6ca-4923-9322-2dcbe13c323f','true','id.token.claim'),('9e85545f-f6ca-4923-9322-2dcbe13c323f','String','jsonType.label'),('9e85545f-f6ca-4923-9322-2dcbe13c323f','updatedAt','user.attribute'),('9e85545f-f6ca-4923-9322-2dcbe13c323f','true','userinfo.token.claim'),('a8032276-8a51-4223-8843-e90b49dbb604','true','access.token.claim'),('a8032276-8a51-4223-8843-e90b49dbb604','clientAddress','claim.name'),('a8032276-8a51-4223-8843-e90b49dbb604','true','id.token.claim'),('a8032276-8a51-4223-8843-e90b49dbb604','String','jsonType.label'),('a8032276-8a51-4223-8843-e90b49dbb604','clientAddress','user.session.note'),('a8032276-8a51-4223-8843-e90b49dbb604','true','userinfo.token.claim'),('af711ee2-37f5-4080-aae7-d42dbe6c7f4e','firstName','attribute.name'),('af711ee2-37f5-4080-aae7-d42dbe6c7f4e','firstName','user.attribute'),('b181f52a-406f-43a0-a77f-16a34b2b6fc8','true','access.token.claim'),('b181f52a-406f-43a0-a77f-16a34b2b6fc8','realm_access.roles','claim.name'),('b181f52a-406f-43a0-a77f-16a34b2b6fc8','String','jsonType.label'),('b181f52a-406f-43a0-a77f-16a34b2b6fc8','true','multivalued'),('b181f52a-406f-43a0-a77f-16a34b2b6fc8','foo','user.attribute'),('b5b04c93-ca4b-4b95-b0a0-280edf5cdc99','true','access.token.claim'),('b5b04c93-ca4b-4b95-b0a0-280edf5cdc99','family_name','claim.name'),('b5b04c93-ca4b-4b95-b0a0-280edf5cdc99','true','id.token.claim'),('b5b04c93-ca4b-4b95-b0a0-280edf5cdc99','String','jsonType.label'),('b5b04c93-ca4b-4b95-b0a0-280edf5cdc99','lastName','user.attribute'),('b5b04c93-ca4b-4b95-b0a0-280edf5cdc99','true','userinfo.token.claim'),('c1e8460d-84d3-4736-a851-cdbf2f83acc6','true','access.token.claim'),('c1e8460d-84d3-4736-a851-cdbf2f83acc6','email','claim.name'),('c1e8460d-84d3-4736-a851-cdbf2f83acc6','true','id.token.claim'),('c1e8460d-84d3-4736-a851-cdbf2f83acc6','String','jsonType.label'),('c1e8460d-84d3-4736-a851-cdbf2f83acc6','email','user.attribute'),('c1e8460d-84d3-4736-a851-cdbf2f83acc6','true','userinfo.token.claim'),('c569ab91-32b5-457b-a559-e86738517797','true','access.token.claim'),('c569ab91-32b5-457b-a559-e86738517797','true','id.token.claim'),('c569ab91-32b5-457b-a559-e86738517797','country','user.attribute.country'),('c569ab91-32b5-457b-a559-e86738517797','formatted','user.attribute.formatted'),('c569ab91-32b5-457b-a559-e86738517797','locality','user.attribute.locality'),('c569ab91-32b5-457b-a559-e86738517797','postal_code','user.attribute.postal_code'),('c569ab91-32b5-457b-a559-e86738517797','region','user.attribute.region'),('c569ab91-32b5-457b-a559-e86738517797','street','user.attribute.street'),('c569ab91-32b5-457b-a559-e86738517797','true','userinfo.token.claim'),('c577a27d-ea2b-4799-b984-ef0fa73339fb','true','access.token.claim'),('c577a27d-ea2b-4799-b984-ef0fa73339fb','nickname','claim.name'),('c577a27d-ea2b-4799-b984-ef0fa73339fb','true','id.token.claim'),('c577a27d-ea2b-4799-b984-ef0fa73339fb','String','jsonType.label'),('c577a27d-ea2b-4799-b984-ef0fa73339fb','nickname','user.attribute'),('c577a27d-ea2b-4799-b984-ef0fa73339fb','true','userinfo.token.claim'),('c730e294-8c33-465b-a1a9-a42a0a1a79db','true','access.token.claim'),('c730e294-8c33-465b-a1a9-a42a0a1a79db','clientAddress','claim.name'),('c730e294-8c33-465b-a1a9-a42a0a1a79db','true','id.token.claim'),('c730e294-8c33-465b-a1a9-a42a0a1a79db','String','jsonType.label'),('c730e294-8c33-465b-a1a9-a42a0a1a79db','clientAddress','user.session.note'),('c730e294-8c33-465b-a1a9-a42a0a1a79db','true','userinfo.token.claim'),('c77be71a-b281-4532-8d89-f663caa47d8a','true','access.token.claim'),('c77be71a-b281-4532-8d89-f663caa47d8a','clientId','claim.name'),('c77be71a-b281-4532-8d89-f663caa47d8a','true','id.token.claim'),('c77be71a-b281-4532-8d89-f663caa47d8a','String','jsonType.label'),('c77be71a-b281-4532-8d89-f663caa47d8a','clientId','user.session.note'),('c77be71a-b281-4532-8d89-f663caa47d8a','true','userinfo.token.claim'),('cc947cdb-8e1e-4b67-a17f-079f7277f21c','true','access.token.claim'),('cc947cdb-8e1e-4b67-a17f-079f7277f21c','clientId','claim.name'),('cc947cdb-8e1e-4b67-a17f-079f7277f21c','true','id.token.claim'),('cc947cdb-8e1e-4b67-a17f-079f7277f21c','String','jsonType.label'),('cc947cdb-8e1e-4b67-a17f-079f7277f21c','clientId','user.session.note'),('cc947cdb-8e1e-4b67-a17f-079f7277f21c','true','userinfo.token.claim'),('cd68315c-0d20-4cfe-9a52-a7f019501b98','true','access.token.claim'),('cd68315c-0d20-4cfe-9a52-a7f019501b98','clientId','claim.name'),('cd68315c-0d20-4cfe-9a52-a7f019501b98','true','id.token.claim'),('cd68315c-0d20-4cfe-9a52-a7f019501b98','String','jsonType.label'),('cd68315c-0d20-4cfe-9a52-a7f019501b98','clientId','user.session.note'),('cd68315c-0d20-4cfe-9a52-a7f019501b98','true','userinfo.token.claim'),('d5a891d5-4a0c-4150-89a2-ac5f78e2b0d4','true','access.token.claim'),('d5a891d5-4a0c-4150-89a2-ac5f78e2b0d4','phone_number_verified','claim.name'),('d5a891d5-4a0c-4150-89a2-ac5f78e2b0d4','true','id.token.claim'),('d5a891d5-4a0c-4150-89a2-ac5f78e2b0d4','boolean','jsonType.label'),('d5a891d5-4a0c-4150-89a2-ac5f78e2b0d4','phoneNumberVerified','user.attribute'),('d5a891d5-4a0c-4150-89a2-ac5f78e2b0d4','true','userinfo.token.claim'),('dad203c1-eacb-4d73-be8a-907eafca608b','true','access.token.claim'),('dad203c1-eacb-4d73-be8a-907eafca608b','nickname','claim.name'),('dad203c1-eacb-4d73-be8a-907eafca608b','true','id.token.claim'),('dad203c1-eacb-4d73-be8a-907eafca608b','String','jsonType.label'),('dad203c1-eacb-4d73-be8a-907eafca608b','nickname','user.attribute'),('dad203c1-eacb-4d73-be8a-907eafca608b','true','userinfo.token.claim'),('dc3e541e-5793-4d6e-b279-f679321c69a0','firstName','attribute.name'),('dc3e541e-5793-4d6e-b279-f679321c69a0','firstName','user.attribute'),('dc4c57c6-5469-4750-bcbf-25e1b8fe62dc','true','access.token.claim'),('dc4c57c6-5469-4750-bcbf-25e1b8fe62dc','clientAddress','claim.name'),('dc4c57c6-5469-4750-bcbf-25e1b8fe62dc','true','id.token.claim'),('dc4c57c6-5469-4750-bcbf-25e1b8fe62dc','String','jsonType.label'),('dc4c57c6-5469-4750-bcbf-25e1b8fe62dc','clientAddress','user.session.note'),('dc4c57c6-5469-4750-bcbf-25e1b8fe62dc','true','userinfo.token.claim'),('ddd59dfb-c4e3-4a87-967d-c0528889e35e','true','access.token.claim'),('ddd59dfb-c4e3-4a87-967d-c0528889e35e','middle_name','claim.name'),('ddd59dfb-c4e3-4a87-967d-c0528889e35e','true','id.token.claim'),('ddd59dfb-c4e3-4a87-967d-c0528889e35e','String','jsonType.label'),('ddd59dfb-c4e3-4a87-967d-c0528889e35e','middleName','user.attribute'),('ddd59dfb-c4e3-4a87-967d-c0528889e35e','true','userinfo.token.claim'),('e1916664-741a-45bd-ad97-5cbe0422ce0d','true','access.token.claim'),('e1916664-741a-45bd-ad97-5cbe0422ce0d','clientHost','claim.name'),('e1916664-741a-45bd-ad97-5cbe0422ce0d','true','id.token.claim'),('e1916664-741a-45bd-ad97-5cbe0422ce0d','String','jsonType.label'),('e1916664-741a-45bd-ad97-5cbe0422ce0d','clientHost','user.session.note'),('e1916664-741a-45bd-ad97-5cbe0422ce0d','true','userinfo.token.claim'),('e29251ff-161d-4de7-9e88-50862b1093db','true','access.token.claim'),('e29251ff-161d-4de7-9e88-50862b1093db','locale','claim.name'),('e29251ff-161d-4de7-9e88-50862b1093db','true','id.token.claim'),('e29251ff-161d-4de7-9e88-50862b1093db','String','jsonType.label'),('e29251ff-161d-4de7-9e88-50862b1093db','locale','user.attribute'),('e29251ff-161d-4de7-9e88-50862b1093db','true','userinfo.token.claim'),('e38548f4-d688-4266-bf51-a38d58adbab6','true','access.token.claim'),('e38548f4-d688-4266-bf51-a38d58adbab6','middle_name','claim.name'),('e38548f4-d688-4266-bf51-a38d58adbab6','true','id.token.claim'),('e38548f4-d688-4266-bf51-a38d58adbab6','String','jsonType.label'),('e38548f4-d688-4266-bf51-a38d58adbab6','middleName','user.attribute'),('e38548f4-d688-4266-bf51-a38d58adbab6','true','userinfo.token.claim'),('e39f0f4d-ff37-417e-8ab6-aaa4bfd1df11','true','access.token.claim'),('e39f0f4d-ff37-417e-8ab6-aaa4bfd1df11','locale','claim.name'),('e39f0f4d-ff37-417e-8ab6-aaa4bfd1df11','true','id.token.claim'),('e39f0f4d-ff37-417e-8ab6-aaa4bfd1df11','String','jsonType.label'),('e39f0f4d-ff37-417e-8ab6-aaa4bfd1df11','locale','user.attribute'),('e39f0f4d-ff37-417e-8ab6-aaa4bfd1df11','true','userinfo.token.claim'),('e4448a31-6363-4930-86ed-8e65b68213cc','true','access.token.claim'),('e4448a31-6363-4930-86ed-8e65b68213cc','given_name','claim.name'),('e4448a31-6363-4930-86ed-8e65b68213cc','true','id.token.claim'),('e4448a31-6363-4930-86ed-8e65b68213cc','String','jsonType.label'),('e4448a31-6363-4930-86ed-8e65b68213cc','firstName','user.attribute'),('e4448a31-6363-4930-86ed-8e65b68213cc','true','userinfo.token.claim'),('e6f2582e-fe36-405f-9843-a3a9e818beee','true','access.token.claim'),('e6f2582e-fe36-405f-9843-a3a9e818beee','email','claim.name'),('e6f2582e-fe36-405f-9843-a3a9e818beee','true','id.token.claim'),('e6f2582e-fe36-405f-9843-a3a9e818beee','String','jsonType.label'),('e6f2582e-fe36-405f-9843-a3a9e818beee','email','user.attribute'),('e6f2582e-fe36-405f-9843-a3a9e818beee','true','userinfo.token.claim'),('eaf0c207-03e4-4f06-8814-2a4e0f054bcf','true','access.token.claim'),('eaf0c207-03e4-4f06-8814-2a4e0f054bcf','clientAddress','claim.name'),('eaf0c207-03e4-4f06-8814-2a4e0f054bcf','true','id.token.claim'),('eaf0c207-03e4-4f06-8814-2a4e0f054bcf','String','jsonType.label'),('eaf0c207-03e4-4f06-8814-2a4e0f054bcf','clientAddress','user.session.note'),('eaf0c207-03e4-4f06-8814-2a4e0f054bcf','true','userinfo.token.claim'),('eb79ff4c-d4d3-4327-b15c-a26f01eb7dfc','true','access.token.claim'),('eb79ff4c-d4d3-4327-b15c-a26f01eb7dfc','zoneinfo','claim.name'),('eb79ff4c-d4d3-4327-b15c-a26f01eb7dfc','true','id.token.claim'),('eb79ff4c-d4d3-4327-b15c-a26f01eb7dfc','String','jsonType.label'),('eb79ff4c-d4d3-4327-b15c-a26f01eb7dfc','zoneinfo','user.attribute'),('eb79ff4c-d4d3-4327-b15c-a26f01eb7dfc','true','userinfo.token.claim'),('ee48d12b-8742-4494-aef0-9166c07f1cd7','true','access.token.claim'),('ee48d12b-8742-4494-aef0-9166c07f1cd7','birthdate','claim.name'),('ee48d12b-8742-4494-aef0-9166c07f1cd7','true','id.token.claim'),('ee48d12b-8742-4494-aef0-9166c07f1cd7','String','jsonType.label'),('ee48d12b-8742-4494-aef0-9166c07f1cd7','birthdate','user.attribute'),('ee48d12b-8742-4494-aef0-9166c07f1cd7','true','userinfo.token.claim'),('f2eac318-5c5d-4730-aef1-0545946cd421','true','access.token.claim'),('f2eac318-5c5d-4730-aef1-0545946cd421','true','id.token.claim'),('f2eac318-5c5d-4730-aef1-0545946cd421','country','user.attribute.country'),('f2eac318-5c5d-4730-aef1-0545946cd421','formatted','user.attribute.formatted'),('f2eac318-5c5d-4730-aef1-0545946cd421','locality','user.attribute.locality'),('f2eac318-5c5d-4730-aef1-0545946cd421','postal_code','user.attribute.postal_code'),('f2eac318-5c5d-4730-aef1-0545946cd421','region','user.attribute.region'),('f2eac318-5c5d-4730-aef1-0545946cd421','street','user.attribute.street'),('f2eac318-5c5d-4730-aef1-0545946cd421','true','userinfo.token.claim'),('f4a18126-e246-4ed1-9438-5b341c01470b','emailAddress','attribute.name'),('f4a18126-e246-4ed1-9438-5b341c01470b','email','user.attribute');
/*!40000 ALTER TABLE `PROTOCOL_MAPPER_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM`
--

DROP TABLE IF EXISTS `REALM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM` (
  `ID` varchar(36) NOT NULL,
  `ACCESS_CODE_LIFESPAN` int(11) DEFAULT NULL,
  `USER_ACTION_LIFESPAN` int(11) DEFAULT NULL,
  `ACCESS_TOKEN_LIFESPAN` int(11) DEFAULT NULL,
  `ACCOUNT_THEME` varchar(255) DEFAULT NULL,
  `ADMIN_THEME` varchar(255) DEFAULT NULL,
  `EMAIL_THEME` varchar(255) DEFAULT NULL,
  `ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `EVENTS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `EVENTS_EXPIRATION` bigint(20) DEFAULT NULL,
  `LOGIN_THEME` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `NOT_BEFORE` int(11) DEFAULT NULL,
  `PASSWORD_POLICY` varchar(2550) DEFAULT NULL,
  `REGISTRATION_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
  `REMEMBER_ME` bit(1) NOT NULL DEFAULT b'0',
  `RESET_PASSWORD_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
  `SOCIAL` bit(1) NOT NULL DEFAULT b'0',
  `SSL_REQUIRED` varchar(255) DEFAULT NULL,
  `SSO_IDLE_TIMEOUT` int(11) DEFAULT NULL,
  `SSO_MAX_LIFESPAN` int(11) DEFAULT NULL,
  `UPDATE_PROFILE_ON_SOC_LOGIN` bit(1) NOT NULL DEFAULT b'0',
  `VERIFY_EMAIL` bit(1) NOT NULL DEFAULT b'0',
  `MASTER_ADMIN_CLIENT` varchar(36) DEFAULT NULL,
  `LOGIN_LIFESPAN` int(11) DEFAULT NULL,
  `INTERNATIONALIZATION_ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `DEFAULT_LOCALE` varchar(255) DEFAULT NULL,
  `REG_EMAIL_AS_USERNAME` bit(1) NOT NULL DEFAULT b'0',
  `ADMIN_EVENTS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `ADMIN_EVENTS_DETAILS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `EDIT_USERNAME_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
  `OTP_POLICY_COUNTER` int(11) DEFAULT '0',
  `OTP_POLICY_WINDOW` int(11) DEFAULT '1',
  `OTP_POLICY_PERIOD` int(11) DEFAULT '30',
  `OTP_POLICY_DIGITS` int(11) DEFAULT '6',
  `OTP_POLICY_ALG` varchar(36) DEFAULT 'HmacSHA1',
  `OTP_POLICY_TYPE` varchar(36) DEFAULT 'totp',
  `BROWSER_FLOW` varchar(36) DEFAULT NULL,
  `REGISTRATION_FLOW` varchar(36) DEFAULT NULL,
  `DIRECT_GRANT_FLOW` varchar(36) DEFAULT NULL,
  `RESET_CREDENTIALS_FLOW` varchar(36) DEFAULT NULL,
  `CLIENT_AUTH_FLOW` varchar(36) DEFAULT NULL,
  `OFFLINE_SESSION_IDLE_TIMEOUT` int(11) DEFAULT '0',
  `REVOKE_REFRESH_TOKEN` bit(1) NOT NULL DEFAULT b'0',
  `ACCESS_TOKEN_LIFE_IMPLICIT` int(11) DEFAULT '0',
  `LOGIN_WITH_EMAIL_ALLOWED` bit(1) NOT NULL DEFAULT b'1',
  `DUPLICATE_EMAILS_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
  `DOCKER_AUTH_FLOW` varchar(36) DEFAULT NULL,
  `REFRESH_TOKEN_MAX_REUSE` int(11) DEFAULT '0',
  `ALLOW_USER_MANAGED_ACCESS` bit(1) NOT NULL DEFAULT b'0',
  `SSO_MAX_LIFESPAN_REMEMBER_ME` int(11) NOT NULL,
  `SSO_IDLE_TIMEOUT_REMEMBER_ME` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_ORVSDMLA56612EAEFIQ6WL5OI` (`NAME`),
  KEY `IDX_REALM_MASTER_ADM_CLI` (`MASTER_ADMIN_CLIENT`),
  CONSTRAINT `FK_TRAF444KK6QRKMS7N56AIWQ5Y` FOREIGN KEY (`MASTER_ADMIN_CLIENT`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM`
--

LOCK TABLES `REALM` WRITE;
/*!40000 ALTER TABLE `REALM` DISABLE KEYS */;
INSERT INTO `REALM` VALUES ('liferay-portal',300,300,300,'deltares-keycloak-theme','deltares-keycloak-theme','deltares-keycloak-theme','','',432000,'deltares-keycloak-theme','liferay-portal',1562574826,NULL,'','','','\0','EXTERNAL',60,36000,'\0','','d21fa08a-b4ef-4ce8-b2a1-ba74c4e29b26',1800,'','en','\0','','','\0',0,1,30,6,'HmacSHA1','totp','6ee5e6cc-eb53-4947-90e2-af4216ec5de0','590ca870-9a16-42b4-8874-02a16b754e91','ee89edb5-990d-479c-9204-33c657204877','338d3c95-8e22-463f-af1a-73fa3dedae37','19d7a544-e0b6-41ac-872f-e8b957353cce',2592000,'\0',900,'','\0','438193d9-170f-4439-b56c-c0d7cb1183a7',0,'\0',0,0),('master',60,300,60,'deltares-keycloak-theme','deltares-keycloak-theme',NULL,'','\0',0,NULL,'master',0,NULL,'\0','\0','\0','\0','EXTERNAL',1800,36000,'\0','\0','b39c0c40-7753-42c6-9356-694c4e7182dc',1800,'\0',NULL,'\0','\0','\0','\0',0,1,30,6,'HmacSHA1','totp','e75011ee-5899-46c9-8f16-bafa3003fedc','2128ac0c-dbda-4e7d-81a0-db457aa4b2ef','e1b2d379-14e4-4585-8060-548924a4e1a4','4b647958-8075-4fb0-b549-5487a63f9e4d','b37eaf99-a47d-4e9d-8bbd-e909ba16a0cf',2592000,'\0',900,'','\0','553687d3-9524-495a-88d3-98bc0ccb616e',0,'\0',0,0);
/*!40000 ALTER TABLE `REALM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_ATTRIBUTE`
--

DROP TABLE IF EXISTS `REALM_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_ATTRIBUTE` (
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`NAME`,`REALM_ID`),
  KEY `IDX_REALM_ATTR_REALM` (`REALM_ID`),
  CONSTRAINT `FK_8SHXD6L3E9ATQUKACXGPFFPTW` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_ATTRIBUTE`
--

LOCK TABLES `REALM_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `REALM_ATTRIBUTE` DISABLE KEYS */;
INSERT INTO `REALM_ATTRIBUTE` VALUES ('actionTokenGeneratedByAdminLifespan','43200','liferay-portal'),('actionTokenGeneratedByAdminLifespan','43200','master'),('actionTokenGeneratedByUserLifespan','300','liferay-portal'),('actionTokenGeneratedByUserLifespan','300','master'),('actionTokenGeneratedByUserLifespan.reset-credentials','1800','liferay-portal'),('actionTokenGeneratedByUserLifespan.verify-email','1800','liferay-portal'),('bruteForceProtected','false','liferay-portal'),('bruteForceProtected','false','master'),('displayName','MyDeltares','liferay-portal'),('displayName','Keycloak','master'),('displayNameHtml','<div class=\"kc-logo-text\"><span>Keycloak</span></div>','master'),('failureFactor','30','liferay-portal'),('failureFactor','30','master'),('maxDeltaTimeSeconds','43200','liferay-portal'),('maxDeltaTimeSeconds','43200','master'),('maxFailureWaitSeconds','900','liferay-portal'),('maxFailureWaitSeconds','900','master'),('minimumQuickLoginWaitSeconds','60','liferay-portal'),('minimumQuickLoginWaitSeconds','60','master'),('offlineSessionMaxLifespan','5184000','liferay-portal'),('offlineSessionMaxLifespan','5184000','master'),('offlineSessionMaxLifespanEnabled','false','liferay-portal'),('offlineSessionMaxLifespanEnabled','false','master'),('permanentLockout','false','liferay-portal'),('permanentLockout','false','master'),('quickLoginCheckMilliSeconds','1000','liferay-portal'),('quickLoginCheckMilliSeconds','1000','master'),('waitIncrementSeconds','60','liferay-portal'),('waitIncrementSeconds','60','master'),('_browser_header.contentSecurityPolicy','frame-src \'self\'; frame-ancestors \'self\'; object-src \'none\';','liferay-portal'),('_browser_header.contentSecurityPolicy','frame-src \'self\'; frame-ancestors \'self\'; object-src \'none\';','master'),('_browser_header.contentSecurityPolicyReportOnly','','liferay-portal'),('_browser_header.contentSecurityPolicyReportOnly','','master'),('_browser_header.strictTransportSecurity','max-age=31536000; includeSubDomains','liferay-portal'),('_browser_header.strictTransportSecurity','max-age=31536000; includeSubDomains','master'),('_browser_header.xContentTypeOptions','nosniff','liferay-portal'),('_browser_header.xContentTypeOptions','nosniff','master'),('_browser_header.xFrameOptions','SAMEORIGIN','liferay-portal'),('_browser_header.xFrameOptions','SAMEORIGIN','master'),('_browser_header.xRobotsTag','none','liferay-portal'),('_browser_header.xRobotsTag','none','master'),('_browser_header.xXSSProtection','1; mode=block','liferay-portal'),('_browser_header.xXSSProtection','1; mode=block','master');
/*!40000 ALTER TABLE `REALM_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_DEFAULT_GROUPS`
--

DROP TABLE IF EXISTS `REALM_DEFAULT_GROUPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_DEFAULT_GROUPS` (
  `REALM_ID` varchar(36) NOT NULL,
  `GROUP_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`REALM_ID`,`GROUP_ID`),
  UNIQUE KEY `CON_GROUP_ID_DEF_GROUPS` (`GROUP_ID`),
  KEY `IDX_REALM_DEF_GRP_REALM` (`REALM_ID`),
  CONSTRAINT `FK_DEF_GROUPS_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `KEYCLOAK_GROUP` (`ID`),
  CONSTRAINT `FK_DEF_GROUPS_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_DEFAULT_GROUPS`
--

LOCK TABLES `REALM_DEFAULT_GROUPS` WRITE;
/*!40000 ALTER TABLE `REALM_DEFAULT_GROUPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REALM_DEFAULT_GROUPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_DEFAULT_ROLES`
--

DROP TABLE IF EXISTS `REALM_DEFAULT_ROLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_DEFAULT_ROLES` (
  `REALM_ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`REALM_ID`,`ROLE_ID`),
  UNIQUE KEY `UK_H4WPD7W4HSOOLNI3H0SW7BTJE` (`ROLE_ID`),
  KEY `IDX_REALM_DEF_ROLES_REALM` (`REALM_ID`),
  CONSTRAINT `FK_EVUDB1PPW84OXFAX2DRS03ICC` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`),
  CONSTRAINT `FK_H4WPD7W4HSOOLNI3H0SW7BTJE` FOREIGN KEY (`ROLE_ID`) REFERENCES `KEYCLOAK_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_DEFAULT_ROLES`
--

LOCK TABLES `REALM_DEFAULT_ROLES` WRITE;
/*!40000 ALTER TABLE `REALM_DEFAULT_ROLES` DISABLE KEYS */;
INSERT INTO `REALM_DEFAULT_ROLES` VALUES ('liferay-portal','beda296e-ad85-43a6-9d77-d37193147efa'),('liferay-portal','f4e93900-0b7d-4fa3-b2ca-523715fff9d1'),('master','977804ea-5096-41be-b20a-35d5b4749896'),('master','c9a15285-475b-4369-94f6-a6de3bcce560');
/*!40000 ALTER TABLE `REALM_DEFAULT_ROLES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_ENABLED_EVENT_TYPES`
--

DROP TABLE IF EXISTS `REALM_ENABLED_EVENT_TYPES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_ENABLED_EVENT_TYPES` (
  `REALM_ID` varchar(36) NOT NULL,
  `VALUE` varchar(255) NOT NULL,
  PRIMARY KEY (`REALM_ID`,`VALUE`),
  KEY `IDX_REALM_EVT_TYPES_REALM` (`REALM_ID`),
  CONSTRAINT `FK_H846O4H0W8EPX5NWEDRF5Y69J` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_ENABLED_EVENT_TYPES`
--

LOCK TABLES `REALM_ENABLED_EVENT_TYPES` WRITE;
/*!40000 ALTER TABLE `REALM_ENABLED_EVENT_TYPES` DISABLE KEYS */;
INSERT INTO `REALM_ENABLED_EVENT_TYPES` VALUES ('liferay-portal','CLIENT_REGISTER'),('liferay-portal','CLIENT_REGISTER_ERROR'),('liferay-portal','IDENTITY_PROVIDER_FIRST_LOGIN'),('liferay-portal','REGISTER'),('liferay-portal','REGISTER_ERROR'),('liferay-portal','SEND_IDENTITY_PROVIDER_LINK_ERROR'),('liferay-portal','SEND_RESET_PASSWORD_ERROR'),('liferay-portal','SEND_VERIFY_EMAIL_ERROR');
/*!40000 ALTER TABLE `REALM_ENABLED_EVENT_TYPES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_EVENTS_LISTENERS`
--

DROP TABLE IF EXISTS `REALM_EVENTS_LISTENERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_EVENTS_LISTENERS` (
  `REALM_ID` varchar(36) NOT NULL,
  `VALUE` varchar(255) NOT NULL,
  PRIMARY KEY (`REALM_ID`,`VALUE`),
  KEY `IDX_REALM_EVT_LIST_REALM` (`REALM_ID`),
  CONSTRAINT `FK_H846O4H0W8EPX5NXEV9F5Y69J` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_EVENTS_LISTENERS`
--

LOCK TABLES `REALM_EVENTS_LISTENERS` WRITE;
/*!40000 ALTER TABLE `REALM_EVENTS_LISTENERS` DISABLE KEYS */;
INSERT INTO `REALM_EVENTS_LISTENERS` VALUES ('liferay-portal','jboss-logging'),('master','jboss-logging');
/*!40000 ALTER TABLE `REALM_EVENTS_LISTENERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_REQUIRED_CREDENTIAL`
--

DROP TABLE IF EXISTS `REALM_REQUIRED_CREDENTIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_REQUIRED_CREDENTIAL` (
  `TYPE` varchar(255) NOT NULL,
  `FORM_LABEL` varchar(255) DEFAULT NULL,
  `INPUT` bit(1) NOT NULL DEFAULT b'0',
  `SECRET` bit(1) NOT NULL DEFAULT b'0',
  `REALM_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`REALM_ID`,`TYPE`),
  CONSTRAINT `FK_5HG65LYBEVAVKQFKI3KPONH9V` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_REQUIRED_CREDENTIAL`
--

LOCK TABLES `REALM_REQUIRED_CREDENTIAL` WRITE;
/*!40000 ALTER TABLE `REALM_REQUIRED_CREDENTIAL` DISABLE KEYS */;
INSERT INTO `REALM_REQUIRED_CREDENTIAL` VALUES ('password','password','','','liferay-portal'),('password','password','','','master');
/*!40000 ALTER TABLE `REALM_REQUIRED_CREDENTIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_SMTP_CONFIG`
--

DROP TABLE IF EXISTS `REALM_SMTP_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_SMTP_CONFIG` (
  `REALM_ID` varchar(36) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`REALM_ID`,`NAME`),
  CONSTRAINT `FK_70EJ8XDXGXD0B9HH6180IRR0O` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_SMTP_CONFIG`
--

LOCK TABLES `REALM_SMTP_CONFIG` WRITE;
/*!40000 ALTER TABLE `REALM_SMTP_CONFIG` DISABLE KEYS */;
INSERT INTO `REALM_SMTP_CONFIG` VALUES ('liferay-portal','true','auth'),('liferay-portal','mydeltares@deltares.nl','from'),('liferay-portal','MyDeltares Account Management','fromDisplayName'),('liferay-portal','smtp.office365.com','host'),('liferay-portal','qywplkxggxrtlhhz','password'),('liferay-portal','587','port'),('liferay-portal','false','ssl'),('liferay-portal','true','starttls'),('liferay-portal','mydeltares@deltares.nl','user');
/*!40000 ALTER TABLE `REALM_SMTP_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_SUPPORTED_LOCALES`
--

DROP TABLE IF EXISTS `REALM_SUPPORTED_LOCALES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_SUPPORTED_LOCALES` (
  `REALM_ID` varchar(36) NOT NULL,
  `VALUE` varchar(255) NOT NULL,
  PRIMARY KEY (`REALM_ID`,`VALUE`),
  KEY `IDX_REALM_SUPP_LOCAL_REALM` (`REALM_ID`),
  CONSTRAINT `FK_SUPPORTED_LOCALES_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_SUPPORTED_LOCALES`
--

LOCK TABLES `REALM_SUPPORTED_LOCALES` WRITE;
/*!40000 ALTER TABLE `REALM_SUPPORTED_LOCALES` DISABLE KEYS */;
INSERT INTO `REALM_SUPPORTED_LOCALES` VALUES ('liferay-portal','en'),('liferay-portal','nl'),('master','');
/*!40000 ALTER TABLE `REALM_SUPPORTED_LOCALES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIRECT_URIS`
--

DROP TABLE IF EXISTS `REDIRECT_URIS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIRECT_URIS` (
  `CLIENT_ID` varchar(36) NOT NULL,
  `VALUE` varchar(255) NOT NULL,
  PRIMARY KEY (`CLIENT_ID`,`VALUE`),
  KEY `IDX_REDIR_URI_CLIENT` (`CLIENT_ID`),
  CONSTRAINT `FK_1BURS8PB4OUJ97H5WUPPAHV9F` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIRECT_URIS`
--

LOCK TABLES `REDIRECT_URIS` WRITE;
/*!40000 ALTER TABLE `REDIRECT_URIS` DISABLE KEYS */;
INSERT INTO `REDIRECT_URIS` VALUES ('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','http://localhost:8080/*'),('364c3e71-9195-4162-9d08-e048421fdd0f','/auth/admin/liferay-portal/console/*'),('56c3ecf9-d922-4563-b0b7-05c1b20f8f31','/auth/admin/master/console/*'),('67d6d3e3-38a7-4562-aac7-03832d713532','http://localhost:8080/*'),('b8a62fae-771b-402a-8515-1b13b025da91','http://liferay:8081/*'),('dbe87c24-ee61-418a-9058-85660d151e0e','http://localhost:8081/*'),('f1b13f7c-a916-4b24-b314-0200496926ce','/auth/realms/liferay-portal/account/*'),('f4a55e24-e087-4f50-9673-67e5f1c2feb7','/auth/realms/master/account/*');
/*!40000 ALTER TABLE `REDIRECT_URIS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REQUIRED_ACTION_CONFIG`
--

DROP TABLE IF EXISTS `REQUIRED_ACTION_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REQUIRED_ACTION_CONFIG` (
  `REQUIRED_ACTION_ID` varchar(36) NOT NULL,
  `VALUE` longtext,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`REQUIRED_ACTION_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REQUIRED_ACTION_CONFIG`
--

LOCK TABLES `REQUIRED_ACTION_CONFIG` WRITE;
/*!40000 ALTER TABLE `REQUIRED_ACTION_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `REQUIRED_ACTION_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REQUIRED_ACTION_PROVIDER`
--

DROP TABLE IF EXISTS `REQUIRED_ACTION_PROVIDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REQUIRED_ACTION_PROVIDER` (
  `ID` varchar(36) NOT NULL,
  `ALIAS` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) DEFAULT NULL,
  `ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `DEFAULT_ACTION` bit(1) NOT NULL DEFAULT b'0',
  `PROVIDER_ID` varchar(255) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_REQ_ACT_PROV_REALM` (`REALM_ID`),
  CONSTRAINT `FK_REQ_ACT_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REQUIRED_ACTION_PROVIDER`
--

LOCK TABLES `REQUIRED_ACTION_PROVIDER` WRITE;
/*!40000 ALTER TABLE `REQUIRED_ACTION_PROVIDER` DISABLE KEYS */;
INSERT INTO `REQUIRED_ACTION_PROVIDER` VALUES ('241da5b0-bd62-4a70-90ce-49f45607a107','terms_and_privacy','Deltares Terms and Privacy','liferay-portal','','','terms_and_privacy',30),('485901e0-01eb-4c2d-8b74-af15f974b83c','UPDATE_PASSWORD','Update Password','liferay-portal','','\0','UPDATE_PASSWORD',50),('512d0e70-db63-49cf-9a9e-09144b61d9dd','UPDATE_PASSWORD','Update Password','master','','\0','UPDATE_PASSWORD',30),('5514949e-5075-4176-be1d-dacff9601b45','CONFIGURE_TOTP','Configure OTP','master','','\0','CONFIGURE_TOTP',10),('56b7018f-b718-4561-ba27-208cb622f145','VERIFY_EMAIL','Verify Email','liferay-portal','\0','\0','VERIFY_EMAIL',40),('5f6e4387-904f-43bc-a572-b3fd12bd5c51','VERIFY_EMAIL','Verify Email','master','','\0','VERIFY_EMAIL',50),('6e8498e3-010f-45d3-8141-2b6fffffc534','UPDATE_PROFILE','Update Profile','master','','\0','UPDATE_PROFILE',40),('8317e61b-5bde-4909-a3bf-f26afc6aa1e7','login_stats_action','Record Login Statistics Action','liferay-portal','','\0','login_stats_action',52),('901fef9b-b236-4700-9da0-ee347781adca','UPDATE_PROFILE','Update Profile','liferay-portal','','\0','UPDATE_PROFILE',51),('94655bf1-ff40-4bdf-a5fb-4d0a027c1c81','terms_and_conditions','Terms and Conditions','liferay-portal','\0','','terms_and_conditions',20),('9ed71a13-4e9e-41de-a504-f2975a52e241','CONFIGURE_TOTP','Configure OTP','liferay-portal','','\0','CONFIGURE_TOTP',10),('e4598063-fa54-46a3-b115-4bee55ac0d94','terms_and_conditions','Terms and Conditions','master','\0','\0','terms_and_conditions',20);
/*!40000 ALTER TABLE `REQUIRED_ACTION_PROVIDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_ATTRIBUTE`
--

DROP TABLE IF EXISTS `RESOURCE_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_ATTRIBUTE` (
  `ID` varchar(36) NOT NULL DEFAULT 'sybase-needs-something-here',
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `RESOURCE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_5HRM2VLF9QL5FU022KQEPOVBR` (`RESOURCE_ID`),
  CONSTRAINT `FK_5HRM2VLF9QL5FU022KQEPOVBR` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_ATTRIBUTE`
--

LOCK TABLES `RESOURCE_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `RESOURCE_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_POLICY`
--

DROP TABLE IF EXISTS `RESOURCE_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_POLICY` (
  `RESOURCE_ID` varchar(36) NOT NULL,
  `POLICY_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`RESOURCE_ID`,`POLICY_ID`),
  KEY `IDX_RES_POLICY_POLICY` (`POLICY_ID`),
  CONSTRAINT `FK_FRSRPOS53XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`),
  CONSTRAINT `FK_FRSRPP213XCX4WNKOG82SSRFY` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_POLICY`
--

LOCK TABLES `RESOURCE_POLICY` WRITE;
/*!40000 ALTER TABLE `RESOURCE_POLICY` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_POLICY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SCOPE`
--

DROP TABLE IF EXISTS `RESOURCE_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SCOPE` (
  `RESOURCE_ID` varchar(36) NOT NULL,
  `SCOPE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`RESOURCE_ID`,`SCOPE_ID`),
  KEY `IDX_RES_SCOPE_SCOPE` (`SCOPE_ID`),
  CONSTRAINT `FK_FRSRPOS13XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`),
  CONSTRAINT `FK_FRSRPS213XCX4WNKOG82SSRFY` FOREIGN KEY (`SCOPE_ID`) REFERENCES `RESOURCE_SERVER_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SCOPE`
--

LOCK TABLES `RESOURCE_SCOPE` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SCOPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER` (
  `ID` varchar(36) NOT NULL,
  `ALLOW_RS_REMOTE_MGMT` bit(1) NOT NULL DEFAULT b'0',
  `POLICY_ENFORCE_MODE` varchar(15) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER`
--

LOCK TABLES `RESOURCE_SERVER` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER` DISABLE KEYS */;
INSERT INTO `RESOURCE_SERVER` VALUES ('07a7d9af-fe25-4479-8d74-9ddc0baeb35e','','0'),('27a25fc2-29bd-4afd-a135-a5b2b8be12b8','','0'),('67d6d3e3-38a7-4562-aac7-03832d713532','','0'),('7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6','','0'),('d377a198-34a9-46a2-9564-f468fb84e9b8','','0');
/*!40000 ALTER TABLE `RESOURCE_SERVER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER_PERM_TICKET`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER_PERM_TICKET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER_PERM_TICKET` (
  `ID` varchar(36) NOT NULL,
  `OWNER` varchar(36) NOT NULL,
  `REQUESTER` varchar(36) NOT NULL,
  `CREATED_TIMESTAMP` bigint(20) NOT NULL,
  `GRANTED_TIMESTAMP` bigint(20) DEFAULT NULL,
  `RESOURCE_ID` varchar(36) NOT NULL,
  `SCOPE_ID` varchar(36) DEFAULT NULL,
  `RESOURCE_SERVER_ID` varchar(36) NOT NULL,
  `POLICY_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_FRSR6T700S9V50BU18WS5PMT` (`OWNER`,`REQUESTER`,`RESOURCE_SERVER_ID`,`RESOURCE_ID`,`SCOPE_ID`),
  KEY `FK_FRSRHO213XCX4WNKOG82SSPMT` (`RESOURCE_SERVER_ID`),
  KEY `FK_FRSRHO213XCX4WNKOG83SSPMT` (`RESOURCE_ID`),
  KEY `FK_FRSRHO213XCX4WNKOG84SSPMT` (`SCOPE_ID`),
  KEY `FK_FRSRPO2128CX4WNKOG82SSRFY` (`POLICY_ID`),
  CONSTRAINT `FK_FRSRHO213XCX4WNKOG82SSPMT` FOREIGN KEY (`RESOURCE_SERVER_ID`) REFERENCES `RESOURCE_SERVER` (`ID`),
  CONSTRAINT `FK_FRSRHO213XCX4WNKOG83SSPMT` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`),
  CONSTRAINT `FK_FRSRHO213XCX4WNKOG84SSPMT` FOREIGN KEY (`SCOPE_ID`) REFERENCES `RESOURCE_SERVER_SCOPE` (`ID`),
  CONSTRAINT `FK_FRSRPO2128CX4WNKOG82SSRFY` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER_PERM_TICKET`
--

LOCK TABLES `RESOURCE_SERVER_PERM_TICKET` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER_PERM_TICKET` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_SERVER_PERM_TICKET` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER_POLICY`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER_POLICY` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) NOT NULL,
  `DECISION_STRATEGY` varchar(20) DEFAULT NULL,
  `LOGIC` varchar(20) DEFAULT NULL,
  `RESOURCE_SERVER_ID` varchar(36) DEFAULT NULL,
  `OWNER` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_FRSRPT700S9V50BU18WS5HA6` (`NAME`,`RESOURCE_SERVER_ID`),
  KEY `IDX_RES_SERV_POL_RES_SERV` (`RESOURCE_SERVER_ID`),
  CONSTRAINT `FK_FRSRPO213XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_SERVER_ID`) REFERENCES `RESOURCE_SERVER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER_POLICY`
--

LOCK TABLES `RESOURCE_SERVER_POLICY` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER_POLICY` DISABLE KEYS */;
INSERT INTO `RESOURCE_SERVER_POLICY` VALUES ('1d1be5b2-368b-4f73-91ef-f5fff682aa73','Default Permission','A permission that applies to the default resource type','resource','1','0','67d6d3e3-38a7-4562-aac7-03832d713532',NULL),('464a4f94-3ebe-4b25-a773-43004e2e73d6','Default Permission','A permission that applies to the default resource type','resource','1','0','07a7d9af-fe25-4479-8d74-9ddc0baeb35e',NULL),('7e75f663-76ca-42cc-92ce-732e3caf409a','Default Policy','A policy that grants access only for users within this realm','js','0','0','27a25fc2-29bd-4afd-a135-a5b2b8be12b8',NULL),('8119b5a3-d21e-4c4b-bac8-134c495e0a77','Default Policy','A policy that grants access only for users within this realm','js','0','0','07a7d9af-fe25-4479-8d74-9ddc0baeb35e',NULL),('c57f7c57-d56d-44d5-bbb2-ebf557f24688','Default Permission','A permission that applies to the default resource type','resource','1','0','d377a198-34a9-46a2-9564-f468fb84e9b8',NULL),('ccef7efc-fb1f-4a55-8429-bac1e0261314','Default Policy','A policy that grants access only for users within this realm','js','0','0','d377a198-34a9-46a2-9564-f468fb84e9b8',NULL),('d66a85ba-62e8-49ed-b0cc-29ab865a86e5','Default Permission','A permission that applies to the default resource type','resource','1','0','27a25fc2-29bd-4afd-a135-a5b2b8be12b8',NULL),('eca0e811-43db-41f8-a3b9-5d37f235e1b3','Default Policy','A policy that grants access only for users within this realm','js','0','0','67d6d3e3-38a7-4562-aac7-03832d713532',NULL);
/*!40000 ALTER TABLE `RESOURCE_SERVER_POLICY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER_RESOURCE`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER_RESOURCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER_RESOURCE` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `ICON_URI` varchar(255) DEFAULT NULL,
  `OWNER` varchar(36) NOT NULL,
  `RESOURCE_SERVER_ID` varchar(36) DEFAULT NULL,
  `OWNER_MANAGED_ACCESS` bit(1) NOT NULL DEFAULT b'0',
  `DISPLAY_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_FRSR6T700S9V50BU18WS5HA6` (`NAME`,`OWNER`,`RESOURCE_SERVER_ID`),
  KEY `IDX_RES_SRV_RES_RES_SRV` (`RESOURCE_SERVER_ID`),
  CONSTRAINT `FK_FRSRHO213XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_SERVER_ID`) REFERENCES `RESOURCE_SERVER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER_RESOURCE`
--

LOCK TABLES `RESOURCE_SERVER_RESOURCE` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER_RESOURCE` DISABLE KEYS */;
INSERT INTO `RESOURCE_SERVER_RESOURCE` VALUES ('52b198f6-673c-4af6-9237-3aaf1a6aebb6','Default Resource','urn:oss-account:resources:default',NULL,'d377a198-34a9-46a2-9564-f468fb84e9b8','d377a198-34a9-46a2-9564-f468fb84e9b8','\0',NULL),('6911acb5-9cf5-4d38-a808-8e79701c2371','Default Resource','urn:api-user:resources:default',NULL,'67d6d3e3-38a7-4562-aac7-03832d713532','67d6d3e3-38a7-4562-aac7-03832d713532','\0',NULL),('6d6d1cf1-4d22-491d-8357-75cee19b88f5','Default Resource','urn:api-viewer:resources:default',NULL,'27a25fc2-29bd-4afd-a135-a5b2b8be12b8','27a25fc2-29bd-4afd-a135-a5b2b8be12b8','\0',NULL),('d2571a2f-7378-40ee-888c-6b8238ba6f6e','Default Resource','urn:api-admin:resources:default',NULL,'07a7d9af-fe25-4479-8d74-9ddc0baeb35e','07a7d9af-fe25-4479-8d74-9ddc0baeb35e','\0',NULL);
/*!40000 ALTER TABLE `RESOURCE_SERVER_RESOURCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER_SCOPE`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER_SCOPE` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `ICON_URI` varchar(255) DEFAULT NULL,
  `RESOURCE_SERVER_ID` varchar(36) DEFAULT NULL,
  `DISPLAY_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_FRSRST700S9V50BU18WS5HA6` (`NAME`,`RESOURCE_SERVER_ID`),
  KEY `IDX_RES_SRV_SCOPE_RES_SRV` (`RESOURCE_SERVER_ID`),
  CONSTRAINT `FK_FRSRSO213XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_SERVER_ID`) REFERENCES `RESOURCE_SERVER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER_SCOPE`
--

LOCK TABLES `RESOURCE_SERVER_SCOPE` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER_SCOPE` DISABLE KEYS */;
INSERT INTO `RESOURCE_SERVER_SCOPE` VALUES ('112db9aa-16ff-49d1-be05-5edfd0cd4b97','map-role-composite',NULL,'7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('1fd3b2dd-6b0a-4c10-b2c6-5ef05598fc86','map-role',NULL,'7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('742e7c8a-0523-4e24-a018-171f7171fd4f','map-role-client-scope',NULL,'7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL),('c8874f85-9eb3-4aac-98f3-dd17428a1611','token-exchange',NULL,'7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',NULL);
/*!40000 ALTER TABLE `RESOURCE_SERVER_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_URIS`
--

DROP TABLE IF EXISTS `RESOURCE_URIS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_URIS` (
  `RESOURCE_ID` varchar(36) NOT NULL,
  `VALUE` varchar(255) NOT NULL,
  KEY `FK_RESOURCE_SERVER_URIS` (`RESOURCE_ID`),
  CONSTRAINT `FK_RESOURCE_SERVER_URIS` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_URIS`
--

LOCK TABLES `RESOURCE_URIS` WRITE;
/*!40000 ALTER TABLE `RESOURCE_URIS` DISABLE KEYS */;
INSERT INTO `RESOURCE_URIS` VALUES ('6911acb5-9cf5-4d38-a808-8e79701c2371','/*'),('d2571a2f-7378-40ee-888c-6b8238ba6f6e','/*'),('6d6d1cf1-4d22-491d-8357-75cee19b88f5','/*'),('52b198f6-673c-4af6-9237-3aaf1a6aebb6','/*');
/*!40000 ALTER TABLE `RESOURCE_URIS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ROLE_ATTRIBUTE`
--

DROP TABLE IF EXISTS `ROLE_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ROLE_ATTRIBUTE` (
  `ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_ROLE_ATTRIBUTE` (`ROLE_ID`),
  CONSTRAINT `FK_ROLE_ATTRIBUTE_ID` FOREIGN KEY (`ROLE_ID`) REFERENCES `KEYCLOAK_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ROLE_ATTRIBUTE`
--

LOCK TABLES `ROLE_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `ROLE_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ROLE_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCOPE_MAPPING`
--

DROP TABLE IF EXISTS `SCOPE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCOPE_MAPPING` (
  `CLIENT_ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`CLIENT_ID`,`ROLE_ID`),
  KEY `IDX_SCOPE_MAPPING_ROLE` (`ROLE_ID`),
  CONSTRAINT `FK_OUSE064PLMLR732LXJCN1Q5F1` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`),
  CONSTRAINT `FK_P3RH9GRKU11KQFRS4FLTT7RNQ` FOREIGN KEY (`ROLE_ID`) REFERENCES `KEYCLOAK_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCOPE_MAPPING`
--

LOCK TABLES `SCOPE_MAPPING` WRITE;
/*!40000 ALTER TABLE `SCOPE_MAPPING` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCOPE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCOPE_POLICY`
--

DROP TABLE IF EXISTS `SCOPE_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCOPE_POLICY` (
  `SCOPE_ID` varchar(36) NOT NULL,
  `POLICY_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`SCOPE_ID`,`POLICY_ID`),
  KEY `IDX_SCOPE_POLICY_POLICY` (`POLICY_ID`),
  CONSTRAINT `FK_FRSRASP13XCX4WNKOG82SSRFY` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`),
  CONSTRAINT `FK_FRSRPASS3XCX4WNKOG82SSRFY` FOREIGN KEY (`SCOPE_ID`) REFERENCES `RESOURCE_SERVER_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCOPE_POLICY`
--

LOCK TABLES `SCOPE_POLICY` WRITE;
/*!40000 ALTER TABLE `SCOPE_POLICY` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCOPE_POLICY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERNAME_LOGIN_FAILURE`
--

DROP TABLE IF EXISTS `USERNAME_LOGIN_FAILURE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERNAME_LOGIN_FAILURE` (
  `REALM_ID` varchar(36) NOT NULL,
  `USERNAME` varchar(255) NOT NULL,
  `FAILED_LOGIN_NOT_BEFORE` int(11) DEFAULT NULL,
  `LAST_FAILURE` bigint(20) DEFAULT NULL,
  `LAST_IP_FAILURE` varchar(255) DEFAULT NULL,
  `NUM_FAILURES` int(11) DEFAULT NULL,
  PRIMARY KEY (`REALM_ID`,`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERNAME_LOGIN_FAILURE`
--

LOCK TABLES `USERNAME_LOGIN_FAILURE` WRITE;
/*!40000 ALTER TABLE `USERNAME_LOGIN_FAILURE` DISABLE KEYS */;
/*!40000 ALTER TABLE `USERNAME_LOGIN_FAILURE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_ATTRIBUTE`
--

DROP TABLE IF EXISTS `USER_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_ATTRIBUTE` (
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `ID` varchar(36) NOT NULL DEFAULT 'sybase-needs-something-here',
  PRIMARY KEY (`ID`),
  KEY `IDX_USER_ATTRIBUTE` (`USER_ID`),
  CONSTRAINT `FK_5HRM2VLF9QL5FU043KQEPOVBR` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_ATTRIBUTE`
--

LOCK TABLES `USER_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `USER_ATTRIBUTE` DISABLE KEYS */;
INSERT INTO `USER_ATTRIBUTE` VALUES ('login.recent-login-date','2021-05-18T13:40:11.433','fc7b51a0-662e-44ca-8621-5921972d159c','013687f0-b154-40d4-b9c6-cd73c3cb8c2b'),('login.login-count.localhost','11','fc7b51a0-662e-44ca-8621-5921972d159c','02f233d7-ce0e-448f-91cf-d0916f0da552'),('login.first-login-date.localhost.guest','2021-04-21T09:59:49.067','fc7b51a0-662e-44ca-8621-5921972d159c','09d51c5b-9cfe-4d75-a460-699441b3244f'),('locale','en','fc7b51a0-662e-44ca-8621-5921972d159c','0df680f2-ebe9-4fb9-80c8-e7cc0c8c77bd'),('login.login-count.guest','1','fc7b51a0-662e-44ca-8621-5921972d159c','0ef039d0-636a-4f12-9e7a-42f96cd5f15f'),('login.first-login-date.liferay-docker2-saml','2021-05-14T11:18:34.040','fc7b51a0-662e-44ca-8621-5921972d159c','130fddb2-2b8f-4e03-9a71-d08414b05387'),('org_name','cz','fc7b51a0-662e-44ca-8621-5921972d159c','14d8c541-9f20-4db5-a978-e815ef2f093a'),('login.recent-login-date.localhost.guest','2021-05-19T09:35:54.600','fc7b51a0-662e-44ca-8621-5921972d159c','191397bb-5751-4b9f-94e7-66ac9da66eeb'),('login.first-login-date.guest','2021-05-12T09:25:44.199','fc7b51a0-662e-44ca-8621-5921972d159c','19a97045-e254-46a7-b55e-7837751c713d'),('login.login-count.your.docker.com.program','2','fc7b51a0-662e-44ca-8621-5921972d159c','1caebd7e-f578-449f-b41c-59d393407fc3'),('login.first-login-date.your.docker.com.my-program','2021-05-19T08:15:03.083','fc7b51a0-662e-44ca-8621-5921972d159c','1e4ca551-d466-4e98-8bb7-4fa09e9bd3b0'),('login.first-login-date.liferay.program','2021-05-19T09:15:40.910','fc7b51a0-662e-44ca-8621-5921972d159c','1ff0ce00-87e5-433a-a348-8dba138222cf'),('login.first-login-date','2021-05-14T08:51:01.945','fc7b51a0-662e-44ca-8621-5921972d159c','2161ea46-e6ec-4b4b-b3fc-cacfed8b71cc'),('login.first-login-date.localhost.admin-console','2021-05-06T07:29:33.312','fc7b51a0-662e-44ca-8621-5921972d159c','2d9b267e-7227-47d6-9000-754b30566c56'),('login.recent-login-date.account','2021-05-18T13:38:20.858','fc7b51a0-662e-44ca-8621-5921972d159c','30c5499c-6087-4b26-a690-5829444f1446'),('login.login-count.liferay-docker2-saml','33','fc7b51a0-662e-44ca-8621-5921972d159c','37513649-7449-49c7-a30c-d02433949bec'),('login.first-login-date.your.docker.com.registration-form','2021-05-17T13:20:26.959','fc7b51a0-662e-44ca-8621-5921972d159c','37920a54-6c75-43e6-9f81-b41002ef519c'),('login.first-login-date.your.docker.com.oss-admin-console','2021-05-14T13:13:28.924','fc7b51a0-662e-44ca-8621-5921972d159c','3d653d50-17d3-4554-968d-9865e25e05a2'),('login.first-login-date.your.docker.com.home','2021-05-14T09:08:02.593','fc7b51a0-662e-44ca-8621-5921972d159c','3fc9e77f-6a5e-4345-b01b-baffacd03152'),('login.login-count.liferay-docker-saml','54','fc7b51a0-662e-44ca-8621-5921972d159c','40f612ef-51c4-4d73-9c0b-cbc3662ca44a'),('login.first-login-date.my.docker.com','2021-05-14T08:47:29.812','fc7b51a0-662e-44ca-8621-5921972d159c','469670e7-3444-4c98-9b16-2627ed1b31de'),('login.login-count.localhost.admin-console','6','fc7b51a0-662e-44ca-8621-5921972d159c','4971b4ad-d4f7-4a56-88b0-745cf9e6d0d2'),('login.recent-login-date.your.docker.com','2021-05-18T12:15:04.321','fc7b51a0-662e-44ca-8621-5921972d159c','49d8e912-aa3e-4c58-9d6c-c2e8ef58589b'),('login.recent-login-date.your.docker.com.program','2021-05-19T08:11:39.800','fc7b51a0-662e-44ca-8621-5921972d159c','4be052c7-d2da-4d96-9953-6db1ea874112'),('login.login-count.liferay.program','3','fc7b51a0-662e-44ca-8621-5921972d159c','50298bd7-ea86-4cc4-ba1f-6b1b0ca404a2'),('login.login-count.localhost.guest','37','fc7b51a0-662e-44ca-8621-5921972d159c','5d5a4922-d538-4d98-aece-6a9e47d963d7'),('org_country','afghanistan','fc7b51a0-662e-44ca-8621-5921972d159c','5e7209a3-e470-4dfd-b1a6-dde5e449203f'),('login.recent-login-date.localhost.admin-console','2021-05-10T15:22:47.817','fc7b51a0-662e-44ca-8621-5921972d159c','6727bec8-e422-4712-9b6f-b75496c619c1'),('login.login-count','25','fc7b51a0-662e-44ca-8621-5921972d159c','67540625-37fb-4f5e-b876-0684f6c73242'),('org_vat','b','fc7b51a0-662e-44ca-8621-5921972d159c','67c256ca-d80b-4c90-9133-af003a75cfda'),('login.login-count.account','6','fc7b51a0-662e-44ca-8621-5921972d159c','695ca46b-a299-4010-b389-5b3a2d0d7386'),('login.login-count.my.docker.com.home','18','fc7b51a0-662e-44ca-8621-5921972d159c','6caca04f-19e1-488c-a9c0-6f731b341cc5'),('login.recent-login-date.your.docker.com.admin-console','2021-05-18T13:40:11.944','fc7b51a0-662e-44ca-8621-5921972d159c','7177a17e-4b4f-48a7-b37b-e51e8442b52f'),('login.recent-login-date.localhost','2021-05-14T08:58:27.156','fc7b51a0-662e-44ca-8621-5921972d159c','7b4b6ca2-b02b-4a4b-9d9b-29ea6da48a0c'),('login.recent-login-date.liferay-docker-saml','2021-05-19T09:35:54.196','fc7b51a0-662e-44ca-8621-5921972d159c','7fb64731-d60b-40c7-bbf8-804c8d9829a1'),('login.login-count.your.docker.com.admin-console','1','fc7b51a0-662e-44ca-8621-5921972d159c','826440bf-e931-4134-8082-b9639b596b99'),('login.first-login-date.account','2021-05-11T14:35:50.443','fc7b51a0-662e-44ca-8621-5921972d159c','82f1c272-2959-469f-98f2-0b2414d25588'),('login.recent-login-date.your.docker.com.my-program','2021-05-19T08:15:03.083','fc7b51a0-662e-44ca-8621-5921972d159c','89e49612-7c8d-489f-9785-4f02dbed1e94'),('org_preferred_payment','bankTransfer','fc7b51a0-662e-44ca-8621-5921972d159c','948e0f91-3f4b-4540-bc96-4948403d71f2'),('login.login-count.your.docker.com.home','31','fc7b51a0-662e-44ca-8621-5921972d159c','97cc82c0-8787-43a8-b19e-b0c2687cdb81'),('login.recent-login-date.guest','2021-05-12T09:25:44.201','fc7b51a0-662e-44ca-8621-5921972d159c','983cb420-67c7-4b4a-8de6-8c95b74cf46f'),('login.recent-login-date.your.docker.com.home','2021-05-18T11:15:08.346','fc7b51a0-662e-44ca-8621-5921972d159c','993d07d4-909e-4b1f-93ed-219185c07cf7'),('login.recent-login-date.my.docker.com.home','2021-05-18T09:29:13.277','fc7b51a0-662e-44ca-8621-5921972d159c','9eee87f1-811f-4075-8c61-c9c98b77f5d2'),('login.recent-login-date.localhost.program','2021-04-23T09:54:27.726','fc7b51a0-662e-44ca-8621-5921972d159c','a66f05a6-2976-4165-9028-3292031c2de9'),('login.first-login-date.localhost.program','2021-04-23T09:54:27.726','fc7b51a0-662e-44ca-8621-5921972d159c','a85f544f-e801-4a80-8741-6c9b640c5c46'),('login.first-login-date.my.docker.com.home','2021-05-14T08:50:24.964','fc7b51a0-662e-44ca-8621-5921972d159c','abd523f9-43f8-4402-9405-42ae30186199'),('login.login-count.my.docker.com','27','fc7b51a0-662e-44ca-8621-5921972d159c','acf09f01-4e8c-46fa-bb18-0da095eb8be7'),('login.first-login-date.your.docker.com','2021-05-14T09:02:02.269','fc7b51a0-662e-44ca-8621-5921972d159c','b27df73a-f5b2-4a26-9315-5b7f1fd10325'),('login.recent-login-date.liferay.program','2021-05-19T09:18:42.728','fc7b51a0-662e-44ca-8621-5921972d159c','b8522647-5a67-4bc1-a98f-3579f984d965'),('login.login-count.localhost.program','1','fc7b51a0-662e-44ca-8621-5921972d159c','c7e5cfba-8182-445e-afca-525ef0d5ea9f'),('login.first-login-date.your.docker.com.program','2021-05-18T13:00:20.238','fc7b51a0-662e-44ca-8621-5921972d159c','ca7983d8-5c40-4a68-9c5b-701f745d146d'),('login.first-login-date.your.docker.com.admin-console','2021-05-18T13:40:11.944','fc7b51a0-662e-44ca-8621-5921972d159c','ceb477e3-2174-4b5c-9a4e-51e88ba63bdf'),('login.first-login-date.liferay-docker-saml','2021-04-21T09:59:48.905','fc7b51a0-662e-44ca-8621-5921972d159c','d004d44f-0448-4351-83d3-1fe4f2464ec3'),('org_city','zc','fc7b51a0-662e-44ca-8621-5921972d159c','d0653c87-5d93-4ec0-b26d-5c8f21827a12'),('login.first-login-date.localhost','2021-04-23T07:58:02.346','fc7b51a0-662e-44ca-8621-5921972d159c','d6fdd69c-3747-4c1d-9895-d396c31aff18'),('login.recent-login-date.my.docker.com','2021-05-14T13:56:08.208','fc7b51a0-662e-44ca-8621-5921972d159c','d8c32b81-9e29-4f05-83b4-f92b8377ad15'),('org_postal','zc','fc7b51a0-662e-44ca-8621-5921972d159c','d99ac113-3203-4311-aa66-2c08e43c10bb'),('login.recent-login-date.your.docker.com.oss-admin-console','2021-05-14T13:13:28.924','fc7b51a0-662e-44ca-8621-5921972d159c','da30810a-1889-4ae4-8560-9401f094cbf4'),('login.login-count.your.docker.com.oss-admin-console','1','fc7b51a0-662e-44ca-8621-5921972d159c','dba527d2-930f-4eec-ad15-29f334f9ae04'),('login.recent-login-date.liferay-docker2-saml','2021-05-19T09:18:42.522','fc7b51a0-662e-44ca-8621-5921972d159c','dc1262b8-fe01-4bfa-919f-20fe83e85275'),('login.login-count.your.docker.com','17','fc7b51a0-662e-44ca-8621-5921972d159c','e267f791-b526-4642-b22d-3194b9184878'),('login.login-count.your.docker.com.my-program','1','fc7b51a0-662e-44ca-8621-5921972d159c','ecd4c14f-9564-4259-b99d-0dacf8f6ed3b'),('login.recent-login-date.your.docker.com.registration-form','2021-05-18T09:20:04.578','fc7b51a0-662e-44ca-8621-5921972d159c','ef48f7ce-da1f-4197-baea-0534d690a226'),('login.login-count.your.docker.com.registration-form','2','fc7b51a0-662e-44ca-8621-5921972d159c','f34da54a-30b7-47ed-a77e-46a16dfe8cae'),('pay_reference','a','fc7b51a0-662e-44ca-8621-5921972d159c','fdc04fa8-9914-41b8-9fb3-ab8b4efa1065'),('org_address','cz','fc7b51a0-662e-44ca-8621-5921972d159c','ff69b89f-42c8-4f3f-a5f8-9745bbab4098');
/*!40000 ALTER TABLE `USER_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_AVATAR`
--

DROP TABLE IF EXISTS `USER_AVATAR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_AVATAR` (
  `ID` varchar(36) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `AVATAR` blob NOT NULL,
  `CONTENT_TYPE` varchar(12) NOT NULL DEFAULT 'image/jpeg',
  PRIMARY KEY (`ID`),
  KEY `FK_USER_AVATAR_USER_ENTITY` (`USER_ID`),
  KEY `FK_USER_AVATAR_REALM_ENTITY` (`REALM_ID`),
  CONSTRAINT `FK_USER_AVATAR_REALM_ENTITY` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `FK_USER_AVATAR_USER_ENTITY` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_AVATAR`
--

LOCK TABLES `USER_AVATAR` WRITE;
/*!40000 ALTER TABLE `USER_AVATAR` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_AVATAR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_CONSENT`
--

DROP TABLE IF EXISTS `USER_CONSENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_CONSENT` (
  `ID` varchar(36) NOT NULL,
  `CLIENT_ID` varchar(36) DEFAULT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `CREATED_DATE` bigint(20) DEFAULT NULL,
  `LAST_UPDATED_DATE` bigint(20) DEFAULT NULL,
  `CLIENT_STORAGE_PROVIDER` varchar(36) DEFAULT NULL,
  `EXTERNAL_CLIENT_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_JKUWUVD56ONTGSUHOGM8UEWRT` (`CLIENT_ID`,`CLIENT_STORAGE_PROVIDER`,`EXTERNAL_CLIENT_ID`,`USER_ID`),
  KEY `IDX_USER_CONSENT` (`USER_ID`),
  CONSTRAINT `FK_GRNTCSNT_USER` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_CONSENT`
--

LOCK TABLES `USER_CONSENT` WRITE;
/*!40000 ALTER TABLE `USER_CONSENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_CONSENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_CONSENT_CLIENT_SCOPE`
--

DROP TABLE IF EXISTS `USER_CONSENT_CLIENT_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_CONSENT_CLIENT_SCOPE` (
  `USER_CONSENT_ID` varchar(36) NOT NULL,
  `SCOPE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`USER_CONSENT_ID`,`SCOPE_ID`),
  KEY `IDX_USCONSENT_CLSCOPE` (`USER_CONSENT_ID`),
  CONSTRAINT `FK_GRNTCSNT_CLSC_USC` FOREIGN KEY (`USER_CONSENT_ID`) REFERENCES `USER_CONSENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_CONSENT_CLIENT_SCOPE`
--

LOCK TABLES `USER_CONSENT_CLIENT_SCOPE` WRITE;
/*!40000 ALTER TABLE `USER_CONSENT_CLIENT_SCOPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_CONSENT_CLIENT_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_ENTITY`
--

DROP TABLE IF EXISTS `USER_ENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_ENTITY` (
  `ID` varchar(36) NOT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `EMAIL_CONSTRAINT` varchar(255) DEFAULT NULL,
  `EMAIL_VERIFIED` bit(1) NOT NULL DEFAULT b'0',
  `ENABLED` bit(1) NOT NULL DEFAULT b'0',
  `FEDERATION_LINK` varchar(255) DEFAULT NULL,
  `FIRST_NAME` varchar(255) DEFAULT NULL,
  `LAST_NAME` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `CREATED_TIMESTAMP` bigint(20) DEFAULT NULL,
  `SERVICE_ACCOUNT_CLIENT_LINK` varchar(36) DEFAULT NULL,
  `NOT_BEFORE` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_DYKN684SL8UP1CRFEI6ECKHD7` (`REALM_ID`,`EMAIL_CONSTRAINT`),
  UNIQUE KEY `UK_RU8TT6T700S9V50BU18WS5HA6` (`REALM_ID`,`USERNAME`),
  KEY `IDX_USER_EMAIL` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_ENTITY`
--

LOCK TABLES `USER_ENTITY` WRITE;
/*!40000 ALTER TABLE `USER_ENTITY` DISABLE KEYS */;
INSERT INTO `USER_ENTITY` VALUES ('04a65cd9-859e-492e-a312-564d9d1505a7','service-account-oss-accounts@placeholder.org','service-account-oss-accounts@placeholder.org','\0','',NULL,NULL,NULL,'liferay-portal','service-account-oss-accounts',1617881981253,'d377a198-34a9-46a2-9564-f468fb84e9b8',1620990527),('2bff2ffa-ebf3-4edf-9c26-98e1eeb6ba7a','service-account-realm-management@placeholder.org','service-account-realm-management@placeholder.org','\0','',NULL,NULL,NULL,'liferay-portal','service-account-realm-management',1617881981292,'7b08eb13-2fdb-4b3e-8ff6-9c09047be2e6',0),('306665eb-fd64-4acb-89be-2a89714745b9','service-account-user-api@placeholder.org','service-account-user-api@placeholder.org','\0','',NULL,NULL,NULL,'liferay-portal','service-account-user-api',1617881981109,'67d6d3e3-38a7-4562-aac7-03832d713532',0),('45bf2730-c155-428d-8d2b-6e9a45c10cfa','service-account-admin-api-admin@placeholder.org','service-account-admin-api-admin@placeholder.org','\0','',NULL,NULL,NULL,'liferay-portal','service-account-admin-api-admin',1617881981182,'07a7d9af-fe25-4479-8d74-9ddc0baeb35e',0),('6360111f-0e87-4f47-873a-72c07714a985','erik.derooij@deltares.nl','erik.derooij@deltares.nl','\0','',NULL,NULL,NULL,'master','admin',1617881981454,NULL,0),('98c7c0ff-a62f-417f-b9b4-8a5cffcb0855','service-account-admin-api-viewer@placeholder.org','service-account-admin-api-viewer@placeholder.org','\0','',NULL,NULL,NULL,'liferay-portal','service-account-admin-api-viewer',1617881981220,'27a25fc2-29bd-4afd-a135-a5b2b8be12b8',0),('fc7b51a0-662e-44ca-8621-5921972d159c','test@liferay.com','test@liferay.com','','',NULL,'Test','User','liferay-portal','test',1617882322772,NULL,1620743754);
/*!40000 ALTER TABLE `USER_ENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_FEDERATION_CONFIG`
--

DROP TABLE IF EXISTS `USER_FEDERATION_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_FEDERATION_CONFIG` (
  `USER_FEDERATION_PROVIDER_ID` varchar(36) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`USER_FEDERATION_PROVIDER_ID`,`NAME`),
  CONSTRAINT `FK_T13HPU1J94R2EBPEKR39X5EU5` FOREIGN KEY (`USER_FEDERATION_PROVIDER_ID`) REFERENCES `USER_FEDERATION_PROVIDER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_FEDERATION_CONFIG`
--

LOCK TABLES `USER_FEDERATION_CONFIG` WRITE;
/*!40000 ALTER TABLE `USER_FEDERATION_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_FEDERATION_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_FEDERATION_MAPPER`
--

DROP TABLE IF EXISTS `USER_FEDERATION_MAPPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_FEDERATION_MAPPER` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `FEDERATION_PROVIDER_ID` varchar(36) NOT NULL,
  `FEDERATION_MAPPER_TYPE` varchar(255) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_USR_FED_MAP_FED_PRV` (`FEDERATION_PROVIDER_ID`),
  KEY `IDX_USR_FED_MAP_REALM` (`REALM_ID`),
  CONSTRAINT `FK_FEDMAPPERPM_FEDPRV` FOREIGN KEY (`FEDERATION_PROVIDER_ID`) REFERENCES `USER_FEDERATION_PROVIDER` (`ID`),
  CONSTRAINT `FK_FEDMAPPERPM_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_FEDERATION_MAPPER`
--

LOCK TABLES `USER_FEDERATION_MAPPER` WRITE;
/*!40000 ALTER TABLE `USER_FEDERATION_MAPPER` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_FEDERATION_MAPPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_FEDERATION_MAPPER_CONFIG`
--

DROP TABLE IF EXISTS `USER_FEDERATION_MAPPER_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_FEDERATION_MAPPER_CONFIG` (
  `USER_FEDERATION_MAPPER_ID` varchar(36) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`USER_FEDERATION_MAPPER_ID`,`NAME`),
  CONSTRAINT `FK_FEDMAPPER_CFG` FOREIGN KEY (`USER_FEDERATION_MAPPER_ID`) REFERENCES `USER_FEDERATION_MAPPER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_FEDERATION_MAPPER_CONFIG`
--

LOCK TABLES `USER_FEDERATION_MAPPER_CONFIG` WRITE;
/*!40000 ALTER TABLE `USER_FEDERATION_MAPPER_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_FEDERATION_MAPPER_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_FEDERATION_PROVIDER`
--

DROP TABLE IF EXISTS `USER_FEDERATION_PROVIDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_FEDERATION_PROVIDER` (
  `ID` varchar(36) NOT NULL,
  `CHANGED_SYNC_PERIOD` int(11) DEFAULT NULL,
  `DISPLAY_NAME` varchar(255) DEFAULT NULL,
  `FULL_SYNC_PERIOD` int(11) DEFAULT NULL,
  `LAST_SYNC` int(11) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `PROVIDER_NAME` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_USR_FED_PRV_REALM` (`REALM_ID`),
  CONSTRAINT `FK_1FJ32F6PTOLW2QY60CD8N01E8` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_FEDERATION_PROVIDER`
--

LOCK TABLES `USER_FEDERATION_PROVIDER` WRITE;
/*!40000 ALTER TABLE `USER_FEDERATION_PROVIDER` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_FEDERATION_PROVIDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_GROUP_MEMBERSHIP`
--

DROP TABLE IF EXISTS `USER_GROUP_MEMBERSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_GROUP_MEMBERSHIP` (
  `GROUP_ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`GROUP_ID`,`USER_ID`),
  KEY `IDX_USER_GROUP_MAPPING` (`USER_ID`),
  CONSTRAINT `FK_USER_GROUP_USER` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_GROUP_MEMBERSHIP`
--

LOCK TABLES `USER_GROUP_MEMBERSHIP` WRITE;
/*!40000 ALTER TABLE `USER_GROUP_MEMBERSHIP` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_GROUP_MEMBERSHIP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_MAILING`
--

DROP TABLE IF EXISTS `USER_MAILING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_MAILING` (
  `ID` varchar(36) NOT NULL,
  `REALM_ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `MAILING_ID` varchar(36) NOT NULL,
  `LANGUAGE` varchar(2) NOT NULL DEFAULT 'en',
  `DELIVERY` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0,1 or 2 (email/post/both)',
  PRIMARY KEY (`ID`),
  KEY `FK_USER_MAILING_USER_ENTITY` (`USER_ID`),
  KEY `FK_USER_MAILING_REALM_ENTITY` (`REALM_ID`),
  KEY `FK_USER_MAILING_MAILING_ENTITY` (`MAILING_ID`),
  CONSTRAINT `FK_USER_MAILING_MAILING_ENTITY` FOREIGN KEY (`MAILING_ID`) REFERENCES `MAILING_ENTITY` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `FK_USER_MAILING_REALM_ENTITY` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `FK_USER_MAILING_USER_ENTITY` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_MAILING`
--

LOCK TABLES `USER_MAILING` WRITE;
/*!40000 ALTER TABLE `USER_MAILING` DISABLE KEYS */;
INSERT INTO `USER_MAILING` VALUES ('bd08d08f-7bde-41e7-aa18-75d0ecb7fcd8','liferay-portal','fc7b51a0-662e-44ca-8621-5921972d159c','c9970d56-1417-4c8f-9f28-68870579bf90','en',0),('d05982f9-08ac-4d53-9210-28262ca8e117','liferay-portal','fc7b51a0-662e-44ca-8621-5921972d159c','dc7abad2-deba-4394-a823-567f8482dd81','en',0);
/*!40000 ALTER TABLE `USER_MAILING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_REQUIRED_ACTION`
--

DROP TABLE IF EXISTS `USER_REQUIRED_ACTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_REQUIRED_ACTION` (
  `USER_ID` varchar(36) NOT NULL,
  `REQUIRED_ACTION` varchar(255) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`REQUIRED_ACTION`,`USER_ID`),
  KEY `IDX_USER_REQACTIONS` (`USER_ID`),
  CONSTRAINT `FK_6QJ3W1JW9CVAFHE19BWSIUVMD` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_REQUIRED_ACTION`
--

LOCK TABLES `USER_REQUIRED_ACTION` WRITE;
/*!40000 ALTER TABLE `USER_REQUIRED_ACTION` DISABLE KEYS */;
INSERT INTO `USER_REQUIRED_ACTION` VALUES ('04a65cd9-859e-492e-a312-564d9d1505a7','terms_and_conditions'),('2bff2ffa-ebf3-4edf-9c26-98e1eeb6ba7a','terms_and_conditions'),('306665eb-fd64-4acb-89be-2a89714745b9','terms_and_conditions'),('45bf2730-c155-428d-8d2b-6e9a45c10cfa','terms_and_conditions'),('98c7c0ff-a62f-417f-b9b4-8a5cffcb0855','terms_and_conditions');
/*!40000 ALTER TABLE `USER_REQUIRED_ACTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_ROLE_MAPPING`
--

DROP TABLE IF EXISTS `USER_ROLE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_ROLE_MAPPING` (
  `ROLE_ID` varchar(255) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`USER_ID`),
  KEY `IDX_USER_ROLE_MAPPING` (`USER_ID`),
  CONSTRAINT `FK_C4FQV34P1MBYLLOXANG7B1Q3L` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_ROLE_MAPPING`
--

LOCK TABLES `USER_ROLE_MAPPING` WRITE;
/*!40000 ALTER TABLE `USER_ROLE_MAPPING` DISABLE KEYS */;
INSERT INTO `USER_ROLE_MAPPING` VALUES ('0ab906cb-cc4f-4af4-9dd4-e676e6f0f9f3','04a65cd9-859e-492e-a312-564d9d1505a7'),('0ab906cb-cc4f-4af4-9dd4-e676e6f0f9f3','2bff2ffa-ebf3-4edf-9c26-98e1eeb6ba7a'),('0ab906cb-cc4f-4af4-9dd4-e676e6f0f9f3','306665eb-fd64-4acb-89be-2a89714745b9'),('0ab906cb-cc4f-4af4-9dd4-e676e6f0f9f3','45bf2730-c155-428d-8d2b-6e9a45c10cfa'),('0ab906cb-cc4f-4af4-9dd4-e676e6f0f9f3','98c7c0ff-a62f-417f-b9b4-8a5cffcb0855'),('0ab906cb-cc4f-4af4-9dd4-e676e6f0f9f3','fc7b51a0-662e-44ca-8621-5921972d159c'),('3d9c5e71-233a-4e29-9e60-f5d9129f9c57','04a65cd9-859e-492e-a312-564d9d1505a7'),('4ae9f0e8-1f16-44ef-90c9-558002aee88b','04a65cd9-859e-492e-a312-564d9d1505a7'),('6ec8ccad-ff0e-40e6-a429-295f985ff0c9','45bf2730-c155-428d-8d2b-6e9a45c10cfa'),('73788df5-8b96-4a88-bd3a-17f99b717b46','2bff2ffa-ebf3-4edf-9c26-98e1eeb6ba7a'),('8c430ca8-55e7-43c6-8625-527ab3f3ec3f','04a65cd9-859e-492e-a312-564d9d1505a7'),('8c430ca8-55e7-43c6-8625-527ab3f3ec3f','2bff2ffa-ebf3-4edf-9c26-98e1eeb6ba7a'),('8c430ca8-55e7-43c6-8625-527ab3f3ec3f','306665eb-fd64-4acb-89be-2a89714745b9'),('8c430ca8-55e7-43c6-8625-527ab3f3ec3f','45bf2730-c155-428d-8d2b-6e9a45c10cfa'),('8c430ca8-55e7-43c6-8625-527ab3f3ec3f','98c7c0ff-a62f-417f-b9b4-8a5cffcb0855'),('8c430ca8-55e7-43c6-8625-527ab3f3ec3f','fc7b51a0-662e-44ca-8621-5921972d159c'),('977804ea-5096-41be-b20a-35d5b4749896','6360111f-0e87-4f47-873a-72c07714a985'),('992f5685-d4b5-44a8-8745-78ef23eee8b4','98c7c0ff-a62f-417f-b9b4-8a5cffcb0855'),('a8772b31-9ccd-42b4-aff3-b9a354828e95','306665eb-fd64-4acb-89be-2a89714745b9'),('b3a357a8-691e-4855-bf2c-a2c5757f8a54','6360111f-0e87-4f47-873a-72c07714a985'),('beda296e-ad85-43a6-9d77-d37193147efa','04a65cd9-859e-492e-a312-564d9d1505a7'),('beda296e-ad85-43a6-9d77-d37193147efa','2bff2ffa-ebf3-4edf-9c26-98e1eeb6ba7a'),('beda296e-ad85-43a6-9d77-d37193147efa','306665eb-fd64-4acb-89be-2a89714745b9'),('beda296e-ad85-43a6-9d77-d37193147efa','45bf2730-c155-428d-8d2b-6e9a45c10cfa'),('beda296e-ad85-43a6-9d77-d37193147efa','98c7c0ff-a62f-417f-b9b4-8a5cffcb0855'),('beda296e-ad85-43a6-9d77-d37193147efa','fc7b51a0-662e-44ca-8621-5921972d159c'),('c9a15285-475b-4369-94f6-a6de3bcce560','6360111f-0e87-4f47-873a-72c07714a985'),('e19edb48-736a-4808-b9c1-daa24ca1a27f','04a65cd9-859e-492e-a312-564d9d1505a7'),('e5636186-d221-43bf-9267-f8a0f67095b0','6360111f-0e87-4f47-873a-72c07714a985'),('ea1d0b81-cdda-4b96-85fe-2c81438537a2','6360111f-0e87-4f47-873a-72c07714a985'),('f4e93900-0b7d-4fa3-b2ca-523715fff9d1','04a65cd9-859e-492e-a312-564d9d1505a7'),('f4e93900-0b7d-4fa3-b2ca-523715fff9d1','2bff2ffa-ebf3-4edf-9c26-98e1eeb6ba7a'),('f4e93900-0b7d-4fa3-b2ca-523715fff9d1','306665eb-fd64-4acb-89be-2a89714745b9'),('f4e93900-0b7d-4fa3-b2ca-523715fff9d1','45bf2730-c155-428d-8d2b-6e9a45c10cfa'),('f4e93900-0b7d-4fa3-b2ca-523715fff9d1','98c7c0ff-a62f-417f-b9b4-8a5cffcb0855'),('f4e93900-0b7d-4fa3-b2ca-523715fff9d1','fc7b51a0-662e-44ca-8621-5921972d159c');
/*!40000 ALTER TABLE `USER_ROLE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_SESSION`
--

DROP TABLE IF EXISTS `USER_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_SESSION` (
  `ID` varchar(36) NOT NULL,
  `AUTH_METHOD` varchar(255) DEFAULT NULL,
  `IP_ADDRESS` varchar(255) DEFAULT NULL,
  `LAST_SESSION_REFRESH` int(11) DEFAULT NULL,
  `LOGIN_USERNAME` varchar(255) DEFAULT NULL,
  `REALM_ID` varchar(255) DEFAULT NULL,
  `REMEMBER_ME` bit(1) NOT NULL DEFAULT b'0',
  `STARTED` int(11) DEFAULT NULL,
  `USER_ID` varchar(255) DEFAULT NULL,
  `USER_SESSION_STATE` int(11) DEFAULT NULL,
  `BROKER_SESSION_ID` varchar(255) DEFAULT NULL,
  `BROKER_USER_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_SESSION`
--

LOCK TABLES `USER_SESSION` WRITE;
/*!40000 ALTER TABLE `USER_SESSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_SESSION_NOTE`
--

DROP TABLE IF EXISTS `USER_SESSION_NOTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_SESSION_NOTE` (
  `USER_SESSION` varchar(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`USER_SESSION`,`NAME`),
  CONSTRAINT `FK5EDFB00FF51D3472` FOREIGN KEY (`USER_SESSION`) REFERENCES `USER_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_SESSION_NOTE`
--

LOCK TABLES `USER_SESSION_NOTE` WRITE;
/*!40000 ALTER TABLE `USER_SESSION_NOTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_SESSION_NOTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WEB_ORIGINS`
--

DROP TABLE IF EXISTS `WEB_ORIGINS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WEB_ORIGINS` (
  `CLIENT_ID` varchar(36) NOT NULL,
  `VALUE` varchar(255) NOT NULL,
  PRIMARY KEY (`CLIENT_ID`,`VALUE`),
  KEY `IDX_WEB_ORIG_CLIENT` (`CLIENT_ID`),
  CONSTRAINT `FK_LOJPHO213XCX4WNKOG82SSRFY` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WEB_ORIGINS`
--

LOCK TABLES `WEB_ORIGINS` WRITE;
/*!40000 ALTER TABLE `WEB_ORIGINS` DISABLE KEYS */;
/*!40000 ALTER TABLE `WEB_ORIGINS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'keycloak'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-19 11:38:13
