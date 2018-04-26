package com.jhonatan.laboratorio_ii;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jhonatan.laboratorio_ii.Modelo.Servicios;
import com.jhonatan.laboratorio_ii.Modelo.Usuarios;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    TextView tNombre,tCorreo;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener  authStateListener;
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;
    private CircleImageView iFoto;
    private Bitmap bitmap;
    private String urlFoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        tNombre=findViewById(R.id.tNombre);
        tCorreo=findViewById(R.id.tCorreo);
        iFoto = findViewById(R.id.iFoto);

        inicializar();


    }



    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    tCorreo.setText("Correo: " + firebaseUser.getEmail() );
                    Picasso.get().load(firebaseUser.getPhotoUrl()).into(iFoto);
                }else {
                    Log.d("Usuario logeado: ", "No hay");
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().
                build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    //ASIGNA EL MENU PREVIAMENTE CREADO
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuperfil,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    //Seleccion del menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.mPrincipal:
                Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.mCerrarS:
               cerrarSesion();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        firebaseAuth.signOut();
        if(Auth.GoogleSignInApi != null) {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            });
        }
        if(LoginManager.getInstance() != null){
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
//Metodo para cargar foto de la multimedia
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
                    Bitmap bitmap= BitmapFactory.decodeStream(bis);

                    iFoto.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
               // SubirFotoFirebase();
            }
        }
    }

  /*  private void SubirFotoFirebase() {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //comprimir
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        storageReference.child("usuariosFotos").child(databaseReference.push().getKey()).
                putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                urlFoto = taskSnapshot.getDownloadUrl().toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error", e.getMessage().toString());
            }
        });
    }*/



}

