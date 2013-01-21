package com.rudeshko.csscomb;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.css.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        List<FutureDeclaration> futureDeclarations = new ArrayList<FutureDeclaration>();
        for (CssDeclaration declaration : block.getDeclarations()) {
            futureDeclarations.add(new FutureDeclaration(declaration));
        }

        Collections.sort(futureDeclarations, CssOrder.DEFAULT_COMPARATOR);

        CssDeclaration lastDeclaration = null;
        for (FutureDeclaration futureDeclaration : futureDeclarations) {
            block.removeDeclaration(futureDeclaration.getDeclaration());
            lastDeclaration = block.addDeclaration(futureDeclaration.getName(), futureDeclaration.getValue(), lastDeclaration);
        }
    }

    public class FutureDeclaration {
        private final String name;
        private final String value;
        private final CssDeclaration declaration;

        public FutureDeclaration(@NotNull CssDeclaration declaration) {
            this.declaration = declaration;
            CssTermList termList = declaration.getValue();
            value = termList == null ? "" : termList.getText();
            name = declaration.getPropertyName();
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public CssDeclaration getDeclaration() {
            return declaration;
        }
    }
}
