package com.rudeshko.css.rearrange;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CssArrangementParseInfo {
    @NotNull
    private final List<CssElementArrangementEntry> myEntries = new ArrayList<CssElementArrangementEntry>();

    @NotNull
    public List<CssElementArrangementEntry> getEntries() {
        return myEntries;
    }

    public void addEntry(@NotNull CssElementArrangementEntry entry) {
        myEntries.add(entry);
    }
}
