USE `CSCI5308_22_DEVINT`;
DROP procedure IF EXISTS `sp_getPasswordConfigSettings`;

DELIMITER $$
USE `CSCI5308_22_DEVINT`$$
CREATE PROCEDURE `sp_getPasswordConfigSettings` ()
BEGIN
	select * from password_settings;
END$$

DELIMITER ;