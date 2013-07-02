package com.rudeshko.css.rearrange;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.arrangement.ArrangementSettings;
import com.intellij.psi.codeStyle.arrangement.Rearranger;
import com.intellij.psi.codeStyle.arrangement.StdArrangementSettings;
import com.intellij.psi.codeStyle.arrangement.group.ArrangementGroupingRule;
import com.intellij.psi.codeStyle.arrangement.group.ArrangementGroupingType;
import com.intellij.psi.codeStyle.arrangement.match.ArrangementEntryType;
import com.intellij.psi.codeStyle.arrangement.match.ArrangementModifier;
import com.intellij.psi.codeStyle.arrangement.match.StdArrangementEntryMatcher;
import com.intellij.psi.codeStyle.arrangement.match.StdArrangementMatchRule;
import com.intellij.psi.codeStyle.arrangement.model.ArrangementAtomMatchCondition;
import com.intellij.psi.codeStyle.arrangement.model.ArrangementMatchCondition;
import com.intellij.psi.codeStyle.arrangement.model.ArrangementSettingType;
import com.intellij.psi.codeStyle.arrangement.order.ArrangementEntryOrderType;
import com.intellij.psi.codeStyle.arrangement.settings.ArrangementStandardSettingsAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CssRearranger implements Rearranger<CssElementArrangementEntry>, ArrangementStandardSettingsAware {
    private static final List<StdArrangementMatchRule> DEFAULT_MATCH_RULES = new ArrayList<StdArrangementMatchRule>();

    static {
        for (String propertyName : CssOrder.DEFAULT_ORDER) {
            addRule(propertyName);
        }
    }

    private static void addRule(String propertyName) {
        ArrangementAtomMatchCondition condition = new ArrangementAtomMatchCondition(ArrangementSettingType.NAME, propertyName);
        StdArrangementEntryMatcher matcher = new StdArrangementEntryMatcher(condition);
        DEFAULT_MATCH_RULES.add(new StdArrangementMatchRule(matcher));
    }

    private static final StdArrangementSettings DEFAULT_SETTINGS = new StdArrangementSettings(Collections.<ArrangementGroupingRule>emptyList(), DEFAULT_MATCH_RULES);
    private static final Set<ArrangementEntryType> SUPPORTED_TYPES = EnumSet.of(ArrangementEntryType.PROPERTY);

    @Nullable
    @Override
    public Pair<CssElementArrangementEntry, List<CssElementArrangementEntry>> parseWithNew(@NotNull PsiElement root,
                                                                                           @Nullable Document document,
                                                                                           @NotNull Collection<TextRange> ranges,
                                                                                           @NotNull PsiElement element,
                                                                                           @Nullable ArrangementSettings settings) {
        return null; // TODO: implement
    }

    @NotNull
    @Override
    public List<CssElementArrangementEntry> parse(@NotNull PsiElement root, @Nullable Document document, @NotNull Collection<TextRange> ranges, @Nullable ArrangementSettings settings) {
        CssArrangementParseInfo parseInfo = new CssArrangementParseInfo();
        root.accept(new CssArrangementVisitor(parseInfo, ranges));
        return parseInfo.getEntries();
    }

    @Override
    public int getBlankLines(@NotNull CodeStyleSettings settings, @Nullable CssElementArrangementEntry parent, @Nullable CssElementArrangementEntry previous, @NotNull CssElementArrangementEntry target) {
        return -1;
    }

    @Nullable
    @Override
    public StdArrangementSettings getDefaultSettings() {
        return DEFAULT_SETTINGS;
    }

    @Override
    public boolean isNameFilterSupported() {
        return true;
    }

    @Override
    public boolean isEnabled(@NotNull ArrangementEntryType type, @Nullable ArrangementMatchCondition current) {
        return false;
    }

    @Override
    public boolean isEnabled(@NotNull ArrangementModifier modifier, @Nullable ArrangementMatchCondition current) {
        return false;
    }

    @Override
    public boolean isEnabled(@NotNull ArrangementGroupingType groupingType, @Nullable ArrangementEntryOrderType orderType) {
        return false;
    }

    @NotNull
    @Override
    public Collection<Set<?>> getMutexes() {
        return Collections.<Set<?>>singleton(SUPPORTED_TYPES);
    }
}
