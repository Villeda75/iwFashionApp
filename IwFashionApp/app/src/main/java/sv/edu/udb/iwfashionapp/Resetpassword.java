package sv.edu.udb.iwfashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class Resetpassword extends AppCompatActivity {
    private EditText edt1;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        edt1 = (EditText)findViewById(R.id.txtreset);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void Back(View view){
        finish();
    }

    public void ResetPass(View view){
        String email = edt1.getText().toString();

        if (email.length() == 0)
            Toast.makeText(this,"Debes ingresar un correo electrónico.",Toast.LENGTH_SHORT).show();
        else{
            progressDialog.setMessage("Espere un momento...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth.setLanguageCode("es");
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Resetpassword.this, "Se ha enviado un correo para restablecer tu contraseña.", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(Resetpassword.this, Login.class);
                        startActivity(i2);
                        finish();
                    }else{
                        String ErrorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        Toast.makeText(Resetpassword.this,ErrorCode,Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }
}