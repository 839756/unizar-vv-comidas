package es.unizar.eina.T223_comidas.database;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import es.unizar.eina.T223_comidas.database.ComidasRoomDatabase;
import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoDao;

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


    /** Inserta un pedido
     * @param pedido
     * @return un valor entero largo con el identificador del plato que se ha creado.
     */
    public long insert(es.unizar.eina.T223_comidas.database.Pedido pedido) {
        final long[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);
        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPedidoDao.insert(pedido);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return result[0];
    }

    /** Modifica un pedido
     * @param pedido
     * @return un valor entero con el numero de filas modificadas.
     */
    public int update(es.unizar.eina.T223_comidas.database.Pedido pedido) {
        final int[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPedidoDao.update(pedido);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return result[0];
    }

    /** Elimina un pedido
     * @param pedido
     * @return un valor entero con el numero de filas eliminadas.
     */
    public int delete(Pedido pedido) {
        final int[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPedidoDao.delete(pedido);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return result[0];
    }

    /** Obtiene el numero de pedidos
     * @return numero de pedidos de la BD
     */
    public int getNumeroDePedidos() {
        final int[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);

        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPedidoDao.getNumeroDePedidos();
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result[0];
    }



}
