package com.example.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.LifeCo.gDirection.FetchURL;
import com.example.LifeCo.gDirection.TaskLoadedCallback;
import com.example.lifeco.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, TaskLoadedCallback {


    private GoogleMap mMap;

    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;
    private Button logoutBtn, settingsBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Boolean currentLogoutDriverStatus = false;
    private DatabaseReference AssignedPassengerRef, AssignedPassengerPickupRef;
    private String DriverID, PassengerID = "";
    String userID;
    private MarkerOptions PickUpLocationMarkerOpt, DriverLocationMarkerOpt;
    private Marker PickUpLocationMarker, DriverLocationMarker;
    private ValueEventListener AssignedPassengerPickUpListener;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark_material_light};
    private Polyline currentPolyline;
    private boolean connect = false;
    private boolean state = false;
    private boolean once = false;
    private SupportMapFragment mapFragment;
    private DatabaseReference DriverLocationRef, OntheJobDriverRef;
    private ValueEventListener driverLocationRefListener, OntheJobRefListener;
    private LatLng PassengerLatLng;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.println(Log.INFO, "CREATION", "MAP STARTINGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
        setContentView(R.layout.activity_driver_maps);
        polylines = new ArrayList<>();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DriverID = mAuth.getCurrentUser().getUid();
//        logoutBtn = findViewById(R.id.drivermap_logout_button);
        settingsBtn = findViewById(R.id.drivermap_settings_button);
        DriverLocationRef = FirebaseDatabase.getInstance().getReference().child("Drivers Working");
        OntheJobDriverRef = FirebaseDatabase.getInstance().getReference().child("Drivers Available");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        GetAssignedRequest();
    }

    private void GetAssignedRequest() {
        AssignedPassengerRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(DriverID).child("CustomerRideID");
        Log.println(Log.ERROR, "THIS IS THE ID of PASSS", "GOING TO EXECUTE OT");

        AssignedPassengerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    PassengerID = dataSnapshot.getValue().toString();
                    GetAssignedPassengerPickUpLocation();
                    Log.println(Log.ERROR, "THIS IS THE ID of PASSS", PassengerID);
                } else {
                    PassengerID = "";
                    if (PickUpLocationMarker != null) {
                        PickUpLocationMarker.remove();
                    }
                    if (AssignedPassengerPickUpListener != null) {
                        AssignedPassengerPickupRef.removeEventListener(AssignedPassengerPickUpListener);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void GetAssignedPassengerPickUpLocation() {
        AssignedPassengerPickupRef = FirebaseDatabase.getInstance().getReference().child("Pick Up Request").child(PassengerID).child("l");
        AssignedPassengerPickUpListener = AssignedPassengerPickupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && !PassengerID.equals("")) {
//                    GenericTypeIndicator<List<Object>> passengerLocIndicator  = new GenericTypeIndicator<List<Object>>() {
//                    };
                    List<Object> passengerLocationMap = (List<Object>) dataSnapshot.getValue();
//                    HashMap<String,Object> passengerLocHMap = (HashMap<String, Object>) dataSnapshot.getValue();
//                    List<Object> passengerLocationMap = new ArrayList<Object>(passengerLocHMap.values());
                    double LocationLat = 0;
                    double LocationLong = 0;

                    if (passengerLocationMap.get(0) != null) {
                        LocationLat = Double.parseDouble(passengerLocationMap.get(0).toString());
                    }
                    if (passengerLocationMap.get(1) != null) {
                        LocationLong = Double.parseDouble(passengerLocationMap.get(1).toString());
                    }
                    PassengerLatLng = new LatLng(LocationLat, LocationLong);
//                    connect = true;

                    PickUpLocationMarkerOpt = new MarkerOptions().position(PassengerLatLng).title("Pickup Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.patientheart));
                    PickUpLocationMarker = mMap.addMarker(PickUpLocationMarkerOpt);
//                    if (DriverLocationMarker!=null){
//                        DriverLocationMarker.remove();
//                    }
//                    DriverLocationMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude())).title("Your Ambulance").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ambulance_foreground)));
//                    //THIS ONE WORKS, BUT IT ONLY LASTS LIKE A FEW SECONDS THEN IT DISAPEARS.
//                    String url = getUrl(DriverLocationMarker.getPosition(),PickUpLocationMarker.getPosition(),"driving");
//                    new FetchURL(DriversMapActivity.this).execute(url, "driving");
                    //this one has problems with the class
//                    getRoutetoMarker(PassengerLatLng);
//                   DOESNT WORK, STILL ASKING FOR API??????
                    //this one has error about API Key
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DriverLocationRef = DriverLocationRef.child(DriverID).child("l");
        Log.println(Log.INFO, "THIS IS THE ID oF DRI", "GOINT TO DIEEEEE");
        driverLocationRefListener = DriverLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    GenericTypeIndicator <List<Object>> driverLocIndicator  = new GenericTypeIndicator<List<Object>>() {
//                    };
                    List<Object> driverLocationMap = (List<Object>) dataSnapshot.getValue();
//                    HashMap <String,Object> driverLocHMap = (HashMap<String, Object>) dataSnapshot.getValue();
//                    List<Object> driverLocationMap = new ArrayList<Object>(driverLocHMap.values());
                    double LocationLat = 0;
                    double LocationLong = 0;
                    //Get closest driver wrong position
                    //mapfragment not support DUNZO
                    // Initialize location

                    if (driverLocationMap.get(0) != null) {
                        LocationLat = Double.parseDouble(driverLocationMap.get(0).toString());
                        Log.println(Log.INFO, " IS THE latitude oF DRI", driverLocationMap.get(0).toString());
//                        {g=qw8nsmhqg3, l=[-7.2662892, 112.692749]}
                    }
                    if (driverLocationMap.get(1) != null) {
                        LocationLong = Double.parseDouble(driverLocationMap.get(1).toString());
                        Log.println(Log.INFO, " ISE Longitude oF DRI", driverLocationMap.get(1).toString());
                    }

                    LatLng DriverLatLng = new LatLng(LocationLat, LocationLong);
//                    connect = true;
                    //THIS IS SUPPOSED TO BE CALLED REPEATEDLY
                    if (DriverLocationMarker != null) {
                        DriverLocationMarker.remove();
                    }
                    DriverLocationMarkerOpt = new MarkerOptions().position(DriverLatLng).title("Your Ambulance").icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulanceheart));
                    DriverLocationMarker = mMap.addMarker(DriverLocationMarkerOpt);
                    if (once == false) {
                        url = getUrl(DriverLocationMarkerOpt.getPosition(), PickUpLocationMarkerOpt.getPosition(), "driving");
                        new FetchURL(DriverMapsActivity.this).execute(url, "driving");
                        once = true;
                    }


                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }


    private void LogoutDriver() {
        Intent welcomeIntent = new Intent(DriverMapsActivity.this, MainActivity.class);
        welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(welcomeIntent);
        finish();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.clear();
        buildGoogleApiClient();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DriverMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permission, 44);
            return;
        }
        mMap.setMyLocationEnabled(true);
//        LatLng latLng = getMyLoc(DriversMapActivity.this);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
//    public LatLng getMyLoc(android.content.Context c){
//        LocationManager locationManager = (LocationManager) getSystemService(c.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        String provider = locationManager.getBestProvider(criteria, true);
//        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        Log.println(Log.INFO,"LATLNG VALUE", String.valueOf(latLng));
//        return latLng;
//    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(3000);

        Log.println(Log.INFO, "MAP SHIFTING", "MAP CHANGGIIIIIIINGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DriverMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permission, 44);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (getApplicationContext() != null) {
            lastLocation = location;
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            GetAssignedRequest();
            Log.println(Log.INFO, "LATLNG VALUE", String.valueOf(latLng));

//            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();//THIS KEEPS RUNNING EVEN THOUGH IT IS CLOSED!
            DatabaseReference DriverAvailabilityRef = FirebaseDatabase.getInstance().getReference().child("Drivers Available");
            GeoFire geoFireAvailability = new GeoFire(DriverAvailabilityRef);

            DatabaseReference DriverWorkingRef = FirebaseDatabase.getInstance().getReference().child("Drivers Working");
            GeoFire geoFireWorking = new GeoFire(DriverWorkingRef);

            switch (PassengerID) {
                case "":
                    state = true;
                    geoFireWorking.removeLocation(userID, new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (error != null) {
                                System.err.println("There was an error saving the location to GeoFire: " + error);
                            } else {
                                System.out.println("Location saved on server successfully!");
                            }
                        }
                    });
                    geoFireAvailability.setLocation(userID, new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (error != null) {
                                System.err.println("There was an error saving the location to GeoFire: " + error);
                            } else {
                                System.out.println("Location saved on server successfully!");
                            }
                        }
                    });
                    break;
                default:
                    state = false;
                    geoFireAvailability.removeLocation(userID, new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (error != null) {
                                System.err.println("There was an error saving the location to GeoFire: " + error);
                            } else {
                                System.out.println("Location saved on server successfully!");
                            }
                        }
                    });
                    geoFireWorking.setLocation(userID, new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (error != null) {
                                System.err.println("There was an error saving the location to GeoFire: " + error);
                            } else {
                                System.out.println("Location saved on server successfully!");
                            }
                        }
                    });
                    break;
            }
            if (connect == true) {//IF STATE TRUE OR LIKE THIS NOW IT LASTS LOONG
//                if (state == true){
                AssignedPassengerPickupRef = FirebaseDatabase.getInstance().getReference().child("Pick Up Request").child(PassengerID).child("l");
                AssignedPassengerPickUpListener = AssignedPassengerPickupRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && !PassengerID.equals("")) {
//                    GenericTypeIndicator<List<Object>> passengerLocIndicator  = new GenericTypeIndicator<List<Object>>() {
//                    };
                            List<Object> passengerLocationMap = (List<Object>) dataSnapshot.getValue();
//                    HashMap<String,Object> passengerLocHMap = (HashMap<String, Object>) dataSnapshot.getValue();
//                    List<Object> passengerLocationMap = new ArrayList<Object>(passengerLocHMap.values());
                            double LocationLat = 0;
                            double LocationLong = 0;

                            if (passengerLocationMap.get(0) != null) {
                                LocationLat = Double.parseDouble(passengerLocationMap.get(0).toString());
                            }
                            if (passengerLocationMap.get(1) != null) {
                                LocationLong = Double.parseDouble(passengerLocationMap.get(1).toString());
                            }
                            PassengerLatLng = new LatLng(LocationLat, LocationLong);
                            PickUpLocationMarkerOpt = new MarkerOptions().position(PassengerLatLng).title("Pickup Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.patientheart));
                            PickUpLocationMarker = mMap.addMarker(PickUpLocationMarkerOpt);
//                    if (DriverLocationMarker!=null){
//                        DriverLocationMarker.remove();
//                    }
//                    DriverLocationMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude())).title("Your Ambulance").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ambulance_foreground)));
//                    //THIS ONE WORKS, BUT IT ONLY LASTS LIKE A FEW SECONDS THEN IT DISAPEARS.
//                    String url = getUrl(DriverLocationMarker.getPosition(),PickUpLocationMarker.getPosition(),"driving");
//                    new FetchURL(DriversMapActivity.this).execute(url, "driving");
                            //this one has problems with the class
//                    getRoutetoMarker(PassengerLatLng);
//                   DOESNT WORK, STILL ASKING FOR API??????
                            //this one has error about API Key
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//                    DriverLocationRef = DriverLocationRef.child(DriverID).child("l");
//                    Log.println(Log.INFO,"THIS IS THE ID oF DRI", "GOINT TO DIEEEEE");
//                    driverLocationRefListener = DriverLocationRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()){
//                                //                    GenericTypeIndicator <List<Object>> driverLocIndicator  = new GenericTypeIndicator<List<Object>>() {
//                                //                    };
//                                List<Object> driverLocationMap = (List<Object>) dataSnapshot.getValue();
//                                //                    HashMap <String,Object> driverLocHMap = (HashMap<String, Object>) dataSnapshot.getValue();
//                                //                    List<Object> driverLocationMap = new ArrayList<Object>(driverLocHMap.values());
//                                double LocationLat = 0;
//                                double LocationLong = 0;
//                                //Get closest driver wrong position
//                                //mapfragment not support DUNZO
//                                // Initialize location
//
//                                if (driverLocationMap.get(0) != null){
//                                    LocationLat = Double.parseDouble(driverLocationMap.get(0).toString());
//                                    Log.println(Log.INFO," IS THE latitude oF DRI", driverLocationMap.get(0).toString());
//                                    //                        {g=qw8nsmhqg3, l=[-7.2662892, 112.692749]}
//                                }
//                                if (driverLocationMap.get(1) != null){
//                                    LocationLong = Double.parseDouble(driverLocationMap.get(1).toString());
//                                    Log.println(Log.INFO," ISE Longitude oF DRI", driverLocationMap.get(1).toString());
//                                }
//
//                                LatLng DriverLatLng = new LatLng(LocationLat,LocationLong);
//                                //THIS IS SUPPOSED TO BE CALLED REPEATEDLY
//                                if (DriverLocationMarker!=null){
//                                    DriverLocationMarker.remove();
//                                }
//                                DriverLocationMarkerOpt = new MarkerOptions().position(DriverLatLng).title("Your Ambulance").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ambulance_foreground));
//                                DriverLocationMarker = mMap.addMarker(DriverLocationMarkerOpt);
//
//                            }else{
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
                if (DriverLocationMarker != null) {
                    DriverLocationMarker.remove();
                }
                DriverLocationMarkerOpt = new MarkerOptions().position(latLng).title("Your Ambulance").icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulanceheart));
                DriverLocationMarker = mMap.addMarker(DriverLocationMarkerOpt);
                if (DriverLocationMarkerOpt != null && PickUpLocationMarkerOpt != null) {
                    url = getUrl(DriverLocationMarkerOpt.getPosition(), PickUpLocationMarkerOpt.getPosition(), "driving");
                    new FetchURL(DriverMapsActivity.this).execute(url, "driving");
                }

            } else {
//                    OntheJobDriverRef = OntheJobDriverRef.child(DriverID).child("l");
//                    Log.println(Log.INFO,"THIS IS THE ID oF DRI", "GOINT TO DIEEEEE");
//                    OntheJobRefListener = OntheJobDriverRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()){
//                                //                    GenericTypeIndicator <List<Object>> driverLocIndicator  = new GenericTypeIndicator<List<Object>>() {
//                                //                    };
//                                List<Object> driverLocationMap = (List<Object>) dataSnapshot.getValue();
//                                //                    HashMap <String,Object> driverLocHMap = (HashMap<String, Object>) dataSnapshot.getValue();
//                                //                    List<Object> driverLocationMap = new ArrayList<Object>(driverLocHMap.values());
//                                double LocationLat = 0;
//                                double LocationLong = 0;
//                                //Get closest driver wrong position
//                                //mapfragment not support DUNZO
//                                // Initialize location
//
//                                if (driverLocationMap.get(0) != null){
//                                    LocationLat = Double.parseDouble(driverLocationMap.get(0).toString());
//                                    Log.println(Log.INFO," IS THE latitude oF DRI", driverLocationMap.get(0).toString());
//                                    //                        {g=qw8nsmhqg3, l=[-7.2662892, 112.692749]}
//                                }
//                                if (driverLocationMap.get(1) != null){
//                                    LocationLong = Double.parseDouble(driverLocationMap.get(1).toString());
//                                    Log.println(Log.INFO," ISE Longitude oF DRI", driverLocationMap.get(1).toString());
//                                }
//
//                                LatLng DriverLatLng = new LatLng(LocationLat,LocationLong);
//                                //THIS IS SUPPOSED TO BE CALLED REPEATEDLY
//                                if (DriverLocationMarker!=null){
//                                    DriverLocationMarker.remove();
//                                }
//                                DriverLocationMarkerOpt = new MarkerOptions().position(DriverLatLng).title("Your Ambulance").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ambulance_foreground));
//                                DriverLocationMarker = mMap.addMarker(DriverLocationMarkerOpt);
//                                url = getUrl(DriverLocationMarkerOpt.getPosition(),PickUpLocationMarkerOpt.getPosition(),"driving");
//                                new FetchURL(DriversMapActivity.this).execute(url, "driving");
//
//                            }else{
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
            }
        }

    }


    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 44: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        //when the this page is exited, SUPPOSEDLY HERE IS WHERE YOU PUT THE DELETE
        super.onStop();

        if (!currentLogoutDriverStatus) {
            DisconnectTheDriver();
        }
//        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference DriverAvailabilityRef = FirebaseDatabase.getInstance().getReference().child("Drivers Available");
//
//        GeoFire geoFire= new GeoFire(DriverAvailabilityRef);
//        geoFire.removeLocation(userID);

    }

    private void DisconnectTheDriver() {
        FirebaseDatabase.getInstance().getReference().child("Drivers Available").child(userID).removeValue();
//        GeoFire geoFire= new GeoFire(DriverAvailabilityRef);
//        geoFire.removeLocation(userID, new GeoFire.CompletionListener() {
//            @Override
//            public void onComplete(String key, DatabaseError error) {
//                if (error != null) {
//                    System.err.println("There was an error saving the location to GeoFire: " + error);
//                } else {
//                    System.out.println("DELEETEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED!");
//                }
//            }
//        });
    }


    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        } else {
            currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        }
    }
}
