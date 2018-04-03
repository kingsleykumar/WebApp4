package com.sb.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Kingsley Kumar on 24/03/2018 at 00:26.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PreferencesCommand {

    private String dateformat;
    private String defaultBreakDownBy;
    private String defaultView;
    private String defaultTableExpandState;
    private String defaultChartType;
    private String defaultPageAfterLogin;
    private String defaultTimeRangeForTxView;
}
