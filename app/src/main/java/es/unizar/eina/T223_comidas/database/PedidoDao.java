package es.unizar.eina.T223_comidas.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** Definicion de un Data Access Object para los pedidos */
@Dao
public interface PedidoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Pedido pedido);

    @Update
    int update(Pedido pedido);

    @Delete
    int delete(Pedido pedido);

    @Query("DELETE FROM pedido")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM pedido")
    int getNumeroDePedidos();

    @Query("SELECT * FROM pedido ORDER BY cliente ASC")
    LiveData<List<Pedido>> getOrderedPedidosByCliente();

    @Query("SELECT * FROM pedido ORDER BY movil ASC")
    LiveData<List<Pedido>> getOrderedPedidosByMovil();

    @Query("SELECT * FROM pedido ORDER BY fecha ASC")
    LiveData<List<Pedido>> getOrderedPedidosByFecha();

    @Query("SELECT * FROM pedido ORDER BY estado ASC")
    LiveData<List<Pedido>> getOrderedPedidosByEstado();
}

