package com.rudeshko.css.rearrange;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.codeStyle.arrangement.DefaultArrangementEntry;
import com.intellij.psi.codeStyle.arrangement.NameAwareArrangementEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CssElementArrangementEntry extends DefaultArrangementEntry implements NameAwareArrangementEntry {
    @Nullable
    private String name;

    public CssElementArrangementEntry(@Nullable CssElementArrangementEntry parent, @NotNull TextRange range, @Nullable String name) {
        super(parent, range.getStartOffset(), range.getEndOffset(), true);
        this.name = name;
    }

    @Nullable
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CssElementArrangementEntry{" +
                "name='" + name + '\'' +
                ", children.size='" + getChildren().size() + '\'' +
                '}';
    }
}
