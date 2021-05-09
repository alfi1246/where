package

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
EditText edtxtEmail,edtxtSifre,edtxtuserAd,edtxtUserSr,edtxtChildAd,edtxtChildSirname;
Button btnKayıtOl;
TextView txt;
    private ProgressDialog registerProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtxtEmail=findViewById(R.id.edtxtEmail);
        edtxtSifre=findViewById(R.id.edtxtKullanici);
        edtxtuserAd=findViewById(R.id.edtxtPassword);
        edtxtUserSr=findViewById(R.id.edtxtKsoyadi);
        edtxtChildAd=findViewById(R.id.edtxtTakip);
        edtxtChildSirname=findViewById(R.id.edtxtTakipSoyadi);
        txt=findViewById(R.id.textView);
        registerProgress=new ProgressDialog(this);

        btnKayıtOl=findViewById(R.id.btnKayitOl);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
txt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       Intent goLogin=new Intent(RegisterActivity.this,LoginActivity.class);
       startActivity(goLogin);
    }
});
    btnKayıtOl.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                String name=edtxtEmail.getText().toString();
                String password=edtxtSifre.getText().toString();
                String ad=edtxtuserAd.getText().toString();
                String syoad=edtxtUserSr.getText().toString();
                String had=edtxtChildAd.getText().toString();
                String hsoyad=edtxtChildSirname.getText().toString();

                    registerProgress.setTitle("kaydediliyor");
                    registerProgress.setMessage("bekle");
                    registerProgress.setCanceledOnTouchOutside(false);
                    registerProgress.show();
                    register_user(name,ad,password,hsoyad,syoad,had);
            }
            catch (Exception ekl)
            {
                Toast.makeText(RegisterActivity.this, "hataburda "+ekl, Toast.LENGTH_SHORT).show();
            }

        }
    });




    }


    private void register_user(final String name, final String password, final String ad, final String hsoyad, final String syoad, final String had) {
        if(mAuth.getCurrentUser()==null)
        {
            mAuth.createUserWithEmailAndPassword(name,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        try {
                            String user_id=mAuth.getCurrentUser().getUid();
                            mDatabase= FirebaseDatabase.getInstance().getReference().child("").child(user_id);
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("",name);
                            hashMap.put("",password);
                            hashMap.put("",ad);;
                            hashMap.put("",syoad);
                            hashMap.put(" ",hsoyad);
                            mDatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {

                                        registerProgress.dismiss();
                                        Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(mainIntent);
                                        Toast.makeText(RegisterActivity.this, "İşlem Başarılı", Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                        registerProgress.dismiss();
                                        Toast.makeText(RegisterActivity.this, "HATA: "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(RegisterActivity.this, "Hatamız: "+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

    }
}