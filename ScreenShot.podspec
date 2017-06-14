Pod::Spec.new do |spec|
  spec.name         = 'ScreenShotModule'
  spec.version      = '1.0.0'
  spec.license      = { :type => 't5online' }
  spec.homepage     = 'https://github.com/t5online-inc/ScreenShotModule'
  spec.authors      = { 't5online' => 'yslee@t5online.com' }
  spec.summary      = 'ScreenShotModule(Nebula)'
  spec.source       = { :git => 'https://github.com/t5online-inc/ScreenShotModule.git' }
  spec.source_files = 'ios/ScreenShotModule/Shared/**/*.{h,m}'
  spec.framework    = 'Foundation', 'UIKit'
end
