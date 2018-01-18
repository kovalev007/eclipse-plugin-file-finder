package com.kovalev007.filefinder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.kovalev007.filefinder.exception.FileFinderException;

public class FileFinderHelperCreateFile {

    public static void createFile(String projectName, String fileName) throws FileFinderException {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = workspaceRoot.getProject(projectName);
        IFile file = project.getFile(FileFinderHelper.SEARCH_FOLDER + fileName);
        if (file.exists()) {
            throw new FileFinderException("File [" + "/" + projectName + FileFinderHelper.SEARCH_FOLDER + fileName + "] already exists");
        }

        InputStream source = new ByteArrayInputStream("".getBytes());
        try {
            file.create(source, false, null);
        } catch (CoreException e) {
            throw new FileFinderException("Create file error: " + e.getMessage());
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