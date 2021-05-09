package

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.where.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Toolbar actionbarLogin;
    private FirebaseAuth mAuth;
    public FirebaseAuth mAauth;
    Button btnWatchChild;
    Button btnHastaActivity;
public void init()
{
    actionbarLogin=(Toolbar)findViewById(R.id.actionbarlogin);
    setSupportActionBar(actionbarLogin);
   // getSupportActionBar(setTitle("açık menü"))//başlık
    getSupportActionBar().setTitle("Kullanıcı Ekranı");
   // getSupportActionBar().setDisplayHomeAsUpEnabled(true); //geri oku
    mAauth=FirebaseAuth.getInstance();
    btnWatchChild=findViewById(R.id.btn_child);
    btnHastaActivity=findViewById(R.id.btnHastaActivity);



}

    private void setSupportActionBar(Toolbar actionbarLogin) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null)
        {
            Intent loginGit=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginGit);
        }
        btnWatchChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goChild=new Intent(MainActivity.this,childFollow.class);
                startActivity(goChild);

            }
        });
        btnHastaActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goFollow=new Intent(MainActivity.this,childWatch.class);
                startActivity(goFollow);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

         getMenuInflater().inflate(R.menu.menu_main,menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.mainLogut)
        {
            //mAuth.signOut;
            mAuth.signOut();
            Intent loginGit=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginGit);

        }
        return true;
    }
}
