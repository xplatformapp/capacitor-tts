package app.xplatform.capacitor.plugins;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

@NativePlugin()
public class TTS extends Plugin implements TextToSpeech.OnInitListener {

    public static final String ERR_INVALID_OPTIONS = "ERR_INVALID_OPTIONS";
    public static final String ERR_NOT_INITIALIZED = "ERR_NOT_INITIALIZED";
    public static final String ERR_ERROR_INITIALIZING = "ERR_ERROR_INITIALIZING";
    public static final String ERR_UNKNOWN = "ERR_UNKNOWN";

    private boolean ttsInitialized = false;
    private TextToSpeech tts = null;
    private Context context = null;


    @Override
    public void onInit(int status) {
        if (status != TextToSpeech.SUCCESS) {
            tts = null;
        } else {
            // warm up the tts engine with an empty string
            tts.setLanguage(Locale.UK);
            if (Build.VERSION.SDK_INT >= 21) {
                Bundle ttsParams = new Bundle();
                ttsParams.putSerializable(TextToSpeech.Engine.KEY_PARAM_STREAM, "");
                tts.speak("", TextToSpeech.QUEUE_FLUSH, ttsParams, "");
            } else {
                HashMap<String, String> ttsParams = new HashMap<String, String>();
                ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
                tts.speak("", TextToSpeech.QUEUE_FLUSH, ttsParams);
            }

            ttsInitialized = true;
        }
    }



    @PluginMethod()
    public void initialize(final PluginCall call) {

        if (!ttsInitialized) {
            context = getContext();
            tts = new TextToSpeech(getContext(), this);
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    // nothing to do
                }

                @Override
                public void onDone(String utteranceId) {
                    if (utteranceId.equals("")) {
                        notifyListeners("onDone", new JSObject().put("value", true));
                    }
                }

                @Override
                public void onError(String utteranceId) {
                    if (utteranceId.equals("")) {
                        notifyListeners("onError", new JSObject().put("value", true));
                    }
                }
            });

            // Set Local Language
            switch(call.getInt("local", 0)){
                case 1:
                    tts.setLanguage(Locale.UK);
                    break;
                case 2:
                    tts.setLanguage(Locale.FRANCE);
                    break;
                default:
                    tts.setLanguage(Locale.US);
                    break;
            }

            float pitch = call.getFloat("pitch", 1.0f);
            tts.setPitch(pitch);

            float rate = call.getFloat("rate", 1.0f);

            if (Build.VERSION.SDK_INT >= 27) {
                tts.setSpeechRate((rate * 0.7f));
            } else {
                tts.setSpeechRate(rate);
            }

            call.success(new JSObject().put("value", true));

        } else {
            call.error("Text-To-Speech Already Initialize");
        }
    }


    @PluginMethod()
    public void stop(final PluginCall call) {
        tts.stop();
        call.success(new JSObject().put("value", true));
    }


    @PluginMethod()
    public void openInstallTts(final PluginCall call) {
        PackageManager pm = context.getPackageManager();
        Intent installIntent = new Intent();
        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        ResolveInfo resolveInfo = pm.resolveActivity(installIntent, PackageManager.MATCH_DEFAULT_ONLY);

        if( resolveInfo == null ) {
            // Not able to find the activity which should be started for this intent
        } else {
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(installIntent);
        }
    }


    @PluginMethod()
    public void checkLanguage(final PluginCall call) {

        Set<Locale> supportedLanguages = tts.getAvailableLanguages();
        String languages = "";
        if(supportedLanguages!= null) {
            for (Locale lang : supportedLanguages) {
                languages = languages + "," + lang;
            }
        }
        if (languages != "") {
            languages = languages.substring(1);
            JSObject ret = new JSObject();
            ret.put("value", languages);
            call.success(ret);
        } else {
            JSObject ret = new JSObject();
            ret.put("value", "");
            call.success(ret);
        }

    }


    @PluginMethod()
    public void setPitch(final PluginCall call) {
       float pitch = call.getFloat("pitch", 1.0f);

       tts.setPitch(pitch);
       call.success(new JSObject().put("value", "true"));
    }


    @PluginMethod()
    public void setSpeechRate(final PluginCall call) {
        float rate = call.getFloat("rate", 1.0f);

        if (Build.VERSION.SDK_INT >= 27) {
            tts.setSpeechRate((float) rate * 0.7f);
        } else {
            tts.setSpeechRate((float) rate);
        }
        call.success(new JSObject().put("value", "true"));
    }


    @PluginMethod()
    public void speak(final PluginCall call) {
        if (ttsInitialized) {

            String text = call.getString("text");

            if (Build.VERSION.SDK_INT >= 21) {
                Bundle ttsParams = new Bundle();
                ttsParams.putSerializable(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, call.getCallbackId());
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, ttsParams, call.getCallbackId());
                call.success(new JSObject().put("value", true));
            } else {
                HashMap<String, String> ttsParams = new HashMap<String, String>();
                ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, call.getCallbackId());
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, ttsParams);
                call.success(new JSObject().put("value", true));
            }
        }

    }



}
