package com.cloudrain.dercho.foundation.generictypes;

import java.util.Date;

/**
 * Created by lugan on 4/28/2016.
 */
public class DateInterval extends Pair<Date>{

    @Override
    public void setSecond(Date second) {
        if(second.compareTo(getFirst()) >= 0) {
            super.setSecond(second);
        }

    }
}
