package net.runsystem.socialphoto.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.runsystem.socialphoto.API.Request.NearbyRequest;
import net.runsystem.socialphoto.API.Response.NearbyResponse;
import net.runsystem.socialphoto.Adapter.CustomInfoWindow;
import net.runsystem.socialphoto.Bean.NewsBean;
import net.runsystem.socialphoto.Constant.HeaderOption;
import net.runsystem.socialphoto.R;

import java.util.List;

import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.FragmentUtil;


public class NearbyFragment extends HeaderFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    GoogleMap mMap;

    GoogleApiClient googleApiClient;
    Location mCurrentLocation;
   // DrawerLayout mDrawerLayout;

    double dbLat;
    double dbLong;

    CustomInfoWindow newInfo;
    List<NewsBean> dataNearbyList;
    LatLng userpostion;
    NewsBean newsBean;


    public static NearbyFragment newInstance() {
        NearbyFragment newFragment = new NearbyFragment();
        return newFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_near_by;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);
       SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected int getRightIcon() {
        return HeaderOption.RIGHT_NO_OPTION;
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    public String getScreenTitle() {
        return "Nearby";
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
      //  DrawerLayout  mDrawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
     //   mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
    }


    private Bitmap resizeMarker(int ResID){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ResID);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 40, 70, true);
        return resized;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (mCurrentLocation != null) {
            dbLat = mCurrentLocation.getLatitude();
            dbLong = mCurrentLocation.getLongitude();

            Toast.makeText(getActivity(),"onConnected", Toast.LENGTH_LONG).show();

            onRequestNearby();
        }

    }
    public void onRequestNearby() {
        NearbyRequest nearbyRequest = new NearbyRequest(dbLat, dbLong);
        nearbyRequest.setRequestCallBack(new ApiObjectCallBack<NearbyResponse>() {
            @Override
            public void onSuccess(NearbyResponse data) {
                Toast.makeText(getActivity(),"success", Toast.LENGTH_LONG).show();
              //  setMarketNearby(data.data);
                initialResponseHandled();
                dataNearbyList = data.data;
                for ( int i = 0; i < dataNearbyList.size(); i++){
                    newInfo = new CustomInfoWindow(getActivity());
                    mMap.setInfoWindowAdapter(newInfo);
                    LatLng postlocation = new LatLng(Double.valueOf(dataNearbyList.get(i).image.lat),
                            Double.valueOf(dataNearbyList.get(i).image._long));
                    MarkerOptions mMarker = new MarkerOptions().position(postlocation);
                    mMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeMarker(R.drawable.map_pin)));

                    mMap.addMarker(mMarker.title(dataNearbyList.get(i).user.username)
                            .snippet(dataNearbyList.get(i).image.caption)).setTag(dataNearbyList.get(i));
                }
                LatLng latLng = new LatLng(dbLat, dbLong);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            }
            @Override
            public void onFail(int failCode, String message) {
                Toast.makeText(getActivity(),"fail", Toast.LENGTH_LONG).show();
            }
        });

        nearbyRequest.execute();
    }
    public void setMarketNearby(List<NewsBean> inNewsBean) {
        if (inNewsBean != null) {
            int size = inNewsBean.size();
            String strLat;
            String strLong;
            String strName;

            LatLng latLng;


            for (int i = 0; i < size; i++) {
                NewsBean newsBean = inNewsBean.get(i);
                if (newsBean != null) {
                    strLat = newsBean.image.lat;
                    strLong = newsBean.image._long;
                    strName =  newsBean.user.username;

                    double dbLat = Double.valueOf(strLat).doubleValue();
                    double dbLong = Double.valueOf(strLong).doubleValue();

                    latLng = new LatLng(dbLat, dbLong);
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }

            latLng = new LatLng(dbLat, dbLong);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.setMaxZoomPreference(14.0f);
            mMap.setMinZoomPreference(6.0f);

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        FragmentUtil.pushFragmentWithAnimation(getActivity(),ImageDetailFragment.newInstance((NewsBean) marker.getTag()),null);
      //  FragmentUtil.pushFragment(getActivity(), ImageDetailFragment.newInstance(newsBean) marker.getTag(), null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
