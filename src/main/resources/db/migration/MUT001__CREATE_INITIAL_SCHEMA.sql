CREATE TABLE `mutant`
(
    `mutant_id`     INT NOT NULL AUTO_INCREMENT,
    `is_mutant`     BOOLEAN DEFAULT 0,
    `dna`           MEDIUMTEXT NULL COMMENT 'DNA as String',
    CONSTRAINT `pk_mutant` PRIMARY KEY (`mutant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
