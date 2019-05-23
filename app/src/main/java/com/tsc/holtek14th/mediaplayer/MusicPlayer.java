package com.tsc.holtek14th.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tsc.holtek14th.R;

import java.io.IOException;

public class MusicPlayer {

    Context context;
    MediaPlayer player;
    SeekBar seekBar;
    TextView txDurationTime, txCurrentTime;
    ImageView btPlay, btFastForward, btRewind;
    Handler handler;
    int duration;

    public MusicPlayer(Context context, MediaPlayer player, SeekBar seekBar, TextView txDurationTime, TextView txCurrentTime, ImageView btPlay, ImageView btFastForward, ImageView btRewind) {
        this.context = context;
        this.player = player;
        this.seekBar = seekBar;
        this.seekBar.setOnSeekBarChangeListener(seekBarListener);
        this.txDurationTime = txDurationTime;
        this.txCurrentTime = txCurrentTime;
        this.btPlay = btPlay;
        this.btFastForward = btFastForward;
        this.btRewind = btRewind;
        this.handler = new Handler();
    }

    public void setMusic(int url) {
        player = player.create(context, url);
        duration = player.getDuration();
        seekBar.setMax(duration);
        String endTime = String.format("%02d:%02d", duration / 1000 / 60, duration / 1000 % 1000 % 60);
        txDurationTime.setText(endTime);

        setPlayerOnCompletionListener(btPlay);
        setPlayOrPause(btPlay);
        setFastForward(btFastForward);
        setRewind(btRewind);
    }

    public void setMusic(String url){
        try {
            player.setDataSource(url);
            player.prepare();
            duration = player.getDuration();
            seekBar.setMax(duration);
            String endTime = String.format("%02d:%02d", duration / 1000 / 60, duration / 1000 % 1000 % 60);
            txDurationTime.setText(endTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPlayerOnCompletionListener(final ImageView button){
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                button.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                handler.removeCallbacks(setSeekBar);
            }
        });
    }

    private void setPlayOrPause(final ImageView button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    player.pause();
                    button.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    handler.removeCallbacks(setSeekBar);
                } else {
                    player.start();
                    button.setImageResource(R.drawable.ic_pause_black_24dp);
                    handler.post(setSeekBar);
                }
            }
        });
    }

    private void setFastForward(ImageView button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = player.getCurrentPosition();
                if (currentPosition <= duration - 30000) {
                    currentPosition += 30000;
                } else {
                    currentPosition = duration;
                }
                player.seekTo(currentPosition);
                seekBar.setProgress(currentPosition);
                String startTime = String.format("%02d:%02d", currentPosition / 1000 / 60, currentPosition / 1000 % 1000 % 60);
                txCurrentTime.setText(startTime);

            }
        });
    }

    private void setRewind(ImageView button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = player.getCurrentPosition();
                if ((currentPosition >= 30000)) {
                    currentPosition -= 30000;
                } else {
                    currentPosition = 0;
                }
                player.seekTo(currentPosition);
                seekBar.setProgress(currentPosition);
                String startTime = String.format("%02d:%02d", currentPosition / 1000 / 60, currentPosition / 1000 % 1000 % 60);
                txCurrentTime.setText(startTime);
            }
        });
    }

    // set seek bar change or from user change
    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                String startTime = String.format("%02d:%02d", progress / 1000 / 60, progress / 1000 % 1000 % 60);
                txCurrentTime.setText(startTime);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.seekTo(seekBar.getProgress());
        }
    };

    private Runnable setSeekBar = new Runnable() {
        @Override
        public void run() {
            int currentPosition = player.getCurrentPosition();
            seekBar.setProgress(currentPosition);
            String startTime = String.format("%02d:%02d",currentPosition/1000/60,currentPosition/1000%1000%60);
            txCurrentTime.setText(startTime);
            handler.postDelayed(setSeekBar,1000);
        }
    };
}
