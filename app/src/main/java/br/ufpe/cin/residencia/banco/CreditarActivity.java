package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

//Ver anotações TODO no código
public class CreditarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacoes);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        TextView tipoOperacao = findViewById(R.id.tipoOperacao);
        EditText numeroContaOrigem = findViewById(R.id.numeroContaOrigem);
        TextView labelContaDestino = findViewById(R.id.labelContaDestino);
        EditText numeroContaDestino = findViewById(R.id.numeroContaDestino);
        LinearLayout campoContaDestino = findViewById(R.id.fieldForgetAccount);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);
        labelContaDestino.setVisibility(View.GONE);
        numeroContaDestino.setVisibility(View.GONE);
        valorOperacao.setHint(valorOperacao.getHint() + " creditado");

        numeroContaOrigem.addTextChangedListener(UtilsMasks.contaMask(numeroContaOrigem));
        numeroContaDestino.addTextChangedListener(UtilsMasks.contaMask(numeroContaDestino));

        campoContaDestino.setVisibility(View.GONE);
        tipoOperacao.setText("CREDITAR");
        btnOperacao.setText("Creditar");

        btnOperacao.setOnClickListener(
            v -> {
                String numOrigem = numeroContaOrigem.getText().toString();
                //TODO lembrar de implementar validação do número da conta e do valor da operação, antes de efetuar a operação de crédito.
                // O método abaixo está sendo chamado, mas precisa ser implementado na classe BancoViewModel para funcionar.
                if (numOrigem.isEmpty()) {
                    numeroContaOrigem.setError("Número da conta não pode ser vazio");
                    return;
                }

                if (valorOperacao.getText().toString().isEmpty()) {
                    valorOperacao.setError("Valor da operação não pode ser vazio");
                    return;
                }

                double valor = Double.parseDouble(valorOperacao.getText().toString());
                viewModel.creditar(numOrigem,valor);
                finish();
            }
        );
    }
}