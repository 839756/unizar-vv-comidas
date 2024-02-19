package es.unizar.eina.T223_comidas.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** Definicion de un Data Access Object para las raciones de platos en pedidos */
@Dao
public interface CantidadDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Cantidad cantidad);

    @Update
    int update(Cantidad cantidad);

    @Delete
    int delete(Cantidad cantidad);

    @Query("DELETE FROM cantidad WHERE idPedido = :idPedido")
    void deletePlatosByPedido(int idPedido);

    @Query("SELECT * FROM Cantidad WHERE idPedido = :idPedido")
    List<Cantidad> getPlatosByPedido(int idPedido);

}

