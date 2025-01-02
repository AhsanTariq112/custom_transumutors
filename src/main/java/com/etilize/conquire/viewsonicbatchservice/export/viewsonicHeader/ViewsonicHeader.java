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
package com.etilize.conquire.viewsonicbatchservice.export.viewsonicHeader;

import com.etilize.conquire.viewsonicbatchservice.constants.CollectionConstants;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Stores header for viewsonic products.
 *
 * @author Ahsan Tariq
 * @since 1.0
 */
@Document(collection = CollectionConstants.VIEWSONIC_PRODUCT_HEADER_COLLECTION_NAME)
public class ViewsonicHeader {
    @Id
    private Long jobInstanceId;

    private Set<String> headers;

    private Boolean isHeaderSet;

    public ViewsonicHeader(final Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
        this.headers = new LinkedHashSet<>();
        this.isHeaderSet = false;
    }


    public Long getJobInstanceId() {
        return jobInstanceId;
    }


    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }


    public Set<String> getHeaders() {
        return headers;
    }


    public void setHeaders(Set<String> headers) {
        this.headers = headers;
    }


    public Boolean getIsHeaderSet() {
        return isHeaderSet;
    }


    public void setIsHeaderSet(Boolean isHeaderSet) {
        this.isHeaderSet = isHeaderSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof ViewsonicHeader)) {
            return false;
        }
        final ViewsonicHeader rhs = (ViewsonicHeader) object;
        return new EqualsBuilder() //
                .append(getJobInstanceId(), rhs.getJobInstanceId()) //
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder() //
                .append(getJobInstanceId()) //
                .hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this) //
                .append("jobInstanceId", getJobInstanceId()) //
                .append("headers", getHeaders()) //
                .append("isHeaderSet", getIsHeaderSet()) //
                .toString();
    }
}
