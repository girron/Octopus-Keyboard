# Crashlytics beta spike (unverified)

This branch is separate from the installed vocabulary beta. Do not merge or
distribute without checking the build, signing, and actual reports.

- Firebase Crashlytics 19.4.4 retains the application's Android API 21 minimum.
- Crashlytics SDK and INTERNET permission are **debug-only**; not in release builds.
- Reporting defaults **OFF** via the debug Android manifest.
- With Firebase configured, Advanced settings shows **Share beta crash diagnostics**.
- Enabling this opt-in switch records one synthetic nonfatal verification exception.
- No app typing contents or clipboard values are deliberately attached.
- Google Analytics is NOT included. Native crash symbol upload is not enabled.
- Crashlytics may hold reports on device after disabling until next lifecycle event.

To build locally: download the Firebase JSON for `helium314.keyboard.debug`
and save to `app/google-services.json` (gitignored). Run
`./gradlew assembleDebug`.

In GitHub Actions, set the repository secret `OCTOPUS_FIREBASE_JSON_B64`
to the base64-encoded *entire* Firebase JSON; it will be decoded at build
time and not committed. With no secret, an ordinary debug APK is built
without an active Crashlytics connection.

The workflow retains the pinned signing certificate check. Version code is
bumped from 4226 to 4227. Verify the built artifact and install it locally
before uploading to Firebase App Distribution.

**Unverified:** Gradle 9 / AGP 9 plugin compatibility, full Android build,
manifest merge, real reporting delivery, signature checks, installation and
privacy behavior need testing before this experimental branch is merged.
