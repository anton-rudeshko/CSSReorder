package com.rudeshko.css.rearrange;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.arrangement.ArrangementSettings;
import com.intellij.psi.codeStyle.arrangement.Rearranger;
import com.intellij.psi.codeStyle.arrangement.match.ArrangementEntryMatcher;
import com.intellij.psi.codeStyle.arrangement.match.StdArrangementEntryMatcher;
import com.intellij.psi.codeStyle.arrangement.match.StdArrangementMatchRule;
import com.intellij.psi.codeStyle.arrangement.model.ArrangementAtomMatchCondition;
import com.intellij.psi.codeStyle.arrangement.model.ArrangementMatchCondition;
import com.intellij.psi.codeStyle.arrangement.std.*;
import com.intellij.util.containers.ContainerUtilRt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CssRearranger implements Rearranger<CssElementArrangementEntry>, ArrangementStandardSettingsAware {
    /**
     * Describing and filling out default order rules.
     * It will be used in default settings and then settings used to rearrange properties.
     */
    private static final List<StdArrangementMatchRule> DEFAULT_MATCH_RULES = new ArrayList<StdArrangementMatchRule>();

    static {
        for (String propertyName : CssOrder.DEFAULT_ORDER) {
            addRule(propertyName);
        }
    }

    private static void addRule(String propertyName) {
        ArrangementAtomMatchCondition condition = new ArrangementAtomMatchCondition(StdArrangementTokens.Regexp.NAME, propertyName);
        StdArrangementEntryMatcher matcher = new StdArrangementEntryMatcher(condition);
        DEFAULT_MATCH_RULES.add(new StdArrangementMatchRule(matcher, StdArrangementTokens.Order.BY_NAME));
    }

    private static final StdArrangementSettings DEFAULT_SETTINGS = new StdArrangementSettings(DEFAULT_MATCH_RULES);
    private static final Set<ArrangementSettingsToken> SUPPORTED_TYPES = ContainerUtilRt.newLinkedHashSet(StdArrangementTokens.EntryType.PROPERTY);

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

    @Nullable
    @Override
    public List<CompositeArrangementSettingsToken> getSupportedGroupingTokens() {
        return null;
    }

    @Nullable
    @Override
    public List<CompositeArrangementSettingsToken> getSupportedMatchingTokens() {
        return null;
    }

    @Override
    public boolean isEnabled(@NotNull ArrangementSettingsToken token, @Nullable ArrangementMatchCondition current) {
        return SUPPORTED_TYPES.contains(token);
    }

    @NotNull
    @Override
    public Collection<Set<ArrangementSettingsToken>> getMutexes() {
        return Collections.singleton(SUPPORTED_TYPES);
    }

    @NotNull
    @Override
    public ArrangementEntryMatcher buildMatcher(@NotNull ArrangementMatchCondition condition) throws IllegalArgumentException {
        throw new IllegalArgumentException("Can't build a matcher for condition " + condition);
    }
}
