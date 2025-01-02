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
package com.etilize.conquire.viewsonicbatchservice.job;

import com.etilize.conquire.viewsonicbatchservice.constants.JobConstants;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.IViewsonicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;

/**
 * This class implements job's listener to perform any action before and/or after the
 * execution of any batch job.
 *
 * @author Ahsan Tariq
 * @since 1.0.0
 */
public class ViewsonicBatchJobExecutionListener extends JobExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(
            ViewsonicBatchJobExecutionListener.class);

    private final IViewsonicService viewsonicService;

    public ViewsonicBatchJobExecutionListener(final IViewsonicService iViewsonicService) {
        this.viewsonicService = iViewsonicService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeJob(final JobExecution jobExecution) {
        super.beforeJob(jobExecution);
        setJobExecutionIdInExecutionContext(jobExecution);
        logger.info("{} job started!", JobConstants.VIEWSONIC_BATCH_JOB_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterJob(final JobExecution jobExecution) {
        super.afterJob(jobExecution);
        cleanRepositories(jobExecution);
        logger.info("{} job completed!", JobConstants.VIEWSONIC_BATCH_JOB_NAME);
    }

    private void setJobExecutionIdInExecutionContext(final JobExecution jobExecution) {
        final ExecutionContext executionContext = jobExecution.getExecutionContext();
        final Long jobInstanceId = jobExecution.getJobInstance().getId();
        executionContext.put(JobConstants.JOB_INSTANCE_ID_KEY, jobInstanceId);
    }

    private void cleanRepositories(final JobExecution jobExecution) {
        final Long jobInstanceId = jobExecution.getJobInstance().getId();
        this.viewsonicService.removeByJobInstanceId(jobInstanceId);
    }
}
