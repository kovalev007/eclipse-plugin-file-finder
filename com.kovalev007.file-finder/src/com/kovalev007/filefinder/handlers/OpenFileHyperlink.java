package com.kovalev007.filefinder.handlers;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.swt.widgets.Display;

import com.kovalev007.filefinder.FileFinderHelper;
import com.kovalev007.filefinder.FileFinderHelperOpenFile;
import com.kovalev007.filefinder.exception.FileFinderException;

public class OpenFileHyperlink implements IHyperlink {

    private final IRegion region;
    private final String regionText;

    public OpenFileHyperlink(IRegion region, String regionText) {
        this.region = region;
        this.regionText = regionText;
    }

    @Override
    public IRegion getHyperlinkRegion() {
        return region;
    }

    @Override
    public String getTypeLabel() {
        return "Open file 2";
    }

    @Override
    public String getHyperlinkText() {
        return "Open file";
    }

    @Override
    public void open() {
        try {
            try {
                FileFinderHelperOpenFile.openFile(FileFinderHelper.getCurrentProjectName(), FileFinderHelper.getFileName(regionText));
            } catch (FileFinderException e) {
                MessageDialog.openError(Display.getDefault().getActiveShell(), FileFinderHelper.WINDOW_TITLE, e.getMessage());
            }
        } catch (Exception e) {
            MessageDialog.openError(Display.getDefault().getActiveShell(), FileFinderHelper.WINDOW_TITLE, "Unexpected error: " + e.getMessage() + " " + FileFinderHelper.stackTraceToString(e));
        }
    }

}