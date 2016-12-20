drop procedure if exists`reporte_principal`;
DELIMITER ;;
CREATE   PROCEDURE `reporte_principal`(IN fi date, IN ff date)
begin
select * 
from(
  select folio,Sucursal_idSucursal,folioCliente,i.Nombre as 'Nombre',Producto_idProducto,descripcion,Cantidad,Precio,(@monto := Cantidad*Precio) Monto_Total,Lote_idLote,Familia,(@ad := 'A') `A/D`,SubDivision,motivo,(@tarim := '---') NombreTarima,Fecha_Recibido,Fecha_Captura
  from (
    select folio,Sucursal_idSucursal,folioCliente,Cliente_RFC,Producto_idProducto,descripcion,Cantidad,Precio,(@monto := Cantidad*Precio) Monto_Total,Lote_idLote,Familia_idFamilia Familia,(@ad := 'A') `A/D`,SubDivision,motivo,(@tarim := '---') NombreTarima,Fecha_Recibido,Fecha_Captura 
    from (
      select folio,Sucursal_idSucursal,folioCliente,Producto_idProducto,descripcion,Cantidad,Precio,(@monto := Cantidad*Precio) Monto_Total,Lote_idLote,SubDivision,motivo,Fecha_Recibido,Fecha_Captura 
      from (
        select folio,Sucursal_idSucursal,folioCliente,Producto_idProducto,descripcion,Cantidad,Lote_idLote,SubDivision,motivo,Fecha_Recibido,Fecha_Captura 
        from (
          select folio,Sucursal_idSucursal,folioCliente,Cantidad,Lote_idLote,SubDivision,motivo,Fecha_Recibido,Fecha_Captura 
          from (
            select idDevolucion_has_Instancia_Producto,folio,Sucursal_idSucursal,folioCliente,Cantidad,Lote_idLote,SubDivision,motivo,Fecha_Recibido,Fecha_Captura 
            from (
              select folio,folioCliente,fechaDevolucion Fecha_Recibido,fechaCaptura Fecha_Captura,SubDivision,Motivo_idMotivo motivo 
              from Devolucion join (
                select folio,folioCliente,fechaDevolucion,fechaCaptura,SubDivision 
                from Descripcion_Devolucion join SubDivision 
                on SubDivision_idSubDivision = idSubDivision
                )a using(folio)
              )b join Devolucion_has_Instancia_Producto on folio = Devolucion_folio
            )c left join Devolucion_has_Instancia_Producto_Destruccion 
              on c.idDevolucion_has_Instancia_Producto = Devolucion_has_Instancia_Producto_Destruccion.idDevolucion_has_Instancia_Producto
              where Devolucion_has_Instancia_Producto_Destruccion.idDevolucion_has_Instancia_Producto is null
          )d join (
            select Producto_idProducto,descripcion,idLote Lote_idLote 
            from Lote join Producto on Producto_idProducto = idProducto
            )e using(Lote_idLote)
        )f join HistorialPrecio using(Sucursal_idSucursal,Producto_idProducto)
      )g join Sucursal on Sucursal_idSucursal = idSucursal
    )h join Cliente i on Cliente_RFC = RFC

  UNION
  select folio,Sucursal_idSucursal,folioCliente,i.Nombre as 'Nombre',Producto_idProducto,descripcion,Cantidad,Precio,(@monto := Cantidad*Precio) Monto_Total,Lote_idLote,Familia,(@ad := 'D') `A/D`,SubDivision,motivo, NombreTarima,Fecha_Recibido,Fecha_Captura
  from(
    select folio,Sucursal_idSucursal,folioCliente,Cliente_RFC,Producto_idProducto,descripcion,Cantidad,Precio,(@monto := Cantidad*Precio) Monto_Total,Lote_idLote,Familia_idFamilia Familia,(@ad := 'D') `A/D`,SubDivision,motivo,NombreTarima,Fecha_Recibido,Fecha_Captura 
    from (
      select folio,Sucursal_idSucursal,folioCliente,Producto_idProducto,descripcion,Cantidad,Precio,(@monto := Cantidad*Precio) Monto_Total,Lote_idLote,SubDivision,motivo,Fecha_Recibido,Fecha_Captura,NombreTarima 
      from (
        select folio,Sucursal_idSucursal,folioCliente,Producto_idProducto,descripcion,Cantidad,Lote_idLote,SubDivision,motivo,Fecha_Recibido,Fecha_Captura,NombreTarima 
        from (
          select folio,Sucursal_idSucursal,folioCliente,Cantidad,Lote_idLote,SubDivision,motivo,Fecha_Recibido,Fecha_Captura,NombreTarima 
          from (
            select idDevolucion_has_Instancia_Producto,folio,Sucursal_idSucursal,folioCliente,Cantidad,Lote_idLote,SubDivision,motivo,Fecha_Recibido,Fecha_Captura 
            from (
              select folio,folioCliente,fechaDevolucion Fecha_Recibido,fechaCaptura Fecha_Captura,SubDivision,Motivo_idMotivo motivo 
              from Devolucion join (
                select folio,folioCliente,fechaDevolucion,fechaCaptura,SubDivision 
                from Descripcion_Devolucion join SubDivision 
                on SubDivision_idSubDivision = idSubDivision
                )a using(folio)
              )b join Devolucion_has_Instancia_Producto 
            on folio = Devolucion_folio
            )c left join (
              select idDevolucion_has_Instancia_Producto,Tarima_idTarima,NombreTarima 
              from Devolucion_has_Instancia_Producto_Destruccion join Tarima 
              on Tarima_idTarima = idTarima
              )x 
            on c.idDevolucion_has_Instancia_Producto = x.idDevolucion_has_Instancia_Producto 
            where x.idDevolucion_has_Instancia_Producto is not null
          )d join (
            select Producto_idProducto,descripcion,idLote Lote_idLote 
            from Lote join Producto 
            on Producto_idProducto = idProducto
          )e using(Lote_idLote)
        )f join HistorialPrecio using(Sucursal_idSucursal,Producto_idProducto)
      )g join Sucursal 
      on Sucursal_idSucursal = idSucursal
    )h join Cliente i on Cliente_RFC = RFC
)l where Fecha_Recibido >= fi and Fecha_Recibido <= ff order by folio;
end ;;
DELIMITER ;

drop procedure if exists `insertaDescripcion_Devolucion`;
DELIMITER ;;
CREATE   PROCEDURE `insertaDescripcion_Devolucion`(IN Transporte_idTransporte varchar(5), IN Motivo_idMotivo int, IN folio int, IN folioAbbot varchar(45), IN documentoCliente varchar(45), IN fechaDevolucion date, IN fechaCaptura datetime, IN cajas int, IN observaciones tinytext, IN idStatus int, IN factura varchar(30), IN idSubDivision int, OUT moco int)
begin
  DECLARE EXIT HANDLER FOR SQLSTATE '23000'
  begin
    SET moco = 3;
    ROLLBACK;
  end;
  START TRANSACTION;
    insert into `Devolucion`(Transporte_idTransporte,Motivo_idMotivo) values(Transporte_idTransporte, Motivo_idMotivo);
    set moco = 0;
    select MAX(Devolucion.folio) into moco from Devolucion;
    insert into `Descripcion_Devolucion` values (moco,folioAbbot,documentoCliente,fechaDevolucion,current_date(),cajas,observaciones,factura,idSubDivision);

    insert into Descripcion_Devolucion_Estado(Descripcion_Devolucion_folio,Status_idStatus) values(moco,idStatus);
    set moco = 2;
  COMMIT;
end ;;
DELIMITER ;