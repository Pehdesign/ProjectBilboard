package com.example.projectbilboard;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {

    private String mTitle;
    private String mDescription;
    private String mAdress;
    private String mPhone;
    private String mEmail;
    private String mImage = "";
    private String mTag;
    private String mRegion;

    EditText titleEditText;
    EditText descriptionEditText;
    EditText phoneEditText;
    EditText emailEditText;

    Spinner tagSpinner;

    String[] tags;

    Button okButton;
    Button cancelButton;
    Button imageButton;
    Button mapButton;

    ImageView imageView;

    InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        titleEditText = (EditText) findViewById(R.id.title);

        descriptionEditText = (EditText) findViewById(R.id.description);


        phoneEditText = (EditText) findViewById(R.id.phone);

        emailEditText = (EditText) findViewById(R.id.email);

        tagSpinner = (Spinner) findViewById(R.id.spinner_tag);

        okButton = (Button) findViewById(R.id.ok_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        imageButton = (Button) findViewById(R.id.image);
        mapButton = (Button) findViewById(R.id.mapButton);

        imageView = (ImageView) findViewById(R.id.imageView);

        Resources resources = getResources();
        tags = resources.getStringArray(R.array.tags);

        tagSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tags));


        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTag = tags[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getApplicationContext(), LocalizationActivity.class);
                startActivityForResult(mapIntent, 5);
            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isBlad = "";
                if (titleEditText.getText().toString().length() == 0) {
                    titleEditText.setBackgroundResource(R.color.colorError);
                } else {
                    mTitle = titleEditText.getText().toString();
                    Log.e("check", mTitle);
                    titleEditText.setBackgroundResource(android.R.color.transparent);
                    isBlad += "1";
                }

                if (descriptionEditText.getText().toString().length() == 0) {
                    descriptionEditText.setBackgroundResource(R.color.colorError);
                } else {
                    mDescription = descriptionEditText.getText().toString();
                    Log.e("check", mDescription);
                    descriptionEditText.setBackgroundResource(android.R.color.transparent);
                    isBlad += "1";
                }


                if (mAdress == null && mRegion == null) {
                    mapButton.setBackgroundResource(R.color.colorError);
                } else {
                    mapButton.setBackgroundResource(android.R.drawable.btn_default);
                    isBlad += "1";
                }

                if (phoneEditText.getText().toString().length() == 0) {
                    phoneEditText.setBackgroundResource(R.color.colorError);
                } else {
                    mPhone = phoneEditText.getText().toString();
                    Log.e("check", mPhone);
                    phoneEditText.setBackgroundResource(android.R.color.transparent);
                    isBlad += "1";
                }

                if (emailEditText.getText().toString().length() == 0) {
                    emailEditText.setBackgroundResource(R.color.colorError);
                } else {
                    mEmail = emailEditText.getText().toString();
                    Log.e("check", mEmail);
                    emailEditText.setBackgroundResource(android.R.color.transparent);
                    isBlad += "1";
                }


                if (isBlad.length() == 5) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Title", mTitle);
                    returnIntent.putExtra("Description", mDescription);
                    returnIntent.putExtra("Image", mImage);
                    returnIntent.putExtra("Tag", mTag);
                    returnIntent.putExtra("Region", mRegion);
                    returnIntent.putExtra("Adress", mAdress);
                    returnIntent.putExtra("Email", mEmail);
                    returnIntent.putExtra("Phone", mPhone);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

                    returnIntent.putExtra("Data", dateFormat.format(calendar.getTime()) + "");


                    setResult(RESULT_OK, returnIntent);
                    Log.e("intent", returnIntent.toString());


                    finish();
                }
            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                Log.e("Input", bitmap.toString());

                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 5 && resultCode == 5) {

            mRegion = data.getStringExtra("LAT");
            Log.e("Region", mRegion);
            mAdress = data.getStringExtra("LNG");
            Log.e("Adres", mAdress);

        }

    }
}
