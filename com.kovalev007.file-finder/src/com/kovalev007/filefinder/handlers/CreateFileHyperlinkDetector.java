package com.kovalev007.filefinder.handlers;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

import com.kovalev007.filefinder.FileFinderHelper;

public class CreateFileHyperlinkDetector extends AbstractHyperlinkDetector implements IHyperlinkDetector {

    @Override
    public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
        IDocument document = textViewer.getDocument();
        int offset = region.getOffset();

        IRegion lineRegion;
        String lineRegionText;
        try {
            lineRegion = document.getLineInformationOfOffset(offset);
            lineRegionText = document.get(lineRegion.getOffset(), lineRegion.getLength());
        } catch (BadLocationException ex) {
            return null;
        }

        int index = lineRegionText.indexOf(FileFinderHelper.ANNOTATION);
        if (index != -1) {
            return new IHyperlink[] { new CreateFileHyperlink(lineRegion, lineRegionText) };
        }

        return null;
    }

}