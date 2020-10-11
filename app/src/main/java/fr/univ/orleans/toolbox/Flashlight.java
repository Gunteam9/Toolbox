package fr.univ.orleans.toolbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Flashlight extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar seekbar;
    private TextView blinkingSpeedLabel;

    private final int CAMERA_REQUEST_CODE = 1;

    private int blinkingSpeed = 0;
    private boolean hasPermission;
    private boolean hasFlashlight;
    private boolean isFlashlightOn;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);

        seekbar = findViewById(R.id.blinkingSpeed);
        seekbar.setOnSeekBarChangeListener(this);
        blinkingSpeedLabel = findViewById(R.id.blinkingSpeedLabel);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            hasPermission = true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }

        hasFlashlight = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * Enable or disable flashlight
     * On click receiver from image button
     * CountDownTimer receiver
     */
    public void activeFlashlight(View view) {
        if (hasPermission) {
            if (hasFlashlight) {
                if (timer != null)
                    timer.cancel();
                try {
                    if (isFlashlightOn) {
                        turnFlashlightOff();
                    }
                    else {
                        turnFlashlightOn();
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            else
                Toast.makeText(this, R.string.flashlight_not_detected, Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, R.string.flashlight_permission_denied, Toast.LENGTH_LONG).show();
    }

    /**
     * Back to home menu
     */
    public void back(View view) {
        finish();
    }

    /**
     * Enable flashlight
     * @throws CameraAccessException
     */
    private void turnFlashlightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
        isFlashlightOn = true;
    }

    /**
     * Disable flashlight
     * @throws CameraAccessException
     */
    private void turnFlashlightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
        isFlashlightOn = false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        blinkingSpeed = progress;
        blinkingSpeedLabel.setText(String.valueOf(blinkingSpeed));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Reset timer on new value in seekBar
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        //Like a infinity count down timer
        timer = new CountDownTimer(Integer.MAX_VALUE, Math.abs(11 - blinkingSpeed) * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                activeFlashlight(getWindow().getDecorView());
            }

            @Override
            public void onFinish() {
                timer.start();
            }
        };
        timer.start();
    }

    //Request permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasFlashlight = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_LONG).show();
                hasPermission = true;

            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
                hasPermission = false;
            }
        }
    }
}