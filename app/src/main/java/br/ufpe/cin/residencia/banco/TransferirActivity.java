package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;

//Ver anotações TODO no código
public class TransferirActivity extends AppCompatActivity {

    BancoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacoes);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        TextView tipoOperacao = findViewById(R.id.tipoOperacao);
        EditText numeroContaOrigem = findViewById(R.id.numeroContaOrigem);
        EditText numeroContaDestino = findViewById(R.id.numeroContaDestino);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);

        numeroContaOrigem.addTextChangedListener(UtilsMasks.contaMask(numeroContaOrigem));
        numeroContaDestino.addTextChangedListener(UtilsMasks.contaMask(numeroContaDestino));

        valorOperacao.setHint(valorOperacao.getHint() + " transferido");
        tipoOperacao.setText("TRANSFERIR");
        btnOperacao.setText("Transferir");

        btnOperacao.setOnClickListener(
            v -> {
                String numOrigem = numeroContaOrigem.getText().toString();
                String numDestino = numeroContaDestino.getText().toString();
                //TODO lembrar de implementar validação dos números das contas e do valor da operação, antes de efetuar a operação de transferência.
                // O método abaixo está sendo chamado, mas precisa ser implementado na classe BancoViewModel para funcionar.
                if (numOrigem.isEmpty()) {
                    numeroContaOrigem.setError("Número da conta não pode ser vazio");
                    return;
                }

                if (numOrigem.length() != 8) {
                    numeroContaOrigem.setError("Número da conta deve ter 7 caracteres");
                    return;
                }

                if (numDestino.isEmpty()) {
                    numeroContaDestino.setError("Número da conta não pode ser vazio");
                    return;
                }

                if (numDestino.length() != 8) {
                    numeroContaDestino.setError("Número da conta deve ter 7 caracteres");
                    return;
                }

                if (numOrigem.equals(numDestino)) {
                    numeroContaDestino.setError("Número da conta destino não pode ser igual ao número da conta origem");
                    return;
                }

                if (valorOperacao.getText().toString().isEmpty()) {
                    valorOperacao.setError("Valor da operação não pode ser vazio");
                    return;
                }

                if (Double.parseDouble(valorOperacao.getText().toString()) <= 0) {
                    valorOperacao.setError("Valor da operação não pode ser menor que zero");
                    return;
                }

                double valor = Double.parseDouble(valorOperacao.getText().toString());
                viewModel.transferir(numOrigem, numDestino, valor);
                finish();

                if(BancoViewModel.status){
                    Toast.makeText(this, "Transferência realizada com sucesso!", Toast.LENGTH_SHORT).show();

                } else {
                    viewModel.erroMsg.observe(this, errorMsg -> {
                        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        );
    }
}