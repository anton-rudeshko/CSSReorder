package com.rudeshko.css.rearrange;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

public class RearrangeTest extends LightCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "c:/dev/java/CSSReorder/testData/";
    }

    public void testSimple() throws Exception {
        runCase("simple");
    }

    public void testSeveral() throws Exception {
        runCase("several");
    }

    public void testComment() throws Exception {
        runCase("comment"); // Failing. TODO: implement
    }

    public void testMedia() throws Exception {
        runCase("media");
    }

    public void testSelection() throws Exception {
        runCase("selection");
    }

    public void runCase(String simple) {
        myFixture.configureByFiles(simple + "/in.css");
        myFixture.performEditorAction("applyCSSComb");
        myFixture.checkResultByFile(simple + "/out.css");
    }

}
