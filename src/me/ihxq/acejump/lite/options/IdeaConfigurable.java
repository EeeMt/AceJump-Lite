package me.ihxq.acejump.lite.options;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.ColorPanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IdeaConfigurable implements Configurable {
    private JPanel _optionsPanel;
    private ColorPanel _firstJumpBackground;
    private ColorPanel _firstJumpForeground;
    private ColorPanel _secondJumpBackground;
    private ColorPanel _secondJumpForeground;
    private JCheckBox _needSelectTextAfterJump;
    private JCheckBox _jumpBehind;
    private ColorPanel _panelBackground;
    private JCheckBox _toUpperCase;

    final PluginConfig config = ServiceManager.getService(PluginConfig.class);

    @Nls
    @Override
    public String getDisplayName() {
        return "emacsIDEAs";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "preferences.topic";
    }

    private void setFromConfig() {
        _firstJumpBackground.setSelectedColor(config.getFirstJumpBackground());
        _firstJumpForeground.setSelectedColor(config.getFirstJumpForeground());
        _secondJumpBackground.setSelectedColor(config.getSecondJumpBackground());
        _secondJumpForeground.setSelectedColor(config.getSecondJumpForeground());
        _panelBackground.setSelectedColor(config.getPanelBackground());
        _needSelectTextAfterJump.setSelected(config._needSelectTextAfterJump);
        _jumpBehind.setSelected(config._jumpBehind);
        _toUpperCase.setSelected(config._toUpperCase);
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        setFromConfig();
        return _optionsPanel;
    }

    @Override
    public boolean isModified() {
        return _firstJumpBackground.getSelectedColor() != config.getFirstJumpBackground()
                || _firstJumpForeground.getSelectedColor() != config.getFirstJumpForeground()
                || _secondJumpBackground.getSelectedColor() != config.getSecondJumpBackground()
                || _secondJumpForeground.getSelectedColor() != config.getSecondJumpForeground()
                || _panelBackground.getSelectedColor() != config.getPanelBackground()
                || _needSelectTextAfterJump.isSelected() != config._needSelectTextAfterJump
                || _jumpBehind.isSelected() != config._jumpBehind
                || _toUpperCase.isSelected() != config._toUpperCase;
    }

    @Override
    public void apply() throws ConfigurationException {
        if (!isModified()) {
            return;
        }

        config._firstJumpBackground = _firstJumpBackground.getSelectedColor().getRGB();
        config._firstJumpForeground = _firstJumpForeground.getSelectedColor().getRGB();
        config._secondJumpBackground = _secondJumpBackground.getSelectedColor().getRGB();
        config._secondJumpForeground = _secondJumpForeground.getSelectedColor().getRGB();
        config._panelBackground = _panelBackground.getSelectedColor().getRGB();
        config._needSelectTextAfterJump = _needSelectTextAfterJump.isSelected();
        config._jumpBehind = _jumpBehind.isSelected();
        config._toUpperCase = _toUpperCase.isSelected();
    }

    @Override
    public void reset() {
        setFromConfig();
    }

    @Override
    public void disposeUIResources() {
        _optionsPanel.removeAll();
        _optionsPanel.getParent().remove(_optionsPanel);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
