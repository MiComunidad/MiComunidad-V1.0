package com.jhonatan.laboratorio_ii;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jhonatan.laboratorio_ii.Modelo.Senderos;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class RegistrarSenderoActivity extends AppCompatActivity {

    private EditText eNombre, eDescripcion,  ePuntos , eUbicacion;
    private ImageView iFoto;
    private Bitmap bitmap;
    String urlFoto = "";
    private String nombre="";
    private ArrayList<Senderos> senderosList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sendero);

        eNombre = findViewById(R.id.eNombre);
        eDescripcion = findViewById(R.id.eDescripcion);
        ePuntos = findViewById(R.id.ePuntos);
        eUbicacion = findViewById(R.id.eUbicacion);
        iFoto = findViewById(R.id.iFoto);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            senderosList = (ArrayList<Senderos>) bundle.get("senderos");
        }

        Log.d("nombre " ,  senderosList.get(0).getNombre().toString());

    }
    public void fotoClicked(View view) {
        Intent fotoIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        fotoIntent.setType("image/*");
        startActivityForResult(fotoIntent,1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234 && resultCode == RESULT_OK){
            if(data==null){
                Toast.makeText(this,"Error cargando foto",Toast.LENGTH_SHORT).show();
            }else{
                Uri imagen = data.getData();

                try {
                    InputStream is= getContentResolver().openInputStream(imagen);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bitmap= BitmapFactory.decodeStream(bis);

                    iFoto.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onButtonClicked(View view){




                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageReference = firebaseStorage.getReference();

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();




            for(int i =0 ; i<senderosList.size();i++) {
                if (eNombre.getText().toString().equals(senderosList.get(i).getNombre().toString())) {
                    Toast.makeText(this, "El nombre de sendero ya Extiste", Toast.LENGTH_SHORT).show();
                }
            }
        if(eNombre.getText().toString().equals("") || eUbicacion.getText().toString().equals("")|| ePuntos.getText().toString().equals("")|| eDescripcion.getText().toString().equals("")) {
            Toast.makeText(this,"Debe llenar todos los campos",Toast.LENGTH_SHORT).show();
        }
            else if(Integer.parseInt(ePuntos.getText().toString())<=1) {
                    Toast.makeText(this, "Deben ser  dos o mas puntos", Toast.LENGTH_SHORT).show();
                }else if(!(bitmap != null)){
                    Toast.makeText(this,"Ingrese una foto",Toast.LENGTH_SHORT).show();
            }else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(); //comprimir
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    byte[] data= baos.toByteArray();

                    storageReference.child("SenderosFotos").child(eNombre.getText().toString()).
                            putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            urlFoto = taskSnapshot.getDownloadUrl().toString();
                            Intent intent = new Intent(RegistrarSenderoActivity.this, MapsRescomendacionesActivity.class);
                            intent.putExtra("nombre", eNombre.getText().toString());
                            intent.putExtra("puntos", ePuntos.getText().toString());
                            intent.putExtra("descripcion", eDescripcion.getText().toString());
                            intent.putExtra("ubicacion", eUbicacion.getText().toString());
                            intent.putExtra("foto", urlFoto);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Error", e.getMessage().toString());
                        }
                    });
            }
    }

}
