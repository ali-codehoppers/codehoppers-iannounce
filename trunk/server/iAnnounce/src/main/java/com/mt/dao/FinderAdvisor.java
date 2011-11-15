package com.mt.dao;

import org.springframework.aop.support.DefaultIntroductionAdvisor;

public class FinderAdvisor extends DefaultIntroductionAdvisor {

    public FinderAdvisor() {
        super(new FinderInterceptor());
    }
}
