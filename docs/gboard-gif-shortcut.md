# Gboard GIF shortcut — issue #6

Independent from native KLIPY GIF picker (#5).

## Behavior
A configurable GIF toolbar key switches from Octopus to enabled Gboard, using supported Android IME APIs. It cannot guarantee opening Gboard's internal GIF panel.

## Implementation investigation
- `ToolbarUtils.kt`: add shortcut entry, icon, and dispatch.
- Identify the active Octopus `InputMethodService` and its toolbar action handling.
- Discover enabled Gboard IME ID via `InputMethodManager.enabledInputMethodList`; do not hardcode an IME ID.
- Android 9+ (`API 28`): evaluate `InputMethodService.switchInputMethod(id)` for direct switch.
- Older Android: show system IME picker when direct switching is unavailable.
- If Gboard is missing or disabled, provide an actionable setup explanation.
- Do not attempt privileged IME switching or unsupported internal Gboard intents.
- Test switching and manual return on Android 15+, preserving Octopus input state where possible.

## Acceptance
No changes to KLIPY feature branch; no regression to Enter, split layout, predictions or autocorrect.

This commit is the initial implementation investigation, not functioning shortcut code.
