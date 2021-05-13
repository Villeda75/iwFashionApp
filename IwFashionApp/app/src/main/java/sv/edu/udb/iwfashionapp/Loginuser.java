package sv.edu.udb.iwfashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class Loginuser extends AppCompatActivity {
    EditText edt1, edt2, edt3, edt4, edt5;
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginuser);
        edt1 = (EditText)findViewById(R.id.txtname);
        edt2 = (EditText)findViewById(R.id.txtsname);
        edt3 = (EditText)findViewById(R.id.txtemailuser);
        edt4 = (EditText)findViewById(R.id.txtpassword);
        edt5 = (EditText)findViewById(R.id.txtconfpass);

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.txtemailuser, Patterns.EMAIL_ADDRESS,R.string.wrong_mail);
        awesomeValidation.addValidation(this,R.id.txtpassword, ".{6,}",R.string.wrong_pass);
    }

    public void Back(View view){
        finish();
    }

    public void IngresarUser(View view){
        String nombre = edt1.getText().toString();
        String apellido = edt2.getText().toString();
        String correo = edt3.getText().toString();
        String contra = edt4.getText().toString();
        String confcontra = edt5.getText().toString();

        if (nombre.length() == 0 || apellido.length() == 0 || correo.length() == 0 || contra.length() == 0)
            Toast.makeText(Loginuser.this,"No pueden haber campos en blanco.",Toast.LENGTH_SHORT).show();
        else if (!contra.equals(confcontra)){
            Toast.makeText(Loginuser.this,"Las contraseñas no coinciden.",Toast.LENGTH_SHORT).show();
        }else{
            if (awesomeValidation.validate()){
                firebaseAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Loginuser.this,"Usuario creado con éxito",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Loginuser.this,Login.class);
                            i.putExtra("nombre",nombre);
                            i.putExtra("apellido",apellido);
                            startActivity(i);
                            FirebaseAuth.getInstance().signOut();
                            Loginuser.this.finish();
                        }else{
                            String ErrorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(Loginuser.this,ErrorCode,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(Loginuser.this,"Los datos ingresados están incompletos",Toast.LENGTH_SHORT).show();
            }
        }
    }
}