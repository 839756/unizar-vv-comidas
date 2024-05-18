package es.unizar.eina.T223_comidas.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.database.Pedido;

public class PedidoListAdapter extends ListAdapter<Pedido, PedidoViewHolder> {
    private int position;
    private PedidoViewModel mPedidoViewModel;
    private CantidadViewModel mCantidadViewModel;

    // Constructor que toma el ViewModel como parámetro
    public PedidoListAdapter(@NonNull DiffUtil.ItemCallback<Pedido> diffCallback, PedidoViewModel pedidoViewModel,
                             CantidadViewModel mCantidadViewModel) {
        super(diffCallback);
        this.mPedidoViewModel = pedidoViewModel;
        this.mCantidadViewModel = mCantidadViewModel;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PedidoViewHolder.create(parent);
    }

    public Pedido getCurrent() {
        return getItem(getPosition());
    }

    @Override
    public void onBindViewHolder(PedidoViewHolder holder, int position) {
        Pedido current = getItem(position);
        String mensaje = current.getCliente() + " - " + current.getFecha();
        holder.bind(mensaje, current.getEstado());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Utiliza holder.getAdapterPosition() para obtener la posición actual
                int adapterPosition = holder.getAdapterPosition();
                Pedido selectedPedido = getItem(adapterPosition);

                Intent intent = new Intent(v.getContext(), PedidoEdit.class);
                intent.putExtra(PedidoEdit.PEDIDO_CLIENTE, selectedPedido.getCliente());
                intent.putExtra(PedidoEdit.PEDIDO_MOVIL, selectedPedido.getMovil());
                intent.putExtra(PedidoEdit.PEDIDO_FECHA, selectedPedido.getFecha());
                intent.putExtra(PedidoEdit.PEDIDO_ESTADO, selectedPedido.getEstado());
                intent.putExtra(PedidoEdit.PEDIDO_ID, selectedPedido.getId());

                PedidoEdit.mPedidos = new MapaPedidos(mCantidadViewModel, current.getId());

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
                        .setMessage("¿Estás seguro de que quieres eliminar el pedido?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Utiliza holder.getAdapterPosition() para obtener la posición actual
                                int adapterPosition = holder.getAdapterPosition();

                                Pedido selectedPedido = getItem(adapterPosition);
                                mPedidoViewModel.delete(selectedPedido);
                                notifyItemRemoved(adapterPosition);

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    static class PedidoDiff extends DiffUtil.ItemCallback<Pedido> {
        @Override
        public boolean areItemsTheSame(@NonNull Pedido oldItem, @NonNull Pedido newItem) {
            return oldItem.getId() == newItem.getId();
        }

        public static boolean areEqual(double a, double b, double tolerance) {
            return Math.abs(a - b) < tolerance;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pedido oldItem, @NonNull Pedido newItem) {
            return oldItem.getCliente().equals(newItem.getCliente())  &&
                    oldItem.getFecha().equals(newItem.getFecha()) &&
                    areEqual(oldItem.getMovil(), newItem.getMovil(), 0.001) &&
                    oldItem.getEstado().equals(newItem.getEstado());
        }
    }
}

