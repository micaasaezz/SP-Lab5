package com.example.sp.dialog;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sp.MainActivity;
import com.example.sp.R;
import com.example.sp.clases.Contacto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MiClickDialogListener implements DialogInterface.OnClickListener {
    private View v;
    private MainActivity m;
    private int type;
    public MiClickDialogListener(View v, MainActivity m, int type) {
        this.v = v;
        this.m = m;
        this.type = type;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (this.type == MainActivity.FORM) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                EditText edName = this.v.findViewById(R.id.edName);
                EditText edTel = this.v.findViewById(R.id.edTel);
                if (!edName.getText().toString().isEmpty()
                    && !edTel.getText().toString().isEmpty()) {
                    Contacto c = new Contacto(
                            edName.getText().toString(),
                            edTel.getText().toString()
                    );
                    SharedPreferences s = this.m.getSharedPreferences("lista", MODE_PRIVATE);
                    String textList = s.getString("lista", "no list");
                    JSONArray array = null;
                    if (textList.equals("no list")) {
                        array = new JSONArray();
                    } else {
                        try {
                            array = new JSONArray(textList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    JSONObject o = new JSONObject();
                    try {
                        o.put("nombre", c.getNombre());
                        o.put("telefono", c.getTelefono());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    array = array.put(o);
                    SharedPreferences.Editor editor = s.edit();
                    Log.d("ACA", String.valueOf(array));
                    editor.putString("lista", String.valueOf(array));
                    editor.commit();
                    this.m.actualizarTv(array);
                }
            }
        } else {

        }
    }
}
