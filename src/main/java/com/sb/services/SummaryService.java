package com.sb.services;

import com.sb.services.calculation.ResultNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kingsley Kumar on 30/03/2018 at 18:25.
 */
public interface SummaryService {

    List<ResultNode> retrieveSummary(HttpServletRequest request, HttpSession session);
}
