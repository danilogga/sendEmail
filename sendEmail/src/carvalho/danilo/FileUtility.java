package carvalho.danilo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings({ "unused", "unchecked" })
public class FileUtility {

    public static void main(String[] args) {
        try {

            //            File processDir = new File("/Users/danilo/Documents/TCERO/03012-17");
            //            File processDir = new File("/Users/danilo/Documents/TCERO/02268-16");
            File processDir = new File("split");

            //            List<byte[]> pecas = new ArrayList<>();
            //            List<String> bookmarks = new ArrayList<>();
            //            List<byte[]> pecasAjustadas = new ArrayList<>();
            //
            //            processDir.list();
            //
            //            for (File file : processDir.listFiles()) {
            //                if (file.isFile() && !file.getName().startsWith(".") && !file.isHidden()) {
            //                    try {
            //                        pecas.add(getBytesOfFile(file));
            //                        bookmarks.add(file.getName());
            //                    } catch (Exception e) {
            //                        System.out.println("### erro no arquivo >> " + file.getName());
            //                    }
            //                }
            //            }
            //
            //            for (byte[] peca : pecas) {
            //                System.out.println("#### ajustando peca >> " + bookmarks.get(pecas.indexOf(peca)));
            //                pecasAjustadas.add(adjustDimensionToFooter(peca));
            //            }

            //            byte[] pecaLink = getBytesOfFile(new File("peca_link.pdf"));
            //            byte[] hyperlink = getBytesOfFile(new File("hyperlink.pdf"));
            //

            //            int count = 1;
            //
            //            for (byte[] byteArray : pecasAjustadas) {
            //                saveFile(byteArray, new File("peca_ajustada" + count++ + ".pdf"));
            //            }

            //                        byte[] consolidado = mergeAndBookmarkerDocument(pecas, bookmarks);
            //            byte[] consolidadoAjustado = mergeAndBookmarkerDocument(pecasAjustadas, bookmarks);

            //            saveFile(consolidado, new File(processDir.getName() + ".pdf"));
            //                        saveFile(consolidadoAjustado, new File("consolidado_ajustado.pdf"));
            //
            //            byte[] consolidadoNumero = processAddPageNumber(consolidado, "RIGHT", false);
            //            byte[] consolidadoAjustadoNumero = processAddPageNumber(consolidadoAjustado, "RIGHT", false);
            //
            //            saveFile(consolidadoNumero, new File("consolidado_numero.pdf"));
            //            saveFile(consolidadoAjustadoNumero, new File("consolidado_ajustado_numero.pdf"));

            //            List<String> footerLines = new ArrayList<>();
            //            footerLines.add("Documento de 1 pág(s) assinado eletronicamente por OSCAR CARLOS DAS NEVES LEBRE em 30/01/2017 13:45.");
            //            footerLines.add("ID=398289 para autenticação no endereço: http://www.tce.ro.gov.br/validardoc.");
            //            footerLines.add("Documento ID=400480 inserido por PRISCILLA MENEZES ANDRADE em 03/02/2017 12:50.");
            //
            //            byte[] consolidadoAjustadoNumeroRodape = processAddFooter(consolidadoAjustadoNumero, footerLines);
            //
            //            saveFile(consolidadoAjustadoNumeroRodape, new File("consolidado_numero_rodape.pdf"));

            //            File file1ToSplit = new File("split/83821_1961_05_577123.pdf");
            //            File file2ToSplit = new File("split/83821_1961_07_577124.pdf");
            //            File file3ToSplit = new File("split/83821_1961_08_577125.pdf");
            //            File file4ToSplit = new File("split/83821_1961_10_577136.pdf");
            //            File file5ToSplit = new File("split/83821_1962_03_577138.pdf");
            //
            //            splitPdfFileToJpgPage(file1ToSplit, null);
            //            splitPdfFileToJpgPage(file2ToSplit, null);
            //            splitPdfFileToJpgPage(file3ToSplit, null);
            //            splitPdfFileToJpgPage(file4ToSplit, null);
            //            splitPdfFileToJpgPage(file5ToSplit, null);

            //            File fileToRotate = new File("50945F.pdf");
            //
            //            byte[] fileRotated = processRotate(getBytesOfFile(fileToRotate), new int[] { 3, 8000 }, 90);
            //            saveFile(fileRotated, new File("50945F_rotated.pdf"));

            Map<Integer, Integer> pagesToRotate = new HashMap<>();
            pagesToRotate.put(1, 180);//PdfPage.INVERTEDPORTRAIT
            pagesToRotate.put(2, 270);//PdfPage.SEASCAPE
            pagesToRotate.put(5, 90);//PdfPage.LANDSCAPE
            pagesToRotate.put(11, 270);//PdfPage.SEASCAPE
            pagesToRotate.put(15, 90);//PdfPage.LANDSCAPE

            Date dateInitial = DateUtility.getCurrentDateTime();

            rotateDocument(new File("50945F.pdf"), pagesToRotate);

            System.out.println("PdfWriter >> " + DateUtility.getTimeElapsed(dateInitial, DateUtility.getCurrentDateTime()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void rotateDocument(File fileToRotate, Map<Integer, Integer> mapPagesToRotate) throws Exception {
        // se veio o mapa
        if (mapPagesToRotate != null && !mapPagesToRotate.isEmpty()) {
            // monta uma lista com os valores válidos
            List<Integer> allowedValues = Arrays.asList(new Integer[] { 90, 180, 270 });

            // varre o mapa verificando se todos os valores são validos
            for (Integer pageNum : mapPagesToRotate.keySet()) {
                // se o valor que veio para rotacionar não é válido
                if (!allowedValues.contains(mapPagesToRotate.get(pageNum))) {
                    // dispara exception
                    throw new Exception("wrong.value");
                }
            }
        } else {
            // dispara exception dizendo que o mapa está vazio
            throw new Exception("map.is.empty");
        }

        PdfReader reader = null;
        OutputStream out = null;
        try {
            // cria o reader do arquivo para rotacionar, utiliza RandomAccessFileOrArray para não dar estouro de memória            
            reader = new PdfReader(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(fileToRotate.getAbsolutePath())), null);

            // cria o arquivo de saída que vai ser uma cópia do arquivo a ser rotacionado
            File fileRotated = new File((fileToRotate.getParentFile() != null ? fileToRotate.getParentFile() : new File(".")),
                    fileToRotate.getName().substring(0, fileToRotate.getName().lastIndexOf(".")) + "_COPY.pdf");

            // cria o outputStream
            out = new FileOutputStream(fileRotated);

            // cria o document
            Document document = new Document();

            // cria o writer 
            PdfWriter writer = PdfWriter.getInstance(document, out);

            // abre o documento, nesse momento já passa no handler
            document.open();
            // cria a imagem da página fora do for
            Image page;

            // pega o número total de páginas
            int totalPages = reader.getNumberOfPages();

            // varre as páginas copiando para o novo pdf
            for (int pageNum = 1; pageNum <= totalPages; pageNum++) {
                // pega a página atual como imagem
                page = Image.getInstance(writer.getImportedPage(reader, pageNum));
                page.setAbsolutePosition(0, 0);

                // se a página tá no mapa pra ser rotacionada
                if (mapPagesToRotate.keySet().contains(pageNum)) {
                    // pega o angulo que é pra rotacionar
                    int angle = mapPagesToRotate.get(pageNum);
                    // pega o angulo atual
                    PdfNumber actualRotate = reader.getPageN(pageNum).getAsNumber(PdfName.ROTATE);

                    // adiciona no pageDict somando o angulo para rotacionar com o atual
                    writer.addPageDictEntry(PdfName.ROTATE, new PdfNumber(actualRotate != null ? actualRotate.intValue() + angle : angle));
                } else {
                    // se não é pra rotacionar, pega o rotate do pageDict original e adiciona
                    writer.addPageDictEntry(PdfName.ROTATE, reader.getPageN(pageNum).getAsNumber(PdfName.ROTATE));
                }

                // adiciona a página no documento
                document.add(page);
                // cria uma nova página
                document.newPage();
            }
            // fecha o documento
            document.close();

            // apaga o arquivo antigo
            fileToRotate.delete();
            // renomeia o arquivo rotacionado
            fileRotated.renameTo(fileToRotate);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }

                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void rotatePdfCopy(PdfReader reader, OutputStream out, Document document) throws DocumentException, IOException, BadPdfFormatException {
        PdfCopy copy = new PdfCopy(document, out);

        document.open();

        int n = reader.getNumberOfPages();
        PdfImportedPage importedPage;
        PdfDictionary pageDict;
        for (int page = 1; page <= n; page++) {
            //            System.out.println("pag " + page + ": " + reader.getPageRotation(page));

            if (page % 2 == 0) {
                copy.addPageDictEntry(PdfName.ROTATE, new PdfNumber(reader.getPageRotation(page) + 90));
            }
            copy.addPage(copy.getImportedPage(reader, page));
        }

        copy.freeReader(reader);
        reader.close();
        document.close();

        out.flush();
        out.close();
    }

    public static byte[] mergeAndBookmarkerDocument(List<byte[]> list, List<String> bookMarks) throws Exception {
        int[] i = new int[list.size()];
        ByteArrayOutputStream baos = null;
        ByteArrayOutputStream baos2 = null;

        try {
            System.out.println("### criando o primeiro array de bytes");
            baos = new ByteArrayOutputStream();

            Document document = new Document();

            System.out.println("### criado o PdfCopy");
            PdfCopy copy = new PdfCopy(document, baos);

            System.out.println("### abriu o documento");
            document.open();

            PdfReader reader = null;

            int n = 0;
            int countDocs = 0;
            int totalPages = 0;
            for (byte[] doc : list) {
                System.out.println("### criou o PdfReader");
                reader = new PdfReader(doc);
                n = reader.getNumberOfPages();

                System.out.println("### copiandos as páginas pra o array de bytes");
                for (int page = 0; page < n;) {
                    copy.addPage(copy.getImportedPage(reader, ++page));
                }

                System.out.println("### chamou o freeReader e fechou o PdfReader");
                copy.freeReader(reader);
                reader.close();

                if (totalPages == 0) {
                    i[countDocs] = 1;
                } else {
                    i[countDocs] = totalPages + 1;
                }

                countDocs++;
                totalPages += n;
            }

            System.out.println("### fechou o document");
            document.close();

            System.out.println("### criou o novo array de bytes");
            baos2 = new ByteArrayOutputStream();

            System.out.println("### criou o novo PdfReader");
            PdfReader reader2 = new PdfReader(baos.toByteArray());

            System.out.println("### fez o flush no array de bytes");
            baos.flush();
            baos.close();

            System.out.println("### criou o PdfStamper com o segundo array de bytes");
            PdfStamper stamper = new PdfStamper(reader2, baos2);

            List<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();

            System.out.println("### adicionando os bookmarks no mapa");
            for (int x = 0; x < i.length; x++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("Title", bookMarks.get(x));
                map.put("Action", "GoTo");
                map.put("Page", StringUtility.concatString(Integer.toString(i[x]), " FitH 806"));
                list2.add(map);
            }

            System.out.println("### setando o mapa de bookmarks no PdfStamper");
            stamper.setOutlines(list2);
            System.out.println("### fechando o PdfStamper");
            stamper.close();

            System.out.println("### pega o array de bytes resultante");
            byte[] result = baos2.toByteArray();

            System.out.println("### libera o segundo array de bytes");
            baos2.flush();
            baos2.close();

            System.out.println("### retorna o resultado");
            return result;

        } catch (DocumentException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    public static byte[] processAddPageNumber(byte[] byteArray, String xPosition, boolean numberFirstPage) throws Exception {
        return processAddPageNumber(byteArray, xPosition, numberFirstPage, "BOTTOM");
    }

    public static byte[] processAddPageNumber(byte[] byteArray, String xPosition, boolean numberFirstPage, String yPosition) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfReader reader = new PdfReader(byteArray);
            int n = reader.getNumberOfPages();

            PdfStamper stamp = new PdfStamper(reader, baos);

            int i = 0;

            PdfContentByte over = null;
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            while (i < n) {
                i++;

                // se é pra numerar a primeira página ou se já passou da primeira página
                if ((i == 1 && numberFirstPage) || i > 1) {
                    Rectangle rectangle = reader.getPageSizeWithRotation(i);

                    // por padrão é no fim da página
                    int y = 20;

                    // se veio pra colocar no topo da página
                    if (!StringUtility.isVazio(yPosition)) {
                        if (yPosition.equals("TOP")) {
                            y = new Float(rectangle.getHeight() - 30).intValue();

                        } else if (yPosition.equals("BOTTOM")) {
                            y = 20;
                        }
                    }

                    over = stamp.getOverContent(i);
                    over.beginText();
                    over.setFontAndSize(bf, 14);
                    over.setTextMatrix(getXPosition(xPosition, rectangle, bf, 14, Integer.toString(i)), y);
                    over.showText(Integer.toString(i));
                    over.endText();
                }
            }
            stamp.close();
        } catch (Exception de) {
            de.printStackTrace();
        }

        return baos.toByteArray();
    }

    private static byte[] processAddFooter(byte[] byteArray, List<String> footerLines) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfReader reader = new PdfReader(byteArray);
            int n = reader.getNumberOfPages();

            PdfStamper stamp = new PdfStamper(reader, baos);

            int i = 0;

            PdfContentByte over = null;
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            while (i < n) {
                i++;

                Rectangle rectangle = reader.getPageSizeWithRotation(i);

                over = stamp.getOverContent(i);

                //Create the table which will be 2 Columns wide and make it 100% of the page
                PdfPTable myTable = new PdfPTable(1);
                myTable.setTotalWidth(rectangle.getWidth() - 20);
                myTable.setWidthPercentage(100.0f);

                //create a 3 cells and add them to the table
                PdfPCell cellTwo = new PdfPCell(new Paragraph(""));
                cellTwo.setBorder(Rectangle.BOTTOM);
                cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);

                //Add the three cells to the table
                myTable.addCell(cellTwo);

                // complete the table
                myTable.completeRow();

                int y = 40;

                // write the table to an absolute position
                myTable.writeSelectedRows(0, -1, 10, y, over);

                Font ffont = new Font(Font.FontFamily.UNDEFINED, 7, Font.NORMAL);

                for (String footerLine : footerLines) {
                    y = y - 10;

                    if (y >= 0) {
                        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, new Phrase(footerLine, ffont), rectangle.getWidth() / 2, y, 0);
                    } else {
                        break;
                    }
                }
            }
            stamp.close();
        } catch (Exception de) {
            de.printStackTrace();
        }

        return baos.toByteArray();
    }

    private static int getXPosition(String position, Rectangle rectangle, BaseFont bf, Integer fontSize, String text) {

        if (position.equalsIgnoreCase("LEFT")) {
            return 30;
        }

        if (position.equalsIgnoreCase("MIDDLE")) {
            return new Float((rectangle.getWidth() - bf.getWidthPoint(text, fontSize)) / 2).intValue();
        }

        if (position.equalsIgnoreCase("RIGHT")) {
            return new Float(rectangle.getWidth() - 30 - bf.getWidthPoint(text, fontSize)).intValue();
        }

        return 0;
    }

    public static byte[] adjustDimensionToFooter(byte[] doc) throws Exception {
        return adjustDimension(doc, 1f, 0, 0, 0.95f, 0, 40);
    }

    public static byte[] adjustDimension(byte[] doc) throws Exception {
        return adjustDimension(doc, 1, 0, 0, 0.9f, 0, 0);
    }

    public static byte[] adjustDimension(byte[] doc, float a, float b, float c, float d, float e, float f) throws Exception {
        PdfReader pdfReader = null;
        try {
            pdfReader = new PdfReader(doc);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        int n = pdfReader.getNumberOfPages();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4, PageSize.A4.getLeft(), PageSize.A4.getRight(), PageSize.A4.getTop(), PageSize.A4.getBottom());
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, baos);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
        document.open();

        PdfContentByte cb = writer.getDirectContent();

        int i = 0;
        while (i < n) {
            i++;
            PdfImportedPage page1 = writer.getImportedPage(pdfReader, i);

            if (page1.getHeight() < page1.getWidth()) {
                document.setPageSize(new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth()));
            } else {
                document.setPageSize(PageSize.A4);
            }
            document.newPage();

            cb.addTemplate(page1, a, b, c, d, e, f);
            cb.beginText();
            cb.endText();
        }
        document.close();

        return baos.toByteArray();
    }

    public static void saveFile(byte[] bytes, File fileOut) throws Exception {
        try {
            if (!fileOut.exists()) {
                fileOut.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(fileOut);
            out.write(bytes);
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //TODO adicionar no log4j
            throw e;
        }
    }

    public static byte[] getBytesOfFile(File file) throws Exception {
        try {
            FileInputStream fileInput;
            byte[] bytes = null;
            fileInput = new FileInputStream(file);

            int size = new Long(file.length()).intValue();

            if (size != 0) {

                byte[] b = new byte[size];
                bytes = new byte[size];
                while ((fileInput.read(b)) != -1) {
                    bytes = b;
                }

                fileInput.close();
            }
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Boolean containOcr(File file) throws Exception {
        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;

        try {
            PDFParser parser = new PDFParser(new FileInputStream(file));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            //pagina inicial
            pdfStripper.setStartPage(1);
            //ultima pagina
            pdfStripper.setEndPage(pdDoc.getNumberOfPages());

            String parsedText = pdfStripper.getText(pdDoc);

            //verifica se possue texto
            if (parsedText.trim().isEmpty()) {
                return false;
            } else {
                //imprime na tela o texto
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (pdDoc != null) {
                    pdDoc.close();
                }

                if (cosDoc != null) {
                    cosDoc.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static List<File> splitPdfFileToJpgPage(File pdfFile, File jpgFileDirectoryWork) throws Exception {
        PDDocument document = null;
        try {
            byte[] byteArray = getBytesOfFile(pdfFile);

            if (jpgFileDirectoryWork == null) {
                jpgFileDirectoryWork = new File(pdfFile.getParentFile(), pdfFile.getName().substring(0, pdfFile.getName().lastIndexOf(".")));
            }
            // cria o diretório temporário
            jpgFileDirectoryWork.mkdirs();

            List<File> listFilesPages = new ArrayList<File>();

            //Lendo arquivo pdf para transformar páginas em imagens
            document = PDDocument.load(new ByteArrayInputStream(byteArray));

            List<PDPage> pages = document.getDocumentCatalog().getAllPages();
            for (int i = 0; i < pages.size(); i++) {
                PDPage page = pages.get(i);
                BufferedImage image = null;
                image = page.convertToImage(BufferedImage.TYPE_INT_RGB, 200);
                File filePage = new File(jpgFileDirectoryWork.getAbsolutePath(), i + ".jpg");

                // create jpegEncode for output image
                JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(new FileOutputStream(filePage));

                // create jpeg encoder from getting defaul value from input buffered
                // image
                JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(image);
                // setting up density unit paramter
                jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
                // setting up jpeg encode parameter
                jpegEncoder.setJPEGEncodeParam(jpegEncodeParam);
                // set quality parameter
                jpegEncodeParam.setQuality(0.75f, false);
                // set X-resolution
                jpegEncodeParam.setXDensity(200);
                // set Y-resolution
                jpegEncodeParam.setYDensity(200);
                // encode output image
                jpegEncoder.encode(image, jpegEncodeParam);
                // flush the buffer image
                image.flush();

                listFilesPages.add(filePage);
            }

            listFilesPages.add(jpgFileDirectoryWork);

            return listFilesPages;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // se o document é diferente de null
            if (document != null) {
                try {
                    // fecha o documento pra liberar memória
                    document.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public static byte[] processRemovePage(byte[] byteArray, int pageNumber) throws Exception {
        int pages[] = new int[] { pageNumber };
        return processRemovePage(byteArray, pages);
    }

    public static byte[] processRemovePage(byte[] byteArray, int[] pageNumber) throws Exception {
        ByteArrayOutputStream baos = null;

        try {
            baos = new ByteArrayOutputStream();

            Document document = new Document();

            PdfCopy copy = new PdfCopy(document, baos);

            document.open();

            PdfReader reader = new PdfReader(byteArray);

            for (int count = 1; count <= reader.getNumberOfPages();) {

                boolean isToRemove = false;

                for (int i = 0; i < pageNumber.length; i++) {
                    if (pageNumber[i] == count) {
                        isToRemove = true;
                        ++count;
                        continue;
                    }
                }

                if (!isToRemove) {
                    copy.addPage(copy.getImportedPage(reader, count++));
                }

            }

            copy.freeReader(reader);
            reader.close();
            document.close();

        } catch (DocumentException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }

        return baos.toByteArray();
    }

    public static int countPdfPages(Long documentNfId) throws Exception {
        try {
            int numberPages = -1;

            // pega o caminho do documento
            //            String pathDoc = DirectoryUtility.getCompleteFilePath(documentNfId, "pdf");

            // cria o file pra verificar se existe
            File file = DirectoryUtility.getCompleteFilePath(documentNfId, "pdf");

            // se existir
            if (file.exists()) {
                // le o pdf e pega o número de páginas que tem
                PdfReader reader = new PdfReader(new FileInputStream(file));
                numberPages = reader.getNumberOfPages();
            }

            return numberPages;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Integer countPages(byte[] byteArray) throws Exception {
        PdfReader pdfReader = null;
        try {
            pdfReader = new PdfReader(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pdfReader.getNumberOfPages();
    }

    public static byte[] processRotate(byte[] byteArray, int[] pages, int angle) throws Exception {
        ByteArrayOutputStream baos = null;
        PdfStamper pdfStamper = null;
        PdfDictionary pdfDictionary = null;
        PdfReader pdfReader = null;

        try {

            pdfReader = new PdfReader(byteArray);

            for (int i = 0; i < pages.length; i++) {
                pdfDictionary = pdfReader.getPageN(pages[i]);
                pdfDictionary.put(PdfName.ROTATE, new PdfNumber(pdfReader.getPageRotation(pages[i]) + angle));
            }

            baos = new ByteArrayOutputStream();

            pdfStamper = new PdfStamper(pdfReader, baos);
            pdfStamper.close();
            pdfReader.close();

        } catch (DocumentException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }

        return baos.toByteArray();
    }
}
