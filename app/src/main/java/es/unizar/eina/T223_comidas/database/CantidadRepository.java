package es.unizar.eina.T223_comidas.database;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CantidadRepository {

    private CantidadDao mCantidadDao;


    public CantidadRepository(Application application) {
        ComidasRoomDatabase db = ComidasRoomDatabase.getDatabase(application);
        mCantidadDao = db.cantidadDao();
    }

    /** Inserta una cantidad
     * @param cantidad
     * @return un valor entero largo con el identificador del plato que se ha creado.
     */
    public long insert(Cantidad cantidad) {
        final long[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);
        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mCantidadDao.insert(cantidad);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return result[0];
    }

    /** Modifica una cantidad
     * @param cantidad
     * @return un valor entero con el numero de filas modificadas.
     */
    public int update(Cantidad cantidad) {
        final int[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mCantidadDao.update(cantidad);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return result[0];
    }

    /** Elimina una cantidad
     * @param cantidad
     * @return un valor entero con el numero de filas eliminadas.
     */
    public int delete(Cantidad cantidad) {
        final int[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mCantidadDao.delete(cantidad);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return result[0];
    }

    /** Obtiene la lista de platos de un pedido
     * @param idPedido
     * @return lista de platos de un pedido.
     */
    public List<Cantidad> getPlatosByPedido(int idPedido) {
        final List<Cantidad>[] result = new List[]{null};
        CountDownLatch latch = new CountDownLatch(1);

        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mCantidadDao.getPlatosByPedido(idPedido);
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result[0];
    }

    /** Elimina todos los platos asociados a un pedido
     * @param idPedido
     */
    public void deletePlatosByPedido(int idPedido) {
        CountDownLatch latch = new CountDownLatch(1);

        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCantidadDao.deletePlatosByPedido(idPedido);
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
