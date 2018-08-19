package com.calculadorabss.gorydev.calculadorabolivaressoberanos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    private EditText editCantidad;
    private Button btnCalcular, btnLimpiar;
    private TextView txtResultado;
    private RadioButton radioBsSaBsf, radioBsFaBsS;
    private RadioGroup radioGroup;
    private final double CONVERSION = 100000;
    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("###,##0.00");//Dar formato al texto de salida

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCantidad = findViewById(R.id.editMonto);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        txtResultado = findViewById(R.id.txtResultado);
        radioBsFaBsS = findViewById(R.id.radioBsFaBsS);
        radioBsSaBsf = findViewById(R.id.radioBsSaBsF);
        radioGroup = findViewById(R.id.radioGroupOperaciones);


        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getBaseContext(),"Seleccione una operación.",Toast.LENGTH_SHORT).show();
                }else{
                    int idSeleccionado = radioGroup.getCheckedRadioButtonId();
                    if(radioBsFaBsS.getId() == idSeleccionado){
                        convertirDeBsFaBsS();
                    }else{
                        convertirDeBsSaBsF();
                    }
                }
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarText();
            }
        });
        //Método para ir dando formato a los números que se van escribiendo.
        editCantidad.addTextChangedListener(new NumberTextWatcher(editCantidad));

        //Método para limpiar el campo de texto cuando se cambia la operación que desea realizar.
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                limpiarText();
            }
        });

    }

    private void limpiarText() {
        editCantidad.setText("");
        txtResultado.setText("");
    }

    private boolean editTextIsEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;//false no esta vacia
    }

    public void convertirDeBsFaBsS() {

        if (editTextIsEmpty(editCantidad)) {
            Toast.makeText(this, "Por favor indique una cantidad para calcular", Toast.LENGTH_SHORT).show();
        }else{
            try {
                double monto = Double.parseDouble(editCantidad.getText().toString().replace(",", ""));
                if(monto >= 1000){
                    double resultado = monto / CONVERSION;
                    String salida = "";
                    //txtResultado.setText(String.valueOf(resultado));
                    salida = "Son: " + REAL_FORMATTER.format((resultado)) + " Bs.Soberanos";
                    txtResultado.setText(salida);
                }else{
                    Toast.makeText(this,"Indique una cantidad mayor a 1.000 Bs.F ya que montos menores no tienen equivalencia en " +
                            "el nuevo cono monetario.", Toast.LENGTH_LONG).show();
                    txtResultado.setText("Son: 0 Bs Soberanos");
                    editCantidad.setError("Indique una cantidad mayor a 1.000 Bs.F");
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Error en la conversión: Debe introducir números ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void convertirDeBsSaBsF(){
        if(editTextIsEmpty(editCantidad)){
            Toast.makeText(this,"Por favor indique una cantidad para calcular.",Toast.LENGTH_SHORT).show();
        }else{
            try {
                double monto = Double.parseDouble(editCantidad.getText().toString().replace(",",""));
                double resultado = monto * CONVERSION;
                String salida = "Son: " + REAL_FORMATTER.format((resultado)) + " Bs.Fuertes";
                txtResultado.setText(salida);
            }catch(NumberFormatException e){
                Toast.makeText(this, "Error en la conversión: Debe introducir números", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



