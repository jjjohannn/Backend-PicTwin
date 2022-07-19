package cl.ucn.disc.dsm.pictwin.backend.web;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Slf4j
public final class Utils {
    public static void printObject(@NonNull String objectName, Object o){

        if (o != null) {
            log.debug("{}:{}", objectName, ToStringBuilder.reflectionToString(o, RecursiveToStringStyle.MULTI_LINE_STYLE));
        }else {
            log.debug("{}:{}", objectName, null);
        }
        }




}

