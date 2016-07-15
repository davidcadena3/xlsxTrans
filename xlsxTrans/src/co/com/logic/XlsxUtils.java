package co.com.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import co.com.utils.IDataFileReader;
import co.com.utils.XExcelFileReader;

public class XlsxUtils {

	public static List<String> xlsxTextTransform(String xlsxFullFilePath, String baseMessage) {
		long start = System.currentTimeMillis();
		List<String> response = new ArrayList<>();
		System.out.println("max rows supported: 1.000.000");
		try {
			IDataFileReader reader = new XExcelFileReader(xlsxFullFilePath);
			List<String[]> list = reader.readRows(1000000);
			System.out.println("file rows: " + reader.rowNum());
			Map<Integer, String> keys = new HashMap<>();
			// List<KeyVO> keys = new ArrayList<>();
			Boolean isHeader = true;

			for (String[] row : list) {
				String auxMessage = baseMessage;
				for (int i = 0; i < row.length; i++) {
					String column = row[i];
					if (isHeader) {
						column = "#" + column.toUpperCase() + "#";
						// System.out.println("header: " + column);
						if (baseMessage.contains(column)) {
							keys.put(i, column);
						}
					} else {
						if (keys.containsKey(i)) {
							auxMessage = auxMessage.replaceAll(keys.get(i), column);
						}
					}
				}
				if (!isHeader) {
					response.add(auxMessage);
				}
				isHeader = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = null;
		}
		long elapsed = System.currentTimeMillis() - start;
		System.out.println(
				"End Time:" + TimeUnit.SECONDS.convert(elapsed, TimeUnit.MILLISECONDS) + "s, en millis:" + elapsed);
		return response;
	}

}
