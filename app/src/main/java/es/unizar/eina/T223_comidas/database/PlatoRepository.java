package es.unizar.eina.T223_comidas.database;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Objects;
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
        if(Objects.equals(plato.getNombre(), "") ||
                (!Objects.equals(plato.getCategoria(), "PRIMERO") && !Objects.equals(plato.getCategoria(), "SEGUNDO") &&
                        !Objects.equals(plato.getCategoria(), "POSTRE")) || plato.getPrecio() < 0){
            return -1;
        }else{
            Future<Long> platoInsertado = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPlatoDao.insert(plato));

            try {
                return platoInsertado.get();
            } catch (InterruptedException | ExecutionException e) {
                return -1;
            }
        }

    }

    /** Modifica un plato
     * @param plato
     * @return un valor entero con el numero de filas modificadas.
     */
    public int update(Plato plato) {
        if(Objects.equals(plato.getNombre(), "") ||
                (!Objects.equals(plato.getCategoria(), "PRIMERO") && !Objects.equals(plato.getCategoria(), "SEGUNDO") &&
                        !Objects.equals(plato.getCategoria(), "POSTRE")) || plato.getPrecio() < 0){
            return 0;
        }else{
            Future<Integer> platoActualizado = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPlatoDao.update(plato));

            try {
                return platoActualizado.get();
            } catch (InterruptedException | ExecutionException e) {
                return 0;
            }
        }

    }

    /** Elimina un plato
     * @param plato
     * @return un valor entero con el numero de filas eliminadas.
     */
    public int delete(Plato plato) {
        Future<Integer> platoEliminado = ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPlatoDao.delete(plato));

        try {
            return platoEliminado.get();
        } catch (InterruptedException | ExecutionException e) {
            return 0;
        }

    }

    public void deleteAll(){
        ComidasRoomDatabase.databaseWriteExecutor.submit(() -> mPlatoDao.deleteAll());
    };


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
