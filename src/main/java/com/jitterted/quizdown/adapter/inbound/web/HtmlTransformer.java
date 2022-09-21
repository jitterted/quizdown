package com.jitterted.quizdown.adapter.inbound.web;

import com.jitterted.quizdown.domain.Response;

public interface HtmlTransformer {
    String toHtml(String text, Response response);
}
