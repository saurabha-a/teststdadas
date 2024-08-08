package com.abhinavsaurabh.commons.converter;

import com.abhinavsaurabh.commons.util.DateUtil;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;

public class JOOQDateConverter implements Converter<Timestamp, String> {

    private static final long serialVersionUID = 8619911328767907526L;

    private static final Logger logger = LoggerFactory.getLogger(JOOQDateConverter.class);

    @Override
    public Timestamp to(String date) {
        try {
            return DateUtil.getSqlTimestampAsString(date, DateUtil.UI_DATE_FORMAT);
        } catch (ParseException e) {
            logger.error("Exception while parsing date {}", date, e);
        }
        return null;
    }

    @Override
    public String from(Timestamp date) {
        return DateUtil.getDateAsString(date, DateUtil.UI_DATE_FORMAT);
    }

    @Override
    public Class<String> toType() {
        return String.class;
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    public static Field<String> getTypeConversion(String tableName, String columnName) {
        DataType<String> type = SQLDataType.TIMESTAMP.asConvertedDataType(new JOOQDateConverter());
        return DSL.field(DSL.name(tableName, columnName), type);
    }

}
