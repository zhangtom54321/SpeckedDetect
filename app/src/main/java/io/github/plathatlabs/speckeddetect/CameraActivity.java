package io.github.plathatlabs.speckeddetect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseModelOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;
import com.google.firebase.ml.custom.model.FirebaseLocalModelSource;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;

public class CameraActivity extends AppCompatActivity {

    private ImageView imageView;
    private String displayString = new String();
    private String genre, user, emotion;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button btnCamera = (Button) findViewById(R.id.btnCamera);
        imageView = (ImageView) findViewById(R.id.ImageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
                Toast.makeText(CameraActivity.this, "Open Camera Clicked",
                        Toast.LENGTH_LONG).show();
                Log.d("Camera", "Camera Clicked");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        //byte[] byteArray = stream.toByteArray();
        int[][][] intArray = new int[bitmap.getWidth()][bitmap.getHeight()][3]; // assumed using RGB, may also include alpha
        for (int h = 0; h < bitmap.getHeight(); h++) {
            for (int w = 0; w < bitmap.getWidth(); w++) {
                intArray[w][h][0] = red(bitmap.getPixel(w, h));
                intArray[w][h][1] = green(bitmap.getPixel(w, h));
                intArray[w][h][0] = blue(bitmap.getPixel(w, h));
            }
        }

        //bitmap.recycle();

        //Intent intentLink = new Intent(CameraActivity.this, LinkActivity.class);
        //CameraActivity.this.startActivity(intentLink);

        FirebaseApp.initializeApp(this);

        FirebaseLocalModelSource localSource = new FirebaseLocalModelSource.Builder("mobilenet.tflite") //could be error here
                .setAssetFilePath("/")  // Or setFilePath if you downloaded from your host
                .build();
        FirebaseModelManager.getInstance().registerLocalModelSource(localSource);

        FirebaseModelOptions options = new FirebaseModelOptions.Builder()
                .setLocalModelName("mobilenet.tflite")
                .build();
        FirebaseModelInterpreter firebaseInterpreter = null;
        try {
            firebaseInterpreter =
                    FirebaseModelInterpreter.getInstance(options);
        } catch (FirebaseMLException e) {
            Toast.makeText(CameraActivity.this, "THIS IS AN ERROR",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        FirebaseModelInputOutputOptions inputOutputOptions = null;
        try {
            inputOutputOptions =
                    new FirebaseModelInputOutputOptions.Builder()
                            .setInputFormat(0, FirebaseModelDataType.INT32, new int[]{bitmap.getWidth(), bitmap.getHeight(), 3})
                            .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 1000})
                            .build();
        } catch (FirebaseMLException e) {
            Toast.makeText(CameraActivity.this, "ANOTHER ERROR",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        FirebaseModelInputs inputs = null;
        try {
            inputs = new FirebaseModelInputs.Builder()
                    .add(intArray)  // add() as many input arrays as your model requires
                    .build();
        } catch (FirebaseMLException e) {
            Toast.makeText(CameraActivity.this, "ANOTHA ERROR",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        Task<FirebaseModelOutputs> result = null;
        try {
            result =
                    firebaseInterpreter.run(inputs, inputOutputOptions)
                            .addOnSuccessListener(
                                    new OnSuccessListener<FirebaseModelOutputs>() {
                                        @Override
                                        public void onSuccess(FirebaseModelOutputs result) {
                                            // ...
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Task failed with an exception
                                            // ...
                                        }
                                    });
        } catch (FirebaseMLException e) {
            e.printStackTrace();
        }
        //float[][] output = result.<float[][]>getOutput(0);
        //float[] probabilities = output[0];

        Intent intentLink = new Intent(CameraActivity.this, ResultActivity.class);
        CameraActivity.this.startActivity(intentLink);
    }

}