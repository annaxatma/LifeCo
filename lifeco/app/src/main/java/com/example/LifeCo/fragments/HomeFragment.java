package com.example.LifeCo.fragments;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.LifeCo.activities.ChatMainActivity;
import com.example.LifeCo.activities.DriverMapsActivity;
import com.example.LifeCo.activities.PassengerMapsActivity;
import com.example.lifeco.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    Button chatApp;
    private GoogleMap mMap;
    FloatingActionButton callAmb;
    private boolean requestbol = false;
    private DatabaseReference DriverLocationRef;
    private ValueEventListener driverLocationRefListener;
    GeoQuery geoQuery;
    private String DriverFoundID;
    private DatabaseReference DriverRef;
    private boolean driverFound = false;
    private int radius = 1;
    private DatabaseReference CustomerDatabaseRef;
    private String passengerID;
    private Marker DriverMarker, PickupMarker;
    Location lastLocation;
    private LatLng PassengerPickupLocation;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DriverLocationRef = FirebaseDatabase.getInstance().getReference().child("Drivers Working");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        chatApp = view.findViewById(R.id.btnChatApp);
        callAmb = view.findViewById(R.id.btnEmergencyCall);

        callAmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PassengerMapsActivity.class);
                startActivity(intent);

//                if (requestbol){
//                    requestbol = false;
//                    geoQuery.removeAllListeners();
//                    DriverLocationRef.removeEventListener(driverLocationRefListener);
//
//                    if (DriverFoundID !=null){
//                        DriverRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(DriverFoundID);
//                        DriverRef.setValue(true);
//                        DriverFoundID = null;
//                    }
//                    driverFound = false;
//                    radius=1;
//
//                    GeoFire geoFire = new GeoFire(CustomerDatabaseRef);
//                    geoFire.removeLocation(passengerID, new GeoFire.CompletionListener() {
//                        @Override
//                        public void onComplete(String key, DatabaseError error) {
//                            if (error != null) {
//                                System.err.println("There was an error removing the location to GeoFire: " + error);
//                            } else {
//                                System.out.println("Location removed on server successfully!");
//                            }
//                        }
//                    });
//
//                    if (PickupMarker != null){
//                        PickupMarker.remove();
//                    }
////                    callBtn.setText("I'M DYING 4 REAL");
//                    FirebaseDatabase.getInstance().getReference().child("Pick Up Request").child(passengerID).removeValue();
//                    FirebaseDatabase.getInstance().getReference().child("Drivers Working").child(DriverFoundID).removeValue();
//                    //CHECK AGAIN, MAYBE WRONG
//
//                }
//                else{
//                    requestbol = true;
//
//                    GeoFire geoFire = new GeoFire(CustomerDatabaseRef);
//                    geoFire.setLocation(passengerID, new GeoLocation( lastLocation.getLatitude(),lastLocation.getLongitude()), new GeoFire.CompletionListener() {
//                        @Override
//                        public void onComplete(String key, DatabaseError error) {
//                            if (error != null) {
//                                System.err.println("There was an error saving the location to GeoFire: " + error);
//                            } else {
//                                System.out.println("Location saved on server successfully!");
//                            }
//                        }
//                    });
//                    PassengerPickupLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
//                    PickupMarker = mMap.addMarker(new MarkerOptions().position(PassengerPickupLocation).title("Pickup Customer").icon(BitmapDescriptorFactory.fromResource(R.drawable.patientheart)));
//
////                    callBtn.setText("Getting an Ambulance....");
//                }




                String akunTipe = getArguments().getString("akun");
                if(akunTipe.equalsIgnoreCase("pasien")){
                    intent = new Intent(getActivity(), PassengerMapsActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), DriverMapsActivity.class);
                    startActivity(intent);
                }

//                else{
//                    requestbol = true;
//
//                    GeoFire geoFire = new GeoFire(CustomerDatabaseRef);
//                    geoFire.setLocation(passengerID, new GeoLocation( lastLocation.getLatitude(),lastLocation.getLongitude()), new GeoFire.CompletionListener() {
//                        @Override
//                        public void onComplete(String key, DatabaseError error) {
//                            if (error != null) {
//                                System.err.println("There was an error saving the location to GeoFire: " + error);
//                            } else {
//                                System.out.println("Location saved on server successfully!");
//                            }
//                        }
//                    });
//                    PassengerPickupLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
//                    PickupMarker = mMap.addMarker(new MarkerOptions().position(PassengerPickupLocation).title("Pickup Customer").icon(BitmapDescriptorFactory.fromResource(R.drawable.patientheart)));
//
////                    callBtn.setText("Getting an Ambulance....");
////                    GetClosestDriverCab();
//                }

                intent = new Intent(getActivity(), PassengerMapsActivity.class);
                startActivity(intent);

            }
        });


//        chatApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Fragment fragment = new ChatsFragment();
////                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                fragmentTransaction.replace(R.id.frame_main, fragment);
////                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.commit();
//
//                Intent intent = new Intent(getActivity(), ChatMainActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}


