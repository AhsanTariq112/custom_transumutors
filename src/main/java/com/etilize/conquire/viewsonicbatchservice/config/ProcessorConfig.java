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

import com.etilize.conquire.viewsonicbatchservice.constants.CommonConstants;
import com.etilize.conquire.viewsonicbatchservice.export.ExportItem;
import com.etilize.conquire.viewsonicbatchservice.export.ViewsonicProductMapService;
import com.etilize.conquire.viewsonicbatchservice.export.viewsonicHeader.IViewsonicAttributesService;
import com.etilize.conquire.viewsonicbatchservice.export.viewsonicHeader.IViewsonicHeaderService;
import com.etilize.conquire.viewsonicbatchservice.job.step.util.FileUtil;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.ViewsonicProduct;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class implements all configuration for step item processors of the batch job.
 *
 * @author Ahsan Tariq
 * @see 1.0
 */
@Configuration
public class ProcessorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorConfig.class);

    /**
     * Attach jobInstanceId and stepExecutionId while importing the viewsonicProduct attributes
     * Save attributes
     *
     * @param jobInstanceId
     * @param stepExecutionId
     * @return ViewsonicProduct
     */
    @Bean
    @StepScope
    public ItemProcessor<ViewsonicProduct, ViewsonicProduct> importViewsonicAttributesProcessor(@Value("#{jobExecutionContext[jobInstanceId]}") final Long jobInstanceId, //
                                                                                                @Value("#{jobExecutionContext[currentStepExecutionId]}") final Long stepExecutionId,
                                                                                                final IViewsonicAttributesService viewsonicAttributesService) {
        return viewsonicProduct -> {
            viewsonicProduct.addJobInstanceId(jobInstanceId);
            viewsonicProduct.addStepExecutionId(stepExecutionId);


            return viewsonicProduct;
        };

    }


    /**
     * Attach jobInstanceId and stepExecutionId along with viewsonicProduct attributes while importing the product list
     *
     * @param jobInstanceId
     * @param stepExecutionId
     * @param viewsonicAttributesService
     * @return
     */
    @Bean
    @StepScope
    public ItemProcessor<Product, Product> importViewsonicProductListProcessor(@Value("#{jobExecutionContext[jobInstanceId]}") final Long jobInstanceId, //
                                                                               @Value("#{jobExecutionContext[currentStepExecutionId]}") final Long stepExecutionId, //
                                                                               final IViewsonicAttributesService viewsonicAttributesService) {

        return product -> {
            if (null != product) {
                product.addJobInstanceId(jobInstanceId);
                product.addStepExecutionId(stepExecutionId);
                logger.trace("Viewsonic file processing, product data is = {}", //
                        product);
                return product;
            }

            return null;
        };
    }

    /**
     * Generate headers for product list
     *
     * @param jobInstanceId
     * @param stepExecutionId
     * @param viewsonicHeaderService
     * @return
     */
    @Bean
    @StepScope
    public ItemProcessor<Product, Product> generateHeaderForViewsonicProductListProcessor(@Value("#{jobExecutionContext[jobInstanceId]}") final Long jobInstanceId, //
                                                                                          @Value("#{jobExecutionContext[currentStepExecutionId]}") final Long stepExecutionId,
                                                                                          final IViewsonicHeaderService viewsonicHeaderService) {
        return viewsonicProduct -> {
            final ViewsonicProductMapService viewsonicProductMapService = new ViewsonicProductMapService();
            final Map<String, String> viewsonicProductMap = viewsonicProductMapService.convertToMap(viewsonicProduct);
            final Set<String> headers = new LinkedHashSet<>();
            for (final String key : viewsonicProductMap.keySet()) {
                headers.add(key);
            }
            if(viewsonicProductMap.size() == 0) {
                return viewsonicProduct;
            } else {
                viewsonicHeaderService.addHeaderForJobInstanceId(jobInstanceId, headers);
                viewsonicProduct.addJobInstanceId(jobInstanceId);
                viewsonicProduct.addStepExecutionId(stepExecutionId);
                return viewsonicProduct;
            }
        };
    }

    /**
     * @return export product processor
     */
    @Bean
    @StepScope
    public ItemProcessor<Product, ExportItem> exportViewsonicProductListProcessor(@Value("#{jobExecutionContext[jobInstanceId]}") final Long jobInstanceId, //
                                                                                  @Value("#{jobExecutionContext[currentStepExecutionId]}") final Long stepExecutionId,
                                                                                  final IViewsonicHeaderService viewsonicHeaderService) {
        return new ItemProcessor<Product, ExportItem>() {
            @Override
            public ExportItem process(Product product) throws Exception {
                final ViewsonicProductMapService viewsonicProductMapService = new ViewsonicProductMapService();
                // Get Product Map
                final Map<String, String> productMap = viewsonicProductMapService.convertToMap(product);
                // Get Product Columns Name
                final Set<String> headers = viewsonicHeaderService.getViewSonicProductHeaderForJobInstanceId(jobInstanceId).getHeaders();
                final StringBuilder sb = new StringBuilder();
                // Set File Header
                if (!viewsonicHeaderService.isHeaderSetForJobInstanceId(jobInstanceId)) {
                    sb.append(String.join(FileUtil.DELIMITER, headers));
                    sb.append(FileUtil.NEW_LINE);
                    viewsonicHeaderService.setHeaderForJobInstanceId(jobInstanceId);
                }
                // Set File Row
                for (final String header : headers) {
                    sb.append(FileUtil.filterDataForCsv(productMap.get(header)));
                }
                final ExportItem exportItem = new ExportItem();
                exportItem.setLine(sb.toString());
                exportItem.setJobInstanceId(jobInstanceId);
                exportItem.setFileName(CommonConstants.OUTPUT_FILE_NAME);
                return exportItem;
            }
        };
    }
}
