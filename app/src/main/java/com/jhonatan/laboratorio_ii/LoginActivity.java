package com.jhonatan.laboratorio_ii;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener  authStateListener;
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;

    private EditText eCorreo,eContraseña;
    private SignInButton btnSignInGoogle;
    private LoginButton btnSignInFaceboook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        eCorreo= findViewById(R.id.eCorreo);
        eContraseña= findViewById(R.id.eContraseña);
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle);
        btnSignInFaceboook = findViewById(R.id.btnSignInFacebook);

        btnSignInGoogle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w){
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,1);
            }
        });
        inicializar();

        btnSignInFaceboook.setReadPermissions("email","public_profile");

        btnSignInFaceboook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login con Facebook","Login Exitoso");
                signInFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Login con Facebook","Login Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Login con Facebook","Login Error");
                error.printStackTrace();
            }
        });


        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.jhonatan.laboratorio_ii",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
//metodo de logeo con Facebook
    private void signInFacebook(AccessToken accessToken) {

        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(LoginActivity.this, "Autenticacion con Facebook no exitosa",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi
                    .getSignInResultFromIntent(data);
            signInGoogle(googleSignInResult);

        }else{
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }
    private void signInGoogle(GoogleSignInResult googleSignInResult){
        if(googleSignInResult.isSuccess()){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(
                    googleSignInResult.getSignInAccount().getIdToken(),null);

            firebaseAuth.signInWithCredential(authCredential).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                               Intent i = new Intent(LoginActivity.this,MainActivity.class);
                               startActivity(i);
                            }
                        }
                    });
        }

    }
    private void inicializar(){
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
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }
    //Función para llamar a la actividad de registro
    public void onClickedTextWiew(View view) {

            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);

    }
    //Funcion que compara los DatosUsuario con los escritos en los edittext
    public void onButtonClicked(View view) {

                iniciarSesionFirebase(eCorreo.getText().toString(),eContraseña.getText().toString());
    }

    private void iniciarSesionFirebase(String correo,String contraseña) {
        firebaseAuth.signInWithEmailAndPassword(correo,contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Correo o contraseñas diferentes", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
