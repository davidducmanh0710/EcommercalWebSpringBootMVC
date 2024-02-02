use login_system_upgrade;


DROP TABLE IF EXISTS `users_detail`;

create table `users_detail`(
	`id` BIGINT NOT NULL AUTO_INCREMENT , 
    `first_name` varchar(255) , 
    `last_name` varchar(255) , 
	`email` varchar(255)  ,
    `phone_number` varchar(255),
    `gender` varchar(255),
    `date_of_birth` date ,
    `address` varchar(255) , 
	`country` varchar(255),
    
    PRIMARY key (`id`) , 
    foreign key (`id`) references users(`id`)
    
) ;



