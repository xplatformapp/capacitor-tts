import {PluginListenerHandle, Plugins} from '@capacitor/core';
import {ITTSPlugin, TTSOptions} from './index';

const { TTS }  = Plugins;

export class TextToSpeech implements ITTSPlugin {

    constructor(options: TTSOptions) {
        this.initialize(options);

        console.log('Engine Inititialize');
    }


    initialize(options: TTSOptions): Promise<{ value: boolean }> {
        return TTS.initialize(options);
    }

    checkLanguage(): Promise<{ values: string[] }> {
        return TTS.checkLanguage();
    }

    openInstallTts(): Promise<{ value: null }> {
        return TTS.openInstallTts();
    }

    setPitch(options: { pitch: number }): Promise<{ value: boolean }> {
        return TTS.setPitch(options);
    }

    setSpeechRate(options: { rate: number }): Promise<{ value: boolean }> {
        return TTS.setSpeechRate(options);
    }

    speak(options: { text: string }): Promise<{ value: boolean }> {
        return TTS.speak(options);
    }

    stop(): Promise<{ value: boolean }> {
        return undefined;
    }

    addListener(eventName: "onStart" | "onDone" | "onError", listenerFunc: (info: any) => void): PluginListenerHandle {
        return TTS.addListener(eventName, listenerFunc);
    }

}
