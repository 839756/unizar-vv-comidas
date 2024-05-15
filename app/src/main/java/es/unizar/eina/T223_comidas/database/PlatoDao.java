package es.unizar.eina.T223_comidas.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** Definicion de un Data Access Object para los platos */
@Dao
public interface PlatoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Plato plato);

    @Update
    int update(Plato plato);

    @Delete
    int delete(Plato plato);

    @Query("DELETE FROM plato")
    void deleteAll();

    @Query("SELECT * FROM plato WHERE id == :id")
    Plato getPlatoById(long id);

    @Query("SELECT * FROM plato WHERE nombre == :nombre")
    Plato getPlatoByNombre(String nombre);

    @Query("SELECT COUNT(*) FROM plato")
    int getNumeroDePlatos();

    @Query("SELECT * FROM plato ORDER BY nombre ASC")
    LiveData<List<Plato>> getOrderedPlatosByNombre();

    @Query("SELECT * FROM plato ORDER BY categoria ASC")
    LiveData<List<Plato>> getOrderedPlatosByCategoria();
}

