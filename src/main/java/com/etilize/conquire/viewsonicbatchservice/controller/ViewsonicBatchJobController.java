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
package com.etilize.conquire.viewsonicbatchservice.controller;

import com.etilize.conquire.viewsonicbatchservice.constants.CommonConstants;
import com.etilize.conquire.viewsonicbatchservice.constants.JobParameterConstants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.etilize.conquire.viewsonicbatchservice.constants.CommonConstants.SIMPLE_BATCH_JOB_NOT_NULL_MESSAGE;
import static com.etilize.conquire.viewsonicbatchservice.constants.CommonConstants.VIEWSONIC_BATCH_JOB_NOT_NULL_MESSAGE;


/**
 * This class implements an end point to start the ODF job.
 * 
 * @author Ahsan Tariq
 * @since 1.0
 */
@RestController
@RequestMapping(CommonConstants.API_VIEWSONIC_BATCH_JOB)
public class ViewsonicBatchJobController {

    private final SimpleJobLauncher simpleJobLauncher;

    private final Job viewsonicBatchJob;



    /**
     * Creates instance of job controller.
     * @param simpleJobLauncher Instance of simple job launcher.
     * @param viewsonicBatchJob Instance of job to start execution of viewsonic
     */
    @Autowired
    public ViewsonicBatchJobController(final SimpleJobLauncher simpleJobLauncher, final Job viewsonicBatchJob) {
        Assert.notNull(simpleJobLauncher, SIMPLE_BATCH_JOB_NOT_NULL_MESSAGE);
        Assert.notNull(viewsonicBatchJob, VIEWSONIC_BATCH_JOB_NOT_NULL_MESSAGE);
        this.simpleJobLauncher = simpleJobLauncher;
        this.viewsonicBatchJob = viewsonicBatchJob;
    }
    
    /**
     * This methods starts the import viewsonic product job.
     * @param username username of the user
     * @param fileName name of the input file
     * @return Response entity with Ok status.
     * @throws Exception that may occur in starting the job.
     */
    @PostMapping(CommonConstants.JOB_ENDPOINT_EXECUTE)
    public ResponseEntity execute(@RequestParam("username") final String username,
                                  @RequestParam("file-name") final String fileName) throws Exception {
        final JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addLong(JobParameterConstants.JOB_PARAMETER_TIME_KEY, //
                System.currentTimeMillis());
        jobParametersBuilder.addString(JobParameterConstants.JOB_PARAMETER_USERNAME_KEY, username);
        jobParametersBuilder.addString(JobParameterConstants.JOB_PARAMETER_FILE_NAME_KEY, fileName);
        final JobExecution jobExecution = this.simpleJobLauncher.run(viewsonicBatchJob, jobParametersBuilder.toJobParameters());
        return new ResponseEntity(jobExecution, HttpStatus.OK);
    }

}
