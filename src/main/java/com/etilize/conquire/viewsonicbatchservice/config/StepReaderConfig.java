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

import com.etilize.conquire.viewsonicbatchservice.constants.CollectionConstants;
import com.etilize.conquire.viewsonicbatchservice.constants.CommonConstants;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.ViewsonicProduct;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.Product;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.Products;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements all configuration of step item reader for the batch job.
 *
 * @author Ahsan Tariq
 * @since 1.0
 */

@Configuration
public class StepReaderConfig {

    private static final String QUERY_FOR_JOB_INSTANCE_ID_AND_STEP_EXECUTION_ID = "{jobInstanceId: ?0, stepExecutionIds: ?1}";


    /**
     * This method is used to read the xml data from the input file for Viewsonic attributes
     * @param viewSonicFilePath fill path of the folder which contains the input file
     * @param fileName
     * @return Viewsonic instance of StaxEventItemReader
     */
    @Bean
    @StepScope
    public StaxEventItemReader<ViewsonicProduct> xmlReaderForViewsonic
            (@Value("${application.viewSonic-file-directory}") final String viewSonicFilePath,
             @Value("#{jobParameters[fileName]}") final String fileName) {
        final Jaxb2Marshaller viewSonicMarshaller = new Jaxb2Marshaller();
        final String filePathName = viewSonicFilePath + "/" + fileName + CommonConstants.XML_EXTENSION;
        viewSonicMarshaller.setClassesToBeBound(ViewsonicProduct.class);
        final StaxEventItemReader<ViewsonicProduct> reader = new StaxEventItemReader<>();
        reader.setUnmarshaller(viewSonicMarshaller);
        reader.setFragmentRootElementName("CATALOG");
        reader.setResource(new FileSystemResource(filePathName));
        return reader;
    }

    /**
     * This method is used to read the xml data from the input file for product list
     * @param viewSonicFilePath
     * @param fileName
     * @return
     */
    @Bean
    @StepScope
    public StaxEventItemReader<Product> xmlReaderForViewsonicProductList
            (@Value("${application.viewSonic-file-directory}") final String viewSonicFilePath,
             @Value("#{jobParameters[fileName]}") final String fileName) {
        final Jaxb2Marshaller viewSonicMarshaller = new Jaxb2Marshaller();
        final String filePathName = viewSonicFilePath + "/" + fileName + CommonConstants.XML_EXTENSION;
        final StaxEventItemReader<Product> reader = new StaxEventItemReader<>();
        viewSonicMarshaller.setClassesToBeBound(Product.class);
        reader.setUnmarshaller(viewSonicMarshaller);
        reader.setFragmentRootElementName("PRODUCT");
        reader.setResource(new FileSystemResource(filePathName));
        return reader;
    }

    /**
     * Mongodb reader for Viewsonic Product
     *
     * @param template Instance of {@link MongoOperations}.
     * @param feedChunkSize Chunk size of feed.
     * @param jobInstanceId Instance id of job.
     * @param stepExecutionId Execution id of step.
     * @return {@link ViewsonicProduct} instance.
     */
    @Bean
    @StepScope
    public ItemReader<Product> mongodbReaderForViewsonicProductList(final MongoOperations template,
                                                                    @Value("${application.chunk-size}") final int feedChunkSize,
                                                                    @Value("#{jobExecutionContext[jobInstanceId]}") final Long jobInstanceId, //
                                                                    @Value("#{jobExecutionContext[previousStepExecutionId]}") final Long stepExecutionId){
        final List<Object> parameterValues = new ArrayList<>();
        parameterValues.add(jobInstanceId);
        parameterValues.add(stepExecutionId);

        final Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("_id", Sort.Direction.ASC);
        final MongoItemReader<Product> reader = new MongoItemReader<>();
        reader.setTemplate(template);
        reader.setTargetType(Product.class);
        reader.setQuery(QUERY_FOR_JOB_INSTANCE_ID_AND_STEP_EXECUTION_ID);
        reader.setParameterValues(parameterValues);
        reader.setSort(sort);
        reader.setPageSize(feedChunkSize);
        reader.setCollection(CollectionConstants.VIEWSONIC_PRODUCT_LIST_COLLECTION_NAME);
        return reader;
    }



}
