package com.rudeshko.csscomb;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.css.*;

import java.util.Arrays;

public class CSSCombVisitor extends CssElementVisitor {

    @Override
    public void visitCssFile(CssFile file) {
        visitCssStylesheet(file.getStylesheet());
    }

    @Override
    public void visitCssStylesheet(final CssStylesheet stylesheet) {
        new WriteCommandAction.Simple(stylesheet.getProject(), "Apply CSSReorder", stylesheet.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                CssRuleset[] ruleSets = stylesheet.getRulesets();
                for (CssRuleset ruleSet : ruleSets) {
                    CssBlock block = ruleSet.getBlock();
                    if (block != null) {
                        sortBlock(block);
                    }
                }
            }
        }.execute();
    }

    private void sortBlock(CssBlock block) {
        CssDeclaration[] sortedDeclarations = block.getDeclarations();
        Arrays.sort(sortedDeclarations, CssOrder.DEFAULT_COMPARATOR);

        CssDeclaration[] declarations = block.getDeclarations();
        for (int i = 0; i < declarations.length; i++) {
            block.addAfter(sortedDeclarations[i], declarations[i]);
        }

        for (CssDeclaration declaration : declarations) {
            declaration.delete();
        }
    }

}
