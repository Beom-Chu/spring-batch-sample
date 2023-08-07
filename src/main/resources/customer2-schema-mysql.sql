create table `customer2` (
    `id` mediumint(8) unsigned NOT NULL auto_increment,
    `firstName` varchar(255) default NULL,
    `lastName` varchar(255) default NULL,
    `birthDate` varchar(255) ,
    primary key (`id`)
) auto_increment=1;