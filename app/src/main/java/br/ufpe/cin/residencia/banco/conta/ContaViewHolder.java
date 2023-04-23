package br.ufpe.cin.residencia.banco.conta;

import static br.ufpe.cin.residencia.banco.conta.EditarContaActivity.KEY_NUMERO_CONTA;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;

import br.ufpe.cin.residencia.banco.R;

public class ContaViewHolder extends RecyclerView.ViewHolder {
    public static final String KEY_NUMERO_CONTA = "numeroConta";
    TextView nomeCliente = null;
    TextView infoConta = null;
    ImageView icone = null;

    public ContaViewHolder(@NonNull View linha) {
        super(linha);
        this.nomeCliente = linha.findViewById(R.id.nomeCliente);
        this.infoConta = linha.findViewById(R.id.infoConta);
        this.icone = linha.findViewById(R.id.icone);
    }

    @SuppressLint("SetTextI18n")
    void bindTo(Conta conta) {
        this.nomeCliente.setText(conta.nomeCliente);
        this.infoConta.setText(conta.numero + " | " + "Saldo atual: R$" + NumberFormat.getCurrencyInstance().format(conta.saldo));
        if(conta.saldo < 0)
            this.icone.setImageResource(R.drawable.delete);
        else
            this.icone.setImageResource(R.drawable.ok);
        this.addListener(conta.numero);
    }

    public void addListener(String numeroConta) {
        this.itemView.setOnClickListener(
            v -> {
                Context context = this.itemView.getContext();
                Intent intent = new Intent(context, EditarContaActivity.class);
                intent.putExtra(KEY_NUMERO_CONTA, numeroConta);
                context.startActivity(intent);
            }
        );
    }
}
