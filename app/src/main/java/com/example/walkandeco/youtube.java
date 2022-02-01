package com.example.walkandeco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


//왜 클래스 YouTubeBaseActivity 설정하는 이유?
//유아이에 직접 유튜브 플레이어 뷰에 확장하려고 함
public class youtube extends YouTubeBaseActivity {

    //유튜브 플레이 객체이름으로 생성하기
    //안드로이드가 유튜브 객체를 어떻게 만들수가 있는지 확인해보기

    //YouTubePlayerView 유튜브 동영상을 표시하기 위한 뷰 이뷰를 사용하기 위해서 YouTubeBaseActivity를 넣는것이다
    YouTubePlayerView playerView;

    //유튜브 동영상 재생및 제어하기 위한 메소드
    YouTubePlayer player;

    //유튜브 키 받기
    private String KEY = "AIzaSyBT-xOeV-fVmzXsqneNy0aP6sUieJIrFNg";
    //보고 싶어하는 영상 키받기
    //탄소 관련 영상 아이디 값
    private String id = "GrMCf4PrKsQ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);


        Log.e("6","");
        //재생 버튼을 누르게 되면 밑에있는playvideo() 메소드가
        Button button = findViewById(R.id.youtubeBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cueVideo는 동영상을 재생하도록 준비는 하지만 play()함수를 호출하기전에는 동영상을 실행하지 않는다
                //()는 아이디값을 넣어준것이다
                player.cueVideo(id);
                Log.e("1","");
            }
        });

        play();

    }

    //isPlaying()는 플레이어가 현재 재생중인지 확인해주는것
//    private void playvideo() {
//        if (player != null) {
//            //cueVideo는 동영상을 재생하도록 준비는 하지만 play()함수를 호출하기전에는 동영상을 실행하지 않는다
//            player.cueVideo(id);
//            Log.e("4","");
//        }
//    }

    private void play() {
        playerView = findViewById(R.id.youTubePlayerView);
        Log.e("5","");
        //뷰를 시작하기 위해서 initialize를 호출하여서 동영상이 나올수 있도록 사용할수있는 유튜브 플레이어를 만든다
        playerView.initialize(KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            //onInitializationSuccess 플레이어가 초기화 될때 호출된다 즉 첫화면이 될때 실행을 할 수 있게 해준다
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //위의 매개변수에 유튜브 플레이어 youTubePlayer는
                player = youTubePlayer;
                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {
                        player.play();
                        Log.e("3","");
                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

}
