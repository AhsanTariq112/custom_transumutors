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
package com.etilize.conquire.viewsonicbatchservice.viewsonic;

import com.querydsl.core.types.dsl.StringPath;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * {@link } implements {@link MongoRepository} for {@link }.
 *
 * @author Ahsan Tariq
 * @since 1.0.0
 */
public interface ViewsonicRepository extends MongoRepository<ViewsonicProduct, ObjectId>,
        QuerydslPredicateExecutor<ViewsonicProduct>, QuerydslBinderCustomizer<QViewsonicProduct> {

    @Override
    default void customize(final QuerydslBindings bindings, final QViewsonicProduct root) {
        bindings.bind(String.class).first((final StringPath path, final String value) -> {
            return path.equalsIgnoreCase(value);
        });
        }

    /**
    * Removes {@link ViewsonicProduct} by jobInstanceId.
    *
    * @param jobInstanceId Instance id of the job.
    * @return List of {@link ViewsonicProduct}s removed.
    */
    void removeByJobInstanceId(Long jobInstanceId);
}
