/******************************************/
/*   Distrib 5.7.17   */
/*   数据库全名 = pnd   */
/******************************************/

CREATE TABLE IF NOT EXISTS `pnd_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '文件或文件夹名',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父目录',
  `type` varchar(45) NOT NULL COMMENT '文件类型：文件夹/文件',
  `gmt_modified` datetime NOT NULL DEFAULT '2019-01-01 00:00:00',
  `gmt_create` datetime NOT NULL DEFAULT '2019-01-01 00:00:00',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '文件对应资源id',
  PRIMARY KEY (`id`),
  KEY `index2` (`parent_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `pnd_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `size` bigint(20) DEFAULT '0',
  `path` varchar(255) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `gmt_create` datetime DEFAULT '2019-01-01 00:00:00',
  `status` varchar(45) NOT NULL COMMENT '资源状态，表示是否完整等',
  `md5` varchar(32) NOT NULL,
  `gmt_modified` datetime DEFAULT '2019-01-01 00:00:00',
  `link` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
