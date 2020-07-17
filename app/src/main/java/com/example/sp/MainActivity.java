package com.example.sp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.sp.clases.Contacto;
import com.example.sp.dialog.MiDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public final static int FORM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences s = getSharedPreferences("lista", MODE_PRIVATE);
        String newList = s.getString("lista", "no list");
        if (newList.equals("no list")) {
            Log.d("NO LIST", "NO LIST");
        } else {
            try {
                JSONArray array = new JSONArray(newList);
                actualizarTv(array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addUser:
                MiDialog m = new MiDialog("Nuevo Contacto",
                        "",
                        MainActivity.FORM,
                        this);
                m.show(super.getSupportFragmentManager(), "form");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void actualizarTv(JSONArray s) {
        TextView tv = findViewById(R.id.textViewList);
        tv.setText(s.toString());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        SharedPreferences s = getSharedPreferences("lista", MODE_PRIVATE);
        String newList = s.getString("lista", "no list");
        try {
            JSONArray array = new JSONArray(newList);
            boolean founs = false;
            MiDialog m = null;
            for(int i = 0; i<array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                if (object.getString("nombre").equals(query)) {
                    m = new MiDialog("Persona encontrada",
                            "el telefono es " + object.getString("telefono"),
                            0,
                            this
                            );
                    founs = true;
                }
            }
            if (!founs) {
                 m = new MiDialog("Persona no encontrada",
                        "",
                        0,
                        this
                );
            }
            m.show(super.getSupportFragmentManager(), "alert");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


}
