package es.unizar.eina.T223_comidas.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa un pedido y que consta de cliente, movil,
 * fecha de recogida, estado y importe */

@Entity(tableName = "pedido")
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "cliente")
    private String cliente;

    @NonNull
    @ColumnInfo(name = "movil")
    private int movil;

    @NonNull
    @ColumnInfo(name = "fecha")
    private String fecha;

    @NonNull
    @ColumnInfo(name = "estado")
    private String estado;


    public Pedido(@NonNull String cliente,@NonNull int movil, @NonNull String fecha,
                   @NonNull String estado) {
        this.cliente = cliente;
        this.movil = movil;
        this.fecha = fecha;
        this.estado = estado;
    }

    /** Devuelve el identificador del pedido */
    public int getId(){
        return this.id;
    }

    /** Permite actualizar el identificador del pedido */
    public void setId(int id) {
        this.id = id;
    }

    /** Devuelve el cliente del pedido */
    public String getCliente(){
        return this.cliente;
    }

    /** Devuelve el movil del pedido */
    public int getMovil(){
        return this.movil;
    }

    /** Devuelve la fecha de recogida del pedido */
    public String getFecha(){
        return this.fecha;
    }

    /** Devuelve el estado del pedido */
    public String getEstado(){
        return this.estado;
    }

}
