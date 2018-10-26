package com.ittepic.controltec;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ittepic.controltec.utilidades.Alumno;
import com.ittepic.controltec.utilidades.DialogoConfirmar;
import com.ittepic.controltec.utilidades.constantes;

public class RegistrodePracticaActivity extends AppCompatActivity {

    private EditText inputNombre, inputCarrera, inputNumControl, inputSemestre, inputCorreo, inputFecha;
    private TextInputLayout inputLayoutNombre, inputLayoutCarrera, inputLayoutNumControl,inputLayoutSemestre, inputLayoutCorreo, inputLayoutFecha;
    private Button btnConfirmar;

    private TextView mMensaje;
    private CardView mCardMessage;
    private Intent mIntent;
    private String mMensajeText;
    private static final String TAG = RegistrodePracticaActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrode_practica);

        initViews();
        initTextListeners();
        setMensaje();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_registro);
        setSupportActionBar(toolbar);
        inputNombre = (EditText)findViewById(R.id.input_name);
        inputLayoutNombre = (TextInputLayout)findViewById(R.id.input_layout_name);
        inputCarrera = (EditText)findViewById(R.id.input_carrera);
        inputLayoutCarrera = (TextInputLayout)findViewById(R.id.input_layout_carrera);
        inputNumControl = (EditText)findViewById(R.id.input_numcontrol);
        inputLayoutNumControl = (TextInputLayout)findViewById(R.id.input_layout_numcontrol);
        inputSemestre = (EditText)findViewById(R.id.input_semestre);
        inputLayoutSemestre = (TextInputLayout)findViewById(R.id.input_layout_semestre);
        inputCorreo = (EditText)findViewById(R.id.input_email);
        inputLayoutCorreo = (TextInputLayout)findViewById(R.id.input_layout_email);
        inputFecha = (EditText)findViewById(R.id.input_fecha);
        inputLayoutFecha = (TextInputLayout)findViewById(R.id.input_layout_fecha);

        btnConfirmar=(Button)findViewById(R.id.btn_confirmar_formulario);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmar();
            }
        });

        mMensaje = (TextView)findViewById(R.id.textView_message_cardView);
        mCardMessage = (CardView)findViewById(R.id.card_message);

        mIntent = getIntent();

        //Fecha y Hora se generan por default
        inputFecha.setText(DialogoConfirmar.getFechayHora());

    }

    private void initTextListeners(){
        inputNombre.addTextChangedListener(new TextListener(inputNombre,inputLayoutNombre));
        inputCarrera.addTextChangedListener(new TextListener(inputCarrera,inputLayoutCarrera));
        inputNumControl.addTextChangedListener(new TextListener(inputNumControl,inputLayoutNumControl));
        inputSemestre.addTextChangedListener(new TextListener(inputSemestre,inputLayoutSemestre));
        inputCorreo.addTextChangedListener(new TextListener(inputCorreo,inputLayoutCorreo));
    }

    private void setMensaje(){

        String s = mIntent.getExtras().getString(constantes.STRING_EXTRA_MENSAJE_CARDVIEW);

        if (s != null) {
            if(s.equals(constantes.MENSAJE_POSITIVO)){
                mMensajeText = mIntent.getExtras().getString(constantes.STRING_MENSAJE);
                mCardMessage.setVisibility(View.VISIBLE);
                mMensaje.setText(mMensajeText);
            }else if(s.equals(constantes.MENSAJE_NEGATIVO)){
                mCardMessage.setVisibility(View.GONE);
            }
        }
    }

    private boolean validar(EditText editText, TextInputLayout inputLayout) {
        //Este metodo comprueba que la casilla de texto no se encuentre vacia
        if (editText.getText().toString().trim().isEmpty()) {
            inputLayout.setError(getString(R.string.mensaje_error));
            requestFocus(editText);
            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void confirmar() {
        if (!validar(inputNombre, inputLayoutNombre)) { return; }
        if (!validar(inputCarrera, inputLayoutCarrera)) { return; }
        if (!validar(inputCorreo, inputLayoutCorreo)) { return; }
        if (!validar(inputSemestre, inputLayoutSemestre)) { return; }
        if (!validar(inputNumControl, inputLayoutNumControl)) { return; }

        String s = mIntent.getExtras().getString(constantes.STRING_EXTRA_MENSAJE_CARDVIEW);
        if (s != null) {
            if (s.equals(constantes.MENSAJE_POSITIVO)) {

                //Se envia el correo en caso de estar en modo edicion
                DialogoConfirmar.mailFromEdited(mMensajeText,
                        RegistrodePracticaActivity.this,
                        new Alumno(inputNombre.getText().toString(),
                                inputCarrera.getText().toString(),
                                Integer.valueOf(inputNumControl.getText().toString()),
                                Integer.valueOf(inputSemestre.getText().toString()),
                                inputCorreo.getText().toString()));
                Log.d(TAG, "Mensaje enviado");

            } else if (s.equals(constantes.MENSAJE_NEGATIVO)) {
                //se actualizan los datos del alumno
                Log.d(TAG, "Datos Actualizados");
            }
        }

    }

    private class TextListener implements TextWatcher {

            private EditText mEditText;
            private TextInputLayout mTextInputLayout;

            TextListener(EditText editText, TextInputLayout textInputLayout)
            {
                this.mEditText = editText;
                this.mTextInputLayout = textInputLayout;
            }
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            public void afterTextChanged(Editable editable) {
               validar(mEditText,mTextInputLayout);
            }
        }

    }