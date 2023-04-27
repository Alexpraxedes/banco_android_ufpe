package br.ufpe.cin.residencia.banco;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository repository;
    public LiveData<List<Conta>> contas;
    private MutableLiveData<Conta> _contas = new MutableLiveData<>();

    private MutableLiveData<List<Conta>> _listaContasAtual = new MutableLiveData<>();
    public LiveData<List<Conta>> listaContasAtual = _listaContasAtual;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.contas = repository.getContas();
    }

    public double saldoTotalBanco() {
        double saldoTotal = 0;
        for (Conta conta : this.contas.getValue()) {
            saldoTotal += conta.saldo;
        }
        return saldoTotal;
    }

    public int totalClientes() {
        return this.contas.getValue().size();
    }

    public int totalContas() {
        return this.contas.getValue().size();
    }

    public int totalTransacoes() {
        return 17;
    }

    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        new Thread( () -> {
            List<Conta> contas = (List<Conta>) this.repository.buscarPeloNumero(numeroContaOrigem);
            Conta contaOrigem = contas.get(0);
            contaOrigem.debitar(valor);
            this.repository.atualizar(contaOrigem);

            contas = (List<Conta>) this.repository.buscarPeloNumero(numeroContaDestino);
            Conta contaDestino = contas.get(0);
            contaDestino.creditar(valor);
            this.repository.atualizar(contaDestino);
        } ).start();
    }

    void creditar(String numeroConta, double valor) {
        new Thread( () -> {
            List<Conta> contas = (List<Conta>) this.repository.buscarPeloNumero(numeroConta);
            Conta conta = contas.get(0);
            conta.creditar(valor);
            this.repository.atualizar(conta);
        } ).start();
    }

    void debitar(String numeroConta, double valor) {
        new Thread( () -> {
            List<Conta> contas = (List<Conta>) this.repository.buscarPeloNumero(numeroConta);
            Conta conta = contas.get(0);
            conta.debitar(valor);
            this.repository.atualizar(conta);
        } ).start();
    }

    void buscarPeloNome(String nomeCliente) {
        new Thread( () -> {
            List<Conta> listaContas = this.repository.buscarPeloNome(nomeCliente);
            _listaContasAtual.postValue(listaContas);
        } ).start();
    }

    void buscarPeloCPF(String cpfCliente) {
        new Thread( () -> {
            List<Conta> listaContas = this.repository.buscarPeloCPF(cpfCliente);
            _listaContasAtual.postValue(listaContas);
        } ).start();
    }

    void buscarPeloNumero(String numeroConta) {
        new Thread( () -> {
            List<Conta> listaContas = this.repository.buscarPeloNumero(numeroConta);
            _listaContasAtual.postValue(listaContas);
        } ).start();
    }

}
