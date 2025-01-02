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
package com.etilize.conquire.viewsonicbatchservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * {@link JobTaskExecutorConfig} provides configuration for task execution in step.
 *
 * @author Ahsan Tariq
 * @since 1.0.0
 */
@Configuration
public class JobTaskExecutorConfig {

    /**
     * Configurations to execute jobs as separate threads. Used for executing jobs in parallel. Creates thread pool of size {@link BatchConfigurationContants#JOB_CONCURRENCY_LIMIT}. Note: this
     * bean is only available in non-test profiles.
     *
     * @param concurrencyLimit Maximum number of allowed parallel jobs.
     * @return {@link TaskExecutor} instance.
     */
    @Profile("!test")
    @Bean
    public TaskExecutor taskExecutorForJob(
            @Value("${application.max-parallel-jobs}") final Integer concurrencyLimit) {
        final SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(concurrencyLimit);
        return asyncTaskExecutor;
    }

}
