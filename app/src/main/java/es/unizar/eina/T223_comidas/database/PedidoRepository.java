package es.unizar.eina.T223_comidas.database;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PedidoRepository {

    private PedidoDao mPedidoDao;
    private LiveData<List<es.unizar.eina.T223_comidas.database.Pedido>> mAllPedidosByCliente;
    private LiveData<List<es.unizar.eina.T223_comidas.database.Pedido>> mAllPedidosByMovil;
    private LiveData<List<es.unizar.eina.T223_comidas.database.Pedido>> mAllPedidosByFecha;
    private LiveData<List<es.unizar.eina.T223_comidas.database.Pedido>> mAllPedidosByEstado;


    public PedidoRepository(Application application) {
        ComidasRoomDatabase db = ComidasRoomDatabase.getDatabase(application);
        mPedidoDao = db.pedidoDao();
        mAllPedidosByCliente = mPedidoDao.getOrderedPedidosByCliente();
        mAllPedidosByMovil = mPedidoDao.getOrderedPedidosByMovil();
        mAllPedidosByFecha = mPedidoDao.getOrderedPedidosByFecha();
        mAllPedidosByEstado = mPedidoDao.getOrderedPedidosByEstado();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<es.unizar.eina.T223_comidas.database.Pedido>> getAllPedidosByCliente() {
        return mAllPedidosByCliente;
    }
    public LiveData<List<es.unizar.eina.T223_comidas.database.Pedido>> getAllPedidosByMovil() {
        return mAllPedidosByMovil;
    }
    public LiveData<List<es.unizar.eina.T223_comidas.database.Pedido>> getAllPedidosByFecha() {
        return mAllPedidosByFecha;
    }
    public LiveData<List<es.unizar.eina.T223_comidas.database.Pedido>> getAllPedidosByEstado() {
        return mAllPedidosByEstado;
    }


    private boolean comprobarPedido(Pedido pedido) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:mm");
        Date fechaActual = new Date(); // Fecha y hora actual
        Date fecha = null;
        try {
            fecha = sdf.parse(pedido.getFecha());
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        int diaDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        int horaDelDia = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);

        boolean fechaNoValida = diaDeLaSemana == Calendar.MONDAY ||
                !(horaDelDia == 19 && minutos >= 30) && !(horaDelDia >= 20 && horaDelDia < 23)
                        &&  !(horaDelDia == 23 && minutos == 00) || fecha.compareTo(fechaActual) <= 0;

        return Objects.equals(pedido.getCliente(), "") || fechaNoValida ||
                Integer.toString(pedido.getMovil()).length() != 9 ||
                (!Objects.equals(pedido.getEstado(), "SOLICITADO") && !Objects.equals(pedido.getEstado(), "PREPARADO") &&
                        !Objects.equals(pedido.getEstado(), "RECOGIDO"));  
    }


    /** Inserta un pedido
     * @param pedido
     * @return un valor entero largo con el identificador del plato que se ha creado.
     */
    public long insert(Pedido pedido) {

        //===============================
        //Obtención del numero de pedidos
        //===============================
        Future<Integer> numeroDePedidos = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPedidoDao.getNumeroDePedidos());
        int numPedidos;

        try {
            numPedidos = numeroDePedidos.get();
        } catch (InterruptedException | ExecutionException e) {
            return -1;
        }

        //=============================================
        //Se realizan las comprobaciones y la inserción
        //=============================================
        if( numPedidos >= 2000 || comprobarPedido(pedido)){
            return -1;
        }else{
            Future<Long> pedidoInsertado = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPedidoDao.insert(pedido));

            try {
                return pedidoInsertado.get();
            } catch (InterruptedException | ExecutionException e) {
                return -1;
            }
        }

    }

    /** Modifica un pedido
     * @param pedido
     * @return un valor entero con el numero de filas modificadas.
     */
    public int update(Pedido pedido) {
        if(comprobarPedido(pedido)){
            return 0;
        }else{
            Future<Integer> pedidoActualizado = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPedidoDao.update(pedido));

            try {
                return pedidoActualizado.get();
            } catch (InterruptedException | ExecutionException e) {
                return 0;
        }
        }
        
    }

    /** Elimina un pedido
     * @param pedido
     * @return un valor entero con el numero de filas eliminadas.
     */
    public int delete(Pedido pedido) {
        Future<Integer> pedidoEliminado = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPedidoDao.delete(pedido));

        try {
            return pedidoEliminado.get();
        } catch (InterruptedException | ExecutionException e) {
            return 0;
        }
    }

    //Elimina todos los pedidos
    public void deleteAll(){
        ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPedidoDao.deleteAll());
    };

    /** Obtiene el numero de pedidos
     * @return numero de pedidos de la BD
     */
    public int getNumeroDePedidos() {
        Future<Integer> numeroDePedidos = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPedidoDao.getNumeroDePedidos());

        try {
            return numeroDePedidos.get();
        } catch (InterruptedException | ExecutionException e) {
            return -1;
        }
    }



}
