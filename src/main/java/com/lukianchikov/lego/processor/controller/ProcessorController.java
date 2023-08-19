package com.lukianchikov.lego.processor.controller;

import com.lukianchikov.lego.processor.batch.writable.PriceTargetPathMaker;
import com.lukianchikov.lego.processor.batch.writable.ProductTargetPathMaker;
import com.lukianchikov.lego.processor.dto.JobNotSubmitted;
import com.lukianchikov.lego.processor.dto.JobSubmitted;
import com.lukianchikov.lego.processor.dto.ProcessorResponse;
import com.lukianchikov.lego.processor.util.FSUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = {"/api/v1"},
        produces = {"application/json"}
)
public class ProcessorController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("processProductJob")
    Job processProductJob;

    @Autowired
    @Qualifier("processPriceJob")
    Job processPriceJob;

    @Autowired
    PriceTargetPathMaker priceTargetPathMaker;

    @Autowired
    ProductTargetPathMaker productTargetPathMaker;

    /**
     * Submit products data file for processing
     *
     * @param filepath local filesystem path to data file to process
     * @return either 202 Accepted if processing Batch job was created or Bad request in case of invalid file.
     * @throws Exception various org.springframework.batch.core.repository exceptions, i.e. JobAlreadyRunning
     */
    @PostMapping("/products")
    public ResponseEntity<ProcessorResponse> processProducts(@RequestParam("filepath") String filepath) throws Exception {
        if (isValidFile(filepath)) {
            JobParameters jobParameters = new JobParametersBuilder().addString("productsFilePath", filepath).toJobParameters();
            final JobExecution jobExecution = jobLauncher.run(processProductJob, jobParameters);
            JobSubmitted response = new JobSubmitted();
            response.setJobId(String.valueOf(jobExecution.getJobId()));
            response.setJobName(jobExecution.getJobInstance().getJobName());
            response.setMessage(String.format("Products processing job was submitted. If successful, check output at: %s/ .",
                    productTargetPathMaker.getTargetContainer()));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            JobNotSubmitted response = new JobNotSubmitted();
            response.setMessage(String.format("Provided file %s is not valid. Check if it exists.", filepath));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Submit prices data file for processing
     *
     * @param filepath local filesystem path to data file to process
     * @return either 202 Accepted if processing Batch job was created or Bad request in case of invalid file.
     * @throws Exception various org.springframework.batch.core.repository exceptions, i.e. JobAlreadyRunning
     */
    @PostMapping("/prices")
    public ResponseEntity<ProcessorResponse> processPrices(@RequestParam("filepath") String filepath) throws Exception {
        if (isValidFile(filepath)) {
            JobParameters jobParameters = new JobParametersBuilder().addString("pricesFilePath", filepath).toJobParameters();
            final JobExecution jobExecution = jobLauncher.run(processPriceJob, jobParameters);

            JobSubmitted response = new JobSubmitted();
            response.setJobId(String.valueOf(jobExecution.getJobId()));
            response.setJobName(jobExecution.getJobInstance().getJobName());
            response.setMessage(String.format("Prices processing job was submitted. If successful, check output at: %s/ .",
                    priceTargetPathMaker.getTargetContainer()));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            JobNotSubmitted response = new JobNotSubmitted();
            response.setMessage(String.format("Provided file %s is not valid. Check if it exists.", filepath));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private static boolean isValidFile(String path) {
        return FSUtil.pathExists(path) && FSUtil.isFile(path);
    }
}
