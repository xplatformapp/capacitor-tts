# Capacitor Text-To-Speech

## Installation

```console
npm i capacitor-tts
```

## Getting Start
```typescript
import { TextToSpeech, Local } from 'capacitor-tts';

const tts = new TextToSpeech({
    local: Local.US,
    rate: 1.0,
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
setPitch(options: { value: number }): Promise<{ value: boolean }>;   
```
Set talking speed
```typescript
setSpeechRate(options: { value: number }): Promise<{ value: boolean }>;
```
Speak from text
```typescript
speak(options: { value: string }): Promise<{ value: boolean }>;
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
    DEVICE = 0,
    US     = 1,
    UK     = 2
}
```
