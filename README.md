# CSSReorder
## About
**Very early version, use on your own risk. Stay tuned for updates.**

Sorting CSS properties in predefined order.
Consider also using node.js/npm version: <a href="https://github.com/csscomb/csscomb.js">csscomb.js</a>

## Disclaimer
Requires IntelliJ platform version &ge; 13.

Formerly known as CSSComb but was renamed to avoid people confusion.
This plugin is NOT supported by [CSSComb organization](https://github.com/csscomb) and have no shared codebase.
CSSComb author is not responsible for any bugs.
This is only my initiative to implement original CSSComb features as native to IDEA platform.

Based on [platform provided rearrangement](http://blogs.jetbrains.com/idea/2012/10/arrange-your-code-automatically-with-intellij-idea-12/).

## Installation
Install from IntelliJ IDEA plugin repositories or download manually [here](http://plugins.jetbrains.com/plugin?pr=&pluginId=7164).

## Usage
Should be invoked using *Code&nbsp;→&nbsp;Rearrange* action on CSS files.
This action also can be invoked on code reformatting action by checking corresponding option (Rearrange entries) in
[Reformat code dialog](https://www.jetbrains.com/help/idea/2016.1/reformat-file-dialog.html).

## Contribution
Feel free to report bugs, request features and contribute.

### How to Run Tests
1. Install desired product (PHPStorm, WebStorm, IntelliJ IDEA, etc).
1. Download sources from [IntelliJ Community](https://github.com/jetbrains/intellij-community).
1. Checkout required tag.
1. Create new SDK and point it into installed instance.
1. Attach sources from Community.
1. Plugin should fully compile by now.
1. Fix hardcoded path.
1. ... something else
1. If getting error about `TestCase class is not found` try using IDEA SDK

Useful links:
  * [IDEA Logs and settings](https://intellij-support.jetbrains.com/entries/23358108-Directories-used-by-the-IDE-to-store-settings-caches-plugins-and-logs)
