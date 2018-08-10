package com.calculadorabss.gorydev.calculadorabolivaressoberanos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    public EditText editCantidad;
    public Button btnCalcular, btnLimpiar;
    public TextView txtResultado;
    public final double CONVERSION = 100000;
    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("###,###.##");//Dar formato al texto de salida

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCantidad = findViewById(R.id.editMonto);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        txtResultado = findViewById(R.id.txtResultado);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertirMonto();
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarText();
            }
        });
        editCantidad.addTextChangedListener(new NumberTextWatcher(editCantidad));

    }

    private void limpiarText() {
        editCantidad.setText("");
        txtResultado.setText("");
    }

    public void convertirMonto() {
        if (!editCantidad.getText().equals("")) {
            try {
                double monto = Double.parseDouble(editCantidad.getText().toString().replace(",", ""));
                double resultado = monto /= CONVERSION;
                String salida = "";
                //txtResultado.setText(String.valueOf(resultado));
                salida = "Son: " + REAL_FORMATTER.format((resultado)) + " Bs.S";
                txtResultado.setText(salida);
            } catch (NumberFormatException e) {
                //Log.e("Error-->", "convertirMonto: " + e.getMessage());
                Toast.makeText(this, "Por favor indique una cantidad para calcular", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



