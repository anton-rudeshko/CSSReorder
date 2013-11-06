package com.rudeshko.css.rearrange;

import com.intellij.application.options.codeStyle.arrangement.ArrangementSettingsPanel;
import com.intellij.lang.css.CSSLanguage;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.css.CssFileType;
import org.jetbrains.annotations.NotNull;

public class CssArrangementPanel extends ArrangementSettingsPanel {

    public CssArrangementPanel(@NotNull CodeStyleSettings settings) {
        super(settings, CSSLanguage.INSTANCE);
    }

    @Override
    protected int getRightMargin() {
        return 80;
    }

    @NotNull
    @Override
    protected FileType getFileType() {
        return CssFileType.INSTANCE;
    }

    @Override
    protected String getPreviewText() {
        return null;
    }
}
