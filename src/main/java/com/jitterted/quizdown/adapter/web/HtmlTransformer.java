package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Response;

public interface HtmlTransformer {
    String toHtml(String text, Response response);
}
