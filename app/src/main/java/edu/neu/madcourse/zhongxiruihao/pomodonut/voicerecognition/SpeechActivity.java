package edu.neu.madcourse.zhongxiruihao.pomodonut.voicerecognition;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

/**
 * Created by Ben_Big on 4/22/17.
 */

public class SpeechActivity extends Activity {
    private TextView textSpeechInput;
    private ImageButton buttonSpeak;
    private final int REQ_CODE_SPEECH_INPUT=100;
    private ConnectivityManager connManager;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognition);

        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        textSpeechInput=(TextView) findViewById(R.id.txtSpeechInput);
        buttonSpeak=(ImageButton) findViewById(R.id.btnSpeak);


        buttonSpeak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                promptSpeechInput();
            }
        });
    }


    private void promptSpeechInput(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.speech_prompt));

        if (connManager.getActiveNetworkInfo()==null) {
            intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        }

        try{
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        }catch(ActivityNotFoundException a){
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textSpeechInput.setText(result.get(0));
                }
                break;
            }
        }
    }



}
