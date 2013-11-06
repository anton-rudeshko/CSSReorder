package com.rudeshko.css.rearrange;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

/**
 * @link http://confluence.jetbrains.com/display/IDEADEV/Testing+IntelliJ+IDEA+Plugins
 * @link http://confluence.jetbrains.com/display/IntelliJIDEA/Tests+Prerequisites
 */
public class RearrangeTest extends LightPlatformCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        // TODO: Dunno how to do it platform independent for now
        return "/Users/rudeshko/dev/idea/CSSReorder/testData";
    }

    public void testSimple() throws Exception {
        runCase("simple");
    }

    public void testSeveral() throws Exception {
        runCase("several");
    }

    public void _testComment() throws Exception {
        runCase("comment"); // Failing. TODO: implement
    }

    public void testMedia() throws Exception {
        runCase("media");
    }

    public void testSelection() throws Exception {
        runCase("selection");
    }

    public void testImports() throws Exception {
        runCase("imports");
    }

    public void runCase(String simple) {
        myFixture.configureByFiles(simple + "/in.css");
        myFixture.performEditorAction("RearrangeCode");
        myFixture.checkResultByFile(simple + "/out.css");
    }

}
