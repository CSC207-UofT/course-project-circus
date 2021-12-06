package application.desktop.ui.components.editor.inventory;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.*;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTabBarFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.flag.ImGuiItemFlags;
import imgui.type.ImString;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;

import java.util.List;

/**
 * Editor window for the PartCatalogue.
 */
public class PartCatalogueEditor extends Panel {
    /**
     * The id of the panel.
     */
    private static final String PANEL_ID = "Part Catalogue###partcatalogue_editor_panel";

    private final PartCatalogue partCatalogue;
    private String selectedPartId;

    /**
     * Construct an PartCatalogueEditor given a PartCatalogue.
     * @param partCatalogue The PartCatalogue to edit.
     */
    public PartCatalogueEditor(PartCatalogue partCatalogue) {
        super(PANEL_ID);
        setCloseable(false);
        setMovable(false);

        this.partCatalogue = partCatalogue;
        selectedPartId = null;
    }

    @Override
    protected void drawContent(DesktopApplication application) {
        drawPartsSelectionPane();
        ImGui.sameLine();
        drawPartsEditPane();
    }

    private void drawPartsSelectionPane() {
        List<Part> parts = partCatalogue.getParts();

        ImGui.text("Parts");
        ImGui.beginChild("left pane", ImGui.getContentRegionAvailX() * 0.25f, 0, true, ImGuiWindowFlags.MenuBar);

        if (ImGui.beginMenuBar()) {
            ImGui.pushStyleColor(ImGuiCol.Button, ImGui.colorConvertFloat4ToU32(0, 0, 0, 0));
            if (ImGui.button("New")) {
                Part newPart = getDefaultPart();
                partCatalogue.addPart(newPart);
                selectedPartId = newPart.getId();
            }
            ImGui.spacing();

            boolean isDisabled = !isPartSelected();
            if (isDisabled) {
                imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true);
                ImGui.pushStyleVar(ImGuiStyleVar.Alpha, ImGui.getStyle().getAlpha() * 0.5f);
            }

            if (ImGui.button("Remove")) {
                partCatalogue.removePartById(selectedPartId);
                selectedPartId = null;
            }

            if (isDisabled) {
                imgui.internal.ImGui.popItemFlag();
                ImGui.popStyleVar();
            }

            ImGui.popStyleColor();
            ImGui.endMenuBar();
        }

        if (parts.isEmpty()) {
            ImGui.textDisabled("No parts yet...");
        } else {
            for (Part part : parts) {
                String partId = part.getId();
                if (ImGui.selectable(part.getName(), selectedPartId != null && selectedPartId.equals(partId))) {
                    selectedPartId = partId;
                }
            }
        }
        ImGui.endChild();
    }

    private Part getDefaultPart() {
        return new Part(String.format("New Part %s", partCatalogue.getParts().size() + 1), "");
    }

    private void drawPartsEditPane() {
        ImGui.beginGroup();
        if (isPartSelected()) {
            ImGui.beginChild("item view", 0, -ImGui.getFrameHeightWithSpacing());

            Part selectedPart = partCatalogue.getPartById(selectedPartId);
            ImGui.text(selectedPart.getName());
            ImGui.separator();
            if (ImGui.beginTabBar("##Tabs", ImGuiTabBarFlags.None)) {
                if (ImGui.beginTabItem("Details")) {
                    ImGui.labelText("ID", selectedPart.getId());
                    ImGui.inputText("Name", new ImString(selectedPart.getName()));
                    ImGui.inputTextMultiline("Description", new ImString(selectedPart.getDescription()));
                    ImGui.endTabItem();
                }
                ImGui.endTabBar();
            }
            ImGui.endChild();
            if (ImGui.button("Revert")) {
            }
            ImGui.sameLine();
            if (ImGui.button("Save")) {
            }
        }
        ImGui.endGroup();
    }

    private boolean isPartSelected() {
        return partCatalogue.getPartById(selectedPartId) != null;
    }
}
