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
package com.etilize.conquire.viewsonicbatchservice.export;


import com.etilize.conquire.viewsonicbatchservice.constants.CommonConstants;
import com.etilize.conquire.viewsonicbatchservice.constants.ExportConstants;
import com.etilize.conquire.viewsonicbatchservice.job.step.util.FileUtil;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link MultipleFileItemWriter} implements {@link ItemWriter} to write files according to
 * the given path and name.
 *
 * @author Ahsan Tariq
 * @since 1.0.0
 */
public class MultipleFileItemWriter implements ItemStream, ItemWriter<ExportItem> {

    private final String fileNamePrefix;

    private final String outputFileFolder;

    private final String outputFileExtension;

    private final Map<String, FlatFileItemWriter<ExportItem>> writers;

    private ExecutionContext executionContext;

    /**
     * Creates {@link MultipleFileItemWriter} instance.
     *
     * @param fileNamePrefix Prefix for the file name.
     * @param outputFileFolder Path of output file folder.
     * @param outputFileExtension Extension for output file.
     */
    public MultipleFileItemWriter(final String fileNamePrefix,
                                  final String outputFileFolder, final String outputFileExtension) {
        this.fileNamePrefix = fileNamePrefix;
        this.outputFileFolder = (outputFileFolder.endsWith(File.separator))
                ? outputFileFolder : outputFileFolder + File.separator;
        this.outputFileExtension = (outputFileExtension.startsWith("."))
                ? outputFileExtension : "." + outputFileExtension;
        this.writers = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open(final ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final ExecutionContext executionContext) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        for (final FlatFileItemWriter<ExportItem> file : writers.values()) {
            file.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final List<? extends ExportItem> exportItems) throws Exception {

        if (null == exportItems || exportItems.isEmpty()) {
            return;
        }

        for (final ExportItem exportItem : exportItems) {
            final FlatFileItemWriter<ExportItem> ffiw = getFlatFileItemWriter(exportItem);
            ffiw.write(Arrays.asList(exportItem));
        }
    }

    /**
     * Implements multiple file item writer.
     *
     * @param exportItem {@link ExportItem} to be written in file.
     * @return {@link FlatFileItemWriter} instance.
     */
    public FlatFileItemWriter<ExportItem> getFlatFileItemWriter(
            final ExportItem exportItem) {

        final String filePathName = this.outputFileFolder //
                + CommonConstants.JOB_INSTANCE_ID_STRING //
                + String.valueOf(exportItem.getJobInstanceId()) //
                + File.separator //
                + fileNamePrefix //
                + FileUtil.escapeStringForFileName(exportItem.getFileName()) //
                + outputFileExtension;
        FlatFileItemWriter<ExportItem> writer = writers.get(filePathName);
        if (writer == null) {
            writer = new FlatFileItemWriter<>();
            writer.setAppendAllowed(true);
            writer.setLineAggregator(new DelimitedLineAggregator<ExportItem>() {

                {
                    setDelimiter(FileUtil.DELIMITER);
                    setFieldExtractor(new BeanWrapperFieldExtractor<ExportItem>() {

                        {
                            setNames(new String[] {
                                ExportConstants.EXPORT_ITEM_ATTRIBUTES_FOR_FILE });
                        }
                    });
                }

            });
            final FileSystemResource fileSystemResource = new FileSystemResource(
                    filePathName);
            writer.setResource(fileSystemResource);
            writer.open(executionContext);
            writer.setEncoding(StandardCharsets.UTF_8.name());
            writers.put(filePathName, writer);
        }
        return writer;
    }

}
