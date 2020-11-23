package fr.univ.orleans.toolbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Flashlight extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView blinkingSpeedLabel;
    private ImageButton imageButton;

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

        SeekBar seekbar = findViewById(R.id.blinkingSpeed);
        seekbar.setOnSeekBarChangeListener(this);
        blinkingSpeedLabel = findViewById(R.id.blinkingSpeedLabel);
        imageButton = findViewById(R.id.imageButton);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            hasPermission = true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }

        hasFlashlight = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     *
     * On click receiver from image button
     *
     */
    public void activeFlashlight(View view) {
        controlFlashlight("button");
    }

    /**
     * Control the flashlight and turn it on or off
     * @param source
     */
    private void controlFlashlight(String source) {
        if (hasPermission) {
            if (hasFlashlight) {
                if (timer == null && blinkingSpeed != 0 && !isFlashlightOn) {
                    createTimer();
                    return;
                }
                else if (timer != null && source.equals("button")){
                    timer.cancel();
                    timer = null;
                }
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
     * Create a timer which control the flashlight activation
     */
    private void createTimer() {
        //Like a infinity count down timer
        timer = new CountDownTimer(Integer.MAX_VALUE, Math.abs(11 - blinkingSpeed) * 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                controlFlashlight("timer");
            }

            @Override
            public void onFinish() {
                timer.start();
            }
        };
        timer.start();
    }

    /**
     * Enable flashlight
     * @throws CameraAccessException
     */
    private void turnFlashlightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
        imageButton.setImageResource(R.mipmap.flashlight_on_foreground);
        isFlashlightOn = true;
    }

    /**
     * Disable flashlight
     * @throws CameraAccessException
     */
    private void turnFlashlightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
        imageButton.setImageResource(R.mipmap.flashlight_off_foreground);
        isFlashlightOn = false;
    }

    /**
     *
     * @return Return the status of the flashlight
     */
    @Deprecated
    private boolean getFlashlightStatus() {

        //Deprecated method but i didn't find the new method
        return Camera.open().getParameters().getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH);
    }

    /**
     * On seekbar's progress changed, update the value of blinkingSpeed
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        blinkingSpeed = progress;
        blinkingSpeedLabel.setText(String.valueOf(blinkingSpeed));
    }

    /**
     * Cancel and delete the timer
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

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