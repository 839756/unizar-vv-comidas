package es.unizar.eina.T223_comidas.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa un plato y que consta de nombre, categoria,
 * descripcion y precio */

@Entity(tableName = "plato")
public class Plato {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @NonNull
    @ColumnInfo(name = "categoria")
    private String categoria;

    @NonNull
    @ColumnInfo(name = "precio")
    private double precio;

    public Plato(@NonNull String nombre, String descripcion,
                 @NonNull String categoria,@NonNull double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
    }

    /** Devuelve el identificador del plato */
    public int getId(){
        return this.id;
    }

    /** Permite actualizar el identificador del plato */
    public void setId(int id) {
        this.id = id;
    }

    /** Devuelve el nombre del plato */
    public String getNombre(){
        return this.nombre;
    }

    /** Devuelve la descripcion del plato */
    public String getDescripcion(){
        return this.descripcion;
    }

    /** Devuelve la categoria del plato */
    public String getCategoria(){
        return this.categoria;
    }

    /** Devuelve el precio del plato */
    public double getPrecio(){
        return this.precio;
    }

}
