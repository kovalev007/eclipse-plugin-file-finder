package com.kovalev007.filefinder;

import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class FileFinderHelper {

    public static final String WINDOW_TITLE = "File-finder";

    public static final String SEARCH_FOLDER = "/src/main/resources/";

    public static final String ANNOTATION = "@TextFile";

    public static final String FILE_NAME_PREFIX = "@TextFile(\"";
    public static final String FILE_NAME_SUFFIX = "\")";

    public static String getCurrentProjectName() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();

        IFileEditorInput input = (IFileEditorInput) workbenchPage.getActiveEditor().getEditorInput();
        String projectName = input.getFile().getProject().getName();
        return projectName;
    }

    public static String getFileName(String text) {
        String fileName = text;
        fileName = fileName.trim();
        fileName = fileName.replace(FILE_NAME_PREFIX, "");
        fileName = fileName.replace(FILE_NAME_SUFFIX, "");
        return fileName;
    }

    public static String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}