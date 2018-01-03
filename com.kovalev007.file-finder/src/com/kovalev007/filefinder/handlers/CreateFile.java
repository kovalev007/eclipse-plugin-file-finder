package com.kovalev007.filefinder.handlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;

public class CreateFile extends AbstractHandler {

    private static final String SEARCH_FOLDER = "/src/main/resources/";

    private static final String WINDOW_TITLE = "File-finder";

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

        try {
            IWorkbench workbench = PlatformUI.getWorkbench();
            IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
            IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();

            IFileEditorInput input = (IFileEditorInput) workbenchPage.getActiveEditor().getEditorInput();
            String projectName = input.getFile().getProject().getName();

            IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
            IProject project = workspaceRoot.getProject(projectName);

            ITextSelection textSelection = (ITextSelection) workbenchPage.getActiveEditor().getSite().getSelectionProvider().getSelection();
            String selectedText = textSelection.getText();
            selectedText = selectedText.trim();
            selectedText = selectedText.replace("@TextFile(\"", "");
            selectedText = selectedText.replace("\")", "");

            IFile file = project.getFile(SEARCH_FOLDER + selectedText);
            if (file.exists()) {
                MessageDialog.openError(window.getShell(), WINDOW_TITLE, "File [" + "/" + projectName + SEARCH_FOLDER + selectedText + "] already exists");
                return null;
            }

            InputStream source = new ByteArrayInputStream("".getBytes());
            try {
                file.create(source, false, null);
            } catch (CoreException e) {
                MessageDialog.openError(window.getShell(), WINDOW_TITLE, "Create file error: " + e.getMessage());
                return null;
            }

            try {
                IDE.openEditor(workbenchPage, file, true);
            } catch (PartInitException e) {
                MessageDialog.openError(window.getShell(), WINDOW_TITLE, "Error when open file: " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            MessageDialog.openError(window.getShell(), WINDOW_TITLE, "Unexpected error: " + e.getMessage() + " " + stackTraceToString(e));
            return null;
        }

        return null;
    }

    private String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}