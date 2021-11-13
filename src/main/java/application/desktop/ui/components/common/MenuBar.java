package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;

/**
 * A menu bar component.
 */
public class MenuBar extends Component {
    /**
     * The horizontal padding on this MenuBar.
     */
    private float paddingX = 4.0f;
    /**
     * The vertical padding on this MenuBar.
     */
    private float paddingY = 4.0f;

    /**
     * Construct a MenuBar with the given menus.
     * @param menus The menus in the bar.
     */
    public MenuBar(Menu... menus) {
        for (Menu menu : menus) {
            addChild(menu);
        }
    }

    @Override
    protected boolean preDraw(DesktopApplication application) {
        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, paddingX, paddingY);
        boolean draw = ImGui.beginMenuBar();
        ImGui.popStyleVar();

        return draw;
    }

    @Override
    protected void postDraw(DesktopApplication application) {
        ImGui.endMenuBar();
    }

    public float getPaddingX() {
        return paddingX;
    }

    public void setPaddingX(float paddingX) {
        this.paddingX = paddingX;
    }

    public float getPaddingY() {
        return paddingY;
    }

    public void setPaddingY(float paddingY) {
        this.paddingY = paddingY;
    }
}
