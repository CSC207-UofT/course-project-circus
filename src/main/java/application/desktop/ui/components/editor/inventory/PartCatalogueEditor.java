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
    private ImString selectedPartNameInputField;

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

    /**
     * Draws the PartCatalogueEditor
     * @param application The application instance.
     */
    @Override
    protected void drawContent(DesktopApplication application) {
        drawPartsSelectionPane();
        ImGui.sameLine();
        drawPartsEditPane();
    }

    /**
     * Draws the Part selection pane.
     */
    private void drawPartsSelectionPane() {
        List<Part> parts = partCatalogue.getParts();

        ImGui.text("Parts");
        ImGui.beginChild("left pane", ImGui.getContentRegionAvailX() * 0.25f, 0, true, ImGuiWindowFlags.MenuBar);

        if (ImGui.beginMenuBar()) {
            ImGui.pushStyleColor(ImGuiCol.Button, ImGui.colorConvertFloat4ToU32(0, 0, 0, 0));
            if (ImGui.button("New")) {
                Part newPart = getNewPart();
                partCatalogue.addPart(newPart);
                selectPart(newPart.getId());
            }
            ImGui.spacing();

            boolean isDisabled = !isPartSelected();
            if (isDisabled) {
                imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true);
                ImGui.pushStyleVar(ImGuiStyleVar.Alpha, ImGui.getStyle().getAlpha() * 0.5f);
            }

            if (ImGui.button("Remove")) {
                removeSelectedPart();
            }

            if (isDisabled) {
                imgui.internal.ImGui.popItemFlag();
                ImGui.popStyleVar();
            }

            ImGui.popStyleColor();
            ImGui.endMenuBar();
        }

        drawPartList(parts);
        ImGui.endChild();
    }

    /**
     * Removes the currently selected Part from the PartCatalogue.
     * Mutates the selectedPartId by setting it to null.
     */
    private void removeSelectedPart() {
        partCatalogue.removePartById(selectedPartId);
        selectPart(null);
    }

    /**
     * Draws a list of Parts by name.
     * @param parts A list of Part objects to draw.
     */
    private void drawPartList(List<Part> parts) {
        if (parts.isEmpty()) {
            ImGui.textDisabled("No parts yet...");
        } else {
            for (Part part : parts) {
                String partId = part.getId();
                if (ImGui.selectable(part.getName(), selectedPartId != null && selectedPartId.equals(partId))) {
                    selectPart(partId);
                }
            }
        }
    }

    /**
     * Select the Part with the given id.
     * @param partId The id of the Part to select.
     */
    private void selectPart(String partId) {
        selectedPartId = partId;
        Part part = getSelectedPart();
        if (part != null) {
            selectedPartNameInputField = new ImString(part.getName());
        }
    }

    /**
     * Draws the edit pane for the currently selected Part.
     */
    private void drawPartsEditPane() {
        ImGui.beginGroup();
        if (isPartSelected()) {
            ImGui.beginChild("item view", 0, -ImGui.getFrameHeightWithSpacing());

            Part selectedPart = getSelectedPart();
            ImGui.text(selectedPart.getName());
            ImGui.separator();

            if (ImGui.beginTabBar("##Tabs", ImGuiTabBarFlags.None)) {
                if (ImGui.beginTabItem("Details")) {
                    ImGui.labelText("ID", selectedPart.getId());
                    ImGui.inputText("Name", selectedPartNameInputField);
                    ImGui.inputTextMultiline("Description", new ImString(selectedPart.getDescription()));
                    ImGui.endTabItem();
                }
                ImGui.endTabBar();
            }
            ImGui.endChild();

            boolean isDisabled = selectedPartNameInputField.get().equals(selectedPart.getName());
            if (isDisabled) {
                imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true);
                ImGui.pushStyleVar(ImGuiStyleVar.Alpha, ImGui.getStyle().getAlpha() * 0.5f);
            }

            if (ImGui.button("Revert")) {
                selectedPartNameInputField.set(selectedPart.getName());
            }

            ImGui.sameLine();
            if (ImGui.button("Save")) {
                System.out.println("saving");
                selectedPart.setName(selectedPartNameInputField.get());
            }

            if (isDisabled) {
                imgui.internal.ImGui.popItemFlag();
                ImGui.popStyleVar();
            }

        }
        ImGui.endGroup();
    }

    /**
     * Return whether a Part is selected.
     * @return True if a Part is selected, and False otherwise.
     */
    private boolean isPartSelected() {
        return getSelectedPart() != null;
    }

    /**
     * Return the selected Part.
     * @return the selected Part, or null if no Part is selected.
     */
    private Part getSelectedPart() {
        return partCatalogue.getPartById(selectedPartId);
    }

    /**
     * A simple factory method for creating a new Part.
     * @return A new Part object.
     */
    private Part getNewPart() {
        return new Part(String.format("New Part %s", partCatalogue.getParts().size() + 1), "");
    }
}
