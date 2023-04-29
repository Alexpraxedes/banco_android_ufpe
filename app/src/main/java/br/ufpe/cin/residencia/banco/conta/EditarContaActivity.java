package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.UtilsMasks;
import br.ufpe.cin.residencia.banco.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditarContaActivity extends AppCompatActivity {
    public static final String KEY_NUMERO_CONTA = "numeroConta";
    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoSaldo = findViewById(R.id.saldo);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoCPF = findViewById(R.id.cpf);
        btnAtualizar.setText("Editar");
        campoNumero.setEnabled(false);

        campoCPF.addTextChangedListener(UtilsMasks.cpfMask(campoCPF));
        campoNumero.addTextChangedListener(UtilsMasks.contaMask(campoNumero));


        Intent intent = getIntent();
        String numeroConta = intent.getStringExtra(KEY_NUMERO_CONTA);
        viewModel.buscarPeloNumero(numeroConta);

        viewModel.contaAtual.observe( this, conta -> {
            campoNome.setText(conta.nomeCliente);
            campoNumero.setText(conta.numero);
            campoCPF.setText(conta.cpfCliente);
            campoSaldo.setText(String.valueOf(conta.saldo));
        });

        btnAtualizar.setOnClickListener(
            v -> {
                String nomeCliente = campoNome.getText().toString();
                String cpfCliente = campoCPF.getText().toString();
                String saldoConta = campoSaldo.getText().toString();
                //TODO: Incluir validações aqui, antes de criar um objeto Conta. Se todas as validações passarem, aí sim monta um objeto Conta.

                if (nomeCliente.isEmpty()) {
                    campoNome.setError("Campo obrigatório");
                    campoNome.requestFocus();
                    return;
                }

                if (cpfCliente.isEmpty()) {
                    campoCPF.setError("Campo obrigatório");
                    campoCPF.requestFocus();
                    return;
                }

                if(numeroConta.length() != 8){
                    campoNumero.setError("Número da conta deve ter 7 caracteres");
                    campoNumero.requestFocus();
                    return;
                }

                if (saldoConta.isEmpty()) {
                    campoSaldo.setError("Campo obrigatório");
                    campoSaldo.requestFocus();
                    return;
                }

                Conta contaAtualizada = new Conta(numeroConta, Double.parseDouble(saldoConta), nomeCliente, cpfCliente);
                viewModel.atualizar(contaAtualizada);
                finish();
            }
        );

        btnRemover.setOnClickListener(
            v -> {
                Conta conta = viewModel.contaAtual.getValue();
                viewModel.remover(conta);

                Context context = getApplicationContext();
                Intent newIntent = new Intent(context, ContasActivity.class);
                context.startActivity(newIntent);
            }
        );
    }
}