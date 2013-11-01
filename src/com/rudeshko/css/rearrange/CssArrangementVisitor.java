package com.rudeshko.css.rearrange;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.css.*;
import com.intellij.util.containers.Stack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CssArrangementVisitor extends CssElementVisitor {
    private final CssArrangementParseInfo myInfo;
    private final Collection<TextRange> myRanges;

    @NotNull
    private final Stack<CssElementArrangementEntry> myStack = new Stack<CssElementArrangementEntry>();

    public CssArrangementVisitor(@NotNull CssArrangementParseInfo parseInfo, @NotNull Collection<TextRange> ranges) {
        this.myInfo = parseInfo;
        this.myRanges = ranges;
    }

    @Override
    public void visitFile(PsiFile file) {
        if (file instanceof CssFile) {
            visitCssFile((CssFile) file);
        }
    }

    @Override
    public void visitCssFile(CssFile file) {
        visitCssStylesheet(file.getStylesheet());
    }

    @Override
    public void visitCssStylesheet(CssStylesheet stylesheet) {
        for (CssRuleset ruleset : stylesheet.getRulesets(true)) {
            visitCssRuleset(ruleset);
        }
    }

    public void visitCssMedia(CssMedia media) {
        for (CssRuleset ruleset : media.getRulesets()) {
            visitCssRuleset(ruleset);
        }
    }

    @Override
    public void visitAtRule(CssAtRule atRule) {
        if (atRule instanceof CssMedia) {
            visitCssMedia((CssMedia) atRule);
        }
    }

    @Override
    public void visitCssRuleset(CssRuleset ruleset) {
        visitCssBlock(ruleset.getBlock());
    }

    @Override
    public void visitCssBlock(CssBlock block) {
        processEntry(createNewEntry(block.getTextRange(), null), block);
    }

    @Override
    public void visitCssDeclaration(CssDeclaration declaration) {
        createNewEntry(declaration.getTextRange(), declaration.getPropertyName());
    }

    private
    @Nullable
    CssElementArrangementEntry createNewEntry(@NotNull TextRange range, @Nullable String propertyName) {
        if (!isWithinBounds(range)) {
            return null;
        }

        CssElementArrangementEntry current = getCurrent();
        CssElementArrangementEntry entry = new CssElementArrangementEntry(current, range, propertyName, true);
        if (current == null) {
            myInfo.addEntry(entry);
        } else {
            current.addChild(entry);
        }
        return entry;
    }

    private void processEntry(@Nullable CssElementArrangementEntry entry, @Nullable PsiElement nextPsiRoot) {
        if (entry == null || nextPsiRoot == null) {
            return;
        }
        myStack.push(entry);
        try {
            nextPsiRoot.acceptChildren(this);
        } finally {
            myStack.pop();
        }
    }

    @Nullable
    private CssElementArrangementEntry getCurrent() {
        return myStack.isEmpty() ? null : myStack.peek();
    }

    private boolean isWithinBounds(@NotNull TextRange range) {
        for (TextRange textRange : myRanges) {
            if (textRange.intersects(range)) {
                return true;
            }
        }
        return false;
    }
}
