package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import application.desktop.ui.events.Event;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.type.ImBoolean;

/**
 * A panel component.
 */
public class Panel extends Component {
    private String title;
    private float paddingX;
    private float paddingY;

    private boolean previousIsOpen;
    private final ImBoolean isOpen;
    private boolean isFirstUpdate;

    private final Event onOpenedEvent;
    private final Event onClosedEvent;

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
     * @param isOpen Whether the panel should be open or not.
     */
    public Panel(String title, float paddingX, float paddingY, boolean isOpen) {
        this.title = title;
        this.paddingX = paddingX;
        this.paddingY = paddingY;

        this.isOpen = new ImBoolean(isOpen);
        isFirstUpdate = true;
        // Events
        onOpenedEvent = new Event();
        onClosedEvent = new Event();
    }

    /**
     * Draw this panel.
     * @param application The application instance.
     */
    @Override
    protected void onDraw(DesktopApplication application) {
        if(isOpen.get()) {
            ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, paddingX, paddingY);
            ImGui.begin(title, isOpen);
            ImGui.popStyleVar();

            super.onDraw(application);
            ImGui.end();
        }
    }

    /**
     * Handle open and close events.
     * @param application The application instance.
     */
    @Override
    protected void handleEvents(DesktopApplication application) {
        super.handleEvents(application);
        if (!isFirstUpdate && previousIsOpen != isOpen()) {
            // The opened state has changed
            if (isOpen()) {
                onOpenedEvent.execute(this, application);
            } else {
                onClosedEvent.execute(this, application);
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

    public boolean isOpen() {
        return isOpen.get();
    }

    public void setOpen(boolean open) {
        isOpen.set(open);
    }

    /**
     * This event is called when the panel is opened, except for possibly when it is first initialised.
     */
    public Event getOnOpenedEvent() {
        return onOpenedEvent;
    }

    /**
     * This event is called when the panel is closed, except for possibly when it is first initialised.
     */
    public Event getOnClosedEvent() {
        return onClosedEvent;
    }
}
