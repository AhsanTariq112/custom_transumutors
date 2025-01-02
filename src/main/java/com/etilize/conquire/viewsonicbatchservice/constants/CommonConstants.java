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
package com.etilize.conquire.viewsonicbatchservice.constants;

/**
 * Lists all common constants for export.
 *
 * @author Ahsan Tariq
 * @since 1.0
 */
public interface CommonConstants {

    String NULL = "null";
    String JOB_INSTANCE_ID_STRING = "jobInstanceId_";

    String OUTPUT_FILE_NAME = "data";

    String REQUIRED_JOB_INSTANCE_ID_STRING = "job Instance Id is required.";

    String REQUIRED_HEADER_STRING = "header is requried.";

    String API_VIEWSONIC_BATCH_JOB = "/viewsonic-batch-job";

    String JOB_ENDPOINT_EXECUTE = "/execute";

    String XML_EXTENSION = ".xml";

    String VIEWSONIC_REPOSITORY_NOT_NULL_MESSAGE = "ViewsonicRepository cannot be null";

    String SIMPLE_BATCH_JOB_NOT_NULL_MESSAGE = "simpleBatchJob cannot be null";

    String VIEWSONIC_BATCH_JOB_NOT_NULL_MESSAGE = "viewsonicBatchJob cannot be null";
}
