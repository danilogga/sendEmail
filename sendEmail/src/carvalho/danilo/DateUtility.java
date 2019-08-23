package carvalho.danilo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DateUtility {

    public static Long countDays(Date d1, Date d2) {
        if (d1 == null)
            d1 = getCurrentDate();
        if (d2 == null)
            d2 = getCurrentDate();
        Long days = d2.getTime() - d1.getTime();
        days = days / 86400000;
        return days;

    }

    public static Boolean checkEndWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekDay != Calendar.SATURDAY && weekDay != Calendar.SUNDAY) {
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("deprecation")
    public static Date getFormatDate(Date date) {
        String dateString = new SimpleDateFormat("yyyy/MM/dd").format(date);
        date = new Date(dateString);
        return date;
    }

    @SuppressWarnings("deprecation")
    public static Date getFormatDate(Date date, String patern) {
        String dateString = new SimpleDateFormat(patern).format(date);
        date = new Date(dateString);
        return date;
    }

    public static String dateToString(Date date, String pattern) {
        String dateString = new SimpleDateFormat(pattern).format(date);
        return dateString;
    }

    public static Date stringToDate(String dateString, String pattern) {
        Date date = null;
        if (dateString != null && !dateString.isEmpty()) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                simpleDateFormat.setLenient(false);
                date = simpleDateFormat.parse(dateString.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * Retorna a data atual com a hora passada como parâmetro do tipo String.
     *
     * @param hourString
     *      Fora para ser setada na data atual
     * @param hourPattern
     *      Formato da hora para ser convertida para Date, utilizar somente o formato da hora, ex: "HH:mm"
     * @return
     */
    public static Date stringHourToDate(String hourString, String hourPattern) {
        Date date = null;
        if (hourString != null && !hourString.isEmpty()) {
            String dateToConvert = StringUtility.concatString(dateToString(getCurrentDate(), "dd/MM/yyyy"), " ", hourString);
            String patterApply = StringUtility.concatString("dd/MM/yyyy ", hourPattern);

            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patterApply);
                simpleDateFormat.setLenient(false);
                date = simpleDateFormat.parse(dateToConvert);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date adjustTimeZone(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis((date.getTime()));

        int timeZone = calendar.get(Calendar.DST_OFFSET);

        date = new Date(date.getTime() + timeZone);

        return date;
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    public static String getSimpleDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public static Date getDateAfter(Date dateAdd, Integer day) {
        Calendar date = Calendar.getInstance();
        date.setTime(dateAdd);
        date.add(Calendar.DAY_OF_MONTH, +day);

        return date.getTime();
    }

    public static Date getDateBefore(Date dateRemove, Integer day) {
        Calendar date = Calendar.getInstance();
        date.setTime(dateRemove);
        date.add(Calendar.DAY_OF_MONTH, -day);

        return date.getTime();
    }

    public static String getDateFormated() {

        String data = "dd/MM/yyyy";
        String hora = "h:mm";
        Date now = getCurrentDateTime();
        String dataFormatada, horaFormatada;
        SimpleDateFormat formatador = new SimpleDateFormat(data);
        dataFormatada = formatador.format(now);
        formatador = new SimpleDateFormat(hora);
        horaFormatada = formatador.format(now);
        dataFormatada = dataFormatada + "-" + horaFormatada;
        return dataFormatada;
    }

    public static Date getCurrentDateTime() {
        return Calendar.getInstance().getTime();
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static String getCurrentDateTimeString() {
        return dateToString(getCurrentDateTime(), "dd/MM/yyyy HH:mm:ss");
    }

    public static String getCurrentDateTimeString(String pattern) {
        if (pattern != null) {
            return dateToString(getCurrentDateTime(), pattern);
        } else {
            return getCurrentDateTimeString();
        }
    }

    public static String getCurrentDateString() {
        return dateToString(getCurrentDate(), "dd/MM/yyyy");
    }

    public static String getCurrentDateString(String pattern) {
        if (pattern != null) {
            return dateToString(getCurrentDate(), pattern);
        } else {
            return getCurrentDateString();
        }
    }

    public static Date removeHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static Date getInstanceDate(int day, int month, int year) {
        if (day == 0 || month == 0 || year == 0) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * Método para somar valore da data e hora atual.
     *
     * @param field = Constantes do objeto {@link Calendar}, ex. Calendar.MINUTE
     * @param val, valor a ser somado.
     * @return
     */
    public static Date add(int field, int val) {
        return add(field, val, getCurrentDateTime());
    }

    /**
     * Método para somar valore da data e hora informada.
     *
     * @param field = Constantes do objeto {@link Calendar}, ex. Calendar.MINUTE
     * @param val, valor a ser somado.
     * @param date, data a ser somada.
     * @return
     */
    public static Date add(int field, int val, Date date) {
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(date);
        cDate.add(field, val);
        return cDate.getTime();
    }

    /**
     * Adiciona os milisegundos passados como parâmetro à data atual
     *
     * @param milli
     * @return
     */
    public static Date addMilliseconds(long milli) {
        return addMilliseconds(milli, Calendar.getInstance().getTime());
    }

    /**
     * Adiciona os milisegundos passados como parâmetro à data informada
     *
     * @param milli
     * @param date
     * @return
     */
    public static Date addMilliseconds(long milli, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime() + milli);

        return calendar.getTime();
    }

    /**
     * Método para subtrair valore da data e hora atual.
     *
     * @param field = Constantes do objeto {@link Calendar}, ex. Calendar.MINUTE
     * @param val, valor a ser subtraído.
     * @return
     */
    public static Date subtract(int field, int val) {
        return subtract(field, val, getCurrentDateTime());
    }

    /**
     * Método para subtrair valore da data e hora informada.
     *
     * @param field = Constantes do objeto {@link Calendar}, ex. Calendar.MINUTE
     * @param val, valor a ser subtraído.
     * @param date, data a ser subtraída.
     * @return
     */
    public static Date subtract(int field, int val, Date date) {
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(date);
        cDate.add(field, -val);
        return cDate.getTime();
    }

    public static Integer compareTo(Date data1, Date data2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(data1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(data2);

        return calendar1.compareTo(calendar2);
    }

    public static boolean isAfter(Date data1, Date data2) {
        return compareTo(data1, data2) > 0;
    }

    public static boolean isBefore(Date data1, Date data2) {
        return compareTo(data1, data2) < 0;
    }

    public static boolean isEqual(Date data1, Date data2) {
        return compareTo(data1, data2) == 0;
    }

    /**
     * Retorna um objeto com a data passada como parâmetro e a hora pra meia noite, necessário
     * para pesquisa no banco por data (between).
     *
     * @param date
     * @return
     */
    public static Date getInitDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * Retorna um objeto com a data passada como parâmetro e a hora pra 23:59:59.9999, necessário
     * para pesquisa no banco por data (between).
     *
     * @param date
     * @return
     */
    public static Date getFinalDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    public static Date getDateBeforeCountBusinessDay(Date startDate, Integer daysBefore, List<Date> holidays) {
        List<Date> holidaysAux = new ArrayList<Date>();
        if (holidays != null && !holidays.isEmpty()) {
            for (Date holiday : holidays) {
                holidaysAux.add(getInitDate(holiday));
            }
        }

        Date dateBefore = getInitDate(startDate);
        for (int i = 0; i < daysBefore; i++) {
            dateBefore = getDateBeforeHoliday(getDateBefore(getInitDate(dateBefore), 1), holidaysAux);
        }

        return dateBefore;
    }

    public static Date getDateBeforeHoliday(Date dateBefore, List<Date> holidays) {
        Boolean dateInHoliday = false;
        Integer days = 1;

        if (isOnDayOfWeek(dateBefore, Calendar.SATURDAY) || isOnDayOfWeek(dateBefore, Calendar.SUNDAY) || holidays.contains(dateBefore)) {
            dateInHoliday = true;
        }

        if (dateInHoliday) {
            dateBefore = getDateBeforeHoliday(getDateBefore(dateBefore, days), holidays);
        }

        return dateBefore;
    }

    public static Date getDateAfterCountBusinessDay(Date startDate, Integer daysAfter, List<Date> holidays) {
        List<Date> holidaysAux = new ArrayList<Date>();
        if (holidays != null && !holidays.isEmpty()) {
            for (Date holiday : holidays) {
                holidaysAux.add(getInitDate(holiday));
            }
        }

        Date dateAfter = getInitDate(startDate);
        for (int i = 0; i < daysAfter; i++) {
            dateAfter = getDateAfterHoliday(getDateAfter(getInitDate(dateAfter), 1), holidaysAux);
        }

        return dateAfter;
    }

    public static Date getDateAfterHoliday(Date dateAfter, List<Date> holidays) {
        Boolean dateInHoliday = false;
        Integer days = 1;

        if (isOnDayOfWeek(dateAfter, Calendar.SATURDAY)) {
            dateInHoliday = true;
            days = 2;
        } else if (isOnDayOfWeek(dateAfter, Calendar.SUNDAY) || holidays.contains(dateAfter)) {
            dateInHoliday = true;
        }

        if (dateInHoliday) {
            dateAfter = getDateAfterHoliday(getDateAfter(dateAfter, days), holidays);
        }

        return dateAfter;
    }

    public static boolean isOnDayOfWeek(Date date, Integer dayOkWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay == dayOkWeek;
    }

    /**
     * Retorna o tempo decorrido entre a data final e a data inicial no formato HH:mm:ss.SSS.
     *
     * @param dateInitial
     * @param dateFinal
     * @return
     */
    public static String getTimeElapsed(Date dateInitial, Date dateFinal) {
        long timeInitial = dateInitial.getTime();
        long timeFinal = dateFinal.getTime();

        return formatMilliseconds(timeFinal - timeInitial);
    }

    public static Long getTimeElapsedinSeconds(Date dateInitial, Date dateFinal) {
        long timeElapsed;
        long timeInitial = dateInitial.getTime();
        long timeFinal = dateFinal.getTime();

        timeElapsed = TimeUnit.MILLISECONDS.toSeconds(timeFinal - timeInitial);

        return timeElapsed;
    }

    public static Long getTimeElapsedinMinutes(Date dateInitial, Date dateFinal) {
        long timeElapsed;
        long timeInitial = dateInitial.getTime();
        long timeFinal = dateFinal.getTime();

        timeElapsed = TimeUnit.MILLISECONDS.toMinutes(timeFinal - timeInitial);

        return timeElapsed;
    }

    public static Long getTimeElapsedinHours(Date dateInitial, Date dateFinal) {
        long timeElapsed;
        long timeInitial = dateInitial.getTime();
        long timeFinal = dateFinal.getTime();

        timeElapsed = TimeUnit.MILLISECONDS.toHours(timeFinal - timeInitial);

        return timeElapsed;
    }

    /**
     * Retorna o tempo restante de um processo no formato HH:mm:ss.SSS, esse tempo é calculado de acordo com
     * os parâmetros informados, calcula-se o tempo que foi gasto até o momento, esse tempo é divido pelo valor
     * parcial para encontrar a média de tempo gasto até agora, essa média de tempo é multiplicada pelo total
     * que falta (valorTotal - ValorParcial) e assim chegamos ao tempo restante.
     *
     * @param valueParcial valor parcial do processo
     * @param valueTotal valor total do processo
     * @param horaInicial hora inicial do prpocesso 
     * @param horaFinalParcial hora parcial do processo
     * @return
     */
    public static String getRemainingTimeFormatted(int valueParcial, int valueTotal, Date horaInicial, Date horaFinalParcial) {
        // retorna o tempo restante formatado
        return formatMilliseconds(getRemainingTimeInMilli(valueParcial, valueTotal, horaInicial, horaFinalParcial));
    }

    /**
     * Retorna o tempo restante de um processo em milisegundos, esse tempo é calculado de acordo com
     * os parâmetros informados, calcula-se o tempo que foi gasto até o momento, esse tempo é divido pelo valor
     * parcial para encontrar a média de tempo gasto até agora, essa média de tempo é multiplicada pelo total
     * que falta (valorTotal - ValorParcial) e assim chegamos ao tempo restante.
     *
     * @param valueParcial
     * @param valueTotal
     * @param horaInicial
     * @param horaFinalParcial
     * @return
     */
    public static long getRemainingTimeInMilli(int valueParcial, int valueTotal, Date horaInicial, Date horaFinalParcial) {
        // calcula o tempo gasto
        long timeElapsed = horaFinalParcial.getTime() - horaInicial.getTime();
        // calcula a média de tempo gasto
        long avgTime = timeElapsed / valueParcial;
        // multiplica a média de tempo gasto com o total que falta
        long remainingTime = avgTime * (valueTotal - valueParcial);

        // retorna o tempo restante formatado
        return remainingTime;
    }

    /**
     * Retorna os milisegundos passados como parâmetro no formato HH:mm:ss.SSS.
     *
     * @param milli
     * @return
     */
    public static String formatMilliseconds(long milli) {
        // pega o valor das unidades
        long hour = TimeUnit.MILLISECONDS.toHours(milli);
        long min = TimeUnit.MILLISECONDS.toMinutes(milli) % 60;
        long sec = TimeUnit.MILLISECONDS.toSeconds(milli) % 60;

        // cria a string formatada
        StringBuilder timeElapsed = new StringBuilder();
        timeElapsed.append(hour < 10 ? StringUtility.completeLeft(String.valueOf(hour), "0", 2) : hour).append(":");
        timeElapsed.append(StringUtility.completeLeft(String.valueOf(min), "0", 2)).append(":");
        timeElapsed.append(StringUtility.completeLeft(String.valueOf(sec), "0", 2)).append(".");
        timeElapsed.append(StringUtility.completeLeft(String.valueOf(milli), "0", 3));

        return timeElapsed.toString();
    }

    public static Date getDate(long miliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliseconds);

        return calendar.getTime();
    }

    public static int getField(int field, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(field);
    }

    public static int getActualField(int field) {
        Calendar calendar = Calendar.getInstance();

        return calendar.get(field);
    }

    public static Date setField(int field, int value) {
        return setField(field, value, Calendar.getInstance().getTime());
    }

    public static Date setField(int field, int value, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(field, value);

        return calendar.getTime();
    }

    public static Long getTimeInMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static Date getLastDayOfMonth(Integer mes, Integer ano) {
        return getLastDayOfMonth(getInstanceDate(1, mes, ano));
    }

    public static Date getLastDayOfMonth(String mes, String ano) {
        return getLastDayOfMonth(stringToDate(StringUtility.concatString(mes, "/", ano), "MM/yyyy"));
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    public static int getLastDayOfMonthFieldDay(Integer mes, Integer ano) {
        return getLastDayOfMonthFieldDay(getInstanceDate(1, mes, ano));
    }

    public static int getLastDayOfMonthFieldDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static boolean validDate(String strDate) {
        return validDate(strDate, "dd/MM/yyyy");
    }

    public static boolean validDate(String strDate, String format) {
        // cria o format
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // seta false pra não interpretar a data
        sdf.setLenient(false);

        try {
            sdf.parse(strDate);
            // se passou pra cá, é porque a data é válida
            return true;
        } catch (ParseException e) {
            // se cair aqui, a data é inválida
            return false;
        }
    }

    public static boolean validHour(String hour) {
        return validHour(hour, "HH:mm:ss");
    }

    public static boolean validHour(String hour, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            sdf.parse(hour);
            // se passou pra cá, é porque a data é válida
            return true;
        } catch (ParseException e) {
            // se cair aqui, a data é inválida
            return false;
        }
    }

    public static boolean dateIsBetween(Date date, Date beginDate, Date finalDate) {
        // data para comparação
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);

        // data de início do período
        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.setTime(beginDate);

        // data de final do período
        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTime(finalDate);

        // se a data não for antes da data de início e se a data não for depois da data final, ou se a data é igual a inicial ou final
        return (!calendarDate.before(calendarBegin) && !calendarDate.after(calendarFinal)) || calendarDate.equals(calendarBegin) || calendarDate.equals(calendarFinal);
    }
}
