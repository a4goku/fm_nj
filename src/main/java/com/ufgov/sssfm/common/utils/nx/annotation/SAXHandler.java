package com.ufgov.sssfm.common.utils.nx.annotation;


import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface SAXHandler {

    String model();

}
