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
import com.etilize.conquire.viewsonicbatchservice.export.MultipleFileItemWriter;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.ViewsonicProduct;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.Product;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * This class implements all configuration related to step item writers for the batch job.

 * @author Ahsan Tariq
 * @since 1.0
 */

@Configuration
public class StepWriterConfig {

    /**
     * CSV file writer
     *
     * @param outputFilePath Path and name of file
     * @return {@link MultipleFileItemWriter} instance.
     */
    @Bean
    @StepScope
    public MultipleFileItemWriter csvWriterForExportItem(
            @Value("${application.output-file-path}") final String outputFilePath) {
        final String fileNamePrefix = "";
        final String outputFileExtension = "csv";
        final MultipleFileItemWriter multipleFileItemWriter = new MultipleFileItemWriter(
                fileNamePrefix, outputFilePath, outputFileExtension);
        return multipleFileItemWriter;
    }

    /**
     * Writes the viewsonic object to mongodb
     *
     * @param template
     * @return Viewsonic instance of MongoItemWriter
     */
    @Bean
    @StepScope
    public MongoItemWriter<ViewsonicProduct> viewsonicWriterForMongodb(final MongoOperations template) {
        final MongoItemWriter<ViewsonicProduct> writer = new MongoItemWriter<>();
        writer.setTemplate(template);
        writer.setCollection(CollectionConstants.VIEWSONIC_FEED_COLLECTION_NAME);
        return writer;
    }

    /**
     * Writes the product object to mongodb
     *
     * @param template
     * @return Viewsonic instance of MongoItemWriter
     */
    @Bean
    @StepScope
    public MongoItemWriter<Product> viewsonicProductListWriterForMongodb(final MongoOperations template) {
        final MongoItemWriter<Product> writer = new MongoItemWriter<>();
        writer.setTemplate(template);
        writer.setCollection(CollectionConstants.VIEWSONIC_PRODUCT_LIST_COLLECTION_NAME);
        return writer;
    }
}
