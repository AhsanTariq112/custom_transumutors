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
package com.etilize.conquire.viewsonicbatchservice.job.step;

import com.etilize.conquire.viewsonicbatchservice.constants.StepConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;

/**
 * This class implements step's listener to perform any action before and/or after the
 * execution of any step.
 *
 * @author Ahsan Tariq
 * @since 1.0.0
 */
public class ViewsonicBatchStepExecutionListener extends StepExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(
            ViewsonicBatchStepExecutionListener.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeStep(final StepExecution stepExecution) {
        final ExecutionContext executionContext = stepExecution.getJobExecution() //
                .getExecutionContext();

        executionContext.put(StepConstants.CURRENT_STEP_EXECUTION_ID, stepExecution.getId());

        logger.info("{} step started!", stepExecution.getStepName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        final ExecutionContext executionContext = stepExecution.getJobExecution() //
                .getExecutionContext();

        // only set previous step id if the step execution was successful
        if (!stepExecution.getStatus().isUnsuccessful()) {
            executionContext.put(StepConstants.PREVIOUS_STEP_EXECUTION_ID, stepExecution.getId());
        }

        logger.info("{} step {}!", stepExecution.getStepName(), //
                stepExecution.getExitStatus());
        return stepExecution.getExitStatus();
    }
}
