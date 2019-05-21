import {PluginListenerHandle} from "@capacitor/core";

declare module "@capacitor/core" {
    interface PluginRegistry {
        TTS: ITTSPlugin;
    }
}

export interface ITTSPlugin {

    initialize(options: TTSOptions): Promise<{ value: boolean }>;

    openInstallTts(): Promise<{value: null}>;

    checkLanguage(): Promise<{values: string[]}>

    setPitch(options: { value: number }): Promise<{ value: boolean }>;

    setSpeechRate(options: { value: number }): Promise<{ value: boolean }>;

    speak(options: { value: string }): Promise<{ value: boolean }>;

    stop(): Promise<{ value: boolean }>;


    addListener(eventName: 'onStart', listenerFunc: (info: any) => void): PluginListenerHandle;

    addListener(eventName: 'onDone', listenerFunc: (info: any) => void): PluginListenerHandle;

    addListener(eventName: 'onError', listenerFunc: (info: any) => void): PluginListenerHandle;

}


export interface TTSOptions {

    local: Local;

    rate?: number,

    pitch?: number,

}


export enum Local {

    DEVICE = 0,

    US = 1,

    UK = 2
}


/*export interface Voice {

}*/
