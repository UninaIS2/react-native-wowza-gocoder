## iOS installation

- cd into your project root directory.
- `yarn add @adrianso/react-native-wowza-gocoder`
- Download the latest iOS Wowza Gocoder SDK from `https://www.wowza.com/pricing/installer#gocodersdk-downloads`.
- Unzip the ZIP. Rename the directory name of the uncompressed files to `wowza-gocoder-sdk` and the move the directory to <PROJECT_ROOT>/ios.
- Your directory structure should look like this.

![Copy SDK](./copy-sdk.png 'Copy SDK')

- Open your iOS workspace in XCode.
- Right click on your project, and select "Add files to project".
  ![Add Framework](./add-framework.png 'Add Framework')

- Select <IOS_PROJECT_ROOT>/wowza-gocoder-sdk/WowzaGoCoderSDK.framework. Click Add.
- In the General tab, under Frameworks Libraries, and Embedbed Content, make you that the Wowza framework has been embedded correctly.
  ![Embed Framework](./embed-framework.png 'Embed Framework')
- Re-compile your project.

## References

https://www.wowza.com/docs/how-to-install-gocoder-sdk-for-ios
