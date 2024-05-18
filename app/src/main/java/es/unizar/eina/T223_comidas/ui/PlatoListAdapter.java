package es.unizar.eina.T223_comidas.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.database.Plato;

public class PlatoListAdapter extends ListAdapter<Plato, PlatoViewHolder> {
    private int position;
    private PlatoViewModel mPlatoViewModel;

    // Constructor que toma el ViewModel como parámetro
    public PlatoListAdapter(@NonNull DiffUtil.ItemCallback<Plato> diffCallback, PlatoViewModel platoViewModel) {
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
    public PlatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PlatoViewHolder.create(parent);
    }

    public Plato getCurrent() {
        return getItem(getPosition());
    }

    @Override
    public void onBindViewHolder(PlatoViewHolder holder, int position) {
        Plato current = getItem(position);
        String mensaje = current.getNombre() + " - " + current.getPrecio() + " €";
        holder.bind(mensaje, current.getCategoria());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Utiliza holder.getAdapterPosition() para obtener la posición actual
                int adapterPosition = holder.getAdapterPosition();
                Plato selectedPlato = getItem(adapterPosition);

                Intent intent = new Intent(v.getContext(), PlatoEdit.class);
                intent.putExtra(PlatoEdit.PLATO_NOMBRE, selectedPlato.getNombre());
                intent.putExtra(PlatoEdit.PLATO_DESC, selectedPlato.getDescripcion());
                intent.putExtra(PlatoEdit.PLATO_CATEG, selectedPlato.getCategoria());
                intent.putExtra(PlatoEdit.PLATO_PRECIO, selectedPlato.getPrecio());
                intent.putExtra(PlatoEdit.PLATO_ID, selectedPlato.getId());
                v.getContext().startActivity(intent);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });

        Button closeButton = holder.itemView.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Confirmar eliminación")
                        .setMessage("¿Estás seguro de que quieres eliminar el plato?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Utiliza holder.getAdapterPosition() para obtener la posición actual
                                int adapterPosition = holder.getAdapterPosition();

                                Plato selectedPlato = getItem(adapterPosition);
                                mPlatoViewModel.delete(selectedPlato);
                                notifyItemRemoved(adapterPosition);

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    static class PlatoDiff extends DiffUtil.ItemCallback<Plato> {
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

