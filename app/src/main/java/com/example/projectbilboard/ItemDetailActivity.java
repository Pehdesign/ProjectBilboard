package com.example.projectbilboard;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ItemDetailActivity extends AppCompatActivity {

    private String key_id;
    private String titlePhoto;
    private String descriptionPhoto;
    private String ownerPhoto;
    private String photo;
    private String email;
    private String phone;
    private String region;
    private String adress;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent();

        if (getIntent().getExtras() != null) {
            key_id = intent.getStringExtra("Key_id");
            Log.e("Key_id", key_id);
            titlePhoto = intent.getStringExtra("Title");
            Log.e("Title", titlePhoto);
            descriptionPhoto = intent.getStringExtra("Description");
            Log.e("Description", descriptionPhoto);
            ownerPhoto = intent.getStringExtra("Owner");
            Log.e("Owner", ownerPhoto);
            photo = intent.getStringExtra("ImageView");
            Log.e("Photo", photo);
            email = intent.getStringExtra("Email");
            Log.e("Photo", email);
            phone = intent.getStringExtra("Phone");
            Log.e("Phone", phone);
            region = intent.getStringExtra("Region");
            Log.e("Region", region);
            adress = intent.getStringExtra("Adress");
            Log.e("Adress", adress);

        }


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(titlePhoto);

        TextView textViewInsideScrollView = (TextView) findViewById(R.id.textViewInsideScrollView);
        textViewInsideScrollView.setText(descriptionPhoto);

        ImageView toolbarImage = (ImageView) findViewById(R.id.image_id);

        if (photo.length() > 5) {
            picassoLoader(this, toolbarImage, photo);
        } else {
            toolbarImage.setImageResource(R.drawable.brak_zdjecia);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toolbar supportToolbar = (Toolbar) findViewById(R.id.supportToolbar);
                setSupportActionBar(supportToolbar);
                if (supportToolbar.isShown()) {
                    supportToolbar.setVisibility(View.GONE);
                } else {
                    supportToolbar.setVisibility(View.VISIBLE);


                    if (ownerPhoto.equals(ToDoActivity.staticUser)) {
                        supportToolbar.setTitle("Ukonczony");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportToolbar.setTitleTextColor(getColor(R.color.textColorPrimary));
                        } else {
                            supportToolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));
                        }
                        supportToolbar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), ToDoActivity.class);
                                intent.putExtra("requestCode", "99");
                                intent.putExtra("key_id", key_id);
                                startActivity(intent);
                            }
                        });
                    } else {
                        supportToolbar.setTitle("Możliwy kontakt:");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportToolbar.setTitleTextColor(getColor(R.color.textColorPrimary));
                        } else {
                            supportToolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));
                        }
                    }

                }
            }
        });

    }

    public void picassoLoader(Context context, ImageView imageView, String url) {
        Log.d("PICASSO", "loading image");
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.central)
                .error(R.drawable.central)
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar supportToolbar = (Toolbar) findViewById(R.id.supportToolbar);
        Menu supportMenu = supportToolbar.getMenu();
        getMenuInflater().inflate(R.menu.support_toolbar_menu, supportMenu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                navigateUpTo(new Intent(this, ToDoItem.class));
            }
            return true;
        } else if (id == R.id.supportPhone) {
            Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(intentPhone);

        } else if (id == R.id.supportMail) {
            Intent intentEmail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Ogłoszenie " + titlePhoto);
            intentEmail.putExtra(Intent.EXTRA_TEXT, "Drogi użytkowniku.");
            startActivity(intentEmail);

        } else if (id == R.id.supportMap) {
            Intent mapIntent = new Intent(this, MapsActivity.class);
            mapIntent.putExtra("LAT", region);
            mapIntent.putExtra("LNG", adress);
            mapIntent.putExtra("Title", titlePhoto);
            this.startActivity(mapIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 99 && resultCode == 100) {
            Toast.makeText(getApplicationContext(), "Usunieto ogloszenie", Toast.LENGTH_LONG).show();
            Log.e("Koniec", "Koniec");
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Nie mozna usunac ogloszenia", Toast.LENGTH_LONG).show();
        }
    }

}
