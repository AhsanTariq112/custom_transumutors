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
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.net.ConnectException;
import java.util.Collections;

/**
 * This class houses configurations related to steps which interact with different
 * services.
 *
 * @author Ahsan Tariq
 * @since 1.0.0
 */
@Configuration
public class ServiceStepConfig {

    /**
     * Retry policy.
     *
     * @param maxRetryAttempts Maximum number of retry attempts.
     *
     * @return {@link SimpleRetryPolicy} instance that retries {@link ExecutionContextConstants#MAX_RETRY_ATTEMPTS} times.
     * @see RetryPolicy
     */
    @Bean
    public RetryPolicy simpleRetryPolicy(
            @Value("${application.max-retry-attempts}") final int maxRetryAttempts) {
        return new SimpleRetryPolicy(maxRetryAttempts,
                Collections.<Class<? extends Throwable>, Boolean> singletonMap(
                        ConnectException.class, true),
                true);
    }

    /**
     * Backoff Policy.
     *
     * @param initialIntervalSleep Initial sleep interval.
     *
     * @return {@link ExponentialBackOffPolicy} instance.
     * @see BackOffPolicy
     */
    @Bean
    public BackOffPolicy exponentialBackOffPolicy(
            @Value("${application.initial-interval-sleep}") final int initialIntervalSleep) {
        final ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(initialIntervalSleep);
        return exponentialBackOffPolicy;
    }
}
