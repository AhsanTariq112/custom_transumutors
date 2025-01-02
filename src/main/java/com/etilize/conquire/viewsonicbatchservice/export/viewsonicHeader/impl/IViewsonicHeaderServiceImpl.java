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
package com.etilize.conquire.viewsonicbatchservice.export.viewsonicHeader.impl;

import com.etilize.conquire.viewsonicbatchservice.constants.CommonConstants;
import com.etilize.conquire.viewsonicbatchservice.export.viewsonicHeader.ViewsonicHeader;
import com.etilize.conquire.viewsonicbatchservice.export.viewsonicHeader.IViewsonicHeaderService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This class implements service for {@link IViewsonicHeaderService}.
 *
 * @author Ahsan Tariq
 * @since 1.0
 */

@Service
public class IViewsonicHeaderServiceImpl implements IViewsonicHeaderService {
    private static final Map<Long, ViewsonicHeader> VIEWSONIC_PRODUCT_HEADER_MAP = new ConcurrentHashMap<>();

    public Boolean isHeaderSetForJobInstanceId(final Long jobInstanceId) {
        Assert.notNull(jobInstanceId, CommonConstants.REQUIRED_JOB_INSTANCE_ID_STRING);
        final ViewsonicHeader viewSonicHeader = getViewSonicProductHeaderForJobInstanceId(jobInstanceId);
        return viewSonicHeader.getIsHeaderSet();
    }

    public void setHeaderForJobInstanceId(final Long jobInstanceId) {
        Assert.notNull(jobInstanceId, CommonConstants.REQUIRED_JOB_INSTANCE_ID_STRING);
        final ViewsonicHeader viewSonicHeader = getViewSonicProductHeaderForJobInstanceId(jobInstanceId);
        viewSonicHeader.setIsHeaderSet(true);
        saveViewSonicroductHeader(viewSonicHeader);
    }

    public void addHeaderForJobInstanceId(final Long jobInstanceId, final Set<String> header) {
        Assert.notNull(jobInstanceId, CommonConstants.REQUIRED_JOB_INSTANCE_ID_STRING);
        Assert.notEmpty(header, CommonConstants.REQUIRED_HEADER_STRING);
        final ViewsonicHeader viewSonicHeader = getViewSonicProductHeaderForJobInstanceId(jobInstanceId);
        viewSonicHeader.getHeaders().addAll(header);
        saveViewSonicroductHeader(viewSonicHeader);
    }

    public ViewsonicHeader getViewSonicProductHeaderForJobInstanceId(final Long jobInstanceId) {
        Assert.notNull(jobInstanceId, CommonConstants.REQUIRED_JOB_INSTANCE_ID_STRING);
        ViewsonicHeader viewSonicHeader = new ViewsonicHeader(jobInstanceId);;
        if(VIEWSONIC_PRODUCT_HEADER_MAP.containsKey(jobInstanceId)) {
            viewSonicHeader = VIEWSONIC_PRODUCT_HEADER_MAP.get(jobInstanceId);
        }
        return viewSonicHeader;
    }

    /**
     * Removes {@link ViewsonicHeader} by jobInstanceId.
     *
     * @param jobInstanceId Instance id of the job.
     * @return List of {@link ViewsonicHeader}s removed.
     */
    public void removeByJobInstanceId(final Long jobInstanceId) {
        VIEWSONIC_PRODUCT_HEADER_MAP.remove(jobInstanceId);
    }

    private void saveViewSonicroductHeader(final ViewsonicHeader viewSonicHeader) {
        Set<String> headers = viewSonicHeader.getHeaders();
        // Convert to list for manipulation
        List<String> headersList = new ArrayList<>(headers);
        // Extract image-related headers
        List<String> imageHeaders;
        imageHeaders = headersList.stream()
                .filter(header -> header.contains("IMAGE_ORDER") || header.contains("IMAGE_FILE_NAME"))
                .collect(Collectors.toList());
        headersList.removeAll(imageHeaders);
        headersList.addAll(imageHeaders);
        viewSonicHeader.setHeaders(new LinkedHashSet<>(headersList));
        VIEWSONIC_PRODUCT_HEADER_MAP.put(viewSonicHeader.getJobInstanceId(), viewSonicHeader);
    }
    }

