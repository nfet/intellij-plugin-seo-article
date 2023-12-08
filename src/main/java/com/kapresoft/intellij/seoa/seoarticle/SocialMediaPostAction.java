package com.kapresoft.intellij.seoa.seoarticle;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class SocialMediaPostAction extends AnAction {

    private static final Logger LOG = Logger.getInstance(SocialMediaPostAction.class);

    @Override
    public void update(@NotNull AnActionEvent e) {
        LOG.warn("update(): entering...");
        e.getPresentation().setEnabled(true);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent evt) {
        LOG.warn("actionPerformed(): entering...");
        Project project = evt.getProject();
        // Get the data context from the action event

        final Editor editor = evt.getRequiredData(CommonDataKeys.EDITOR);

        DataContext dataContext = evt.getDataContext();
        // Use CommonDataKeys to get the selected file
        VirtualFile selectedFile = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);
        if (ofNullable(selectedFile).isEmpty()) {
            return;
        }

        // You have the selected file, you can now work with it
        final String filePath = selectedFile.getPath();
        final String fname = selectedFile.getName();
        String selectedTextContent = getSelectedTextContent(editor);
        LOG.warn("File[%s]: selectedContent=%s".formatted(fname, selectedTextContent));

        String fileContentAsString = getSelectedFileContent(selectedFile);
        if (isBlank(fileContentAsString)) {
            return;
        }

        LOG.warn("File[%s]: %s".formatted(fname, fileContentAsString));
        String socialMediaPostText = parseSocialMedia(fileContentAsString);
        if (isBlank(socialMediaPostText)) {
            return;
        }

        saveToClipboard(socialMediaPostText);

        notifyUser(project);
    }

    private void notifyUser(Project project) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("SEO Article")
                .createNotification("Social media content copied clipboard", NotificationType.INFORMATION)
                .notify(project);
    }

    private static void saveToClipboard(String socialMediaPostText) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // Create a StringSelection object with the text
        StringSelection selection = new StringSelection(socialMediaPostText);

        // Set the contents of the clipboard to the StringSelection
        clipboard.setContents(selection, null);
    }

    private String parseSocialMedia(String fileContentAsString) {
        // TODO: Implement me
        String ret = """
                Java • int vs long
                Lombok's @Builder simplifies implementing the Builder Pattern and Copy Constructors in OOP, improving code maintainability.
                
                https://www.kapresoft.com/java/2021/12/27/lombok-builders-and-copy-constructors.html
                
                #Java #JavaLombok
                #Kotlin #Scala #SoftwareEngineering #CleanCode
                #CopyConstructors #BuilderPattern                  
                """;
        return ret.trim();
    }

    private static String getSelectedFileContent(VirtualFile selectedFile) {
        byte[] fileContent;
        try {
            fileContent = selectedFile.contentsToByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (fileContent.length == 0) {
            return "";
        }

        return new String(fileContent, selectedFile.getCharset());
    }

    private String getSelectedTextContent(Editor editor) {
        // Get the selection model from the editor
        SelectionModel selectionModel = editor.getSelectionModel();

        // Get the selected text
        String selectedText = selectionModel.getSelectedText();
        if (isBlank(selectedText)) {
            return null;
        }
        return selectedText;
    }

}
