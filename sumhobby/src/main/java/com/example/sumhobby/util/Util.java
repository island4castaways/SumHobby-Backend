package com.example.sumhobby.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {
	
	public static Timestamp stringToTimestamp(String strDate) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(strDate);
			Timestamp timestamp = new Timestamp(date.getTime());
			
			return timestamp;
		} catch (Exception e) {
			log.info(e.getMessage());
			
			return null;
		}
	}
	
	public static String timestampToString(Timestamp timestamp) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(timestamp.toString());
			String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
			
			return str;
		} catch (Exception e) {
			log.info(e.getMessage());
			
			return null;
		}
	}

}
