USE DevoxDB;
drop procedure if exists `folios_por_fecha`;
DELIMITER ;;
CREATE   PROCEDURE `folios_por_fecha`(IN fi date, IN ff date)
begin
select folio from Descripcion_Devolucion where DATE(fechaCaptura) BETWEEN fi AND ff;
end ;;
DELIMITER ;

drop procedure if exists `rma_devolucion`;
DELIMITER ;;
CREATE   PROCEDURE `rma_devolucion`(IN folio int)
begin
SELECT x.Sucursal_idSucursal, x.folioCliente, x.observaciones, x.SubDivision_idSubDivision, DATE(x.fechaCaptura) as fecha, c.Motivo_idMotivo FROM
(SELECT b.Sucursal_idSucursal, a.folio as folio, a.folioCliente, a.observaciones, a.SubDivision_idSubDivision, a.fechaCaptura
FROM descripcion_devolucion a
	JOIN devolucion_has_instancia_producto b
    ON a.folio = b.Devolucion_folio)x JOIN devolucion c ON x.folio = c.folio where x.folio = folio;
end ;;
DELIMITER ;

drop procedure if exists `rma_productos`;
DELIMITER ;;
CREATE   PROCEDURE `rma_productos`(IN folio int)
begin
SELECT b.Producto_idProducto, a.Cantidad, a.Precio_de_Captura
FROM devolucion_has_instancia_producto a JOIN Lote b ON a.Lote_idLote = b.idLote where a.Devolucion_folio = folio;
end ;;
DELIMITER ;