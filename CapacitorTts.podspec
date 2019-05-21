
  Pod::Spec.new do |s|
    s.name = 'CapacitorTts'
    s.version = '0.0.1'
    s.summary = 'This is the plugins for Capacitor Text to Speach '
    s.license = 'MIT'
    s.homepage = 'https://github.com/xplatformapp/capacitor-tts.git'
    s.author = 'Rahadur Rahman <get.rahadur@gmail.com>'
    s.source = { :git => 'https://github.com/xplatformapp/capacitor-tts.git', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end