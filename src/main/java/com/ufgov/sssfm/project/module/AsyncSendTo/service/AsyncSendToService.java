package com.ufgov.sssfm.project.module.AsyncSendTo.service;

import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface AsyncSendToService {

    @Async
    String[] send_outcome_intcome_to_czsb(String msgContext, List<String> ossstrList, List<String> filePathList, String bse173);
}
