package com.example.sp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.sp.MainActivity;
import com.example.sp.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MiDialog extends DialogFragment {

    private String titulo;
    private String mensaje;
    private int type;
    private MainActivity mainActivity;

    public MiDialog(String titulo, String mensaje, int type, MainActivity mainActivity) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.type = type;
        this.mainActivity = mainActivity;
    }


    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder b = new AlertDialog.Builder(
                super.getActivity()
        );
        b.setTitle(this.titulo);
        b.setMessage(this.mensaje);

        View v = null;
        LayoutInflater l = LayoutInflater.from(getActivity());
        if (this.type == MainActivity.FORM) {
            v = l.inflate(R.layout.dialog_form, null);
        } else {
           v = l.inflate(R.layout.dialog, null);
        }
        b.setView(v);

        MiClickDialogListener cd = new MiClickDialogListener(v, this.mainActivity, this.type);
        if (this.type == MainActivity.FORM) {
            b.setPositiveButton("GUARDAR", cd);
            b.setNeutralButton("CANCELAR", cd);
        } else {
            b.setPositiveButton("OK", cd);
        }
        return b.create();
    }
}
