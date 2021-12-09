package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import application.desktop.ui.events.ComponentEvent;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

/**
 * A panel component.
 */
public class Panel extends Component {
    private String title;
    private float paddingX;
    private float paddingY;

    private boolean showTitleBar = true;
    private boolean showScrollbar = true;
    private boolean showMenuBar = false;
    private boolean movable = true;
    private boolean resizable = true;
    private boolean closeable = true;
    private boolean collapsable = true;
    private boolean showBackground = true;
    private boolean bringToFrontOnFocus = true;

    private boolean previousIsOpen;
    private final ImBoolean isOpen;
    private boolean isFirstUpdate;

    private final ComponentEvent onOpenedEvent;
    private final ComponentEvent onClosedEvent;

    /**
     * Construct a new Panel with the given title.
     * @param title The title of the panel.
     */
    public Panel(String title) {
        this(title, 10.0f, 10.0f, true);
    }

    /**
     * Construct a new Panel with the given title and open state.
     * @param title The title of the panel.
     * @param paddingX Horizontal padding applied to (both sides of) contents in this Panel.
     * @param paddingY Vertical padding applied to (both sides of) contents in this Panel.
     * @param isOpen Whether the panel should be open or not.
     */
    public Panel(String title, float paddingX, float paddingY, boolean isOpen) {
        this.title = title;
        this.paddingX = paddingX;
        this.paddingY = paddingY;

        // State management
        this.isOpen = new ImBoolean(isOpen);
        isFirstUpdate = true;

        // Events
        onOpenedEvent = new ComponentEvent();
        onClosedEvent = new ComponentEvent();
    }

    @Override
    protected boolean preDraw() {
        if (!isOpen.get()) return false;
        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, paddingX, paddingY);

        boolean draw;
        int windowFlags = buildWindowFlags();
        if (closeable) {
            draw = ImGui.begin(title, isOpen, windowFlags);
        } else {
            draw = ImGui.begin(title, windowFlags);
        }

        ImGui.popStyleVar();
        return draw;
    }

    @Override
    protected void postDraw() {
        ImGui.end();
    }

    /**
     * Get the window flags for this Panel.
     */
    private int buildWindowFlags() {
        int flags = 0;
        if (!showTitleBar) {
            flags |= ImGuiWindowFlags.NoTitleBar;
        }
        if (!showScrollbar) {
            flags |= ImGuiWindowFlags.NoScrollbar;
        }
        if (showMenuBar) {
            flags |= ImGuiWindowFlags.MenuBar;
        }
        if (!movable) {
            flags |= ImGuiWindowFlags.NoMove;
        }
        if (!resizable) {
            flags |= ImGuiWindowFlags.NoResize;
        }
        if (!collapsable) {
            flags |= ImGuiWindowFlags.NoCollapse;
        }
        if (!showBackground) {
            flags |= ImGuiWindowFlags.NoBackground;
        }
        if (!bringToFrontOnFocus) {
            flags |= ImGuiWindowFlags.NoBringToFrontOnFocus;
        }
        return flags;
    }

    /**
     * Handle open and close application.desktop.ui.events.
     * @param application The application instance.
     */
    @Override
    protected void handleEvents() {
        super.handleEvents();
        if (!isFirstUpdate && previousIsOpen != isOpen()) {
            // The opened state has changed
            if (isOpen()) {
                onOpenedEvent.execute(this);
            } else {
                onClosedEvent.execute(this);
            }
        }
        previousIsOpen = isOpen.get();
        if (isFirstUpdate) {
            isFirstUpdate = false;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isShowTitleBar() {
        return showTitleBar;
    }

    public void setShowTitleBar(boolean showTitleBar) {
        this.showTitleBar = showTitleBar;
    }

    public boolean isShowScrollbar() {
        return showScrollbar;
    }

    public void setShowScrollbar(boolean showScrollbar) {
        this.showScrollbar = showScrollbar;
    }

    public boolean isShowMenuBar() {
        return showMenuBar;
    }

    public void setShowMenuBar(boolean showMenuBar) {
        this.showMenuBar = showMenuBar;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public boolean isCloseable() {
        return closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public boolean isCollapsable() {
        return collapsable;
    }

    public void setCollapsable(boolean collapsable) {
        this.collapsable = collapsable;
    }

    public boolean isShowBackground() {
        return showBackground;
    }

    public void setShowBackground(boolean showBackground) {
        this.showBackground = showBackground;
    }

    public boolean isBringToFrontOnFocus() {
        return bringToFrontOnFocus;
    }

    public void setBringToFrontOnFocus(boolean bringToFrontOnFocus) {
        this.bringToFrontOnFocus = bringToFrontOnFocus;
    }

    public boolean isOpen() {
        return isOpen.get();
    }

    public void setOpen(boolean open) {
        isOpen.set(open);
    }

    /**
     * This event is called when the panel is opened, except for possibly when it is first initialised.
     */
    public ComponentEvent getOnOpenedEvent() {
        return onOpenedEvent;
    }

    /**
     * This event is called when the panel is closed, except for possibly when it is first initialised.
     */
    public ComponentEvent getOnClosedEvent() {
        return onClosedEvent;
    }
}
