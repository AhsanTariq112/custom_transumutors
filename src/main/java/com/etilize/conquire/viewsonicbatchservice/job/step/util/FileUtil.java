/*
 * #region
 * viewsonic-batch-service
 * %%
 * Copyright (C) 2022 Etilize
 * %%
 * NOTICE: All information contained herein is, and remains the property of ETILIZE.
 * The intellectual and technical concepts contained herein are proprietary to
 * ETILIZE and may be covered by U.S. and Foreign Patents, patents in process, and
 * are protected by trade secret or copyright law. Dissemination of this information
 * or reproduction of this material is strictly forbidden unless prior written
 * permission is obtained from ETILIZE. Access to the source code contained herein
 * is hereby forbidden to anyone except current ETILIZE employees, managers or
 * contractors who have executed Confidentiality and Non-disclosure agreements
 * explicitly covering such access.
 *
 * The copyright notice above does not evidence any actual or intended publication
 * or disclosure of this source code, which includes information that is confidential
 * and/or proprietary, and is a trade secret, of ETILIZE. ANY REPRODUCTION, MODIFICATION,
 * DISTRIBUTION, PUBLIC PERFORMANCE, OR PUBLIC DISPLAY OF OR THROUGH USE OF THIS
 * SOURCE CODE WITHOUT THE EXPRESS WRITTEN CONSENT OF ETILIZE IS STRICTLY PROHIBITED,
 * AND IN VIOLATION OF APPLICABLE LAWS AND INTERNATIONAL TREATIES. THE RECEIPT
 * OR POSSESSION OF THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR
 * IMPLY ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO
 * MANUFACTURE, USE, OR SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
 * #endregion
 */

package com.etilize.conquire.viewsonicbatchservice.job.step.util;

import com.google.common.net.MediaType;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link FileUtil} provides util functions for file.
 *
 * @author Ahsan Tariq
 * @since 1.0.0
 */
public final class FileUtil {

    // Ref: https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
    private static final String LINE_TOKENIZER_REGEX = "(?x)   " + //
            "%s         " + // Split on delimiter
            "(?=        " + // Followed by
            "  (?:      " + // Start a non-capture group
            "    [^\"]* " + // 0 or more non-quote characters
            "    \"     " + // 1 quote
            "    [^\"]* " + // 0 or more non-quote characters
            "    \"     " + // 1 quote
            "  )*       " + // 0 or more repetition of non-capture group (multiple of 2
            // quotes will be even)
            "  [^\"]*   " + // Finally 0 or more non-quotes
            "  $        " + // Till the end (This is necessary, else every comma will
            // satisfy the condition)
            ")          "; // End look-ahead

    private static final String REMOVE_SPECIAL_CHARACTER_REGEX = "^\"|\"$";

    public static final String DELIMITER = ",";

    public static final String CUSTOM_ESCAPE_DELIMITER = "---COMMA---";

    public static final String DOUBLE_QUOTE = "\"";

    public static final String CUSTOM_ESCAPE_DOUBLE_QUOTE = "---DOUBLE-QUOTE---";

    public static final String JOINNER = "|";

    public static final String INNER_JOINNER = "&";

    public static final String FURTHER_INNER_JOINNER = "#";

    public static final String EMPTY_CELL = "";
    
    public static final String NEW_LINE = "\n";

    private FileUtil() {

    }

    /**
     * Validates file format for csv.
     *
     * @param file File to be validated for csv format.
     * @return True if valid file format else false.
     */
    public static boolean validateCSVFileFormat(final MultipartFile file) {
        if (null != file && null != file.getContentType()
                && (file.getContentType().endsWith(MediaType.CSV_UTF_8.subtype()) // NOSONAR
                        || file.getContentType() //
                                .endsWith(MediaType.MICROSOFT_EXCEL.subtype()))) { // NOSONAR
            return true;
        }
        return false;
    }
    
    /**
     * Validates file format for xml.
     *
     * @param file File to be validated for xml format.
     * @return True if valid file format else false.
     */
    public static boolean validateXMLFileFormat(final MultipartFile file) {
        if (null != file && null != file.getContentType()
                && (file.getContentType().endsWith(MediaType.XML_UTF_8.subtype()))) { // NOSONAR
            return true;
        }
        return false;
    }

    /**
     * Escapes string for csv.
     *
     * @param data String to be escaped for csv.
     * @return Date with escaped csv string.
     */
    public static String filterDataForCsv(final String data) {
        if (null == data || data.trim().isEmpty()) {
            return new StringBuilder(StringEscapeUtils.escapeCsv(EMPTY_CELL)) //
                    .append(FileUtil.DELIMITER) //
                    .toString();
        }
        return new StringBuilder(StringEscapeUtils //
                .escapeCsv(data //
                        .replace(FileUtil.CUSTOM_ESCAPE_DOUBLE_QUOTE, //
                                FileUtil.DOUBLE_QUOTE) //
                        .replace(FileUtil.CUSTOM_ESCAPE_DELIMITER, //
                                FileUtil.DELIMITER))) //
                                        .append(FileUtil.DELIMITER) //
                                        .toString();
    }

    /**
     * Escape illegal characters for file name.
     *
     * @param fileName Name of the file without extension.
     * @return Name of the file with illegal characters replaced by '_'.
     */
    public static String escapeStringForFileName(final String fileName) {
        if (null == fileName) {
            return null;
        }
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

    /**
     * Converts comma separated string to list.
     *
     * @param csvString The csvString.
     * @return The list of string.
     */
    public static List<String> convertCSVStringToList(final String csvString) {
        final List<String> data = Arrays.asList(
                csvString.split(String.format(LINE_TOKENIZER_REGEX, DELIMITER)));
        return data;
    }

    /**
     * Filter the provided data.
     *
     * @param data The data.
     * @return The filtered data.
     */
    public static List<String> filterListData(final List<String> data) {
        final List<String> filteredData = new ArrayList<String>();
        for (final String product : data) {
            filteredData.add(product.replaceAll(REMOVE_SPECIAL_CHARACTER_REGEX, ""));
        }
        return filteredData;
    }

}
