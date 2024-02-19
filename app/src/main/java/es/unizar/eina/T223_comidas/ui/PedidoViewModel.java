package es.unizar.eina.T223_comidas.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;

public class PedidoViewModel extends AndroidViewModel {

    private PedidoRepository mRepository;

    private final LiveData<List<Pedido>> mAllPedidosCliente;
    private final LiveData<List<Pedido>> mAllPedidosMovil;
    private final LiveData<List<Pedido>> mAllPedidosFecha;
    private final LiveData<List<Pedido>> mAllPedidosEstado;

    public PedidoViewModel(Application application) {
        super(application);
        mRepository = new PedidoRepository(application);
        mAllPedidosCliente = mRepository.getAllPedidosByCliente();
        mAllPedidosMovil = mRepository.getAllPedidosByMovil();
        mAllPedidosFecha = mRepository.getAllPedidosByFecha();
        mAllPedidosEstado = mRepository.getAllPedidosByEstado();
    }

    LiveData<List<Pedido>> getAllPedidosByCliente() { return mAllPedidosCliente; }
    LiveData<List<Pedido>> getAllPedidosByMovil() { return mAllPedidosMovil; }
    LiveData<List<Pedido>> getAllPedidosByFecha() { return mAllPedidosFecha; }
    LiveData<List<Pedido>> getAllPedidosByEstado() { return mAllPedidosEstado; }

    public long insert(Pedido pedido) { return mRepository.insert(pedido); }

    public void update(Pedido pedido) { mRepository.update(pedido); }
    public void delete(Pedido pedido) { mRepository.delete(pedido); }

    public int getNumeroDePedidos() { return mRepository.getNumeroDePedidos(); }
}
