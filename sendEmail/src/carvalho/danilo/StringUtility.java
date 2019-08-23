package carvalho.danilo;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtility {

    private static Pattern[] arrayPatterns;
    private static String[] strReplaces = { "a", "A", "e", "E", "i", "I", "o", "O", "u", "U", "c", "C", "" };
    private static Charset charsetUTF8 = Charset.forName("UTF-8");
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    /**
     * Retorna a string passada como parametro, mas com a inicial maiuscula.
     */
    public static String tornarInicialMaiuscula(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    /**
     * Retorna a string passada como parametro, mas com a inicial minuscula.
     */
    public static String tornarInicialMinuscula(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    public static String removerMascaraCPF(String campo) {
        if (campo != null) {
            return getNumbers(campo);
        }
        return campo;
    }

    public static String removerMascaraCNPJ(String campo) {
        if (campo != null) {
            return getNumbers(campo);
        }
        return campo;
    }

    public static String addMascaraCPFCNPJ(String campo) {
        StringBuilder builder = new StringBuilder();
        if (campo != null) {
            if (campo.toString().trim().length() == 14) {
                builder.append(campo.substring(0, 2)).append(".");
                builder.append(campo.substring(2, 5)).append(".");
                builder.append(campo.substring(5, 8)).append("/");
                builder.append(campo.substring(8, 12)).append("-");
                builder.append(campo.substring(12, 14));
            } else if (campo.toString().trim().length() == 11) {
                builder.append(campo.substring(0, 3)).append(".");
                builder.append(campo.substring(3, 6)).append(".");
                builder.append(campo.substring(6, 9)).append("-");
                builder.append(campo.substring(9, 11));
            } else {
                builder.append(campo);
            }
        }

        return builder.toString();
    }

    public static String addMascaraCodOperacao(String campo) {
        StringBuilder builder = new StringBuilder();

        if (campo != null) {
            if (campo.toString().trim().length() != 19) {
                campo = completeLeft(campo.trim(), "0", 19);
            }
            builder.append(campo.substring(0, 1)).append(".");
            builder.append(campo.substring(1, 6)).append(".");
            builder.append(campo.substring(6, 14)).append(".");
            builder.append(campo.substring(14, 16)).append("/");
            builder.append(campo.substring(16, 19));

        }

        return builder.toString();
    }

    public static String addMascaraProcInpiBR(String campo) {
        StringBuilder builder = new StringBuilder();
        if (campo != null) {
            if (campo.toString().trim().length() == 14) {
                builder.append(campo.substring(1, 13));
                builder.append("-");
                builder.append(campo.substring(13, 14));
                ;
            } else {
                builder.append(campo);
            }
        }

        return builder.toString();
    }

    public static String removeSpace(String val) {
        if (val != null) {
            return val.replaceAll(" ", "");
        }
        return "";
    }

    public static String removeHTML(String htmlString) {
        String textString = htmlString.replaceAll("\\<.*?\\>", "");
        textString = textString.replaceAll("\r", "");
        textString = textString.replaceAll("\n", " ");
        textString = textString.replaceAll("\'", "'");
        return textString;
    }

    public static String toLowerCase(String string) {
        if (string != null) {
            return string.toLowerCase();
        }
        return null;
    }

    public static String toUpperCase(String string) {
        if (string != null) {
            return string.toUpperCase();
        }
        return null;
    }

    public static String trim(String string) {
        if (string != null) {
            return string.trim();
        }
        return null;
    }

    public static StringBuilder setTagValue(StringBuilder sb, String tagName, String tagValue) {
        if (sb != null) {
            if (tagValue == null) {
                tagValue = "";
            }
            String tagName2 = "${" + tagName + "}";
            return sb.replace(sb.indexOf(tagName2), sb.indexOf(tagName2) + tagName2.length(), tagValue);
        }
        return null;
    }

    public static String limitLength(String str, int maxLength) {
        if (str != null && str.length() > maxLength) {
            StringBuilder builder = new StringBuilder();
            builder.append(str.substring(0, maxLength - 3));
            builder.append("...");

            return builder.toString();
        }
        return str;
    }

    public static String replaceSpecialCharForSOLR(String text) {
        if (text != null && !text.isEmpty()) {
            Map<String, String> specialChars = new TreeMap<String, String>();
            for (String specialChar : new String[] { "+", "-", "&&", "||", "!", "(", ")", "{", "}", "[", "]", "^", "\"", "~", "*", "?", ":" }) {
                specialChars.put(specialChar, "\\" + specialChar);
            }

            String result = text.replace("\\", "\\\\");

            for (String key : specialChars.keySet()) {
                try {
                    result = result.replace(key, specialChars.get(key));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            return result;
        }

        return null;
    }

    public static String encodingInURLForSOLR(String text) {
        try {
            if (text != null && !text.isEmpty()) {
                return URLEncoder.encode(text, "UTF-8").replace("%", "@").replace("+", " ");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public static String decodingInURLForSOLR(String text) {
        try {
            if (text != null && !text.isEmpty()) {
                return URLDecoder.decode(text.replace("@", "%"), "UTF-8");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public static String replaceSpecialChars(String text) {
        String result = text;
        Pattern[] patterns = getPatterns();
        for (int i = 0; i < patterns.length; i++) {
            Matcher matcher = patterns[i].matcher(result);
            result = matcher.replaceAll(strReplaces[i]);
        }

        return result;
    }

    private static Pattern[] getPatterns() {
        if (arrayPatterns == null) {
            arrayPatterns = new Pattern[strReplaces.length];

            arrayPatterns[0] = Pattern.compile("[âãáàä]", Pattern.CASE_INSENSITIVE);
            arrayPatterns[1] = Pattern.compile("[âãáàä]".toUpperCase(), Pattern.CASE_INSENSITIVE);

            arrayPatterns[2] = Pattern.compile("[éèêë]", Pattern.CASE_INSENSITIVE);
            arrayPatterns[3] = Pattern.compile("[éèêë]".toUpperCase(), Pattern.CASE_INSENSITIVE);

            arrayPatterns[4] = Pattern.compile("[íìîï]", Pattern.CASE_INSENSITIVE);
            arrayPatterns[5] = Pattern.compile("[íìîï]".toUpperCase(), Pattern.CASE_INSENSITIVE);

            arrayPatterns[6] = Pattern.compile("[óòôõö]", Pattern.CASE_INSENSITIVE);
            arrayPatterns[7] = Pattern.compile("[óòôõö]".toUpperCase(), Pattern.CASE_INSENSITIVE);

            arrayPatterns[8] = Pattern.compile("[úùûü]", Pattern.CASE_INSENSITIVE);
            arrayPatterns[9] = Pattern.compile("[úùûü]".toUpperCase(), Pattern.CASE_INSENSITIVE);

            arrayPatterns[10] = Pattern.compile("[ç]", Pattern.CASE_INSENSITIVE);
            arrayPatterns[11] = Pattern.compile("[ç]".toUpperCase(), Pattern.CASE_INSENSITIVE);
            arrayPatterns[12] = Pattern.compile("[&#*$}{<>:º^;,.§%@!?]".toUpperCase(), Pattern.CASE_INSENSITIVE);
        }

        return arrayPatterns;
    }

    public static String getNumbers(String val) {
        if (val != null) {
            return val.replaceAll("[\\D]", "");
        }
        return "";
    }

    public static String getNotNumbers(String val) {
        if (val != null) {
            return val.replaceAll("[\\d]", "");
        }
        return "";
    }

    public static String substring(String original, int beginIndex, int endIndex) {
        if (original != null && original.length() > endIndex) {
            return original != null ? original.substring(beginIndex, endIndex) : null;
        }
        return original;
    }

    public synchronized static String completeLeft(Integer val, String caracter, Integer length) {
        return completeLeft(String.valueOf(val), caracter, length);
    }

    public synchronized static String completeLeft(String val, String caracter, Integer length) {
        if (val.length() < length) {
            StringBuilder newVal = new StringBuilder();
            int diff = length - val.length();
            for (int i = 0; i < diff; i++) {
                newVal.append(caracter);
            }
            newVal.append(val);
            return newVal.toString();
        } else {
            return val.substring(val.length() - length);
        }
    }

    public synchronized static String completeRight(String val, String caracter, Integer length) {
        if (val.length() < length) {
            StringBuilder newVal = new StringBuilder();
            int diff = length - val.length();
            newVal.append(val);
            for (int i = 0; i < diff; i++) {
                newVal.append(caracter);
            }
            return newVal.toString();
        } else {
            return val.substring(0, length);
        }
    }

    public synchronized static String concatString(String... strings) {
        StringBuilder string = new StringBuilder();
        if (strings != null) {
            for (String val : strings) {
                string.append(val);
            }
        }
        return string.toString();
    }

    public synchronized static String concat(Object... objects) {
        StringBuilder string = new StringBuilder();
        if (objects != null) {
            for (Object val : objects) {
                string.append(val);
            }
        }
        return string.toString();
    }

    public synchronized static String convertToUTF8(String string) {
        String result = null;
        if (string != null) {
            result = new String(charsetUTF8.encode(string).array());
        }
        return result;
    }

    public synchronized static String stringToHex(String input) {
        if (input == null) {
            return null;
        }
        return asHex(input.getBytes());
    }

    public synchronized static String hexToString(String txtInHex) {
        byte[] txtInByte = new byte[txtInHex.length() / 2];
        int j = 0;
        for (int i = 0; i < txtInHex.length(); i += 2) {
            txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);
        }
        return new String(txtInByte);
    }

    private synchronized static String asHex(byte[] buf) {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }

    public synchronized static String removeHexNull(String str) {
        if (str != null) {
            StringBuilder result = new StringBuilder();
            char[] chars = str.toCharArray();

            String hexCod = null;
            for (char c : chars) {
                hexCod = stringToHex(Character.toString(c));
                if (hexCod.equals("00")) {
                    result.append(" ");
                } else {
                    result.append(c);
                }
            }
            return result.toString();
        }
        return null;
    }

    public synchronized static boolean isVazio(String valor) {
        return valor == null || valor.trim().equals("");
    }

    public static Integer getLength(Object text) {
        if (text != null) {
            if (text instanceof Number || text instanceof String) {
                return text.toString().length();
            }
        }
        return 0;
    }

    public synchronized static String removeStrangeCharacters(String str) {
        StringBuilder result = new StringBuilder();

        int asciiCode = 0;
        for (int i = 0; i < str.length(); i++) {
            asciiCode = (int) str.charAt(i);

            if (asciiCode > 31 && asciiCode < 127) {
                result.append(str.charAt(i));
            } else {
                result.append(" ");
            }

        }

        return result.toString();
    }

    public static List<String> breakLines(int maxSize, String text) {
        // lista de retorno
        List<String> lines = new ArrayList<String>();

        if (text != null) {
            // string builder pra facilitar a manipulação da str
            StringBuilder strText = new StringBuilder(text.trim());
            // indice do espaço em branco
            int indexNextSpace = 0;

            // enquanto o tamanho do StringBuilder for maior que o tamanho máximo da linha
            while (strText.length() > maxSize) {
                // busca o indice do último espaço em branco até o tamanho máximo da linha
                indexNextSpace = strText.lastIndexOf(" ", maxSize);

                if (indexNextSpace > 0) {
                    // incrementa o índice para o espaço tbém entrar no substring
                    indexNextSpace++;

                    // faz o subsring adicionando na lista 
                    lines.add(strText.substring(0, indexNextSpace).trim());

                    // apaga da string original os caracteres adicionados na lista 
                    strText.delete(0, indexNextSpace);
                } else {
                    // se a palavra é maior que o tamanho da linha, ou seja, não encontrou espaço em branco nos 35 caracteres, apenas limita adicionando "..." no final
                    lines = new ArrayList<String>();
                    lines.add(limitLength(text, maxSize));

                    // limpa o stringbuilder
                    strText = new StringBuilder();

                    // pára o for
                    break;
                }
            }

            // se sobrou algum caracter, adiciona na lista
            if (!strText.toString().trim().isEmpty()) {
                lines.add(strText.toString().trim());
            }
        }

        // retorna a lista com as linhas
        return lines;
    }

    public static String replaceSpecialCharForMicrofichas(String text) {
        if (text != null && !text.isEmpty()) {
            Map<String, String> specialChars = new TreeMap<String, String>();
            for (String specialChar : new String[] { "+", "-", "&&", "||", "!", "(", ")", "{", "}", "[", "]", "^", "\"", "~", "*", "?", ":" }) {
                specialChars.put(specialChar, "\\" + specialChar);
            }

            String result = text.replace("\\", "\\\\");

            for (String key : specialChars.keySet()) {
                try {
                    result = result.replace(key, specialChars.get(key));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            return result;
        }

        return "";
    }

    public static String escapeForXML(String str) {
        StringBuilder sb = new StringBuilder(str.length());

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == '&') {
                sb.append("&amp;");
            } else if (c == '<') {
                sb.append("&lt;");
            } else if (c == '>') {
                sb.append("&gt;");
            } else if (c == '"') {
                sb.append("&quot;");
            } else if (c == '\'') {
                sb.append("&apos;");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}