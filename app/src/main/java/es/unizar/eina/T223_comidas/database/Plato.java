package es.unizar.eina.T223_comidas.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

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


    @Override
    public String toString() {
        return "Plato{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plato plato = (Plato) o;
        return id == plato.id && Double.compare(plato.precio, precio) == 0 && Objects.equals(nombre, plato.nombre) && Objects.equals(descripcion, plato.descripcion) && Objects.equals(categoria, plato.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, categoria, precio);
    }

    public Plato(@NonNull String nombre, String descripcion,
                 @NonNull String categoria, @NonNull double precio) {
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
