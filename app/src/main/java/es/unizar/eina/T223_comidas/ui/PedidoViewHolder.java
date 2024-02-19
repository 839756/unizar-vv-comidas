package es.unizar.eina.T223_comidas.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.T223_comidas.R;

class PedidoViewHolder extends RecyclerView.ViewHolder {
    private final TextView mPedidoItemView;
    private final TextView mPedidoItemView2;

    private PedidoViewHolder(View itemView) {
        super(itemView);
        mPedidoItemView = itemView.findViewById(R.id.textView);
        mPedidoItemView2 = itemView.findViewById(R.id.textView2);
    }

    public void bind(String text1, String text2) {
        mPedidoItemView.setText(text1);
        mPedidoItemView2.setText(text2);
    }

    static PedidoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        return new PedidoViewHolder(view);
    }

}

