# GIF picker integration (issue #5)

This branch starts the native GIF feature work without changing existing IME behavior.

## Existing integration points (octopus-mvp)
- `app/src/main/java/helium314/keyboard/latin/InputView.java`: keyboard root view and panel lifecycle.
- `app/src/main/java/helium314/keyboard/latin/utils/ToolbarUtils.kt`: toolbar keys, icons and keycode dispatch.
- `app/src/main/java/helium314/keyboard/keyboard/KeyboardSwitcher.java`: keyboard layout switching.
- `app/build.gradle.kts`: Android dependencies and SDK settings.

## Design constraints
- KLIPY provider must be replaceable; no credentials in source control.
- GIF content should be sent through `InputConnection.commitContent` (compatibility wrapper where required) using a locally accessible `content://` URI and explicit read permission, not a remote HTTPS URL.
- Respect the target editor's declared MIME types. Preserve composing text and selection when opening/closing the picker.
- Never forward keyboard text to the provider; only deliberate GIF search queries.
- Do not modify prediction, autocorrect, split-spacebar or Enter handling as part of this initial scaffold.

## Next implementation steps
1. Check KLIPY's current API contract and production licensing.
2. Add a provider-neutral GIF search model and client.
3. Add toolbar action and integrated picker panel.
4. Implement safe URI sharing and fallback, then test compatible/incompatible apps.
5. Build and run regression checks before requesting review.

This document is a codebase inspection/scaffold note, **not a claim that GIF sending is implemented**.
