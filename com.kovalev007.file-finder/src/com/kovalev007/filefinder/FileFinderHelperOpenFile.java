package com.kovalev007.filefinder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.kovalev007.filefinder.exception.FileFinderException;

public class FileFinderHelperOpenFile {

    public static void openFile(String projectName, String fileName) throws FileFinderException {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = workspaceRoot.getProject(projectName);
        IFile file = project.getFile(FileFinderHelper.SEARCH_FOLDER + fileName);
        if (!file.exists()) {
            throw new FileFinderException("File [" + "/" + projectName + FileFinderHelper.SEARCH_FOLDER + fileName + "] not found");
        }

        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
        try {
            IDE.openEditor(workbenchPage, file, true);
        } catch (PartInitException e) {
            throw new FileFinderException("Error when open file: " + e.getMessage());
        }
    }

}