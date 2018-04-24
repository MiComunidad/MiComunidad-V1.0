package com.jhonatan.laboratorio_ii;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener  authStateListener;
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ComprobarLogin();


           }

    private void ComprobarLogin() {
        // comprobar cuentas logeadas
        if(FirebaseAuth.getInstance().getCurrentUser() == null
                              ) {

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }else {
            inicializar();
        }

    }

    private void inicializar() {
            firebaseAuth = FirebaseAuth.getInstance();

            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if(firebaseUser != null){
                        Log.d("Usuario logeado: ",firebaseUser.getEmail());
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
        getMenuInflater().inflate(R.menu.menuap,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.mMiPerfil:
                Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
                startActivity(intent);
                break;
            case R.id.mCerrarSe:
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
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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

    //impide que regrese a Login
    @Override
    public void onBackPressed() { moveTaskToBack(true); super.onBackPressed(); }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
