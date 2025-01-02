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

import com.etilize.conquire.viewsonicbatchservice.constants.JobParameterConstants;
import com.etilize.conquire.viewsonicbatchservice.job.parameter.JobParameterType;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements jobParameterValidator to perform any validation of job parameters
 * @author Ahsan Tariq
 * @since 1.0.0
 */
public class ViewsonicBatchJobParametersValidator implements JobParametersValidator {

    private final List<String> errors = new ArrayList<String>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final JobParameters parameters) throws JobParametersInvalidException {
        checkWhetherJobParameterExists(JobParameterType.LONG, parameters, JobParameterConstants.JOB_PARAMETER_TIME_KEY);
        checkWhetherJobParameterExists(JobParameterType.STRING, parameters, JobParameterConstants.JOB_PARAMETER_USERNAME_KEY);
        checkWhetherJobParameterExists(JobParameterType.STRING, parameters, JobParameterConstants.JOB_PARAMETER_FILE_NAME_KEY);
    }

    private void checkWhetherJobParameterExists(final JobParameterType jobParameterType,
                                                final JobParameters parameters, //
                                                final String jobParameterKey) {
        if (jobParameterType.equals(JobParameterType.STRING)
                && parameters.getString(jobParameterKey) == null) {
            this.errors.add(String //
                    .format("Job parameter[%s] is not provided.", //
                            jobParameterKey));
        } else if (jobParameterType.equals(JobParameterType.LONG)
                && parameters.getLong(jobParameterKey) == null) {
            this.errors.add(String //
                    .format("Job parameter[%s] is not provided.", //
                            jobParameterKey));
        }
    }
}
