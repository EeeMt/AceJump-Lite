package me.ihxq.acejump.lite.options;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

@State(
        name = "emacsIDEAsPluginConfig",
        storages = {
                @Storage(
                        id = "other",
                        file = "$APP_CONFIG$/AceJump-Lite.xml")
        }
)
public class PluginConfig implements PersistentStateComponent<PluginConfig> {
    int _firstJumpBackground = new JBColor(new Color(214, 71, 255), new Color(237, 66, 124)).getRGB();
    int _firstJumpForeground = new JBColor(new Color(249, 255, 249), new Color(232, 232, 225)).getRGB();
    int _secondJumpBackground = new JBColor(new Color(255, 149, 0), new Color(253, 149, 0)).getRGB();
    int _secondJumpForeground = new JBColor(new Color(255, 248, 248), new Color(254, 253, 254)).getRGB();
    boolean _needSelectTextAfterJump = true;
    public boolean _jumpBehind = true;
    int _panelBackground = new JBColor(new Color(111, 110, 110), new Color(71, 71, 73)).getRGB();
    public boolean _toUpperCase = true;
    public String _fontType = "Bold";
    public int _bgOpacity = 30;
    public String _markersCharsets = "asdfjeghiybcmnopqrtuvwkl";

    public Color getFirstJumpBackground() {
        return new JBColor(new Color(_firstJumpBackground), new Color(_firstJumpBackground));
    }

    public Color getFirstJumpForeground() {
        return new JBColor(new Color(_firstJumpForeground), new Color(_firstJumpForeground));
    }

    public Color getSecondJumpBackground() {
        return new JBColor(new Color(_secondJumpBackground), new Color(_secondJumpBackground));
    }

    public Color getSecondJumpForeground() {
        return new JBColor(new Color(_secondJumpForeground), new Color(_secondJumpForeground));
    }

    public Color getPanelBackground() {
        return new JBColor(new Color(_panelBackground), new Color(_panelBackground));
    }

    @Nullable
    @Override
    public PluginConfig getState() {
        return this;
    }

    @Override
    public void loadState(PluginConfig config) {
        XmlSerializerUtil.copyBean(config, this);
    }

    @Nullable
    public static PluginConfig getInstance(Project project) {
        return ServiceManager.getService(project, PluginConfig.class);
    }
}
