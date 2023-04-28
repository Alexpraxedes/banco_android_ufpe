package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import br.ufpe.cin.residencia.banco.R;

public class ContasActivity extends AppCompatActivity {
    ContaAdapter adapter;
    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);
        Button adicionarConta = findViewById(R.id.btn_Adiciona);

        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);
        adapter = new ContaAdapter(getLayoutInflater());

        RecyclerView recyclerView = findViewById(R.id.rvContas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel.contas.observe(this, contas -> {
            adapter.submitList(contas);
        });

        adicionarConta.setOnClickListener(
            v -> {
                Intent intent = new Intent(this, AdicionarContaActivity.class);
                startActivity(intent);
            }
        );
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }
}