require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-wowza-gocoder"
  s.version      = package["version"]
  s.summary      = package['description']
  s.author       = package['author']
  s.homepage     = package['homepage']
  s.license      = package['license']
  s.ios.deployment_target = "9.0"
  
  s.source_files  = "ios/*.{h,m}"
  s.source = {:path => "ios/"}
  s.pod_target_xcconfig = { 'USER_HEADER_SEARCH_PATHS' => '"${PROJECT_DIR}/../wowza-gocoder-sdk/wowzagocoder_static_lib/headers"' }

  s.dependency "React"
end