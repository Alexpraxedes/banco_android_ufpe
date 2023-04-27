package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;

import br.ufpe.cin.residencia.banco.cliente.ClientesActivity;
import br.ufpe.cin.residencia.banco.conta.ContasActivity;

//Ver anotações TODO no código
public class MainActivity extends AppCompatActivity {
    BancoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        LinearLayout contas = findViewById(R.id.btn_accounts);
        LinearLayout clientes = findViewById(R.id.btn_clients);
        RelativeLayout transferir = findViewById(R.id.rl_transfer_money);
        RelativeLayout debitar = findViewById(R.id.rl_debit_money);
        RelativeLayout creditar = findViewById(R.id.rl_credit_money);
        LinearLayout pesquisar = findViewById(R.id.btn_search);
        TextView totalBanco = findViewById(R.id.totalDinheiroBanco);
        TextView totalClientes = findViewById(R.id.text_total_clients);
        TextView totalContas = findViewById(R.id.text_total_accounts);
        TextView totalTransacoes = findViewById(R.id.text_total_transactions);

        //Remover a linha abaixo se for implementar a parte de Clientes
        clientes.setEnabled(false);

        contas.setOnClickListener(
                v -> startActivity(new Intent(this, ContasActivity.class))
        );
        clientes.setOnClickListener(
                v -> startActivity(new Intent(this, ClientesActivity.class))
        );
        transferir.setOnClickListener(
                v -> startActivity(new Intent(this, TransferirActivity.class))
        );
        creditar.setOnClickListener(
                v -> startActivity(new Intent(this, CreditarActivity.class))
        );
        debitar.setOnClickListener(
                v -> startActivity(new Intent(this, DebitarActivity.class))
        );
        pesquisar.setOnClickListener(
                v -> startActivity(new Intent(this, PesquisarActivity.class))
        );

        viewModel.contas.observe(
            this,
                listaContas -> {
                    double saldoBanco = viewModel.saldoTotalBanco();
                    totalBanco.setText("R"+NumberFormat.getCurrencyInstance().format(saldoBanco));

                    int totalDeClientes = viewModel.totalClientes();
                    totalClientes.setText(totalDeClientes+"");

                    int totalDeContas = viewModel.totalContas();
                    totalContas.setText(totalDeContas+"");

                    int totalDeTransacoes = viewModel.totalTransacoes();
                    totalTransacoes.setText(totalDeTransacoes+"");
                }
        );
    }
}