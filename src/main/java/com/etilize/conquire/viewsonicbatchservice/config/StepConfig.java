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

import com.etilize.conquire.viewsonicbatchservice.constants.StepConstants;
import com.etilize.conquire.viewsonicbatchservice.export.ExportItem;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.ViewsonicProduct;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.Product;
import com.mongodb.MongoSocketReadException;
import feign.RetryableException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class houses all configuration for steps of the job.
 *
 * @author Ahsan Tariq
 * @since 1.0
 */

@Configuration
public class StepConfig {

    /**
     * This method is responsible for importing the xml data from the input file for viewsonicProduct attributes.
     *
     * @param stepBuilderFactory Instance of step builder factory.
     * @param chunkSize Size of the chunk to be processed at a time.
     * @param reader The reader of the step.
     * @param processor The processor of the step.
     * @param writer The writer for of the step.
     * @param listener The listener of the step.
     * @return import product Step instance.
     */
    @Bean
    public Step importViewsonicProductAttributes(final StepBuilderFactory stepBuilderFactory, //
                                                 @Value("${application.chunk-size}") final Integer chunkSize,
                                                 @Qualifier("xmlReaderForViewsonic") final ItemReader<ViewsonicProduct> reader, //
                                                 @Qualifier("importViewsonicAttributesProcessor") final ItemProcessor<ViewsonicProduct, ViewsonicProduct> processor, //
                                                 @Qualifier("viewsonicWriterForMongodb") final ItemWriter<ViewsonicProduct> writer,
                                                 @Qualifier("viewsonicBatchStepExecutionListener") final StepExecutionListener listener) {
        return stepBuilderFactory.get(StepConstants.IMPORT_VIEWSONIC_ATTRIBUTES_STEP_NAME) //
                .<ViewsonicProduct, ViewsonicProduct> chunk(chunkSize) //
                .reader(reader) //
                .processor(processor) //
                .writer(writer)
                .listener(listener) //
                .allowStartIfComplete(true) //
                .build();
    }

    /**
     * This method is responsible for importing the xml data from the input file for product list.
     *
     * @param stepBuilderFactory Instance of step builder factory.
     * @param chunkSize Size of the chunk to be processed at a time.
     * @param reader The reader of the step.
     * @param processor The processor of the step.
     * @param writer The writer for of the step.
     * @param listener The listener of the step.
     * @return import product Step instance.
     */
    @Bean
    public Step importViewsonicProductList(final StepBuilderFactory stepBuilderFactory, //
                                           @Value("${application.chunk-size}") final Integer chunkSize,
                                           @Qualifier("xmlReaderForViewsonicProductList") final ItemReader<Product> reader, //
                                           @Qualifier("importViewsonicProductListProcessor") final ItemProcessor<Product, Product> processor, //
                                           @Qualifier("viewsonicProductListWriterForMongodb") final MongoItemWriter<Product> writer,
                                           @Qualifier("viewsonicBatchStepExecutionListener") final StepExecutionListener listener) {
        return stepBuilderFactory.get(StepConstants.IMPORT_VIEWSONIC_PRODUCT_LIST_STEP_NAME) //
                .<Product, Product> chunk(chunkSize) //
                .reader(reader) //
                .processor(processor) //
                .writer(writer) //
                .listener(listener) //
                .allowStartIfComplete(true) //
                .build();
    }

    /**
     * This method implements and configures header generator step.
     *
     * @param stepBuilderFactory Instance of step builder factory.
     * @param chunkSize Size of the chunk to be processed at a time.
     * @param reader The reader of the step.
     * @param processor The processor of the step.
     * @param writer The writer for of the step.
     * @param listener The listener of the step.
     * @return header generator step instance.
     */
    @Bean
    public Step generateHeaderForViewsonicProductList(final StepBuilderFactory stepBuilderFactory, //
                                                      @Value("${application.chunk-size}") final Integer chunkSize,
                                                      @Qualifier("mongodbReaderForViewsonicProductList") final ItemReader<Product> reader, //
                                                      @Qualifier("generateHeaderForViewsonicProductListProcessor") final ItemProcessor<Product, Product> processor, //
                                                      @Qualifier("viewsonicProductListWriterForMongodb") final ItemWriter<Product> writer,
                                                      @Qualifier("viewsonicBatchStepExecutionListener") final StepExecutionListener listener) {
        return stepBuilderFactory.get(StepConstants.GENERATE_HEADER_FOR_VIEWSONIC_STEP_NAME) //
                .<Product, Product> chunk(chunkSize) //
                .reader(reader) //
                .processor(processor) //
                .writer(writer) //
                .faultTolerant()
                .retryLimit(StepConstants.MAX_RETRY_ATTEMPTS) //
                .retry(RetryableException.class) //
                .retry(MongoSocketReadException.class) //
                .listener(listener) //
                .allowStartIfComplete(true) //
                .build();
    }

    /**
     * This method implements and configures export product step.
     *
     * @param stepBuilderFactory Instance of step builder factory.
     * @param chunkSize Size of the chunk to be processed at a time.
     * @param reader The reader of the step.
     * @param processor The processor of the step.
     * @param writer The writer for of the step.
     * @param listener The listener of the step.
     * @return import product step instance.
     */
    @Bean
    public Step exportViewsonicProductList(final StepBuilderFactory stepBuilderFactory, //
                                           @Value("${application.chunk-size}") final Integer chunkSize,
                                           @Qualifier("mongodbReaderForViewsonicProductList") final ItemReader<Product> reader, //
                                           @Qualifier("exportViewsonicProductListProcessor") final ItemProcessor<Product, ExportItem> processor, //
                                           @Qualifier("csvWriterForExportItem") final ItemWriter<ExportItem> writer,
                                           @Qualifier("viewsonicBatchStepExecutionListener") final StepExecutionListener listener) {
        return stepBuilderFactory.get(StepConstants.EXPORT_PRODUCT_STEP_NAME) //
                .<Product, ExportItem> chunk(chunkSize) //
                .reader(reader) //
                .processor(processor) //
                .writer(writer) //
                .listener(listener) //
                .allowStartIfComplete(true) //
                .build();
    }

}
