package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;

/**
 * An ImGui component.
 */
public abstract class UIComponent {
    /**
     * Render this component.
     */
    public abstract void render(DesktopApplication application);
}
