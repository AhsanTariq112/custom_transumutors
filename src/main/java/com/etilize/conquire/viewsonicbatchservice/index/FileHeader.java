/*
 * #region
 * viewsonic-batch-service
 * %%
 * Copyright (C) 2019 - 2020 Etilize
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

package com.etilize.conquire.viewsonicbatchservice.index;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link FileHeader} implements methods to perform file headers operations.
 *
 * @author Ahsan Tariq
 * @since 1.0.0
 */
public final class FileHeader {

    private static final Map<String, List<String>> COLUMNS = new HashMap<String, List<String>>();

    /**
     * Private default constructor.
     */
    private FileHeader() {
    }

    /**
     * Returns true if columns for given fileName is available.
     *
     * @param fileName Name of the file.
     * @return True if columns information is available for the provided file.
     */
    public static boolean hasColumnsForFile(final String fileName) {
        return COLUMNS.containsKey(fileName);
    }

    /**
     * Returns list of columns name in the file.
     *
     * @param fileId Unique name of the file.
     * @return list of columns name in the file.
     */
    public static List<String> getColumns(final String fileId) {
        if (COLUMNS.containsKey(fileId)) {
            return COLUMNS.get(fileId);
        }
        return Collections.emptyList();
    }

    /**
     * Adds columns against given file Id.
     *
     * @param fileId Id of the file.
     * @param columns List of columns/headers label.
     */
    public static void addColumns(final String fileId, final List<String> columns) {
        if (COLUMNS.containsKey(fileId)) {
            COLUMNS.replace(fileId, columns);
        } else {
            COLUMNS.put(fileId, columns);
        }
    }

    /**
     * Remove column header names from map.
     *
     * @param fileId Id of the file.
     */
    public static void removeColumnNameForFile(final String fileId) {
        if (COLUMNS.containsKey(fileId)) {
            COLUMNS.remove(fileId);
        }
    }

}
