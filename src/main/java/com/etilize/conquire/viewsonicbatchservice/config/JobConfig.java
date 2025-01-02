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

import com.etilize.conquire.viewsonicbatchservice.constants.JobConstants;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class houses all configurations for batch jobs.
 *
 * @author Ahsan Tariq
 * @since 1.0
 */

@Configuration
public class JobConfig {


    /**
     * runs id incrementor for each job
     * runs the validator for job parameters
     *
     * @return Job
     */
    @Bean
    public Job viewsonicBatchJob(final JobBuilderFactory jobBuilderFactory,
                                 @Qualifier("runIdIncrementer") final JobParametersIncrementer jobParametersIncrementer, //
                                 @Qualifier("viewsonicBatchJobParametersValidator") final JobParametersValidator validator, //
                                 @Qualifier("viewsonicBatchJobExecutionListener") final JobExecutionListener listener,
                                 @Qualifier("jobMainFlow") final Flow jobMainFlow
    ) {
        return jobBuilderFactory.get(JobConstants.VIEWSONIC_BATCH_JOB_NAME) //
                .incrementer(jobParametersIncrementer) //
                .listener(listener)
                .start(jobMainFlow) //
                .end()
                .build();
    }

    /**
     * @return job main flow
     */
    @Bean
    public Flow jobMainFlow(@Qualifier("importViewsonicProductAttributes") final Step importViewsonicAttributes,
                            @Qualifier("importViewsonicProductList") final Step importViewsonicProductList,
                            @Qualifier("generateHeaderForViewsonicProductList") final Step generateHeaderForViewsonic,
                            @Qualifier("exportViewsonicProductList") final Step exportViewsonicProduct){

        return new FlowBuilder<Flow>("jobMainFlow") //
                .start(importViewsonicAttributes) //
                .next(importViewsonicProductList)
                .next(generateHeaderForViewsonic) //
                .next(exportViewsonicProduct) //
                .build();
    }

}
