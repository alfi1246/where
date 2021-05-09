package

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.where.R;
import com.example.where.Services.MyService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class childWatch extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    LocationManager locationManager;
    LocationListener locationListener;
    private Toolbar actionbarLogin;
    String check;
    String equals;
    Button btnMainGo;
    TextView txtDogrula;
    public void init()
    {
        actionbarLogin=(Toolbar)findViewById(R.id.actionbarlogin);
        setSupportActionBar(actionbarLogin);
        // getSupportActionBar(setTitle("açık menü"))//başlık
       // getSupportActionBar().setTitle("Kullanıcı Ekranı");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true); //geri oku
        btnMainGo=findViewById(R.id.btn_oncekiSayfa);
        txtDogrula=findViewById(R.id.edtxtGeriGit);
        btnMainGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null)
                {
                    try
                    {
                        String user_id=mAuth.getCurrentUser().getUid();
                        equals=txtDogrula.getText().toString();
                       // Toast.makeText(childWatch.this, "eq"+equals, Toast.LENGTH_SHORT).show();
                        mDatabase=FirebaseDatabase.getInstance().getReference().child("").child(user_id).child("");

                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   check=snapshot.getValue().toString();
                             //   Toast.makeText(childWatch.this, "dene"+check, Toast.LENGTH_SHORT).show();
                                   if(check.equals(equals))
                                   {
                                       Toast.makeText(childWatch.this, "Şifre Doğrulanıyor....", Toast.LENGTH_SHORT).show();
                                       Toast.makeText(childWatch.this, "Şifre Doğru....", Toast.LENGTH_SHORT).show();
                                       Toast.makeText(childWatch.this, "Ana Menüye Geri Dönüyorsunuz.", Toast.LENGTH_SHORT).show();
                                       txtDogrula.setText(" ");
                                       Intent mainGo=new Intent(childWatch.this,MainActivity.class);
                                       startActivity(mainGo);
                                   }
                                   else
                                   {
                                       Toast.makeText(childWatch.this, "Şifre Doğrulanıyor....", Toast.LENGTH_SHORT).show();
                                       Toast.makeText(childWatch.this, "Şifre Yanlış....", Toast.LENGTH_SHORT).show();

                                   }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(childWatch.this, "Hata: "+e, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void setSupportActionBar(Toolbar actionbarLogin) {
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0)
        {
            if(requestCode==1)
            {
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,0,locationListener);
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            // Intent loginGit=new Intent(MainActivity.this, LoginActivity.class);
            // startActivity(loginGit);

        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_watch);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        init();
        mAuth=FirebaseAuth.getInstance();
        startService(new Intent(getApplicationContext(),MyService.class));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String user_id=mAuth.getCurrentUser().getUid();
                mDatabase= FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
                double last=0,longi=0;
                final String last2,long2;
                LatLng userLocation=new LatLng(location.getLatitude(),location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Hasta"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15F));
                last=userLocation.latitude;
                last2=String.valueOf(last);
                longi=userLocation.longitude;
                long2=String.valueOf(longi);
                mDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(user_id).child("konum");
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("enlem",last2);
                hashMap.put("boylam",long2);
                mDatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(childWatch.this, "Konumuz Gönderildi", Toast.LENGTH_SHORT).show();


                        }
                        else Toast.makeText(childWatch.this, "Konumuz Gönderilemedi !!!!!s", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Permission işlemeri
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                LatLng userLastLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLastLocation).title("hello"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation, 10F));
                double lat = userLastLocation.latitude;
                final double longtu = userLastLocation.longitude;
                Geocoder geocoder = new Geocoder(this);
                Locale.getDefault();
                try {
                    String user_id = mAuth.getCurrentUser().getUid();
                    List<Address> addressList = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
                    final String ev = addressList.get(0).getAddressLine(0);
                    String loca = addressList.get(0).getLocality();
                    final double enlem = addressList.get(0).getLatitude();
                    double boylam = addressList.get(0).getLongitude();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user_id).child("konum");
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("enlem", String.valueOf(enlem));
                    hashMap.put("boylam", String.valueOf(boylam));
                    mDatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                               // Toast.makeText(childWatch.this, "OLDU...." + String.valueOf(enlem), Toast.LENGTH_SHORT).show();
                               // Toast.makeText(childWatch.this, "OLDU...." + String.valueOf(longtu), Toast.LENGTH_SHORT).show();
                               // Toast.makeText(childWatch.this, "OLDU...." + String.valueOf(ev), Toast.LENGTH_SHORT).show();


                            } else
                                Toast.makeText(childWatch.this, "Hata", Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (IOException e) {
                    Toast.makeText(this, "Hata: " + e, Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "burası", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Çıkış yapmak için lütfen şifreyi giriniz");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try
                {
                    String user_id=mAuth.getCurrentUser().getUid();
                    equals=txtDogrula.getText().toString();
                    // Toast.makeText(childWatch.this, "eq"+equals, Toast.LENGTH_SHORT).show();
                    mDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(user_id).child("password");

                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            check=snapshot.getValue().toString();
                            //   Toast.makeText(childWatch.this, "dene"+check, Toast.LENGTH_SHORT).show();
                            if(check.equals(equals))
                            {
                                Toast.makeText(childWatch.this, "Şifre Doğrulanıyor....", Toast.LENGTH_SHORT).show();
                                Toast.makeText(childWatch.this, "Şifre Doğru....", Toast.LENGTH_SHORT).show();
                                Toast.makeText(childWatch.this, "Ana Menüye Geri Dönüyorsunuz.", Toast.LENGTH_SHORT).show();
                                txtDogrula.setText(" ");
                                Intent mainGo=new Intent(childWatch.this,MainActivity.class);
                                startActivity(mainGo);
                            }
                            else
                            {
                                Toast.makeText(childWatch.this, "Şifre Doğrulanıyor....", Toast.LENGTH_SHORT).show();
                                Toast.makeText(childWatch.this, "Şifre Yanlış....", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                catch (Exception e)
                {
                    Toast.makeText(childWatch.this, "Hata: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            @Override            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}