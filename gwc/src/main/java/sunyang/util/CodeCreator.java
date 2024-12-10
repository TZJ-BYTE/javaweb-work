package sunyang.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CodeCreator {
	public String createAccountcode(Integer userid){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String accountCode  = sdf.format(new Date()).toString();
		if (userid < 10) {
			accountCode  = accountCode  + "00" + userid.toString();
		} else if (userid < 100) {
			accountCode  = accountCode  + "0" + userid.toString();
		} else {
			accountCode  = accountCode  + userid.toString();
		}
		return accountCode ;
	}
}
