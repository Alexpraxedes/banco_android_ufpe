package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.conta.ContaAdapter;

//Ver anotações TODO no código
public class PesquisarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    ContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);

        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);
        adapter = new ContaAdapter(getLayoutInflater());

        RecyclerView rvResultado = findViewById(R.id.rvResultado);
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        tipoPesquisa.setOnCheckedChangeListener(
            (group, checkedId) -> {
                aPesquisar.setText("");

                switch (checkedId) {
                    case R.id.peloNomeCliente:
                        aPesquisar.setHint("Digite o nome do cliente");
                        break;

                    case R.id.peloCPFcliente:
                        aPesquisar.setHint("Digite o CPF do cliente");
                        aPesquisar.addTextChangedListener(UtilsMasks.cpfMask(aPesquisar));
                        break;

                    case R.id.peloNumeroConta:
                        aPesquisar.setHint("Digite o número da conta");
                        aPesquisar.addTextChangedListener(UtilsMasks.contaMask(aPesquisar));
                        break;
                }
            }
        );

        btnPesquisar.setOnClickListener(
            v -> {
                String oQueFoiDigitado = aPesquisar.getText().toString();
                //TODO implementar a busca de acordo com o tipo de busca escolhido pelo usuário
                if(!oQueFoiDigitado.isEmpty()){
                    switch (tipoPesquisa.getCheckedRadioButtonId()) {
                        case R.id.peloNomeCliente:
                            //TODO implementar a busca por nome
                            viewModel.buscarPeloNome(oQueFoiDigitado);
                            Toast.makeText(this, "Busca realizada por nome", Toast.LENGTH_SHORT).show();
                            break;

                        case R.id.peloCPFcliente:
                            //TODO implementar a busca por CPF
                            viewModel.buscarPeloCPF(oQueFoiDigitado);
                            Toast.makeText(this, "Busca realizada por CPF", Toast.LENGTH_SHORT).show();
                            break;

                        case R.id.peloNumeroConta:
                            //TODO implementar a busca por número
                            viewModel.buscarPeloNumero(oQueFoiDigitado);
                            Toast.makeText(this, "Busca realizada por número da conta", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(this, "Nenhum termo foi digitado para a busca", Toast.LENGTH_SHORT).show();
                }
            }
        );

        //TODO atualizar o RecyclerView com resultados da busca na medida que encontrar
        viewModel.listaContasAtual.observe(this, contas -> {
            if (contas.isEmpty()) {
                Toast.makeText(this, "Nenhum resultado encontrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Resultado encontrado", Toast.LENGTH_SHORT).show();
            }
            adapter.submitList(contas);
        });
    }
}