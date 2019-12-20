package com.labin.browsingimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private EditText etName,etDescription;
    private Button btnSave;
    private ImageView imgProfile;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=findViewById(R.id.etName);
        etDescription=findViewById(R.id.etDesc);
        imgProfile =findViewById(R.id.imgProfile);
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener((.this ));
                imgProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BrowseImage();
                    }

                    private void BrowseImage() {

                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 0);

                    }
                    @Override
                    protected void onActivityResult(int requestcode, int resultCode, @Nullable Intent data){
                        super.onActivityResult(requestcode,resultCode,data);

                        if (resultCode == RESULT_OK) {
                            if (data==null) {
                                Toast.makeText(this, "please select image", Toast.LENGTH_SHORT.show());
                            }
                            }
                        Uri uri =data.getData();
                        imagePath=getRealPathFromUri(uri);
                        previewImage (imagePath);
                    }

                    private String getRealPathFromUri(Uri uri) {
                        String[] projection ={MediaStore.Images.Media.DATA};
                        CursorLoader loader =new CursorLoader(getApplicationContext(), uri, projection,null,
                                null ,null );
                        Cursor cursor =loader.loadInBackground();
                        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String result =cursor.getString(colIndex);
                        cursor.close();
                        return result;
                    }


                
                });
    }

    private void previewImage(String imagePath) {
        File imgFile= new File(imagePath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgProfile.setImageBitmap(myBitmap);
        }
    }
}
