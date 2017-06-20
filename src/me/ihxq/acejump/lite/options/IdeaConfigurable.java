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
    private JComboBox _fontType;
    private JSlider _bgOpacity;
    private JLabel _bgOpacityValue;
    private JTextField _markersCharsets;

    private final PluginConfig config = ServiceManager.getService(PluginConfig.class);

    @Nls
    @Override
    public String getDisplayName() {
        return "AceJump-Lite";
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
        _fontType.setSelectedItem(config._fontType);
        _bgOpacity.setValue(config._bgOpacity);
        _markersCharsets.setText(config._markersCharsets);
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        setFromConfig();
        return _optionsPanel;
    }

    @Override
    public boolean isModified() {
        System.out.println(_fontType.getSelectedItem());
        System.out.println(_bgOpacity.getValue());
        System.out.println(_bgOpacity.getChangeListeners().length);
        _bgOpacityValue.setText(_bgOpacity.getValue() + "%");
        return _firstJumpBackground.getSelectedColor() != config.getFirstJumpBackground()
                || _firstJumpForeground.getSelectedColor() != config.getFirstJumpForeground()
                || _secondJumpBackground.getSelectedColor() != config.getSecondJumpBackground()
                || _secondJumpForeground.getSelectedColor() != config.getSecondJumpForeground()
                || _panelBackground.getSelectedColor() != config.getPanelBackground()
                || _needSelectTextAfterJump.isSelected() != config._needSelectTextAfterJump
                || _jumpBehind.isSelected() != config._jumpBehind
                || _fontType.getSelectedItem() != config._fontType
                || _bgOpacity.getValue() != config._bgOpacity
                || !_markersCharsets.getText().equals(config._markersCharsets)
                || _toUpperCase.isSelected() != config._toUpperCase;
    }

    @Override
    public void apply() throws ConfigurationException {
        if (!isModified()) {
            return;
        }
        if (_firstJumpBackground.getSelectedColor() != null) {
            config._firstJumpBackground = _firstJumpBackground.getSelectedColor().getRGB();
        }
        if (_firstJumpForeground.getSelectedColor() != null) {
            config._firstJumpForeground = _firstJumpForeground.getSelectedColor().getRGB();
        }
        if (_secondJumpBackground.getSelectedColor() != null) {
            config._secondJumpBackground = _secondJumpBackground.getSelectedColor().getRGB();
        }
        if (_secondJumpForeground.getSelectedColor() != null) {
            config._secondJumpForeground = _secondJumpForeground.getSelectedColor().getRGB();
        }
        if (_panelBackground.getSelectedColor() != null) {
            config._panelBackground = _panelBackground.getSelectedColor().getRGB();
        }
        config._needSelectTextAfterJump = _needSelectTextAfterJump.isSelected();
        config._jumpBehind = _jumpBehind.isSelected();
        config._toUpperCase = _toUpperCase.isSelected();
        config._fontType = (String) _fontType.getSelectedItem();
        config._bgOpacity = _bgOpacity.getValue();
        config._markersCharsets = _markersCharsets.getText();
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
