# Wake screen up by proximity sensor

# Build
./gradlew build

# Install
apk-sign ./app/build/outputs/apk/release/app-release-unsigned.apk && adb install ./app/build/outputs/apk/release/app-release-unsigned.apk
