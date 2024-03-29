package es.unizar.eina.T223_comidas.database;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
        Future<Long> cantidadInsertada = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mCantidadDao.insert(cantidad));

        try {
            return cantidadInsertada.get();
        } catch (InterruptedException | ExecutionException e) {
            return -1;
        }
    }

    /** Modifica una cantidad
     * @param cantidad
     * @return un valor entero con el numero de filas modificadas.
     */
    public int update(Cantidad cantidad) {
        Future<Integer> cantidadActualizada = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mCantidadDao.update(cantidad));

        try {
            return cantidadActualizada.get();
        } catch (InterruptedException | ExecutionException e) {
            return 0;
        }
    }

    /** Elimina una cantidad
     * @param cantidad
     * @return un valor entero con el numero de filas eliminadas.
     */
    public int delete(Cantidad cantidad) {
        Future<Integer> cantidadEliminada = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mCantidadDao.delete(cantidad));

        try {
            return cantidadEliminada.get();
        } catch (InterruptedException | ExecutionException e) {
            return 0;
        }
    }

    /** Obtiene la lista de platos de un pedido
     * @param idPedido
     * @return lista de platos de un pedido.
     */
    public List<Cantidad> getPlatosByPedido(int idPedido) {
        Future<List<Cantidad>> platosPorPedido = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mCantidadDao.getPlatosByPedido(idPedido));

        try {
            return platosPorPedido.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    /** Elimina todos los platos asociados a un pedido
     * @param idPedido
     */
    public void deletePlatosByPedido(int idPedido) {

        ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mCantidadDao.deletePlatosByPedido(idPedido));

    }
}
