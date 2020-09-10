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

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.example.LifeCo.gDirection.FetchURL;
import com.example.LifeCo.gDirection.TaskLoadedCallback;
import com.example.lifeco.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
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
import com.google.android.libraries.places.api.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PassengerMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener, RoutingListener, TaskLoadedCallback {


    private GoogleMap mMap;
    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;
    private Button logoutBtn, settingsBtn,callBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Boolean currentLogoutDriverStatus = false;
    private String passengerID;
    private DatabaseReference CustomerDatabaseRef;
    private DatabaseReference DriverAvailableRef;
    private DatabaseReference DriverRef;
    private DatabaseReference DriverLocationRef;
    private ValueEventListener driverLocationRefListener;
    private LatLng PassengerPickupLocation;
    private int radius = 1;
    private boolean driverFound = false;
    private String DriverFoundID;
    private Marker DriverMarker, PickupMarker;
    private boolean requestbol = false;
    GeoQuery geoQuery;
    private Polyline currentPolyline;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark_material_light};
    private String destination;
    private LatLng DriverLatLng;
    private boolean connect = false;
    private SupportMapFragment mapFragment;
    //Possible Errors 1. The polyline 2. The Permission 3. The Database method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_maps);
        mAuth = FirebaseAuth.getInstance();

        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CustomerDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Pick Up Request");
        DriverAvailableRef = FirebaseDatabase.getInstance().getReference().child("Drivers Available");
        DriverLocationRef = FirebaseDatabase.getInstance().getReference().child("Drivers Working");
        polylines = new ArrayList<>();
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        InitializePosition();
        currentUser = mAuth.getCurrentUser();
//        logoutBtn = findViewById(R.id.passenger_logout_button);
        settingsBtn = findViewById(R.id.passenger_settings_button);

//        callBtn = findViewById(R.id.passengerMap_callDriver_button);
//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentLogoutDriverStatus = true;
//                DisconnectThePassenger();
//                mAuth.signOut();
//                LogoutPassenger();
//            }
//        });
//        callBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
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
//                    callBtn.setText("I'M DYING 4 REAL");
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
//                    callBtn.setText("Getting an Ambulance....");
//                    GetClosestDriverCab();
//                }
//
//            }
//
//        });
    }
    private void InitializePosition(){
        if (requestbol){
            requestbol = false;
            geoQuery.removeAllListeners();
            DriverLocationRef.removeEventListener(driverLocationRefListener);

            if (DriverFoundID !=null){
                DriverRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(DriverFoundID);
                DriverRef.setValue(true);
                DriverFoundID = null;
            }
            driverFound = false;
            radius=1;

            GeoFire geoFire = new GeoFire(CustomerDatabaseRef);
            geoFire.removeLocation(passengerID, new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    if (error != null) {
                        System.err.println("There was an error removing the location to GeoFire: " + error);
                    } else {
                        System.out.println("Location removed on server successfully!");
                    }
                }
            });

            if (PickupMarker != null){
                PickupMarker.remove();
            }
//                    callBtn.setText("I'M DYING 4 REAL");
            FirebaseDatabase.getInstance().getReference().child("Pick Up Request").child(passengerID).removeValue();
            FirebaseDatabase.getInstance().getReference().child("Drivers Working").child(DriverFoundID).removeValue();
            //CHECK AGAIN, MAYBE WRONG

        }
        else{
            requestbol = true;

            GeoFire geoFire = new GeoFire(CustomerDatabaseRef);
            geoFire.setLocation(passengerID, new GeoLocation( lastLocation.getLatitude(),lastLocation.getLongitude()), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    if (error != null) {
                        System.err.println("There was an error saving the location to GeoFire: " + error);
                    } else {
                        System.out.println("Location saved on server successfully!");
                    }
                }
            });
            PassengerPickupLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
            PickupMarker = mMap.addMarker(new MarkerOptions().position(PassengerPickupLocation).title("Pickup Customer").icon(BitmapDescriptorFactory.fromResource(R.drawable.patientheart)));

//                    callBtn.setText("Getting an Ambulance....");
        }
        PassengerPickupLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
        PickupMarker = mMap.addMarker(new MarkerOptions().position(PassengerPickupLocation).title("Pickup Customer").icon(BitmapDescriptorFactory.fromResource(R.drawable.patientheart)));


        GetClosestDriverCab();
    }
    private void GetClosestDriverCab() {
        GeoFire geoFire = new GeoFire(DriverAvailableRef);
        geoQuery = geoFire.queryAtLocation(new GeoLocation(PassengerPickupLocation.latitude,PassengerPickupLocation.longitude),radius);
        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (!driverFound && requestbol){
                    driverFound = true;
                    DriverFoundID = key;

                    DriverRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(DriverFoundID);
                    HashMap driverMap = new HashMap();
                    driverMap.put("CustomerRideID",passengerID);
                    DriverRef.updateChildren(driverMap);

                    GettingDriverLocation();

                    callBtn.setText("Initializing Ambulance Location");
                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                if (!driverFound){
                    radius++;
                    GetClosestDriverCab();
                }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }
    private void GettingDriverLocation() {
        DriverLocationRef = DriverLocationRef.child(DriverFoundID).child("l");
        Log.println(Log.INFO,"THIS IS THE ID oF DRI", "GOINT TO DIEEEEE");
        driverLocationRefListener = DriverLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
//                    GenericTypeIndicator <List<Object>> driverLocIndicator  = new GenericTypeIndicator<List<Object>>() {
//                    };
                    List<Object> driverLocationMap = (List<Object>) dataSnapshot.getValue();

                    double LocationLat = 0;
                    double LocationLong = 0;
//                    callBtn.setText("Driver Found");

                    if (driverLocationMap.get(0) != null){
                        LocationLat = Double.parseDouble(driverLocationMap.get(0).toString());
                        Log.println(Log.INFO," IS THE latitude oF DRI", driverLocationMap.get(0).toString());
//                        {g=qw8nsmhqg3, l=[-7.2662892, 112.692749]}
                    }
                    if (driverLocationMap.get(1) != null){
                        LocationLong = Double.parseDouble(driverLocationMap.get(1).toString());
                        Log.println(Log.INFO," ISE Longitude oF DRI", driverLocationMap.get(1).toString());
                    }

                    DriverLatLng = new LatLng(LocationLat,LocationLong);
                    connect = true;
                    //THIS IS SUPPOSED TO BE CALLED REPEATEDLY
                    if (DriverMarker!=null){
                        DriverMarker.remove();
                    }

                    PickupMarker = mMap.addMarker(new MarkerOptions().position(PassengerPickupLocation).title("Pickup Customer").icon(BitmapDescriptorFactory.fromResource(R.drawable.patientheart)));

                    DriverMarker = mMap.addMarker(new MarkerOptions().position(DriverLatLng).title("Your Ambulance").icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulanceheart)));

                    Location location1 = new Location("");
                    location1.setLatitude(PassengerPickupLocation.latitude);
                    location1.setLongitude(PassengerPickupLocation.longitude);

                    Location location2 = new Location("");
                    location2.setLatitude(DriverLatLng.latitude);
                    location2.setLongitude(DriverLatLng.longitude);
                    String url = getUrl(DriverMarker.getPosition(),PickupMarker.getPosition(),"driving");
//                    new  FetchURL(PassengerMapsActivity.this).execute(url, "driving");
//                    float Distance = location1.distanceTo(location2);
//                    if (Distance<75){
////                        callBtn.setText("Your Driver is here");
//                        callBtn.setText("Driver is " + String.valueOf(Distance) +  "m Away");
//
//                    }
//                    else{
//                        callBtn.setText("Driver is " + String.valueOf(Distance) +  "m Away");
//                    }
                }else{
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

    private void LogoutPassenger() {
        Intent welcomeIntent = new Intent(PassengerMapsActivity.this, MainActivity.class);
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
     * MAP FRAGMENT, NOT SUPPORT MAP FRAGMENT
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        buildGoogleApiClient();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PassengerMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},44);
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permission,44);
            return;
        }
        mMap.setMyLocationEnabled(true);
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(3000);
        Log.println(Log.INFO,"MAP SHIFTING","MAP CHANGGIIIIIIINGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PassengerMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},44);
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permission,44);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        Log.println(Log.INFO,"LATLNG VALUE", String.valueOf(latLng));
        DatabaseReference PassengerOnlineRef = FirebaseDatabase.getInstance().getReference().child("Passenger Online");
        GeoFire geoFireOnline= new GeoFire(PassengerOnlineRef);
        geoFireOnline.setLocation(passengerID, new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null) {
                    System.err.println("There was an error saving the location to GeoFire: " + error);
                } else {
                    System.out.println("Location saved on server successfully!");
                }
            }
        });
//        if(connect == true){
//            if (DriverMarker!=null){
//                DriverMarker.remove();
//            }
//            DriverMarker = mMap.addMarker(new MarkerOptions().position(DriverLatLng).title("Your Ambulance").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ambulance_foreground)));
//            String url = getUrl(DriverMarker.getPosition(),PickupMarker.getPosition(),"driving");
//            new FetchURL(PassengerMapsActivity.this).execute(url, "driving");
//            mapFragment.getMapAsync(PassengerMapsActivity.this);

//            driverLocationRefListener = DriverLocationRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()){
////                    GenericTypeIndicator <List<Object>> driverLocIndicator  = new GenericTypeIndicator<List<Object>>() {
////                    };
//                        List<Object> driverLocationMap = (List<Object>) dataSnapshot.getValue();
////                    HashMap <String,Object> driverLocHMap = (HashMap<String, Object>) dataSnapshot.getValue();
////                    List<Object> driverLocationMap = new ArrayList<Object>(driverLocHMap.values());
//                        double LocationLat = 0;
//                        double LocationLong = 0;
//                        callBtn.setText("Driver Found");
//                        //Get closest driver wrong position
//                        //mapfragment not support DUNZO
//                        // Initialize location
//
//                        if (driverLocationMap.get(0) != null){
//                            LocationLat = Double.parseDouble(driverLocationMap.get(0).toString());
//                            Log.println(Log.INFO," IS THE latitude oF DRI", driverLocationMap.get(0).toString());
////                        {g=qw8nsmhqg3, l=[-7.2662892, 112.692749]}
//                        }
//                        if (driverLocationMap.get(1) != null){
//                            LocationLong = Double.parseDouble(driverLocationMap.get(1).toString());
//                            Log.println(Log.INFO," ISE Longitude oF DRI", driverLocationMap.get(1).toString());
//                        }
//
//                        DriverLatLng = new LatLng(LocationLat,LocationLong);
//                        connect = true;
//                        //THIS IS SUPPOSED TO BE CALLED REPEATEDLY
//                    if (DriverMarker!=null){
//                        DriverMarker.remove();
//                    }
//                    DriverMarker = mMap.addMarker(new MarkerOptions().position(DriverLatLng).title("Your Ambulance").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ambulance_foreground)));
//
//                        Location location1 = new Location("");
//                        location1.setLatitude(PassengerPickupLocation.latitude);
//                        location1.setLongitude(PassengerPickupLocation.longitude);
//
//                        Location location2 = new Location("");
//                        location2.setLatitude(DriverLatLng.latitude);
//                        location2.setLongitude(DriverLatLng.longitude);
//                    String url = getUrl(DriverMarker.getPosition(),PickupMarker.getPosition(),"driving");
//                    new FetchURL(PassengerMapsActivity.this).execute(url, "driving");
//                        float Distance = location1.distanceTo(location2);
//                        if (Distance<75){
////                        callBtn.setText("Your Driver is here");
//                            callBtn.setText("Driver is " + String.valueOf(Distance) +  "m Away");
//
//                        }
//                        else{
//                            callBtn.setText("Driver is " + String.valueOf(Distance) +  "m Away");
//                        }
//
//
//                    }else{
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }


//        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();//THIS KEEPS RUNNING EVEN THOUGH IT IS CLOSED!
//        DatabaseReference DriverAvailabilityRef = FirebaseDatabase.getInstance().getReference().child("Passengers");
//
//        geoFire= new GeoFire(DriverAvailabilityRef);
//        geoFire.setLocation(userID, new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
//            @Override
//            public void onComplete(String key, DatabaseError error) {
//                if (error != null) {
//                    System.err.println("There was an error saving the location to GeoFire: " + error);
//                } else {
//                    System.out.println("Location saved on server successfully!");
//                }
//            }
//        });
    }
    protected synchronized void buildGoogleApiClient(){
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
        switch  (requestCode){
            case 44:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                else {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!currentLogoutDriverStatus){
            DisconnectThePassenger();
        }
//        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference DriverAvailabilityRef = FirebaseDatabase.getInstance().getReference().child("Drivers Available");
//
//        GeoFire geoFire= new GeoFire(DriverAvailabilityRef);
//        geoFire.removeLocation(userID);

    }

    private void DisconnectThePassenger() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Passenger Online").child(userID).removeValue();
//        DatabaseReference DriverAvailabilityRef = FirebaseDatabase.getInstance().getReference().child("Drivers Available");
//
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
    public void onRoutingFailure(RouteException e) {
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRoutingCancelled() {

    }


    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null){
            currentPolyline.remove();
        }
        else{
            currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        }
    }
}