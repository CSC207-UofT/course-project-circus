package application.desktop.ui.components.editor.warehouse;

import imgui.ImVec2;

/**
 * Transform for the WarehouseCanvas.
 */
public class WarehouseCanvasTransform {
    private ImVec2 contentSize;
    private ImVec2 contentTopLeft;

    /**
     * Construct an empty WarehouseCanvasTransform.
     */
    public WarehouseCanvasTransform() {
        this.contentSize = new ImVec2();
        this.contentTopLeft = new ImVec2();
    }

    /**
     * Construct a WarehouseCanvasTransform.
     * @param contentSize The size of the canvas content.
     * @param contentTopLeft The (absolute) top left coordinate of the canvas.
     * @param origin The origin.
     */
    public WarehouseCanvasTransform(ImVec2 contentSize, ImVec2 contentTopLeft, ImVec2 origin) {
        this.contentSize = contentSize;
        this.contentTopLeft = contentTopLeft;
    }

    public ImVec2 getContentSize() {
        return contentSize;
    }

    public void setContentSize(ImVec2 contentSize) {
        this.contentSize = contentSize;
    }

    public ImVec2 getContentTopLeft() {
        return contentTopLeft;
    }

    public void setContentTopLeft(ImVec2 contentTopLeft) {
        this.contentTopLeft = contentTopLeft;
    }

    /**
     * Get the bottom right coordinate of the canvas.
     */
    public ImVec2 getContentBottomRight() {
        return new ImVec2(contentTopLeft.x + contentSize.x, contentTopLeft.y + contentSize.y);
    }
}
