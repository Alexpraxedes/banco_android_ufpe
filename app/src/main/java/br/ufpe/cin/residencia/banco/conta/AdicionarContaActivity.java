package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class AdicionarContaActivity extends AppCompatActivity {

    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);

        btnAtualizar.setText("Inserir");
        btnRemover.setVisibility(View.GONE);

        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String numeroConta = campoNumero.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    if(numeroConta.isEmpty()){
                        campoNumero.setError("Número da conta não pode ser vazio");
                        return;
                    }else if(numeroConta.length() != 6){
                        campoNumero.setError("Número da conta deve ter 6 caracteres");
                        return;
                    }

                    if(nomeCliente.isEmpty()){
                        campoNome.setError("Nome não pode ser vazio");
                        return;
                    }else if(nomeCliente.length() < 5){
                        campoNome.setError("Nome deve ter pelo menos 5 caracteres");
                        return;
                    }

                    if(cpfCliente.isEmpty()){
                        campoCPF.setError("CPF não pode ser vazio");
                        return;
                    }else if(cpfCliente.length() != 11){
                        campoCPF.setError("CPF deve ter 11 caracteres");
                        return;
                    }

                    if(saldoConta.isEmpty()){
                        campoSaldo.setError("Saldo não pode ser vazio");
                        return;
                    }

                    Conta c = new Conta(numeroConta, Double.parseDouble(saldoConta), nomeCliente, cpfCliente);
                    viewModel.inserir(c);
                    finish();
                }
        );

    }
}