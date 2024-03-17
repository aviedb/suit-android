package dev.aviedb.suit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler h;
    private Thread t;
    private ImageView ivLawan;
    private ImageView ivRock;
    private ImageView ivScissor;
    private ImageView ivPaper;
    private ImageView ivBattlePlayer;
    private ImageView ivBattleLawan;

    private int tanganLawan;
    private int tanganPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ivLawan = this.findViewById(R.id.ivLawan);
        this.ivRock = this.findViewById(R.id.ivRock);
        this.ivPaper = this.findViewById(R.id.ivPaper);
        this.ivScissor = this.findViewById(R.id.ivScissor);
        this.ivBattlePlayer = this.findViewById(R.id.ivBattlePlayer);
        this.ivBattleLawan = this.findViewById(R.id.ivBattleLawan);

        this.ivRock.setOnClickListener(this);
        this.ivPaper.setOnClickListener(this);
        this.ivScissor.setOnClickListener(this);

        this.h = new Handler(Looper.getMainLooper());
        this.t = new Thread(this::randomize);

        this.t.start();
    }

    private void randomize() {
        try {
            while(true) {
                int i = (int) Math.floor(Math.random() * 3);
                Thread.sleep(100);

                h.post(() -> {
                    MainActivity.this.tanganLawan = i;
                    if (i == 0) {
                        this.ivLawan.setImageResource(R.drawable.rock);
                    }
                    else if (i == 1) {
                        this.ivLawan.setImageResource(R.drawable.paper);
                    }
                    else {
                        this.ivLawan.setImageResource(R.drawable.scissor);
                    }
                });
            }
        } catch (Exception e) {}
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivRock) {
            this.ivBattlePlayer.setImageResource(R.drawable.rock);
            this.tanganPlayer = 0;
        } else if (v.getId() == R.id.ivPaper) {
            this.ivBattlePlayer.setImageResource(R.drawable.paper);
            this.tanganPlayer = 1;
        } else {
            this.ivBattlePlayer.setImageResource(R.drawable.scissor);
            this.tanganPlayer = 2;
        }

        if (this.tanganLawan == 0) this.ivBattleLawan.setImageResource(R.drawable.rock);
        else if (this.tanganLawan == 1) this.ivBattleLawan.setImageResource(R.drawable.paper);
        else this.ivBattleLawan.setImageResource(R.drawable.scissor);

        if (this.tanganLawan == this.tanganPlayer) {
            Toast.makeText(this, "Seri", Toast.LENGTH_SHORT).show();
        } else if ((this.tanganPlayer - this.tanganLawan) % 3 == 1) {
            Toast.makeText(this, "Anda Menang!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Anda Kalah!", Toast.LENGTH_SHORT).show();
        }
    }
}