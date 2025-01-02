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
 * This class implements all the step constants
 * 
 * @author Ahsan Tariq
 * @see 1.0
 */
public final class StepConstants {

    private StepConstants() {
    }

    public static final String CURRENT_STEP_EXECUTION_ID = "currentStepExecutionId";
    
    public static final String PREVIOUS_STEP_EXECUTION_ID = "previousStepExecutionId";
    
    public static final String EXPORT_PRODUCT_STEP_NAME = "Export Product";
    
    public static final Integer MAX_RETRY_ATTEMPTS = 3;

    public static final String IMPORT_VIEWSONIC_PRODUCT_LIST_STEP_NAME = "Import viewsonic product list";

    public static final String IMPORT_VIEWSONIC_ATTRIBUTES_STEP_NAME = "Import viewsonic Attributes";

    public static final String GENERATE_HEADER_FOR_VIEWSONIC_STEP_NAME = "Generate Header for Viewsonic";

}
