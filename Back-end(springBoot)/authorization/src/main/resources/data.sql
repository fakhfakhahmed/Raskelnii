drop table if exists oauth_access_token;
create table oauth_access_token(
  token_id          VARCHAR(255),
  token             varbinary(4096),
  authentication_id VARCHAR(255),
  user_name         VARCHAR(255),
  client_id         VARCHAR(255),
  authentication    varbinary(4096),
  refresh_token VARCHAR(255)
);

drop table if exists oauth_refresh_token;
create table oauth_refresh_token(
  token_id        VARCHAR(255),
  token           varbinary(4096),
  authentication  varbinary(4096)
);

drop table if exists oauth_code;
create table oauth_code(
  code            VARCHAR(255),
  authentication  varbinary(4096)
);

drop table if exists oauth_approvals;
create table oauth_approvals(
  userId         VARCHAR(255),
  clientId       VARCHAR(255),
  scope          VARCHAR(255),
  status         VARCHAR(10),
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

drop table if exists ClientDetails;
create table ClientDetails(
  appId                  VARCHAR(255) PRIMARY KEY,
  resourceIds            VARCHAR(255),
  appSecret              VARCHAR(255),
  scope                  VARCHAR(255),
  grantTypes             VARCHAR(255),
  redirectUrl            VARCHAR(255),
  authorities            VARCHAR(255),
  access_token_validity  INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation  VARCHAR(4096),
  autoApproveScopes      VARCHAR(255)
);

create index oauth_access_token_id on oauth_access_token(token_id);
create index oauth_refresh_token_id on oauth_access_token(token_id);



--START CLIENT CREDENTIAL TABLES--

drop table if exists oauth_client_details;
create table oauth_client_details(
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
);

drop table if exists oauth_client_token;
create table oauth_client_token(
  token_id          VARCHAR(255),
  token             varbinary(4096),
  authentication_id VARCHAR(255),
  user_name         VARCHAR(255),
  client_id         VARCHAR(255)
);

drop table if exists user_role;
CREATE TABLE user_role (
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL
);

-- ############################################################################################# --

-- BASIC AUTH with token time declarationuser_role
-- ACCESS TOKEN VALIDITY = 300 SECOND
-- REFRESH TOKEN VALIDITY = 1800 SECOND
-- insert client details [clientId = client & clientSecret = TESTtest1234]
INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types,
authorities, access_token_validity, refresh_token_validity)
VALUES ('client', '$2a$10$HWM1iSbiXN2aVEhRHIvaxO0wCQW4mahSHAumrpfZxhPmS8YIQypxe',
'read,write', 'password,refresh_token,client_credentials,authorization_code',
'ROLE_TRUSTED_CLIENT', 24000, 35000);


-- ##########################################################--
-- USER ROLES
drop table if exists auth_role;
CREATE TABLE auth_role (
    id bigint(20) NOT NULL,
    role_name VARCHAR(255),
    description VARCHAR(255)
);

INSERT INTO auth_role(id, role_name, description)
VALUES (1, 'ROLE_ADMIN', 'Admin User - Has permission to perform admin tasks');

INSERT INTO auth_role(id, role_name, description)
VALUES (2, 'ROLE_USER', 'USER - Has no admin rights');

INSERT INTO auth_role(id, role_name, description)
VALUES (3, 'ROLE_LIVREUR', 'LIVREUR - Has no admin rights');
