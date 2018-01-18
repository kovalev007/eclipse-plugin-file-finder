package com.kovalev007.filefinder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.kovalev007.filefinder.FileFinderHelper;
import com.kovalev007.filefinder.FileFinderHelperCreateFile;
import com.kovalev007.filefinder.exception.FileFinderException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;

public class CreateFile extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

        try {
            IWorkbench workbench = PlatformUI.getWorkbench();
            IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
            IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();

            ITextSelection textSelection = (ITextSelection) workbenchPage.getActiveEditor().getSite().getSelectionProvider().getSelection();

            try {
                FileFinderHelperCreateFile.createFile(FileFinderHelper.getCurrentProjectName(), FileFinderHelper.getFileName(textSelection.getText()));
            } catch (FileFinderException e) {
                MessageDialog.openError(window.getShell(), FileFinderHelper.WINDOW_TITLE, e.getMessage());
                return null;
            }
        } catch (Exception e) {
            MessageDialog.openError(window.getShell(), FileFinderHelper.WINDOW_TITLE, "Unexpected error: " + e.getMessage() + " " + FileFinderHelper.stackTraceToString(e));
            return null;
        }

        return null;
    }

}