package com.devox.main;

import com.devox.GUI.Home;

/**
 * 14/07/2016 v 1.2.160714.2
 * Se actualizó el reporte RMA
 * Agregué la alerta para cambiar el almacén de una devolución en tarimas
 * Agregué 3 SP a la BD: folios_por_fecha (in fecha, in fecha), rma_devolucion(in folio) y rma_productos(in folio)
 * 
 * 20/07/2016 v 1.2.160720.3
 * La librería DevoxLibrary se revisó y arreglaron errores de conexión a la base de Datos<p>
 * Se reviso que todas las conexiones DatabaseConnection() fueran correctamente instanciadas, cachadas con Log.print(SQLException) y cerradas (closeConnection()).
 * Se corrigieron busg en AccionesTarima (excepciones al crear CuadriculaTarima)
 * Se corrigieron bugs en com.devox.LOG.Entidades.Producto.seleccionaLote() (Excepciones al no seleccionar ningún lote)
 * Se corrigieron bugs en historialprecio.alta() (excepciones al dar de alta productos existentes)
 * Se corrigieron bugs en demasiadas conexiones de la base de datos abiertas al mismo tiempo.
 * Se corrigió el bug de los wildcards en la búsqueda (por lotes y clientes)
 * Se cambió la estética de la aplicación por "Nimbus"
 * 
 * 22/07/2016 v 1.2.160722.4
 * Se cambió el modo de conexión a la base de datos: existe solo una conexión a la base de datos para todo el proceso del programa
 * Cuando la ventana se cierre, se cierra la conexión.
 * 
 * 23/07/2016 v 1.2.160727.5
 * Se modificó en la BD el reporte principal exportado a CSV: cliente en vez de sucursal
 * Encabezados en el reporte CSV
 * se eliminaron todos los Connection.close() en Devox para así asegurar una misma conexión abierta todo el tiempo en la app
 * 
 * 27/07/2016 c 1.2.160727.6
 * Modificaciones en el reporte principal para Miriam: los reportes principales de Miriam que salen en Excel, ahora tengan el nombre del cliente en la tercera columna, no el nombre de la sucursal.
 * Se agregó la cabecera en Excel.
 * 
 * 01/08/2016 v 1.2.160801.7
 * Se agregó glasspane para mostrar "cargando" al abrir devoluciones
 * 
 * 02/08/2016 v 1.2.160802.8
 * PRUEBA: se agregó System.gc(); en cada método de com.devox.GUI.Actions cuando cambia de ventana, esto para estar recolectando la basura de objetos creados que ya no se usen.
 * 
 * 03/08/2016 v 1.2.160803.9
 * Se estabilizaron y corrigieron errores en cambios de Estados.
 * 
 * 04/08/2016 v 1.2.160804.10
 * Se corrigieron bugs en la fecha de recibido de la devolucion.
 * 
 * 05/08/2016 v 1.2.160805.11
 * Se corrigió en la captura de productos el cambio de precio: siempre respetará el precio si se modifica, no el original.
 * Se cambió para esto el procedimiento almacenado de agrega_lote_a_devolucion(?,?,?,?) a agrega_producto_a_devolucion(?,?,?,?,?), la 4ta variable es el flotante del precio final para esta devolucion.
 * 
 * 08/08/2016 v 1.2.160808.12
 * Se agregaron las columnas "folio Abbot", "Folio cliente" y "Factura" en la busqueda principal
 * Se modificó el procedimiento almacenado "busqueda_por_cliente(?)"
 * Se tuvo que editar DevoxTable para agregar las 3 columnas y se tuvo que editar org.inspira.devox.services.Busqueda
 * 
 * 10/08/2016 v 1.2.160810.13
 * Se cambió proceso de llenado de devoluciones, a usar un hilo
 * Se quitó System.gc() de VerDEvoluciones
 * 
 * 12/08/2016 v 1.2.160810.14
 * Se cambió proceso de llenado de devoluciones en verEstados, a usar un hilo
 * 
 * 23/08/2016 v 1.2.160810.15
 * ACTUALIZACION IMPORTANTE: se tendrá esta versión a manera de prueba. NO ES DEFINITIVA
 * A partir del folio 599 hacia atrás, no se llenarán datos.
 * VerDevoluciones linea 480
 * VerEstados linea 1287
 * verReportes linea 1133
 * URGE REPARAR EL ARCHIVO PARA GUARDAR DEVOLUCIONES!!!
 * 
 * 30/08/2016 v 1.3.160830.16
 * ListaCuadriculas tiene la implementación de solo crear las cuadriculas necesarias y útiles, no todas cada vez
 * 
 * 05/09/2016 v 1.3.160905.17
 * Reportes con letra más grande
 * Tarimas ahora contempla estructura de las cuadriculas de devoluciones y evita la repetición de algunas con el mismo nombre.
 * 
 * 14/10/2016 v 1.3.161014.18
 * Log para monitorear en caso de q se trabe, quizás por la cantidad de conecciones? o la cantidad de obnjetos Cuadricula creados...
 * 
 * @author David Azaraf
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Home.iniciar();
        
    }

}
