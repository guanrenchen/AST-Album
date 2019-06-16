package csie.aad.ast_album.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import csie.aad.ast_album.Models.SpacePhoto;
import csie.aad.ast_album.R;

public class SpacePhotoActivity extends AppCompatActivity {

    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";
    Animation barHide, barShow, editHide, editShow, test;
    Boolean showFlag = true;
    private ImageView mImageView;
    private SpacePhoto spacePhoto;
    private FloatingActionButton fab, back_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_photo);

        getSupportActionBar().hide();

        setTitle("");
        fab = findViewById(R.id.fab);
        back_fab = findViewById(R.id.back_fab);
        mImageView = findViewById(R.id.image);
        spacePhoto = getIntent().getParcelableExtra(EXTRA_SPACE_PHOTO);

        Glide.with(this)
                .load(spacePhoto.mpath)
                .asBitmap()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(SpacePhotoActivity.this, "Can't find the photo", Toast.LENGTH_SHORT).show();
                        finish();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);

        test = AnimationUtils.loadAnimation(this, R.anim.anim);
        barHide = AnimationUtils.loadAnimation(this, R.anim.hidebar);
        barShow = AnimationUtils.loadAnimation(this, R.anim.showbar);
        editHide = AnimationUtils.loadAnimation(this, R.anim.hideedit);
        editShow = AnimationUtils.loadAnimation(this, R.anim.showedit);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showFlag) {
                    back_fab.startAnimation(barHide);
                    back_fab.setClickable(false);
                    fab.startAnimation(editHide);
                    fab.setClickable(false);
                    showFlag = false;
                } else {
                    back_fab.startAnimation(barShow);
                    back_fab.setClickable(true);
                    fab.startAnimation(editShow);
                    fab.setClickable(true);
                    showFlag = true;
                }
            }
        });

    }

    public void backToMain(View view) {
        finish();
        //overridePendingTransition(R.anim.showbar, R.anim.hidebar);
    }

    public void editPhoto(View view) {
        Toast.makeText(this, spacePhoto.mpath, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, StylizeActivity.class);
        intent.putExtra(EXTRA_SPACE_PHOTO, spacePhoto);
        startActivity(intent);
    }

}