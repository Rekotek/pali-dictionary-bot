package com.scriptorium.pali.engine;

import com.scriptorium.pali.MainApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
public class DocumentFileHelper {
    private static final String DICT_FILENAME = "/pali-rus.docx";

    private DocumentFileHelper() { }

    public static void readDictionaryFile(BiConsumer<String, String> processingFunc) throws FileNotFoundException, InvalidObjectException {
            InputStream fis = MainApplication.class.getResourceAsStream(DICT_FILENAME);
            if (fis == null) {
                String errorMsg = String.format("Dictionary file %s not found!", DICT_FILENAME);
                log.error(errorMsg);
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
                        log.info("Total Number of Rows: {}", table.getNumberOfRows());

                        for (XWPFTableRow row : table.getRows()) {
                            String paliWord = row.getCell(0).getParagraphs().get(0).getText();
                            String translation = row.getCell(1).getParagraphs().get(0).getText();

                            processingFunc.accept(paliWord, translation);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InvalidObjectException(e.getMessage());
        }
    }

}
