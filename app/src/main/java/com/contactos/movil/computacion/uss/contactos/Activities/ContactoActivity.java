package com.contactos.movil.computacion.uss.contactos.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.contactos.movil.computacion.uss.contactos.DAO.DaoContacto;
import com.contactos.movil.computacion.uss.contactos.Modelo.Contacto;
import com.contactos.movil.computacion.uss.contactos.R;

import java.util.jar.Manifest;

public class ContactoActivity extends AppCompatActivity {

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    EditText etNombreE, etTelefonoE, etEmailE;
    ImageView imagenAdd;
    DaoContacto daocontacto;
    Contacto contacto;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();


        etNombreE = (EditText) findViewById(R.id.etNombreE);
        etTelefonoE = (EditText) findViewById(R.id.etTelefonoE);
        etEmailE = (EditText) findViewById(R.id.etEmailE);
        imagenAdd = (ImageView)findViewById(R.id.imgAdd);
        daocontacto = new DaoContacto(this);

      imagenAdd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ActivityCompat.requestPermissions(ContactoActivity.this,
                      new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                      REQUEST_CODE_GALLERY
              );
          }
      });



        if (bundle != null) {
            contacto = new Contacto(bundle.get("email").toString(),
                    bundle.get("phone").toString(),
                    bundle.get("nombre").toString(),
                    Integer.parseInt(bundle.get("id").toString()));
            etNombreE.setText(contacto.getNombre());
            etTelefonoE.setText(contacto.getTelefono());
            etEmailE.setText(contacto.getEmail());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_guardar:
                if (!etNombreE.getText().toString().equals("") &&
                        !etTelefonoE.getText().toString().equals("") &&
                        !etEmailE.getText().toString().equals("")) {

                    if (etEmailE.getText().toString().matches(PATTERN_EMAIL)) {

                        daocontacto.updateEntry(new Contacto(etEmailE.getText().toString()
                                , etTelefonoE.getText().toString()
                                , etNombreE.getText().toString()
                                , contacto.getId()));
                        Intent i = new Intent(ContactoActivity.this, MainActivity.class);
                        startActivity(i);
                        Toast.makeText(ContactoActivity.this,"Editado Correctamente",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ContactoActivity.this, "Email incorrecto", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ContactoActivity.this, "Ingreso los datos requeridos", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
                intentGallery.setType("image/*");
                startActivityForResult(intentGallery, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Dont have permission", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY &&  resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
