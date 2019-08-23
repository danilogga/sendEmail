package carvalho.danilo;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class DirectoryUtility {

    public static File getCompletePathPage(Long documentNfId, Integer page) throws Exception {
        // cria o diretório com o id do documento
        File dirDocumentNf = getDirPages(documentNfId);

        // monta o nome da imagem da página que será o documentNfId_numpag
        StringBuilder pageName = new StringBuilder();
        pageName.append(documentNfId).append("_").append(page);
        pageName.append(".").append("jpg");

        // cria o objeto file do arquivo
        File fieldFile = new File(dirDocumentNf, pageName.toString());

        return fieldFile;
    }

    public static File getCompletePathThumbnail(Long documentNfId, Integer page) throws Exception {
        // cria o diretório com o id do documento
        File dirDocumentNf = getDirThumbnails(documentNfId);

        // monta o nome da imagem do thumbnail da página que será o documentNfId_numpag_T
        StringBuilder pageName = new StringBuilder();
        pageName.append(documentNfId).append("_").append(page);
        pageName.append(".").append("jpg");

        // cria o objeto file do arquivo
        File fieldFile = new File(dirDocumentNf, pageName.toString());

        return fieldFile;
    }

    public static File getDirPages(Long documentNfId) throws Exception {
        StringBuilder dirDoc = new StringBuilder(mountDirDoc(documentNfId));
        dirDoc.append(documentNfId);

        File fileFields = new File(getDirPagesRoot(documentNfId), dirDoc.toString());
        fileFields.mkdirs();
        return fileFields;
    }

    public static File getDirPagesRoot(Long documentNfId) throws Exception {
        File file = new File("/usr/local/flexdoc/repository/pages");
        file.mkdirs();

        return file;
    }

    public static File getDirThumbnails(Long documentNfId) throws Exception {
        // pega o caminho completo das páginas do documento
        File dirPages = getDirPages(documentNfId);

        // pega a pasta thumb dentro da pasta das páginas
        File dirThumb = new File(dirPages, "thumb");
        dirThumb.mkdirs();

        return dirThumb;
    }

    public static String mountDirDoc(Long documentId) throws Exception {
        StringBuilder diretorio = new StringBuilder();
        // formata os níveis de pasta de acordo com o id do documento
        Locale locale = new Locale("pt", "BR");
        NumberFormat numberFormat = new DecimalFormat("000,000,000,000", new DecimalFormatSymbols(locale));
        String estrutura = numberFormat.format(documentId);

        diretorio.append(Integer.parseInt(estrutura.substring(0, 3))).append("/");
        diretorio.append(Integer.parseInt(estrutura.substring(4, 7))).append("/");
        diretorio.append(Integer.parseInt(estrutura.substring(8, 11))).append("/");

        return diretorio.toString();
    }

    public static File getCompleteFilePath(Long documentNfId, String extension) throws Exception {
        return getCompleteFilePath(documentNfId, extension, false);
    }

    public static File getCompleteFilePath(Long documentNfId, String extension, boolean isBack) throws Exception {
        StringBuilder completePath = new StringBuilder();
        completePath.append(getDirDoc(documentNfId));
        completePath.append(documentNfId).append(isBack ? "V" : "F");
        completePath.append(".").append(extension);

        return new File(completePath.toString());
    }

    public static String getDirDoc(Long documentId) throws Exception {
        StringBuilder diretorio = new StringBuilder();
        diretorio.append("/usr/local/flexdoc/repository/");
        diretorio.append(mountDirDoc(documentId));
        return diretorio.toString();
    }

}
