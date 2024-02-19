package es.unizar.eina.T223_comidas.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa la cantidad de raciones de un plato en un pedido */

@Entity(tableName = "cantidad",
        foreignKeys = {
                @ForeignKey(entity = Plato.class,
                        parentColumns = "id",
                        childColumns = "idPlato",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Pedido.class,
                        parentColumns = "id",
                        childColumns = "idPedido",
                        onDelete = ForeignKey.CASCADE)
        },
        primaryKeys = {"idPlato", "idPedido"})
public class Cantidad {
    @NonNull
    @ColumnInfo(name = "idPlato")
    private int idPlato;

    @NonNull
    @ColumnInfo(name = "idPedido")
    private int idPedido;

    @NonNull
    @ColumnInfo(name = "raciones")
    private int raciones;

    @NonNull
    @ColumnInfo(name = "precio")
    private Double precio;

    public Cantidad(@NonNull int idPlato, @NonNull int idPedido, @NonNull int raciones, @NonNull Double precio) {
        this.idPlato = idPlato;
        this.idPedido = idPedido;
        this.raciones = raciones;
        this.precio = precio;
    }

    /** Devuelve el identificador del plato */
    public int getIdPlato(){return this.idPlato;}

    /** Devuelve el identificador del pedido */
    public int getIdPedido(){return this.idPedido;}

    /** Devuelve las raciones de un plato en un pedido */
    public int getRaciones(){return this.raciones;}

    /** Devuelve el precio de un plato en un pedido */
    public Double getPrecio(){return this.precio;}

}
