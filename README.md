# Capacitor Text-To-Speech


## Platform
* Android

## Installation

```console
npm i capacitor-tts
```

## Getting Start
```typescript
import { TextToSpeech, Local } from 'capacitor-tts';

const tts = new TextToSpeech({
    local: Local.US,
    rate:  1.0,
    pitch: 1.0
});

tts.speak({value: 'Text to speech'})
    .then(res => {
        console.log(res);
    })
    .catch(err => {
        console.error(err);
    })

```


## API

#### Class
```typescript
TextToSpeech
```
#### Methods
Open Installed text to speech activity
```typescript
openInstallTts(): Promise<{value: null}>;
```
Check available languages
```typescript
checkLanguage(): Promise<{values: string[]}>;
```
Set voice pitch
```typescript
setPitch(options: { pitch: number }): Promise<{ value: boolean }>;   
```
Set talking speed
```typescript
setSpeechRate(options: { rate: number }): Promise<{ value: boolean }>;
```
Speak from text
```typescript
speak(options: { text: string }): Promise<{ value: boolean }>;
```
Stop text to speech engine
```typescript
stop(): Promise<{ value: boolean }>; 
```
#### Types
```typescript
export interface TTSOptions {
    
    local: Local;
    
    rate?: number,
    
    pitch?: number,
}
```
#### Enums
```typescript
export enum Local {
    US     = 0,
    UK     = 1,
    FRANCE = 2
}
```
