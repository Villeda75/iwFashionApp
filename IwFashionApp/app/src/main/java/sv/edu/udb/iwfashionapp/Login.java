package sv.edu.udb.iwfashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import sv.edu.udb.iwfashionapp.models.Cliente;
import sv.edu.udb.iwfashionapp.services.DataBaseUtilities;
import sv.edu.udb.iwfashionapp.services.DatabaseAPI;

public class Login extends AppCompatActivity {
    EditText edt1, edt2;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN = 1;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    public String nombreUser="";
    public String ApellidoUser="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        edt1 = (EditText)findViewById(R.id.txtemaillog);
        edt2 = (EditText)findViewById(R.id.txtpasslog);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        firebaseAuth = FirebaseAuth.getInstance();
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.setReadPermissions(Arrays.asList("public_profile"));
        callbackManager = CallbackManager.Factory.create();
        Bundle bundle=getIntent().getExtras();


               if(getIntent().getExtras()!=null)
                {
               nombreUser=bundle.getString("nombre");
               ApellidoUser=bundle.getString("apellido");

                }
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(Login.this, "Cancelado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    Intent intentDashboard = new Intent(getApplicationContext(), MainActivity.class);

                    intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentDashboard);
                }
            }
        };
    }

    private void handleFacebookToken(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "Login exitoso", Toast.LENGTH_LONG).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Intent i2 = new Intent(Login.this,MainActivity.class);
                    Cliente _cliente= new Cliente(0,user.getUid(),"","",user.getEmail());
                    DataBaseUtilities dataBaseUtilities =new DataBaseUtilities();
                    dataBaseUtilities.VerifyUser(Login.this.getApplicationContext(),_cliente);
                    startActivity(i2);
                }else{
                    Log.d("Error", task.getException().toString());
                    Toast.makeText(Login.this, "Error login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    Toast.makeText(this, "Ocurrio un error. " + task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Ocurrio un error. " + task.getException().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this,"Sesión iniciada con éxito",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent i2 = new Intent(Login.this,MainActivity.class);

                            String nombres=user.getDisplayName().substring(0,user.getDisplayName().indexOf(" "));
                            String apellidos=user.getDisplayName().substring(user.getDisplayName().indexOf(" "),user.getDisplayName().length());

                            Cliente _cliente= new Cliente(0,user.getUid(),nombres,apellidos,user.getEmail());

                            DataBaseUtilities dataBaseUtilities =new DataBaseUtilities();
                            dataBaseUtilities.VerifyUser(Login.this.getApplicationContext(),_cliente);

                            DatabaseAPI database=new DatabaseAPI();
                            database.RegisterUser(Login.this.getApplicationContext(),user.getDisplayName(),user.getEmail());

                            startActivity(i2);
                        } else {
                            Toast.makeText(Login.this, "Ocurrio un error. " + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        firebaseAuth.addAuthStateListener(mAuthStateListener);
        super.onStart();
    }

    @Override
    protected void onStop() {
        firebaseAuth.removeAuthStateListener(mAuthStateListener);
        super.onStop();
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void SignGoogle(View view){
        signIn();
    }

    public void ToNewUser(View view){
        Intent i = new Intent(this,Loginuser.class);
        startActivity(i);
    }

    public void ToResetPassword(View view){
        Intent i = new Intent(this,Resetpassword.class);
        startActivity(i);
    }

    public void login(View view){
        String email = edt1.getText().toString();
        String password = edt2.getText().toString();

        if (password.length() == 0 || email.length() == 0)
            Toast.makeText(Login.this,"No pueden haber campos en blanco.",Toast.LENGTH_SHORT).show();
        else{
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this,"Sesión iniciada con éxito",Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Intent i2 = new Intent(Login.this,MainActivity.class);



                        Cliente _cliente= new Cliente(0,user.getUid(),nombreUser,ApellidoUser,user.getEmail());

                        DataBaseUtilities dataBaseUtilities =new DataBaseUtilities();
                        dataBaseUtilities.VerifyUser(Login.this.getApplicationContext(),_cliente);

                        DatabaseAPI database=new DatabaseAPI();
                        database.RegisterUser(Login.this.getApplicationContext(),nombreUser+" "+ApellidoUser,user.getEmail());

                        startActivity(i2);
                    }else{
                        String ErrorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        Toast.makeText(Login.this,ErrorCode,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}