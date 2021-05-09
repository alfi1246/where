package

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.where.R;
import com.example.where.Services.MyService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class childFollow extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public FirebaseAuth mAauth;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    String enlem,boylam;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_follow);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mAauth=FirebaseAuth.getInstance();
        btn=findViewById(R.id.btn_show);
        if(mAauth.getCurrentUser()!=null)
        {


            String user_adi=mAauth.getCurrentUser().getUid();
            mDatabase=FirebaseDatabase.getInstance().getReference().child("").child(user_adi).child("");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    enlem=dataSnapshot.child("").getValue().toString();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            mDatabase2=FirebaseDatabase.getInstance().getReference().child("").child(user_adi).child("");
            mDatabase2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boylam=dataSnapshot.child("").getValue().toString();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enlem!=null&&boylam!=null)
                { Toast.makeText(childFollow.this, ": "+enlem, Toast.LENGTH_SHORT).show();
                    LatLng konum=new LatLng(Double.parseDouble(enlem),Double.parseDouble(boylam));
                    mMap.addMarker(new MarkerOptions().position(konum).title("));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15F));
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("  "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
