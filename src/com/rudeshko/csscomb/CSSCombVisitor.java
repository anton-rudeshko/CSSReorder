package com.rudeshko.csscomb;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.css.CssBlock;
import com.intellij.psi.css.CssDeclaration;
import com.intellij.psi.css.CssRuleset;
import com.intellij.psi.css.CssStylesheet;

import java.util.Arrays;

public class CSSCombVisitor extends PsiElementVisitor {

    @Override
    public void visitElement(PsiElement element) {
        if (element instanceof CssStylesheet) {
            final CssStylesheet cssStylesheet = (CssStylesheet) element;
            new WriteCommandAction.Simple(cssStylesheet.getProject(), cssStylesheet.getContainingFile()) {
                @Override
                protected void run() throws Throwable {
                    CssRuleset[] ruleSets = cssStylesheet.getRulesets();
                    for (CssRuleset ruleSet : ruleSets) {
                        CssBlock block = ruleSet.getBlock();
                        if (block != null) {
                            sortBlock(block);
                        }
                    }
                }
            }.execute();
        }
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
