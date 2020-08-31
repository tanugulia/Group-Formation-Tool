USE `CSCI5308_22_DEVINT`;
DROP procedure IF EXISTS `sp_deleteAlgo`;

DELIMITER $$
USE `CSCI5308_22_DEVINT`$$
CREATE PROCEDURE `sp_deleteAlgo` (in algoId varchar(40))
BEGIN
delete from algorithm where algo_id=algoId;
END$$

DELIMITER ;