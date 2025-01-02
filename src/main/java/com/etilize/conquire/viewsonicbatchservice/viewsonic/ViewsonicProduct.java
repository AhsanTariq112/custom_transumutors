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

import com.etilize.conquire.viewsonicbatchservice.base.AbstractMongoEntity;
import com.etilize.conquire.viewsonicbatchservice.constants.CollectionConstants;
import com.etilize.conquire.viewsonicbatchservice.constants.XmlDataConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * This class implements pojo for root element of Viewsonic i.e. Feed
 * @author Asad Qureshi
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement(name = XmlDataConstants.CATALOG)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode
@Document(collection = CollectionConstants.VIEWSONIC_FEED_COLLECTION_NAME)

public class ViewsonicProduct extends AbstractMongoEntity {


    private Set<Long> jobInstanceId = new HashSet<>();

    private Set<Long> stepExecutionIds = new HashSet<>();

    public void addJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId.add(jobInstanceId);
    }

    public void addStepExecutionId(Long stepExecutionId) {
        this.stepExecutionIds.add(stepExecutionId);
    }



}
