package com.codestructure.customui;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.VideoView;

public class VideoPlayer extends VideoView implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private MediaPlayer mediaPlayer;
    public boolean ismute = true;

    private int mVideoWidth;
    private int mVideoHeight;


    public VideoPlayer(Context context, AttributeSet attributes) {
        super(context, attributes);

        mVideoWidth = this.getMeasuredWidth();
        mVideoHeight = this.getMeasuredHeight();
        this.setOnPreparedListener(this);
        this.setOnCompletionListener(this);
        this.setOnErrorListener(this);
    }
    public VideoPlayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Log.i("@@@", "onMeasure");
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if (mVideoWidth * height > width * mVideoHeight) {
                // Log.i("@@@", "image too tall, correcting");
                height = width * mVideoHeight / mVideoWidth;
            } else if (mVideoWidth * height < width * mVideoHeight) {
                // Log.i("@@@", "image too wide, correcting");
                width = height * mVideoWidth / mVideoHeight;
            } else {
                // Log.i("@@@", "aspect ratio is correct: " +
                // width+"/"+height+"="+
                // mVideoWidth+"/"+mVideoHeight);
            }
        }
        // Log.i("@@@", "setting size: " + width + 'x' + height);
        setMeasuredDimension(width, height);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }



    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        mute();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {  }

    public void setVideoSize(int width, int height) {
        mVideoWidth = width;
        mVideoHeight = height;
    }


    public void mute() {
        this.setVolume(0);
        ismute = true;
    }

    public void getVolume(){

    }

    public void unmute() {
        this.setVolume(100);
        ismute = false;
    }

    private void setVolume(int amount) {
        try {
            final int max = 100;
            final double numerator = max - amount > 0 ? Math.log(max - amount) : 0;
            final float volume = (float) (1 - (numerator / Math.log(max)));

            this.mediaPlayer.setVolume(volume, volume);
            ismute = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}