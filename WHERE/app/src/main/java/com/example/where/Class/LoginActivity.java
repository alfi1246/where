package

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.where.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText edtxtMail,edtxtSifre;
    private Button btnGirisYap,btnCreateAccount;
    private FirebaseAuth mAuth;
    private ProgressDialog loginProgress;
    private TextView noAccount;

public void init()
{
    edtxtMail=findViewById(R.id.loginMailTxt);
    edtxtSifre=findViewById(R.id.loginSifreTxt);
    btnGirisYap=findViewById(R.id.btn_signin);
    noAccount=findViewById(R.id.txtViewCreate);
    mAuth=FirebaseAuth.getInstance();
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRegister=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(goRegister);
            }
        });
        btnGirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edtxtMail.getText().toString();
                String sifre=edtxtSifre.getText().toString();
                if(!TextUtils.isEmpty(email)||!TextUtils.isEmpty(sifre))
                {
                    try {
                      //  loginProgress.setTitle("Oturum Açılıyor");
                      //  loginProgress.setMessage("Hesabınıza Giriş Yapılıyor");
                     //   loginProgress.setCanceledOnTouchOutside(false);
                      //  loginProgress.show();
                        loginUser(email,sifre);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(LoginActivity.this, "hata burda"+e, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



    }

    private void loginUser(String email, String password)
    {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if(task.isSuccessful())
                    {
                        //loginProgress.dismiss();
                        Toast.makeText(getApplicationContext(), "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                        Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(mainIntent);
                    }
                    else
                    {
                       // loginProgress.dismiss();
                        Toast.makeText(getApplicationContext(), "Giriş Başarısız ", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(LoginActivity.this, "hatamız: "+e, Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    }
