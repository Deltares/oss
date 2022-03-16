<?php
$CONFIG = array (
  'htaccess.RewriteBase' => '/',
  'memcache.local' => '\\OC\\Memcache\\APCu',
  'apps_paths' =>
  array (
    0 =>
    array (
      'path' => '/var/www/html/apps',
      'url' => '/apps',
      'writable' => false,
    ),
    1 =>
    array (
      'path' => '/var/www/html/custom_apps',
      'url' => '/custom_apps',
      'writable' => true,
    ),
  ),
  'instanceid' => 'ocvqf5vtzn7j',
  'passwordsalt' => 'QeeyI6H81nqyJXLarLzVbsgMKwCntZ',
  'secret' => 'PJX9X10uHq5C2JBNzoAefczkKfW7FWQCRBwb01fmv2gOaRU3',
  'trusted_domains' =>
  array (
    0 => 'localhost:8180',
    1 => '127.0.0.1:8180'
  ),
  'datadirectory' => '/var/www/html/data',
  'dbtype' => 'mysql',
  'version' => '23.0.2.1',
  'overwrite.cli.url' => 'http://nextcloud:8180',
  'dbname' => 'nextcloud',
  'dbhost' => 'mariadb-nextcloud',
  'dbport' => '',
  'dbtableprefix' => 'oc_',
  'mysql.utf8mb4' => true,
  'dbuser' => 'nextcloud',
  'dbpassword' => 'nextcloud',
  'installed' => true,
  'mail_smtpmode' => 'smtp',
  'mail_smtpsecure' => 'tls',
  'mail_sendmailmode' => 'smtp',
  'mail_from_address' => 'mydeltares',
  'mail_domain' => 'deltares.nl',
  'mail_smtpauthtype' => 'LOGIN',
  'mail_smtpauth' => 1,
  'mail_smtphost' => 'smtp.office365.com',
  'mail_smtpport' => '587',
  'mail_smtpname' => '@EMAIL_USER@',
  'mail_smtppassword' => '@EMAIL_PASSWORD@',
);