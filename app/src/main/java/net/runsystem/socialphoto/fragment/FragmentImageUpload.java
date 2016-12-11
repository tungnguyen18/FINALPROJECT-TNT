package net.runsystem.socialphoto.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fenlisproject.hashtagedittext.HashTagEditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.theartofdev.edmodo.cropper.CropImage;

import net.runsystem.socialphoto.API.Request.UploadImageRequest;
import net.runsystem.socialphoto.API.Response.DataUploadResponse;
import net.runsystem.socialphoto.API.Response.UploadImageResponse;
import net.runsystem.socialphoto.Constant.ApiConstance;
import net.runsystem.socialphoto.Constant.HeaderOption;
import net.runsystem.socialphoto.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.BitmapUtil;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.ImagePickerUtil;
import vn.app.base.util.NetworkUtils;
import vn.app.base.util.SharedPrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentImageUpload extends HeaderFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {

    final int CAMERA_PICTURE = 94;

    @BindView(R.id.img_photo_upload)
    ImageView ivPhotoPreview;

    @BindView(R.id.fbUpload)
    FloatingActionButton fabUpload;

    @BindView(R.id.hashtagView)
    HashTagEditText etHashTag;

    @BindView(R.id.etCaption)
    EditText etCaption;

    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnPost)
    Button btnPost;

    @BindView(R.id.swSendLocation)
    SwitchCompat mSwitch;


    private Uri fileUri;
    File imageAvatar;

    String strimgPicture;
    String strcaption;
    String strlocation;
    String strlat;
    String strlong;
    String strhashtagView;

    double dbLat;
    double dbLong;

    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;

    public FragmentImageUpload() {
    }

    public static FragmentImageUpload newInstance() {
        FragmentImageUpload fragmentImageUpload = new FragmentImageUpload();
        return fragmentImageUpload;
    }
    @Override
    protected void getArgument(Bundle bundle) {
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
    }

    @Override
    protected void initData() {
        initValue();

    }

    private void initValue() {
        strimgPicture = "";
        strcaption = "";
        strlocation = "";
        strlat = "";
        strlong = "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_upload;
    }
    @Override
    public String getScreenTitle() {
        return "Post Image";
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }
    }

    @OnClick(R.id.fbUpload)
    public void onUpload() {
        onLauchCamera();
    }

    @OnClick(R.id.btnCancel)
    public void onCancel() {
        FragmentUtil.popBackStack(getActivity());
    }

    @OnClick(R.id.btnPost)
    public void onPost() {
        uploadImage();
    }

    @OnCheckedChanged(R.id.swSendLocation)
    public void checkChangedSend(){
        if (mSwitch.isChecked()){
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();
        }
    }

    private void onLauchCamera() {
        ImagePickerUtil imagePickerUtil = new ImagePickerUtil();
        Intent getCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(imagePickerUtil.createFileUri(getActivity()));
        getCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(getCamera, ApiConstance.REQUEST_CODE_TAKEPHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ApiConstance.REQUEST_CODE_TAKEPHOTO && resultCode == Activity.RESULT_OK) {
            //Start cropImage Activity
            CropImage.activity(fileUri).setAspectRatio(16, 9).start(getContext(), this);
        }
        //Get result from cropImage Activity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    //lay bitmap tu uri result
                    Bitmap bitmap = BitmapUtil.decodeFromFile(resultUri.getPath(), 900, 900);
                    creatFilefromBitmap(bitmap);
                    ivPhotoPreview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    //Tao file tu Bitmap
    private File creatFilefromBitmap(Bitmap bitmap) throws IOException {
        File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/SocialPhoto");
        imageDir.mkdir();
        imageAvatar = new File(imageDir, "avatarCropped.jpg");
        OutputStream fOut = new FileOutputStream(imageAvatar);
        Bitmap getBitmap = bitmap;
        getBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return imageAvatar;
    }

    public void uploadImage() {
        showCoverNetworkLoading();

        Map<String, String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, SharedPrefUtils.getAccessToken());

        //param value
        strcaption = etCaption.getText().toString();
        //getValuesHashTagView();
        strhashtagView = etHashTag.getValues().toString();

        Map<String, String> params = new HashMap<>();

        if (!strcaption.isEmpty()) {
            params.put(ApiConstance.FILE_CAPTION, strcaption);
        }

        if (!strhashtagView.isEmpty()) {
            params.put(ApiConstance.FILE_HASHTAG, strhashtagView);
        }

        if (mSwitch.isChecked()) {
            params.put(ApiConstance.FILE_LOCATION, strlocation);
            params.put(ApiConstance.LAT, strlat);
            params.put(ApiConstance.LONG, strlong);
        }

        Map<String, File> filePart = new HashMap<>();
        filePart.put(ApiConstance.FILE_IMAGE, imageAvatar);

        UploadImageRequest uploadImageRequest = new UploadImageRequest(Request.Method.POST, ApiConstance.IMAGE_UPLOAD, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                initialNetworkError();
            }
        }, UploadImageResponse.class, header, new Response.Listener<UploadImageResponse>() {
            @Override
            public void onResponse(UploadImageResponse response) {
                hideCoverNetworkLoading();
                if (response != null) {
                    reInit(response.data);
                }
            }
        }, params, filePart);

        NetworkUtils.getInstance(getActivity().getApplicationContext()).addToRequestQueue(uploadImageRequest);
    }
    private void reInit(DataUploadResponse data){
        ImageLoader.loadImage(getContext(), R.drawable.loading_list_image_220, data.image.url, ivPhotoPreview);
        etCaption.setText(data.image.caption);
        if (data.image.lat.isEmpty() || data.image._long.isEmpty() ) {
            mSwitch.setEnabled(false);
        } else {
            mSwitch.setEnabled(true);
        }
        etHashTag.setText(data.image.hashtag.toString());
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mCurrentLocation != null) {
            dbLat = mCurrentLocation.getLatitude();
            dbLong = mCurrentLocation.getLongitude();
            strlat = Double.toString(dbLat);
            strlong = Double.toString(dbLong);

            getAddress();

        }
    }
    public void getAddress() {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(dbLat, dbLong, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            strlocation = address + ", " + state + ", " + city + ", " + country;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {

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
}

