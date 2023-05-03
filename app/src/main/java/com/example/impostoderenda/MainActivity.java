package com.example.impostoderenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private EditText editSal;
    private EditText editDep;
    private TextView resultado;


    //buttons
    public Button buttonMAIS;
    public Button buttonMENOS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSal = findViewById(R.id.editSal);
        editDep = findViewById(R.id.editDep);
        resultado = findViewById(R.id.resultado);

        buttonMAIS= findViewById(R.id.buttonMAIS);
        buttonMENOS= findViewById(R.id.buttonMENOS);

        editSal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus && editSal.getText().toString().equals("0.00")) {
                    editSal.setText("");
                }
            }
        });


        buttonMAIS.setOnClickListener(v -> {
            if (editDep.getText() == null || editDep.getText().toString().isEmpty()) {
                editDep.setText("0");
            } else {
                int valorAtual = Integer.parseInt(editDep.getText().toString());
                int novoValor = valorAtual + 1;
                editDep.setText(Integer.toString(novoValor));
            }
        });

        buttonMENOS.setOnClickListener(v -> {
            int valorAtual = Integer.parseInt(editDep.getText().toString());
            if (valorAtual <= 0){
                editDep.setText("0");
            } else {
                int novoValor = valorAtual - 1;
                editDep.setText(Integer.toString(novoValor));
            }
        });
    }


    public void calculoIr (View view) {
        if (editSal.getText() == null || editSal.getText().toString().isEmpty()) {
            resultado.setText("É necessário preencher todos os campos corretamente");
            return;
        };
        if (editDep.getText() == null || editDep.getText().toString().isEmpty()) {
            editDep.setText("0");
        };

        BigDecimal dependentes0 = new BigDecimal(editDep.getText().toString());
        BigDecimal nmrd = new BigDecimal("189.59");

        BigDecimal salario = new BigDecimal(editSal.getText().toString());
        BigDecimal dependentes = dependentes0.multiply(nmrd); //189.59 por dependente
        BigDecimal inss = new BigDecimal(0);
        BigDecimal aliquota = new BigDecimal(0);
        BigDecimal deducao = new BigDecimal(0);
        BigDecimal valorFinal = new BigDecimal(0);

        //aliquotas
        BigDecimal setemeio = new BigDecimal("0.075");
        BigDecimal nove = new BigDecimal("0.09");
        BigDecimal doze = new BigDecimal("0.12");
        BigDecimal quatorze = new BigDecimal("0.14");
        BigDecimal quinze = new BigDecimal("0.15");
        BigDecimal vintdoismei = new BigDecimal("0.225");
        BigDecimal vintsetmei = new BigDecimal("0.275");

        //faixas de salário inss
        BigDecimal faixa1 = new BigDecimal("1302");
        BigDecimal faixa2 = new BigDecimal("251.29");
        BigDecimal faixa3 = new BigDecimal("3856.94");
        BigDecimal faixa4 = new BigDecimal("7507.49");

        //calculando inss
        BigDecimal teto = new BigDecimal("877.22");

        if (salario.compareTo(faixa1) <= 0){
            inss = salario.multiply(setemeio);
        } else if (salario.compareTo(faixa1) > 0 && salario.compareTo(faixa2) <=0) {
            inss = salario.multiply(nove);
        } else if (salario.compareTo(faixa2) > 0 && salario.compareTo(faixa3) <=0) {
            inss = salario.multiply(doze);
        } else if (salario.compareTo(faixa3) > 0 && salario.compareTo(faixa4) < 0) {
            inss = salario.multiply(quatorze);
        } else if (salario.compareTo(faixa4) >= 0) {
            inss = salario.multiply(quatorze);
        }

        if (inss.compareTo(teto) > 0){
            inss = teto;
        }

        //calculando a primeira parte da fórmula

        BigDecimal formula1 = salario.subtract(dependentes).subtract(inss);


        //faixas de salário imposto de renda
        BigDecimal sal1 = new BigDecimal("1903.98");
        BigDecimal sal2 = new BigDecimal("2826.65");
        BigDecimal sal3 = new BigDecimal("3751.05");
        BigDecimal sal4 = new BigDecimal("4664.68");

        //calculando aliquota

        if (formula1.compareTo(sal1) > 0 && formula1.compareTo(sal2) <=0) {
            aliquota = setemeio;
            deducao = new BigDecimal("142.80");

        } else if (formula1.compareTo(sal2) > 0 && formula1.compareTo(sal3) <=0) {
            aliquota = quinze;
            deducao = new BigDecimal("354.80");

        } else if (formula1.compareTo(sal3) > 0 && formula1.compareTo(sal4) <= 0) {
            aliquota = vintdoismei;
            deducao = new BigDecimal("636.13");

        } else if (formula1.compareTo(sal4) > 0) {
            aliquota = vintsetmei;
            deducao = new BigDecimal("869.36");
        }

        //calculando a segunda parte da fórmula

        valorFinal = formula1.multiply(aliquota).subtract(deducao);

        BigDecimal Total = valorFinal.setScale(2, RoundingMode.HALF_EVEN);

        salario = salario.setScale(2, RoundingMode.HALF_EVEN);

        float aliqPorcen = aliquota.multiply(new BigDecimal(100)).floatValue();

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("salario1", salario.toString());
        intent.putExtra("deducaoCom", deducao.add(dependentes).toString());
        intent.putExtra("inssValor", inss.toString());
        intent.putExtra("aliqValor", String.valueOf(aliqPorcen));
        intent.putExtra("deducaoValor", deducao.toString());
        intent.putExtra("meuTotal", Total.toString());
        startActivity(intent);

    }
}