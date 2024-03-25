package es.unizar.eina.T223_comidas.database;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PlatoRepository {

    private PlatoDao mPlatoDao;
    private LiveData<List<Plato>> mAllPlatosByNombre;
    private LiveData<List<Plato>> mAllPlatosByCategoria;


    public PlatoRepository(Application application) {
        ComidasRoomDatabase db = ComidasRoomDatabase.getDatabase(application);
        mPlatoDao = db.platoDao();
        mAllPlatosByNombre = mPlatoDao.getOrderedPlatosByNombre();
        mAllPlatosByCategoria = mPlatoDao.getOrderedPlatosByCategoria();

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Plato>> getAllPlatosByNombre() {
        return mAllPlatosByNombre;
    }
    public LiveData<List<Plato>> getAllPlatosByCategoria() {
        return mAllPlatosByCategoria;
    }


    /** Inserta un plato
     * @param plato
     * @return un valor entero largo con el identificador del plato que se ha creado.
     */
    public long insert(Plato plato) {
        final long[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);
        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPlatoDao.insert(plato);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return result[0];
    }

    /** Modifica un plato
     * @param plato
     * @return un valor entero con el numero de filas modificadas.
     */
    public int update(Plato plato) {
        final int[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPlatoDao.update(plato);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return result[0];
    }

    /** Elimina un plato
     * @param plato
     * @return un valor entero con el numero de filas eliminadas.
     */
    public int delete(Plato plato) {
        final int[] result = {0};
        CountDownLatch latch = new CountDownLatch(1);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPlatoDao.delete(plato);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return result[0];
    }


    /** Obtiene el plato por id
     * @return plato
     */
    public Plato getPlatoById(long id){
        Future<Plato> plato = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPlatoDao.getPlatoById(id));

        try {
            return plato.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    /** Obtiene el numero de platos
     * @return numero de platos de la BD
     */
    public int getNumeroDePlatos() {
        Future<Integer> numeroDePlatos = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPlatoDao.getNumeroDePlatos());

        try {
            return numeroDePlatos.get();
        } catch (InterruptedException | ExecutionException e) {
            return -1;
        }
    }
}
