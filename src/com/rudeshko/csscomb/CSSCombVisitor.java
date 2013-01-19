package com.rudeshko.csscomb;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.css.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by: Tesla
 * Date: 19.01.13 at 21:28
 */
public class CSSCombVisitor extends CssElementVisitor {

    @Override
    public void visitCssStylesheet(CssStylesheet stylesheet) {
        CssRuleset[] ruleSets = stylesheet.getRulesets();
        for (CssRuleset ruleSet : ruleSets) {
            visitCssRuleset(ruleSet);
        }
    }

    @Override
    public void visitCssRuleset(CssRuleset ruleSet) {
        CssBlock block = ruleSet.getBlock();
        if (block != null) {
            visitCssBlock(block);
        }
    }

    @Override
    public void visitCssBlock(@NotNull final CssBlock block) {
        final List<FutureDeclaration> futureDeclarations = new ArrayList<FutureDeclaration>();
        for (CssDeclaration declaration : block.getDeclarations()) {
            futureDeclarations.add(new FutureDeclaration(declaration));
        }

        Collections.sort(futureDeclarations, CssOrder.DEFAULT_COMPARATOR);

        new WriteCommandAction.Simple(block.getProject(), block.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                CssDeclaration lastDeclaration = null;
                for (FutureDeclaration futureDeclaration : futureDeclarations) {
                    block.removeDeclaration(futureDeclaration.getDeclaration());
                    lastDeclaration = block.addDeclaration(futureDeclaration.getName(), futureDeclaration.getValue(), lastDeclaration);
                }
            }
        }.execute();
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
