package com.example.impostoderenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent intent = getIntent();

        TextView salarioText = findViewById(R.id.salarioText);
        TextView depenText = findViewById(R.id.depenText);
        TextView inssText = findViewById(R.id.inssText);
        TextView aliqText = findViewById(R.id.aliqText);
        TextView parcText = findViewById(R.id.parcText);
        TextView nmrIR = findViewById(R.id.nmrIR);

        salarioText.setText(intent.getStringExtra("salario1"));
        depenText.setText(intent.getStringExtra("deducaoCom"));
        inssText.setText(intent.getStringExtra("inssValor"));
        aliqText.setText(intent.getStringExtra("aliqValor") + "%");
        parcText.setText(intent.getStringExtra("deducaoValor"));
        nmrIR.setText("R$" + intent.getStringExtra("meuTotal"));
    }
}