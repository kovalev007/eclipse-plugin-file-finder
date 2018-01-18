package com.kovalev007.filefinder.handlers;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.swt.widgets.Display;

import com.kovalev007.filefinder.FileFinderHelper;
import com.kovalev007.filefinder.FileFinderHelperCreateFile;
import com.kovalev007.filefinder.exception.FileFinderException;

public class CreateFileHyperlink implements IHyperlink {

    private final IRegion region;
    private final String regionText;

    public CreateFileHyperlink(IRegion region, String regionText) {
        this.region = region;
        this.regionText = regionText;
    }

    @Override
    public IRegion getHyperlinkRegion() {
        int index = regionText.indexOf(FileFinderHelper.ANNOTATION);
        IRegion targetRegion = new Region(region.getOffset() + index, FileFinderHelper.ANNOTATION.length());
        return targetRegion;
    }

    @Override
    public String getTypeLabel() {
        return "Create file 2";
    }

    @Override
    public String getHyperlinkText() {
        return "Create file";
    }

    @Override
    public void open() {
        try {
            FileFinderHelperCreateFile.createFile(FileFinderHelper.getCurrentProjectName(), FileFinderHelper.getFileName(regionText));
        } catch (FileFinderException e) {
            MessageDialog.openError(Display.getDefault().getActiveShell(), FileFinderHelper.WINDOW_TITLE, e.getMessage());
        }
    }

}