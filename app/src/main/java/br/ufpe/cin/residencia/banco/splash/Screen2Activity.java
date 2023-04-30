package br.ufpe.cin.residencia.banco.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import br.ufpe.cin.residencia.banco.MainActivity;

public class Screen2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Screen2Activity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
