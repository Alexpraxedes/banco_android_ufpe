package br.ufpe.cin.residencia.banco.conta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.BancoDB;

public class ContaViewModel extends AndroidViewModel {
    private ContaRepository repository;
    public LiveData<List<Conta>> contas;
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;

    public ContaViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.contas = this.repository.contas;
    }

    void inserir(Conta c) {
        new Thread(
            () -> repository.inserir(c)
        ).start();
    }

    void atualizar(Conta c) {
        new Thread(
            () -> this.repository.atualizar(c)
        ).start();
    }

    void remover(Conta c) {
        new Thread(
            () -> this.repository.remover(c)
        ).start();
    }

    void buscarPeloNumero(String numeroConta) {
        new Thread( () -> {
            List<Conta> contas = this.repository.buscarPeloNumero(numeroConta);
            _contaAtual.postValue(contas.get(0));
        }).start();
    }
}
