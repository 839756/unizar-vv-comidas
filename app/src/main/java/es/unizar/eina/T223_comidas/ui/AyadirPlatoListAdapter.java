package es.unizar.eina.T223_comidas.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.database.Plato;

public class AyadirPlatoListAdapter extends ListAdapter<Plato, AyadirPlatoViewHolder> {
    private int position;
    private PlatoViewModel mPlatoViewModel;

    // Constructor que toma el ViewModel como parámetro
    public AyadirPlatoListAdapter(@NonNull DiffUtil.ItemCallback<Plato> diffCallback, PlatoViewModel platoViewModel) {
        super(diffCallback);
        this.mPlatoViewModel = platoViewModel;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public AyadirPlatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AyadirPlatoViewHolder.create(parent);
    }

    public Plato getCurrent() {
        return getItem(getPosition());
    }

    @Override
    public void onBindViewHolder(AyadirPlatoViewHolder holder, int position) {
        Plato current = getItem(position);
        String mensaje = current.getNombre() + "\n" + current.getPrecio() + " €";
        holder.bind(mensaje, current.getCategoria(),String.valueOf(MapaPedidos.obtenerCantidadPlato(current.getId())));

        Button mas = holder.itemView.findViewById(R.id.MasButton);

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapaPedidos.ayadirPlato(current.getId(), current.getPrecio());
                holder.bind(mensaje, current.getCategoria(),String.valueOf(MapaPedidos.obtenerCantidadPlato(current.getId())));
            }
        });


        Button menos = holder.itemView.findViewById(R.id.MenosButton);

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapaPedidos.quitarPlato(current.getId());
                holder.bind(mensaje, current.getCategoria(),String.valueOf(MapaPedidos.obtenerCantidadPlato(current.getId())));
            }
        });
    }

    static class AyadirPlatoDiff extends DiffUtil.ItemCallback<Plato> {
        @Override
        public boolean areItemsTheSame(@NonNull Plato oldItem, @NonNull Plato newItem) {
            return oldItem.getId() == newItem.getId();
        }

        public static boolean areEqual(double a, double b, double tolerance) {
            return Math.abs(a - b) < tolerance;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Plato oldItem, @NonNull Plato newItem) {
            return oldItem.getNombre().equals(newItem.getNombre())  &&
                    oldItem.getCategoria().equals(newItem.getCategoria()) &&
                    areEqual(oldItem.getPrecio(), newItem.getPrecio(), 0.001) &&
                    oldItem.getDescripcion().equals(newItem.getDescripcion());
        }
    }
}

