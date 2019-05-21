import { WebPlugin } from '@capacitor/core';
import { TTSPlugin } from './definitions';

export class TTSWeb extends WebPlugin implements TTSPlugin {
  constructor() {
    super({
      name: 'TTS',
      platforms: ['web']
    });
  }

  async echo(options: { value: string }): Promise<{value: string}> {
    console.log('ECHO', options);
    return options;
  }
}

const TTS = new TTSWeb();

export { TTS };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(TTS);
