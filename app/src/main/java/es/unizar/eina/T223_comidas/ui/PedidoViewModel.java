package es.unizar.eina.T223_comidas.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.database.PlatoRepository;

public class PedidoViewModel extends AndroidViewModel {

    private PedidoRepository mRepository;

    private final LiveData<List<Pedido>> mAllPedidosCliente;
    private final LiveData<List<Pedido>> mAllPedidosMovil;
    private final LiveData<List<Pedido>> mAllPedidosFecha;
    private final LiveData<List<Pedido>> mAllPedidosEstado;
    private final LiveData<List<Pedido>> mAllPedidosPrevistos;
    private final LiveData<List<Pedido>> mAllPedidosVigentes;
    private final LiveData<List<Pedido>> mAllPedidosCaducados;


    public PedidoViewModel(Application application) {
        super(application);
        mRepository = new PedidoRepository(application);

        mAllPedidosCliente = mRepository.getAllPedidosByCliente();
        mAllPedidosMovil = mRepository.getAllPedidosByMovil();
        mAllPedidosFecha = mRepository.getAllPedidosByFecha();
        mAllPedidosEstado = mRepository.getAllPedidosByEstado();

        mAllPedidosPrevistos = mRepository.getAllPedidosPrevistos();
        mAllPedidosVigentes = mRepository.getAllPedidosVigentes();
        mAllPedidosCaducados = mRepository.getAllPedidosCaducados();
    }

    LiveData<List<Pedido>> getAllPedidosByCliente() { return mAllPedidosCliente; }
    LiveData<List<Pedido>> getAllPedidosByMovil() { return mAllPedidosMovil; }
    LiveData<List<Pedido>> getAllPedidosByFecha() { return mAllPedidosFecha; }
    LiveData<List<Pedido>> getAllPedidosByEstado() { return mAllPedidosEstado; }
    LiveData<List<Pedido>> getAllPedidosPrevistos() { return mAllPedidosPrevistos; }
    LiveData<List<Pedido>> getAllPedidosVigentes() { return mAllPedidosVigentes; }
    LiveData<List<Pedido>> getAllPedidosCaducados() { return mAllPedidosCaducados; }

    public long insert(Pedido pedido) { return mRepository.insert(pedido); }

    public int update(Pedido pedido) { return mRepository.update(pedido); }
    public int delete(Pedido pedido) { return mRepository.delete(pedido); }

    public int getNumeroDePedidos() { return mRepository.getNumeroDePedidos(); }

    public PedidoRepository getPedidoRepository() {
        return mRepository;
    }
}
