# iOS installation

1. Download the latest iOS Wowza Gocoder SDK from `https://www.wowza.com/pricing/installer#gocodersdk-downloads`.
2. Unzip the ZIP. Rename the directory name of the uncompressed files to `wowza-gocoder-sdk` and the move the directory to <PROJECT_ROOT>/ios.
3. Your directory structure should look like this.

![Copy SDK](./copy-sdk.png 'Copy SDK')

4. cd into your `<PROJECT_ROOT>` directory.
5. `yarn add @adrianso/react-native-wowza-gocoder`

6. Open your iOS workspace in XCode.
7. Right click on your project, and select "Add files to project".

![Add Framework](./add-framework.png 'Add Framework')

8. Select `<IOS_PROJECT_ROOT>/wowza-gocoder-sdk/WowzaGoCoderSDK.framework`. Click Add.
9. In the General tab, under Frameworks Libraries, and Embedbed Content, make you that the Wowza framework has been embedded correctly.

![Embed Framework](./embed-framework.png 'Embed Framework')

10. Re-compile your project.

## References

https://www.wowza.com/docs/how-to-install-gocoder-sdk-for-ios
