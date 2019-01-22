//bug in this app is that when we dectivate the app  then also msg is being sent

package com.example.salil.shoutforhelp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
        , NavigationView.OnNavigationItemSelectedListener {
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
static  TextView t4,t5;
boolean check=false;
    SharedPreferences sharedPreferences;
    boolean active=false;
    private LocationManager locationManager;
    Button btnview;
    static  ArrayList arrayList=new ArrayList();
    TextView txtname;
   static int count=0;
    static final int PICK_CONTACT = 1;
    ListView l1;
    NotificationManager notificationManager;
    String st;
    ImageView i1,i2;
    IntentFilter filter;
    ArrayAdapter arrayAdapter;
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    private MyReceiver myReceiver;
    public void red(View view)
    {            Log.i("count value:", String.valueOf(count));



       if(check==true) {

          if (count >=1)
          {
                Toast.makeText(MainActivity.this, "App is activated", Toast.LENGTH_SHORT).show();
                i1 = (ImageView) findViewById(R.id.imageView1);
                active = true;
                i1.setVisibility(View.GONE);
                i2.setVisibility(View.VISIBLE);
              Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
                Notification notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("shout for help")
                        .setContentText("App is activated")
                        .setContentIntent(pendingIntent)
                        .setSound(soundUri)
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .build();
             notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1, notification);

                filter= new IntentFilter(Intent.ACTION_SCREEN_ON);
                filter.addAction(Intent.ACTION_SCREEN_OFF);
                filter.addAction(Intent.ACTION_USER_PRESENT);
                myReceiver = new MyReceiver();
                registerReceiver(myReceiver, filter);

           } else{
                Toast.makeText(MainActivity.this, "you need to select min. 2 contacts", Toast.LENGTH_SHORT).show();
            }
        }
       if(check==false)
        {
            Toast.makeText(this, "swich danger mode on", Toast.LENGTH_SHORT).show();
        }
    }
    public void green(View view)
    {notificationManager.cancelAll();
        Toast.makeText(this, "App is deactivated", Toast.LENGTH_SHORT).show();
        i2=(ImageView)findViewById(R.id.imageView);
        i2.setVisibility(View.GONE);
        i1.setVisibility(View.VISIBLE);
        active=false;
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
finish();


    }


    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Switch myswitch=(Switch)findViewById(R.id.switch1);
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked==true) {
                    check=true;
                }
                if(isChecked==false)
                {
                    check=false;
                }

            }

        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
         sharedPreferences=this.getSharedPreferences("com.example.salil.shoutforhelp",Context.MODE_PRIVATE);
        try {
            arrayList=(ArrayList<String>) objectserializer.deserialize(sharedPreferences.getString("places",objectserializer.serialize(new ArrayList<String>())));
            SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
             count = sp.getInt("count", -1);
             Log.i("count", String.valueOf(count));
        } catch (IOException e) {
            e.printStackTrace();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t5);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        checkLocation();
        i2=(ImageView)findViewById(R.id.imageView);
        i2.setVisibility(View.GONE);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("How to use App")
                .setMessage("you need to select minimum 2 \ncontacts to which your location\n will be send.\n Then click alert button and press power button \ntwice to send alert message.\nMust deactivate if you are out of\n danger")
                .setPositiveButton("Ok", null)
                .show();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Attention message")
                .setMessage("1) you must put your gps always on\n whenever you are going out or\n feel unsafe.it will take 2 miniutes to\n get your current location\n 2) you must allow sms and gps permission\n to app from settings")
                .setPositiveButton("Ok", null)
                .show();
        AccessContact();
        l1=(ListView)findViewById(R.id.list_item);

        btnview = (Button) findViewById(R.id.b1);

        txtname=(TextView) findViewById(R.id.text);


        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        l1.setAdapter(arrayAdapter);

        l1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemtodel=i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("are you sure")
                        .setMessage("do you want to delete this contact")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arrayList.remove(itemtodel);
                                arrayAdapter.notifyDataSetChanged();
                                try {
                                    sharedPreferences.edit().putString("places",objectserializer.serialize(MainActivity.arrayList)).apply();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeButton("no",null)
                        .show();
                count--;
                SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("count", count);
                editor.commit();

                return true;
            }
        });

    }
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("tag:", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("tag:", "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        t4.setText(String.valueOf(location.getLatitude()));
        t5.setText(String.valueOf(location.getLongitude() ));
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    private boolean checkLocation() {

        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.rating) {
            Intent intent=new Intent(getApplicationContext(),rating.class);
            startActivity(intent);
        } else if (id == R.id.aboutus) {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("about us")
                    .setMessage("copyright by \n salil5chhabra@gmail.com")
                    .setPositiveButton("ok",null)
                    .show();
        }
        else if(id==R.id.emergency)
        {
            Intent intent=new Intent(getApplicationContext(),emergency.class);
            startActivity(intent);
        }
        else if (id == R.id.exit) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("do you want to quit the app")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                            Toast.makeText(MainActivity.this, "app closed", Toast.LENGTH_SHORT).show();
                        }


                    })
                    .setNegativeButton("no",null)
                    .show();

        } else if (id == R.id.nav_share) {
            try{
                Intent n=new Intent(Intent.ACTION_SEND);
                n.setType("text/plain");
                n.putExtra(Intent.EXTRA_SUBJECT,"my application name");
                String saux="\n let me recommend you this application\n\n";
                saux=saux+"http://play.google.com/store/apps/details?id=the.package.id\n\n";
                n.putExtra(Intent.EXTRA_TEXT,saux);
                startActivity(Intent.createChooser(n,"choose one"));
            }
            catch(Exception e)
            {
                System.out.print(e);
            }
        } else if (id == R.id.feedback) {

            Intent intent=new Intent(getApplicationContext(),feedback.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void AccessContact()

    {

        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();

        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))

        {
            permissionsNeeded.add("Read Contacts");
        }

        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))

            permissionsNeeded.add("Write Contacts");

        if (permissionsList.size() > 0) {

            if (permissionsNeeded.size() > 0) {

                String message = "You need to grant access to " + permissionsNeeded.get(0);

                for (int i = 1; i < permissionsNeeded.size(); i++)

                    message = message + ", " + permissionsNeeded.get(i);

                showMessageOKCancel(message,

                        new DialogInterface.OnClickListener() {

                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),

                                        REQUEST_MULTIPLE_PERMISSIONS);

                            }

                        });

                return;

            }

            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),

                    REQUEST_MULTIPLE_PERMISSIONS);

            return;

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {

        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {

            permissionsList.add(permission);



            if (!shouldShowRequestPermissionRationale(permission))

                return false;

        }

        return true;

    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(MainActivity.this)

                .setMessage(message)

                .setPositiveButton("OK", okListener)

                .setNegativeButton("Cancel", null)

                .create()

                .show();

    }
    public void onClick(View v) {
        if(count<=5) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

            startActivityForResult(intent, PICK_CONTACT);
        }
        else {
            Toast.makeText(this, "max 5 contacts can be saved.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);
        String cNumber=null;
        switch (reqCode) {

            case (PICK_CONTACT):

                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();

                    Cursor c = managedQuery(contactData, null, null, null, null);

                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        try {

                            if (hasPhone.equalsIgnoreCase("1")) {

                                Cursor phones = getContentResolver().query(

                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,

                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,

                                        null, null);

                                phones.moveToFirst();

                                cNumber = phones.getString(phones.getColumnIndex("data1"));

                                System.out.println("number is:" + cNumber);

                                //  String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                                count++;
                                arrayList.add(cNumber);
                                sharedPreferences.edit().putString("places",objectserializer.serialize(MainActivity.arrayList)).apply();
                                SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("count", count);
                                editor.commit();
                                arrayAdapter.notifyDataSetChanged();

                            }
                        }
                        catch (Exception ex)

                        {

                            System.out.print(ex);
                        }

                    }

                }

                break;

        }
        l1.setAdapter(arrayAdapter);
    }


}
