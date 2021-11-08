package com.scriptorium.pali.engine;

import com.scriptorium.pali.MainApplication;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

import static org.slf4j.LoggerFactory.getLogger;

public class SourceFileReader {
    private static final String DICT_FILENAME = "/pali-rus.docx";
    private static final Logger LOG = getLogger(SourceFileReader.class);

    public static void readDictionaryFile(BiConsumer<String, String> processingFunc) throws FileNotFoundException, InvalidObjectException {
            InputStream fis = MainApplication.class.getResourceAsStream(DICT_FILENAME);
            if (fis == null) {
                String errorMsg = String.format("Dictionary file %s not found!", DICT_FILENAME);
                LOG.error(errorMsg);
                throw new FileNotFoundException(errorMsg);
            }
        try {
            XWPFDocument document=new XWPFDocument(OPCPackage.open(fis));
            Iterator<IBodyElement> bodyElementIterator = document.getBodyElementsIterator();
            while(bodyElementIterator.hasNext()) {
                IBodyElement element = bodyElementIterator.next();
                if("TABLE".equalsIgnoreCase(element.getElementType().name())) {
                    List<XWPFTable> tableList =  element.getBody().getTables();
                    for (XWPFTable table: tableList){
                        LOG.info("Total Number of Rows: {}", table.getNumberOfRows());

                        for (XWPFTableRow row : table.getRows()) {
                            String paliWord = row.getCell(0).getParagraphs().get(0).getText();
                            String translation = row.getCell(1).getParagraphs().get(0).getText();

                            processingFunc.accept(paliWord, translation);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new InvalidObjectException(e.getMessage());
        }
    }

}
