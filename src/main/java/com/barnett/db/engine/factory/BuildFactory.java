package com.barnett.db.engine.factory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.barnett.db.base.exception.InnerException;
import com.barnett.db.engine.inner.Pair;
import com.barnett.db.engine.inner.sql.DicoSql;
import com.barnett.db.engine.parser.Parser;
import com.barnett.db.engine.route.Router;
import com.barnett.db.engine.route.TableRulerBean;
import com.barnett.db.util.ArrUtils;
import com.barnett.db.util.PatternMatcherUnit;

public class BuildFactory {

    public static void build(DicoSql currSql, Map<?, ?> params) 
            throws InnerException {
        //loading ruler.
        loadFromConfig(currSql);
        //calcu ruler for where data goes.
        TableRulerBean ruler = currSql.getRuler();
        String dbId = Parser.parse(ruler, params);
        //create connection && prepared sql param.
        new ConnectFactory(currSql).makeConnect(dbId).preparedStmt(params);
        
    }
    
    /**
     * decorate sql.
     * @param currSql
     */
    private static void loadFromConfig(DicoSql currSql) {
        currSql.setTableName(calcuTabnameFromSqlStr(currSql.getSql()));
        TableRulerBean tabRuler = Router.config(currSql.getTableName());
        currSql.setRuler(tabRuler);
        currSql.setPartionKey(new Pair(tabRuler.getColumn(), null));
    }

    /**
     * search for tab name.
     * @param sql
     * @return
     */
    private static String calcuTabnameFromSqlStr(String sql) {
        String[] res = PatternMatcherUnit.split(sql,"\\s+");
        boolean tag = false;
        for(String sub : res){
            if(ArrUtils.inArray(sub.trim(), "into","delete","update","from")){
                tag = true;
                continue;
            }
            if(tag){
                if(!StringUtils.isEmpty(sub.trim())){
                    return sub;
                }
            }
        }
        
        return null;
    }

    public static void main(String[] args) {
        Pattern p = Pattern.compile("\\#\\w+\\#");
        Matcher m = p.matcher("sfssf   #aiW_# sdf  #sd#");
        while(m.find()) { 
            System.out.println(m.group()); 
       }
        
    }
    
    
}
