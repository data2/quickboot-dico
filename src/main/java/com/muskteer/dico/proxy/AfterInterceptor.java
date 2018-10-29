package com.muskteer.dico.proxy;

import com.muskteer.dico.inner.JdbcQuickDicoService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AfterInterceptor implements CutPointInterceptor {

    @Override
    public void execute(Object obj) {
        JdbcQuickDicoService col = (JdbcQuickDicoService) obj;
        System.out.println("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]"
                + "{execute sql end.}{sql content:" + col.getCurrSql().getSql() + "}");
    }

}
