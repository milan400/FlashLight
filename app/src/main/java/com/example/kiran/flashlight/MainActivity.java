package com.example.kiran.flashlight;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    private ImageButton flashimage;
    android.hardware.Camera camera;
    android.hardware.Camera.Parameters parameters;
    boolean isflash = false;
    boolean ison = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashimage = (ImageButton) findViewById(R.id.flashimage);
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            camera = android.hardware.Camera.open();
            parameters = camera.getParameters();
            isflash = true;

        }
        flashimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isflash) {
                    if (!ison) {
                        flashimage.setImageResource(R.drawable.on);
                        parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        ison = true;

                    } else {
                        flashimage.setImageResource(R.drawable.off);
                        parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        ison = false;
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error.....");
                    builder.setMessage("Flash is not Available on this device....");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(camera!=null){
            camera.release();
            camera = null;
        }
    }
}
