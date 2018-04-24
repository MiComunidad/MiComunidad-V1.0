package com.jhonatan.laboratorio_ii;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends AppCompatActivity {
    TextView tNombre,tCorreo,tLugar,tFecha,tSexo,tContraseña;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener  authStateListener;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tNombre=findViewById(R.id.tNombre);
        tContraseña = findViewById(R.id.tContraseña);
        tCorreo=findViewById(R.id.tCorreo);
        tLugar=findViewById(R.id.tLugar);
        tFecha=findViewById(R.id.tFecha);
        tSexo=findViewById(R.id.tSexo);

        inicializar();

    }
    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.d("Usuario logeado: ",firebaseUser.getEmail());
                }else{
                    Log.d("Usuario logeado: ", "No hay");
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().
                build();

        googleApiClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
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
        Intent intent2 = new Intent(PerfilActivity.this, LoginActivity.class);
        startActivity(intent2);
        finish();
    }
}

